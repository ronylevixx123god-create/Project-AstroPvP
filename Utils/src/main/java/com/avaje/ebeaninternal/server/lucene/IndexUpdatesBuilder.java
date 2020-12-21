/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.lucene.IndexUpdates;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import com.avaje.ebeaninternal.server.transaction.DeleteByIdMap;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IndexUpdatesBuilder {
    private final SpiEbeanServer server;
    private final Map<String, IndexUpdates> map = new HashMap<String, IndexUpdates>();
    private final RemoteTransactionEvent txnEvent;

    public static Collection<IndexUpdates> create(SpiEbeanServer server, RemoteTransactionEvent txnEvent) {
        return new IndexUpdatesBuilder(server, txnEvent).create();
    }

    private IndexUpdatesBuilder(SpiEbeanServer server, RemoteTransactionEvent txnEvent) {
        this.server = server;
        this.txnEvent = txnEvent;
    }

    private Collection<IndexUpdates> create() {
        List<BeanDeltaList> beanDeltaLists;
        List<BeanPersistIds> beanPersistList;
        DeleteByIdMap deleteByIdMap;
        Object d;
        List<TransactionEventTable.TableIUD> tableIUDList;
        Set<IndexInvalidate> indexInvalidations = this.txnEvent.getIndexInvalidations();
        if (indexInvalidations != null) {
            for (IndexInvalidate indexInvalidate : indexInvalidations) {
                LIndex index = this.server.getLuceneIndexManager().getIndex(indexInvalidate.getIndexName());
                BeanDescriptor<?> d2 = index.getBeanDescriptor();
                this.getEventByType(d2).setInvalidate(true);
            }
        }
        if ((tableIUDList = this.txnEvent.getTableIUDList()) != null) {
            for (int i = 0; i < tableIUDList.size(); ++i) {
                TransactionEventTable.TableIUD tableIUD = tableIUDList.get(i);
                List<BeanDescriptor<?>> descList = this.server.getBeanDescriptors(tableIUD.getTable());
                if (descList == null) continue;
                for (int j = 0; j < descList.size(); ++j) {
                    d = descList.get(j);
                    this.getEventByType((BeanDescriptor<?>)d).addTableIUD(tableIUD);
                }
            }
        }
        if ((deleteByIdMap = this.txnEvent.getDeleteByIdMap()) != null) {
            for (BeanPersistIds delIds : deleteByIdMap.values()) {
                this.getEventByType(delIds.getBeanDescriptor()).setDeleteIds(delIds);
            }
        }
        if ((beanPersistList = this.txnEvent.getBeanPersistList()) != null) {
            for (int i = 0; i < beanPersistList.size(); ++i) {
                BeanPersistIds b = beanPersistList.get(i);
                this.getEventByType(b.getBeanDescriptor()).setBeanPersistIds(b);
            }
        }
        if ((beanDeltaLists = this.txnEvent.getBeanDeltaLists()) != null) {
            for (int i = 0; i < beanDeltaLists.size(); ++i) {
                d = beanDeltaLists.get(i);
                this.getEventByType(((BeanDeltaList)d).getBeanDescriptor()).setDeltaList((BeanDeltaList)d);
            }
        }
        return this.map.values();
    }

    private IndexUpdates getEventByType(BeanDescriptor<?> d) {
        String beanDescKey = d.getBeanType().getName();
        IndexUpdates eventByType = this.map.get(beanDescKey);
        if (eventByType == null) {
            eventByType = new IndexUpdates(d);
            this.map.put(beanDescKey, eventByType);
        }
        return eventByType;
    }
}

