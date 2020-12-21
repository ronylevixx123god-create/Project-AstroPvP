/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.api.TransactionEventBeans;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaMap;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIdMap;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.server.transaction.TransactionManager;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class PostCommitProcessing {
    private static final Logger logger = Logger.getLogger(PostCommitProcessing.class.getName());
    private final ClusterManager clusterManager;
    private final LuceneIndexManager luceneIndexManager;
    private final SpiTransaction transaction;
    private final TransactionEvent event;
    private final String serverName;
    private final TransactionManager manager;
    private final List<PersistRequestBean<?>> persistBeanRequests;
    private final BeanPersistIdMap beanPersistIdMap;
    private final BeanDeltaMap beanDeltaMap;
    private final RemoteTransactionEvent remoteTransactionEvent;
    private final DeleteByIdMap deleteByIdMap;

    public PostCommitProcessing(ClusterManager clusterManager, LuceneIndexManager luceneIndexManager, TransactionManager manager, SpiTransaction transaction, TransactionEvent event) {
        this.clusterManager = clusterManager;
        this.luceneIndexManager = luceneIndexManager;
        this.manager = manager;
        this.serverName = manager.getServerName();
        this.transaction = transaction;
        this.event = event;
        this.deleteByIdMap = event.getDeleteByIdMap();
        this.persistBeanRequests = this.createPersistBeanRequests();
        this.beanPersistIdMap = this.createBeanPersistIdMap();
        this.beanDeltaMap = new BeanDeltaMap(event.getBeanDeltas());
        this.remoteTransactionEvent = this.createRemoteTransactionEvent();
    }

    public void notifyLocalCacheIndex() {
        if (this.luceneIndexManager.isLuceneAvailable()) {
            this.luceneIndexManager.processEvent(this.remoteTransactionEvent, this.transaction);
        }
        this.processTableEvents(this.event.getEventTables());
        this.event.notifyCache();
    }

    private void processTableEvents(TransactionEventTable tableEvents) {
        if (tableEvents != null && !tableEvents.isEmpty()) {
            BeanDescriptorManager dm = this.manager.getBeanDescriptorManager();
            for (TransactionEventTable.TableIUD tableIUD : tableEvents.values()) {
                dm.cacheNotify(tableIUD);
            }
        }
    }

    public void notifyCluster() {
        if (this.remoteTransactionEvent != null && !this.remoteTransactionEvent.isEmpty()) {
            if (this.manager.getClusterDebugLevel() > 0 || logger.isLoggable(Level.FINE)) {
                logger.info("Cluster Send: " + this.remoteTransactionEvent.toString());
            }
            this.clusterManager.broadcast(this.remoteTransactionEvent);
        }
    }

    public Runnable notifyPersistListeners() {
        return new Runnable(){

            public void run() {
                PostCommitProcessing.this.localPersistListenersNotify();
            }
        };
    }

    private void localPersistListenersNotify() {
        if (this.persistBeanRequests != null) {
            for (int i = 0; i < this.persistBeanRequests.size(); ++i) {
                this.persistBeanRequests.get(i).notifyLocalPersistListener();
            }
        }
    }

    private List<PersistRequestBean<?>> createPersistBeanRequests() {
        TransactionEventBeans eventBeans = this.event.getEventBeans();
        if (eventBeans != null) {
            return eventBeans.getRequests();
        }
        return null;
    }

    private BeanPersistIdMap createBeanPersistIdMap() {
        if (this.persistBeanRequests == null) {
            return null;
        }
        BeanPersistIdMap m = new BeanPersistIdMap();
        for (int i = 0; i < this.persistBeanRequests.size(); ++i) {
            this.persistBeanRequests.get(i).addToPersistMap(m);
        }
        return m;
    }

    private RemoteTransactionEvent createRemoteTransactionEvent() {
        Set<IndexInvalidate> indexInvalidations;
        TransactionEventTable eventTables;
        if (!this.clusterManager.isClustering() && !this.luceneIndexManager.isLuceneAvailable()) {
            return null;
        }
        RemoteTransactionEvent remoteTransactionEvent = new RemoteTransactionEvent(this.serverName);
        if (this.beanDeltaMap != null) {
            for (BeanDeltaList deltaList : this.beanDeltaMap.deltaLists()) {
                remoteTransactionEvent.addBeanDeltaList(deltaList);
            }
        }
        if (this.beanPersistIdMap != null) {
            for (BeanPersistIds beanPersist : this.beanPersistIdMap.values()) {
                remoteTransactionEvent.addBeanPersistIds(beanPersist);
            }
        }
        if (this.deleteByIdMap != null) {
            remoteTransactionEvent.setDeleteByIdMap(this.deleteByIdMap);
        }
        if ((eventTables = this.event.getEventTables()) != null && !eventTables.isEmpty()) {
            for (TransactionEventTable.TableIUD tableIUD : eventTables.values()) {
                remoteTransactionEvent.addTableIUD(tableIUD);
            }
        }
        if ((indexInvalidations = this.event.getIndexInvalidations()) != null) {
            for (IndexInvalidate indexInvalidate : indexInvalidations) {
                remoteTransactionEvent.addIndexInvalidate(indexInvalidate);
            }
        }
        return remoteTransactionEvent;
    }
}

