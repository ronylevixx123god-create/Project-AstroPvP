/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.TxIsolation;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.transaction.ExternalJdbcTransaction;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.server.transaction.JdbcTransaction;
import com.avaje.ebeaninternal.server.transaction.PostCommitProcessing;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.transaction.TransactionLogManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    private final LuceneIndexManager luceneIndexManager;
    private final BeanDescriptorManager beanDescriptorManager;
    private LogLevel logLevel;
    private final TransactionLogManager transLogger;
    private final String prefix;
    private final String externalTransPrefix;
    private final DataSource dataSource;
    private final OnQueryOnly onQueryOnly;
    private final boolean defaultBatchMode;
    private final BackgroundExecutor backgroundExecutor;
    private final ClusterManager clusterManager;
    private final int commitDebugLevel;
    private final String serverName;
    private AtomicLong transactionCounter = new AtomicLong(1000L);
    private int clusterDebugLevel;

    public TransactionManager(ClusterManager clusterManager, LuceneIndexManager luceneIndexManager, BackgroundExecutor backgroundExecutor, ServerConfig config, BeanDescriptorManager descMgr) {
        this.beanDescriptorManager = descMgr;
        this.clusterManager = clusterManager;
        this.luceneIndexManager = luceneIndexManager;
        this.serverName = config.getName();
        this.logLevel = config.getLoggingLevel();
        this.transLogger = new TransactionLogManager(config);
        this.backgroundExecutor = backgroundExecutor;
        this.dataSource = config.getDataSource();
        this.commitDebugLevel = GlobalProperties.getInt("ebean.commit.debuglevel", 0);
        this.clusterDebugLevel = GlobalProperties.getInt("ebean.cluster.debuglevel", 0);
        this.defaultBatchMode = config.isPersistBatching();
        this.prefix = GlobalProperties.get("transaction.prefix", "");
        this.externalTransPrefix = GlobalProperties.get("transaction.prefix", "e");
        String value = GlobalProperties.get("transaction.onqueryonly", "ROLLBACK").toUpperCase().trim();
        this.onQueryOnly = this.getOnQueryOnly(value, this.dataSource);
    }

    public void shutdown() {
        this.transLogger.shutdown();
    }

    public BeanDescriptorManager getBeanDescriptorManager() {
        return this.beanDescriptorManager;
    }

    public LogLevel getTransactionLogLevel() {
        return this.logLevel;
    }

    public void setTransactionLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    private OnQueryOnly getOnQueryOnly(String onQueryOnly, DataSource ds) {
        if (onQueryOnly.equals("COMMIT")) {
            return OnQueryOnly.COMMIT;
        }
        if (onQueryOnly.startsWith("CLOSE")) {
            if (!this.isReadCommitedIsolation(ds)) {
                String m = "transaction.queryonlyclose is true but the transaction Isolation Level is not READ_COMMITTED";
                throw new PersistenceException(m);
            }
            return OnQueryOnly.CLOSE_ON_READCOMMITTED;
        }
        return OnQueryOnly.ROLLBACK;
    }

    private boolean isReadCommitedIsolation(DataSource ds) {
        Connection c = null;
        try {
            c = ds.getConnection();
            int isolationLevel = c.getTransactionIsolation();
            boolean bl = isolationLevel == 2;
            return bl;
        }
        catch (SQLException ex) {
            String m = "Errored trying to determine the default Isolation Level";
            throw new PersistenceException(m, ex);
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException ex) {
                logger.log(Level.SEVERE, "closing connection", ex);
            }
        }
    }

    public String getServerName() {
        return this.serverName;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public int getClusterDebugLevel() {
        return this.clusterDebugLevel;
    }

    public void setClusterDebugLevel(int clusterDebugLevel) {
        this.clusterDebugLevel = clusterDebugLevel;
    }

    public OnQueryOnly getOnQueryOnly() {
        return this.onQueryOnly;
    }

    public TransactionLogManager getLogger() {
        return this.transLogger;
    }

    public void log(TransactionLogBuffer logBuffer) {
        if (!logBuffer.isEmpty()) {
            this.transLogger.log(logBuffer);
        }
    }

    public SpiTransaction wrapExternalConnection(Connection c) {
        return this.wrapExternalConnection(this.externalTransPrefix + c.hashCode(), c);
    }

    public SpiTransaction wrapExternalConnection(String id, Connection c) {
        ExternalJdbcTransaction t = new ExternalJdbcTransaction(id, true, this.logLevel, c, this);
        if (this.defaultBatchMode) {
            t.setBatchMode(true);
        }
        return t;
    }

    public SpiTransaction createTransaction(boolean explicit, int isolationLevel) {
        try {
            long id = this.transactionCounter.incrementAndGet();
            Connection c = this.dataSource.getConnection();
            JdbcTransaction t = new JdbcTransaction(this.prefix + id, explicit, this.logLevel, c, this);
            if (this.defaultBatchMode) {
                t.setBatchMode(true);
            }
            if (isolationLevel > -1) {
                c.setTransactionIsolation(isolationLevel);
            }
            if (this.commitDebugLevel >= 3) {
                String msg = "Transaction [" + t.getId() + "] begin";
                if (isolationLevel > -1) {
                    TxIsolation txi = TxIsolation.fromLevel(isolationLevel);
                    msg = msg + " isolationLevel[" + (Object)((Object)txi) + "]";
                }
                logger.info(msg);
            }
            return t;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    public SpiTransaction createQueryTransaction() {
        try {
            long id = this.transactionCounter.incrementAndGet();
            Connection c = this.dataSource.getConnection();
            JdbcTransaction t = new JdbcTransaction(this.prefix + id, false, this.logLevel, c, this);
            if (this.defaultBatchMode) {
                t.setBatchMode(true);
            }
            if (this.commitDebugLevel >= 3) {
                logger.info("Transaction [" + t.getId() + "] begin - queryOnly");
            }
            return t;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }

    public void notifyOfRollback(SpiTransaction transaction, Throwable cause) {
        try {
            if (transaction.isLogSummary() || this.commitDebugLevel >= 1) {
                String msg = "Rollback";
                if (cause != null) {
                    msg = msg + " error: " + this.formatThrowable(cause);
                }
                if (transaction.isLogSummary()) {
                    transaction.logInternal(msg);
                }
                if (this.commitDebugLevel >= 1) {
                    logger.info("Transaction [" + transaction.getId() + "] " + msg);
                }
            }
            this.log(transaction.getLogBuffer());
        }
        catch (Exception ex) {
            String m = "Potentially Transaction Log incomplete due to error:";
            logger.log(Level.SEVERE, m, ex);
        }
    }

    public void notifyOfQueryOnly(boolean onCommit, SpiTransaction transaction, Throwable cause) {
        try {
            if (this.commitDebugLevel >= 2) {
                String msg;
                if (onCommit) {
                    msg = "Commit queryOnly";
                } else {
                    msg = "Rollback queryOnly";
                    if (cause != null) {
                        msg = msg + " error: " + this.formatThrowable(cause);
                    }
                }
                if (transaction.isLogSummary()) {
                    transaction.logInternal(msg);
                }
                logger.info("Transaction [" + transaction.getId() + "] " + msg);
            }
            this.log(transaction.getLogBuffer());
        }
        catch (Exception ex) {
            String m = "Potentially Transaction Log incomplete due to error:";
            logger.log(Level.SEVERE, m, ex);
        }
    }

    private String formatThrowable(Throwable e) {
        if (e == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        this.formatThrowable(e, sb);
        return sb.toString();
    }

    private void formatThrowable(Throwable e, StringBuilder sb) {
        Throwable cause;
        sb.append(e.toString());
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            sb.append(" stack0: ");
            sb.append(stackTrace[0]);
        }
        if ((cause = e.getCause()) != null) {
            sb.append(" cause: ");
            this.formatThrowable(cause, sb);
        }
    }

    public void notifyOfCommit(SpiTransaction transaction) {
        try {
            this.log(transaction.getLogBuffer());
            PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, transaction, transaction.getEvent());
            postCommit.notifyLocalCacheIndex();
            postCommit.notifyCluster();
            this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
            if (this.commitDebugLevel >= 1) {
                logger.info("Transaction [" + transaction.getId() + "] commit");
            }
        }
        catch (Exception ex) {
            String m = "NotifyOfCommit failed. Cache/Lucene potentially not notified.";
            logger.log(Level.SEVERE, m, ex);
        }
    }

    public void externalModification(TransactionEventTable tableEvents) {
        TransactionEvent event = new TransactionEvent();
        event.add(tableEvents);
        PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, null, event);
        postCommit.notifyLocalCacheIndex();
        this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
    }

    public void remoteTransactionEvent(RemoteTransactionEvent remoteEvent) {
        List<IndexEvent> indexEventList;
        List<BeanPersistIds> beanPersistList;
        if (this.clusterDebugLevel > 0 || logger.isLoggable(Level.FINE)) {
            logger.info("Cluster Received: " + remoteEvent.toString());
        }
        this.luceneIndexManager.processEvent(remoteEvent, null);
        List<TransactionEventTable.TableIUD> tableIUDList = remoteEvent.getTableIUDList();
        if (tableIUDList != null) {
            for (int i = 0; i < tableIUDList.size(); ++i) {
                TransactionEventTable.TableIUD tableIUD = tableIUDList.get(i);
                this.beanDescriptorManager.cacheNotify(tableIUD);
            }
        }
        if ((beanPersistList = remoteEvent.getBeanPersistList()) != null) {
            for (int i = 0; i < beanPersistList.size(); ++i) {
                BeanPersistIds beanPersist = beanPersistList.get(i);
                beanPersist.notifyCacheAndListener();
            }
        }
        if ((indexEventList = remoteEvent.getIndexEventList()) != null) {
            for (int i = 0; i < indexEventList.size(); ++i) {
                IndexEvent indexEvent = indexEventList.get(i);
                this.luceneIndexManager.processEvent(indexEvent);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum OnQueryOnly {
        ROLLBACK,
        CLOSE_ON_READCOMMITTED,
        COMMIT;

    }
}

