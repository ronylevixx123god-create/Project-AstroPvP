/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.Query;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.IndexUpdatesBuilder;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.lucene.LIndexFactory;
import com.avaje.ebeaninternal.server.lucene.LIndexSync;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefaultLuceneIndexManager
implements LuceneIndexManager,
Runnable {
    private static final Logger logger = Logger.getLogger(DefaultLuceneIndexManager.class.getName());
    private final ConcurrentHashMap<String, LIndex> indexMap;
    private final ClusterManager clusterManager;
    private final LuceneClusterIndexSync clusterIndexSync;
    private final BackgroundExecutor backgroundExecutor;
    private final Analyzer defaultAnalyzer;
    private final String baseDir;
    private final LIndexFactory indexFactory;
    private final boolean luceneAvailable;
    private final Query.UseIndex defaultUseIndex;
    private final String serverName;
    private SpiEbeanServer server;
    private Thread thread;
    private volatile boolean shutdown;
    private volatile boolean shutdownComplete;
    private long manageFreqMillis = 100L;

    public DefaultLuceneIndexManager(ClusterManager clusterManager, BackgroundExecutor backgroundExecutor, Analyzer defaultAnalyzer, String baseDir, String serverName, Query.UseIndex defaultUseIndex) {
        this.luceneAvailable = true;
        this.serverName = serverName;
        this.clusterManager = clusterManager;
        this.clusterIndexSync = clusterManager.getLuceneClusterIndexSync();
        this.backgroundExecutor = backgroundExecutor;
        this.defaultUseIndex = defaultUseIndex;
        this.defaultAnalyzer = defaultAnalyzer;
        this.baseDir = baseDir + File.separator + serverName + File.separator;
        this.indexMap = new ConcurrentHashMap();
        this.indexFactory = new LIndexFactory(this);
        this.thread = new Thread((Runnable)this, "Ebean-" + serverName + "-LuceneManager");
    }

    @Override
    public void notifyCluster(IndexEvent event) {
        if (this.clusterIndexSync != null && this.clusterIndexSync.isMaster()) {
            System.out.println("-- notifyCluster commit ... ");
            RemoteTransactionEvent e = new RemoteTransactionEvent(this.serverName);
            e.addIndexEvent(event);
            this.clusterManager.broadcast(e);
        }
    }

    protected void execute(LIndexSync indexSync) {
        if (this.clusterIndexSync != null) {
            IndexSynchRun r = new IndexSynchRun(this.clusterIndexSync, indexSync);
            this.backgroundExecutor.execute(r);
        }
    }

    @Override
    public void processEvent(IndexEvent indexEvent) {
        if (this.clusterIndexSync == null) {
            return;
        }
        String masterHost = this.clusterIndexSync.getMasterHost();
        if (masterHost == null) {
            logger.warning("Master got IndexEvent " + indexEvent + " ?");
        } else {
            String idxName = indexEvent.getIndexName();
            if (idxName != null) {
                LIndex index = this.getIndex(idxName);
                if (index == null) {
                    logger.warning("Can't find Lucene Index [" + idxName + "]");
                } else {
                    index.queueSync(masterHost);
                }
            }
        }
    }

    @Override
    public void processEvent(RemoteTransactionEvent txnEvent, SpiTransaction localTransaction) {
        Collection<IndexUpdates> events = IndexUpdatesBuilder.create(this.server, txnEvent);
        for (IndexUpdates e : events) {
            BeanDescriptor<?> beanDescriptor = e.getBeanDescriptor();
            LIndex luceneIndex = beanDescriptor.getLuceneIndex();
            if (luceneIndex == null) continue;
            LIndexUpdateFuture future = luceneIndex.process(e);
            if (localTransaction == null) continue;
            localTransaction.addIndexUpdateFuture(future);
        }
    }

    @Override
    public LuceneClusterIndexSync getClusterIndexSync() {
        return this.clusterIndexSync;
    }

    @Override
    public boolean isLuceneAvailable() {
        return this.luceneAvailable;
    }

    @Override
    public Query.UseIndex getDefaultUseIndex() {
        return this.defaultUseIndex;
    }

    @Override
    public LIndex create(IndexDefn<?> indexDefn, BeanDescriptor<?> descriptor) throws IOException {
        return this.indexFactory.create(indexDefn, descriptor);
    }

    @Override
    public SpiEbeanServer getServer() {
        return this.server;
    }

    @Override
    public void setServer(SpiEbeanServer server) {
        this.server = server;
    }

    public Analyzer getDefaultAnalyzer() {
        return this.defaultAnalyzer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void addIndex(LIndex index) throws IOException {
        ConcurrentHashMap<String, LIndex> concurrentHashMap = this.indexMap;
        synchronized (concurrentHashMap) {
            this.indexMap.put(index.getName(), index);
        }
    }

    @Override
    public LIndex getIndex(String name) {
        return this.indexMap.get(name);
    }

    @Override
    public String getIndexDirectory(String indexName) {
        return this.baseDir + indexName;
    }

    @Override
    public void start() {
        this.thread.setDaemon(true);
        this.thread.start();
        logger.info("Lucene Manager started");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        this.shutdown = true;
        Thread thread = this.thread;
        synchronized (thread) {
            try {
                this.thread.wait(20000L);
            }
            catch (InterruptedException e) {
                logger.info("InterruptedException:" + e);
            }
        }
        if (!this.shutdownComplete) {
            String msg = "WARNING: Shutdown of Lucene Manager did not complete?";
            System.err.println(msg);
            logger.warning(msg);
        }
    }

    private void fireOnStartup() {
        String masterHost;
        if (this.clusterIndexSync != null && !this.clusterIndexSync.isMaster() && (masterHost = this.clusterIndexSync.getMasterHost()) != null) {
            for (LIndex index : this.indexMap.values()) {
                index.queueSync(masterHost);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object;
        this.fireOnStartup();
        while (!this.shutdown) {
            object = this.indexMap;
            synchronized (object) {
                long start = System.currentTimeMillis();
                for (LIndex idx : this.indexMap.values()) {
                    idx.manage(this);
                }
                long exeTime = System.currentTimeMillis() - start;
                long sleepMillis = this.manageFreqMillis - exeTime;
                if (sleepMillis > 0L) {
                    try {
                        Thread.sleep(sleepMillis);
                    }
                    catch (InterruptedException e) {
                        logger.log(Level.INFO, "Interrupted", e);
                    }
                }
            }
        }
        this.shutdownComplete = true;
        object = this.thread;
        synchronized (object) {
            this.thread.notifyAll();
        }
    }

    private static class IndexSynchRun
    implements Runnable {
        private final LuceneClusterIndexSync clusterIndexSync;
        private final LIndex index;
        private final String masterHost;

        private IndexSynchRun(LuceneClusterIndexSync clusterIndexSync, LIndexSync indexSync) {
            this.clusterIndexSync = clusterIndexSync;
            this.index = indexSync.getIndex();
            this.masterHost = indexSync.getMasterHost();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run() {
            boolean success = false;
            try {
                this.clusterIndexSync.sync(this.index, this.masterHost);
                success = true;
            }
            catch (IOException e) {
                String msg = "Failed to sync Lucene index " + this.index;
                logger.log(Level.SEVERE, msg, e);
            }
            finally {
                this.index.syncFinished(success);
            }
        }
    }
}

