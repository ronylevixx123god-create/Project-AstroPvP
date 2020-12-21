/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.cluster.ClusterBroadcast;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
import com.avaje.ebeaninternal.server.cluster.mcast.McastClusterManager;
import com.avaje.ebeaninternal.server.cluster.socket.SocketClusterBroadcast;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterFactory;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClusterManager {
    private static final Logger logger = Logger.getLogger(ClusterManager.class.getName());
    private final ClusterBroadcast broadcast;
    private final ConcurrentHashMap<String, EbeanServer> serverMap = new ConcurrentHashMap();
    private LuceneClusterListener luceneListener;
    private LuceneClusterIndexSync luceneIndexSync;
    private boolean started;

    public ClusterManager() {
        String clusterType = GlobalProperties.get("ebean.cluster.type", null);
        if (clusterType == null || clusterType.trim().length() == 0) {
            this.broadcast = null;
        } else {
            SLuceneClusterFactory luceneFactory = new SLuceneClusterFactory();
            int lucenePort = GlobalProperties.getInt("ebean.cluster.lucene.port", 9991);
            this.luceneListener = luceneFactory.createListener(this, lucenePort);
            String masterHostPort = GlobalProperties.get("ebean.cluster.lucene.masterHostPort", null);
            this.luceneIndexSync = luceneFactory.createIndexSync();
            this.luceneIndexSync.setMasterHost(masterHostPort);
            this.luceneIndexSync.setMode(masterHostPort == null ? LuceneClusterIndexSync.Mode.MASTER_MODE : LuceneClusterIndexSync.Mode.SLAVE_MODE);
            logger.info("... luceneListener using [" + lucenePort + "]");
            try {
                if ("mcast".equalsIgnoreCase(clusterType)) {
                    this.broadcast = new McastClusterManager();
                } else if ("socket".equalsIgnoreCase(clusterType)) {
                    this.broadcast = new SocketClusterBroadcast();
                } else {
                    logger.info("Clustering using [" + clusterType + "]");
                    this.broadcast = (ClusterBroadcast)ClassUtil.newInstance(clusterType);
                }
            }
            catch (Exception e) {
                String msg = "Error initialising ClusterManager type [" + clusterType + "]";
                logger.log(Level.SEVERE, msg, e);
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerServer(EbeanServer server) {
        ConcurrentHashMap<String, EbeanServer> concurrentHashMap = this.serverMap;
        synchronized (concurrentHashMap) {
            if (!this.started) {
                this.startup();
            }
            this.serverMap.put(server.getName(), server);
        }
    }

    public LuceneClusterIndexSync getLuceneClusterIndexSync() {
        return this.luceneIndexSync;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public EbeanServer getServer(String name) {
        ConcurrentHashMap<String, EbeanServer> concurrentHashMap = this.serverMap;
        synchronized (concurrentHashMap) {
            return this.serverMap.get(name);
        }
    }

    private void startup() {
        this.started = true;
        if (this.broadcast != null) {
            this.broadcast.startup(this);
        }
        if (this.luceneListener != null) {
            this.luceneListener.startup();
        }
    }

    public boolean isClustering() {
        return this.broadcast != null;
    }

    public void broadcast(RemoteTransactionEvent remoteTransEvent) {
        if (this.broadcast != null) {
            this.broadcast.broadcast(remoteTransEvent);
        }
    }

    public void shutdown() {
        if (this.luceneListener != null) {
            this.luceneListener.shutdown();
        }
        if (this.broadcast != null) {
            logger.info("ClusterManager shutdown ");
            this.broadcast.shutdown();
        }
    }
}

