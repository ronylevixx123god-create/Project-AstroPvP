/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaMap;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RemoteTransactionEvent
implements Serializable,
Runnable {
    private static final long serialVersionUID = 757920022500956949L;
    private List<BeanPersistIds> beanPersistList = new ArrayList<BeanPersistIds>();
    private List<TransactionEventTable.TableIUD> tableList;
    private List<BeanDeltaList> beanDeltaLists;
    private BeanDeltaMap beanDeltaMap;
    private List<IndexEvent> indexEventList;
    private Set<IndexInvalidate> indexInvalidations;
    private DeleteByIdMap deleteByIdMap;
    private String serverName;
    private transient SpiEbeanServer server;

    public RemoteTransactionEvent(String serverName) {
        this.serverName = serverName;
    }

    public RemoteTransactionEvent(SpiEbeanServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        this.server.remoteTransactionEvent(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.beanDeltaMap != null) {
            sb.append(this.beanDeltaMap);
        }
        sb.append(this.beanPersistList);
        if (this.tableList != null) {
            sb.append(this.tableList);
        }
        return sb.toString();
    }

    public void writeBinaryMessage(BinaryMessageList msgList) throws IOException {
        if (this.indexInvalidations != null) {
            for (IndexInvalidate indexInvalidate : this.indexInvalidations) {
                indexInvalidate.writeBinaryMessage(msgList);
            }
        }
        if (this.tableList != null) {
            for (int i = 0; i < this.tableList.size(); ++i) {
                this.tableList.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.deleteByIdMap != null) {
            for (BeanPersistIds deleteIds : this.deleteByIdMap.values()) {
                deleteIds.writeBinaryMessage(msgList);
            }
        }
        if (this.beanPersistList != null) {
            for (int i = 0; i < this.beanPersistList.size(); ++i) {
                this.beanPersistList.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.beanDeltaLists != null) {
            for (int i = 0; i < this.beanDeltaLists.size(); ++i) {
                this.beanDeltaLists.get(i).writeBinaryMessage(msgList);
            }
        }
        if (this.indexEventList != null) {
            for (int i = 0; i < this.indexEventList.size(); ++i) {
                this.indexEventList.get(i).writeBinaryMessage(msgList);
            }
        }
    }

    public boolean isEmpty() {
        return this.beanPersistList.isEmpty() && (this.tableList == null || this.tableList.isEmpty());
    }

    public void addBeanPersistIds(BeanPersistIds beanPersist) {
        this.beanPersistList.add(beanPersist);
    }

    public void addIndexInvalidate(IndexInvalidate indexInvalidate) {
        if (this.indexInvalidations == null) {
            this.indexInvalidations = new HashSet<IndexInvalidate>();
        }
        this.indexInvalidations.add(indexInvalidate);
    }

    public void addTableIUD(TransactionEventTable.TableIUD tableIud) {
        if (this.tableList == null) {
            this.tableList = new ArrayList<TransactionEventTable.TableIUD>(4);
        }
        this.tableList.add(tableIud);
    }

    public void addBeanDeltaList(BeanDeltaList deltaList) {
        if (this.beanDeltaLists == null) {
            this.beanDeltaLists = new ArrayList<BeanDeltaList>();
        }
        this.beanDeltaLists.add(deltaList);
    }

    public void addBeanDelta(BeanDelta beanDelta) {
        if (this.beanDeltaMap == null) {
            this.beanDeltaMap = new BeanDeltaMap();
        }
        this.beanDeltaMap.addBeanDelta(beanDelta);
    }

    public void addIndexEvent(IndexEvent indexEvent) {
        if (this.indexEventList == null) {
            this.indexEventList = new ArrayList<IndexEvent>(2);
        }
        this.indexEventList.add(indexEvent);
    }

    public String getServerName() {
        return this.serverName;
    }

    public SpiEbeanServer getServer() {
        return this.server;
    }

    public void setServer(SpiEbeanServer server) {
        this.server = server;
    }

    public DeleteByIdMap getDeleteByIdMap() {
        return this.deleteByIdMap;
    }

    public void setDeleteByIdMap(DeleteByIdMap deleteByIdMap) {
        this.deleteByIdMap = deleteByIdMap;
    }

    public Set<IndexInvalidate> getIndexInvalidations() {
        return this.indexInvalidations;
    }

    public List<IndexEvent> getIndexEventList() {
        return this.indexEventList;
    }

    public List<TransactionEventTable.TableIUD> getTableIUDList() {
        return this.tableList;
    }

    public List<BeanPersistIds> getBeanPersistList() {
        return this.beanPersistList;
    }

    public List<BeanDeltaList> getBeanDeltaLists() {
        if (this.beanDeltaMap != null) {
            this.beanDeltaLists.addAll(this.beanDeltaMap.deltaLists());
        }
        return this.beanDeltaLists;
    }
}

