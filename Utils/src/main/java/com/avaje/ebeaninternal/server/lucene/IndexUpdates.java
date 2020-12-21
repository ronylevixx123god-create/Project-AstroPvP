/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import com.avaje.ebeaninternal.server.transaction.BeanPersistIds;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class IndexUpdates {
    private final BeanDescriptor<?> beanDescriptor;
    private List<TransactionEventTable.TableIUD> tableList;
    private BeanPersistIds deleteIds;
    private BeanPersistIds beanPersistIds;
    private BeanDeltaList deltaList;
    private boolean invalidate;

    public IndexUpdates(BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
    }

    public BeanDescriptor<?> getBeanDescriptor() {
        return this.beanDescriptor;
    }

    public boolean isInvalidate() {
        return this.invalidate;
    }

    public void setInvalidate(boolean invalidate) {
        this.invalidate = invalidate;
    }

    public void addTableIUD(TransactionEventTable.TableIUD tableIud) {
        if (this.tableList == null) {
            this.tableList = new ArrayList<TransactionEventTable.TableIUD>(4);
        }
        this.tableList.add(tableIud);
    }

    public List<TransactionEventTable.TableIUD> getTableList() {
        return this.tableList;
    }

    public void setTableList(List<TransactionEventTable.TableIUD> tableList) {
        this.tableList = tableList;
    }

    public BeanPersistIds getBeanPersistIds() {
        return this.beanPersistIds;
    }

    public void setBeanPersistIds(BeanPersistIds beanPersistIds) {
        this.beanPersistIds = beanPersistIds;
    }

    public BeanPersistIds getDeleteIds() {
        return this.deleteIds;
    }

    public void setDeleteIds(BeanPersistIds deleteIds) {
        this.deleteIds = deleteIds;
    }

    public BeanDeltaList getDeltaList() {
        return this.deltaList;
    }

    public void setDeltaList(BeanDeltaList deltaList) {
        this.deltaList = deltaList;
    }
}

