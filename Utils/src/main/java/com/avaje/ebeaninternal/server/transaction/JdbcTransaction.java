/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.LogLevel;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

public class JdbcTransaction
implements SpiTransaction {
    private static final Logger logger = Logger.getLogger(JdbcTransaction.class.getName());
    private static final String illegalStateMessage = "Transaction is Inactive";
    protected final TransactionManager manager;
    final String id;
    final boolean explicit;
    final TransactionManager.OnQueryOnly onQueryOnly;
    boolean active;
    Connection connection;
    BatchControl batchControl;
    TransactionEvent event;
    PersistenceContext persistenceContext;
    boolean persistCascade = true;
    boolean queryOnly = true;
    boolean localReadOnly;
    LogLevel logLevel;
    boolean batchMode;
    int batchSize = -1;
    boolean batchFlushOnQuery = true;
    Boolean batchGetGeneratedKeys;
    Boolean batchFlushOnMixed;
    int depth = 0;
    HashSet<Integer> persistingBeans;
    TransactionLogBuffer logBuffer;
    List<LIndexUpdateFuture> indexUpdateFutures;

    public JdbcTransaction(String id, boolean explicit, LogLevel logLevel, Connection connection, TransactionManager manager) {
        try {
            this.active = true;
            this.id = id;
            this.explicit = explicit;
            this.logLevel = logLevel;
            this.manager = manager;
            this.connection = connection;
            this.onQueryOnly = manager == null ? TransactionManager.OnQueryOnly.ROLLBACK : manager.getOnQueryOnly();
            this.persistenceContext = new DefaultPersistenceContext();
            this.logBuffer = new TransactionLogBuffer(50, id);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public String toString() {
        return "Trans[" + this.id + "]";
    }

    public void addIndexUpdateFuture(LIndexUpdateFuture future) {
        if (this.indexUpdateFutures == null) {
            this.indexUpdateFutures = new ArrayList<LIndexUpdateFuture>();
        }
        this.indexUpdateFutures.add(future);
    }

    public void waitForIndexUpdates() {
        if (this.indexUpdateFutures != null) {
            try {
                for (LIndexUpdateFuture f : this.indexUpdateFutures) {
                    f.get();
                }
            }
            catch (InterruptedException e) {
                throw new PersistenceLuceneException(e);
            }
            catch (ExecutionException e) {
                throw new PersistenceLuceneException(e);
            }
        }
    }

    public void registerBean(Integer persistingBean) {
        if (this.persistingBeans == null) {
            this.persistingBeans = new HashSet();
        }
        this.persistingBeans.add(persistingBean);
    }

    public void unregisterBean(Integer persistedBean) {
        if (this.persistingBeans != null) {
            this.persistingBeans.remove(persistedBean);
        }
    }

    public boolean isRegisteredBean(Integer persistingBean) {
        if (this.persistingBeans == null) {
            return false;
        }
        return this.persistingBeans.contains(persistingBean);
    }

    public int depth(int diff) {
        this.depth += diff;
        return this.depth;
    }

    public boolean isReadOnly() {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        try {
            return this.connection.isReadOnly();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void setReadOnly(boolean readOnly) {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        try {
            this.localReadOnly = readOnly;
            this.connection.setReadOnly(readOnly);
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    public void setBatchMode(boolean batchMode) {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        this.batchMode = batchMode;
    }

    public void setBatchGetGeneratedKeys(boolean getGeneratedKeys) {
        this.batchGetGeneratedKeys = getGeneratedKeys;
        if (this.batchControl != null) {
            this.batchControl.setGetGeneratedKeys(getGeneratedKeys);
        }
    }

    public void setBatchFlushOnMixed(boolean batchFlushOnMixed) {
        this.batchFlushOnMixed = batchFlushOnMixed;
        if (this.batchControl != null) {
            this.batchControl.setBatchFlushOnMixed(batchFlushOnMixed);
        }
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        if (this.batchControl != null) {
            this.batchControl.setBatchSize(batchSize);
        }
    }

    public boolean isBatchFlushOnQuery() {
        return this.batchFlushOnQuery;
    }

    public void setBatchFlushOnQuery(boolean batchFlushOnQuery) {
        this.batchFlushOnQuery = batchFlushOnQuery;
    }

    public boolean isBatchThisRequest() {
        if (!this.explicit && this.depth <= 0) {
            return false;
        }
        return this.batchMode;
    }

    public BatchControl getBatchControl() {
        return this.batchControl;
    }

    public void setBatchControl(BatchControl batchControl) {
        this.queryOnly = false;
        this.batchControl = batchControl;
        if (this.batchGetGeneratedKeys != null) {
            batchControl.setGetGeneratedKeys(this.batchGetGeneratedKeys);
        }
        if (this.batchSize != -1) {
            batchControl.setBatchSize(this.batchSize);
        }
        if (this.batchFlushOnMixed != null) {
            batchControl.setBatchFlushOnMixed(this.batchFlushOnMixed);
        }
    }

    public void flushBatch() {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        if (this.batchControl != null) {
            this.batchControl.flush();
        }
    }

    public void batchFlush() {
        this.flushBatch();
    }

    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }

    public void setPersistenceContext(PersistenceContext context) {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        this.persistenceContext = context;
    }

    public TransactionEvent getEvent() {
        this.queryOnly = false;
        if (this.event == null) {
            this.event = new TransactionEvent();
        }
        return this.event;
    }

    public void setLoggingOn(boolean loggingOn) {
        this.logLevel = loggingOn ? LogLevel.SQL : LogLevel.NONE;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    public boolean isLogSql() {
        return this.logLevel.ordinal() >= LogLevel.SQL.ordinal();
    }

    public boolean isLogSummary() {
        return this.logLevel.ordinal() >= LogLevel.SUMMARY.ordinal();
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void log(String msg) {
        if (this.isLogSummary()) {
            this.logInternal(msg);
        }
    }

    public void logInternal(String msg) {
        if (this.manager != null && this.logBuffer.add(msg)) {
            this.manager.log(this.logBuffer);
            this.logBuffer = this.logBuffer.newBuffer();
        }
    }

    public String getId() {
        return this.id;
    }

    public Connection getInternalConnection() {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        return this.connection;
    }

    public Connection getConnection() {
        this.queryOnly = false;
        return this.getInternalConnection();
    }

    protected void deactivate() {
        try {
            if (this.localReadOnly) {
                this.connection.setReadOnly(false);
            }
        }
        catch (SQLException e) {
            logger.log(Level.SEVERE, "Error setting to readOnly?", e);
        }
        try {
            this.connection.close();
        }
        catch (Exception ex) {
            logger.log(Level.SEVERE, "Error closing connection", ex);
        }
        this.connection = null;
        this.active = false;
    }

    public TransactionLogBuffer getLogBuffer() {
        return this.logBuffer;
    }

    protected void notifyCommit() {
        if (this.manager == null) {
            return;
        }
        if (this.queryOnly) {
            this.manager.notifyOfQueryOnly(true, this, null);
        } else {
            this.manager.notifyOfCommit(this);
        }
    }

    private void commitQueryOnly() {
        try {
            switch (this.onQueryOnly) {
                case ROLLBACK: {
                    this.connection.rollback();
                    break;
                }
                case COMMIT: {
                    this.connection.commit();
                    break;
                }
                case CLOSE_ON_READCOMMITTED: {
                    break;
                }
                default: {
                    this.connection.rollback();
                    break;
                }
            }
        }
        catch (SQLException e) {
            String m = "Error when ending a query only transaction via " + (Object)((Object)this.onQueryOnly);
            logger.log(Level.SEVERE, m, e);
        }
    }

    public void commit() throws RollbackException {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        try {
            if (this.queryOnly) {
                this.commitQueryOnly();
            } else {
                if (this.batchControl != null && !this.batchControl.isEmpty()) {
                    this.batchControl.flush();
                }
                this.connection.commit();
            }
            this.deactivate();
            this.notifyCommit();
        }
        catch (Exception e) {
            throw new RollbackException(e);
        }
    }

    protected void notifyRollback(Throwable cause) {
        if (this.manager == null) {
            return;
        }
        if (this.queryOnly) {
            this.manager.notifyOfQueryOnly(false, this, cause);
        } else {
            this.manager.notifyOfRollback(this, cause);
        }
    }

    public void rollback() throws PersistenceException {
        this.rollback(null);
    }

    public void rollback(Throwable cause) throws PersistenceException {
        if (!this.isActive()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        try {
            this.connection.rollback();
            this.deactivate();
            this.notifyRollback(cause);
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

    public void end() throws PersistenceException {
        if (this.isActive()) {
            this.rollback();
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isPersistCascade() {
        return this.persistCascade;
    }

    public void setPersistCascade(boolean persistCascade) {
        this.persistCascade = persistCascade;
    }

    public void addModification(String tableName, boolean inserts, boolean updates, boolean deletes) {
        this.getEvent().add(tableName, inserts, updates, deletes);
    }

    public final TransactionManager getTransactionManger() {
        return this.manager;
    }
}

