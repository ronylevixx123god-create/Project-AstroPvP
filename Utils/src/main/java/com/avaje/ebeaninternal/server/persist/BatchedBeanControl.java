/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.persist.BatchedBeanHolder;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BatchedBeanControl {
    private final HashMap<String, BatchedBeanHolder> beanHoldMap = new HashMap();
    private final SpiTransaction transaction;
    private final BatchControl batchControl;
    private int topOrder;

    public BatchedBeanControl(SpiTransaction t, BatchControl batchControl) {
        this.transaction = t;
        this.batchControl = batchControl;
    }

    public ArrayList<PersistRequest> getPersistList(PersistRequestBean<?> request) {
        BatchedBeanHolder beanHolder = this.getBeanHolder(request);
        return beanHolder.getList(request);
    }

    private BatchedBeanHolder getBeanHolder(PersistRequestBean<?> request) {
        BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
        BatchedBeanHolder batchBeanHolder = this.beanHoldMap.get(beanDescriptor.getFullName());
        if (batchBeanHolder == null) {
            int relativeDepth = this.transaction.depth(0);
            if (relativeDepth == 0) {
                ++this.topOrder;
            }
            int stmtOrder = this.topOrder * 100 + relativeDepth;
            batchBeanHolder = new BatchedBeanHolder(this.batchControl, beanDescriptor, stmtOrder);
            this.beanHoldMap.put(beanDescriptor.getFullName(), batchBeanHolder);
        }
        return batchBeanHolder;
    }

    public boolean isEmpty() {
        return this.beanHoldMap.isEmpty();
    }

    public BatchedBeanHolder[] getArray() {
        BatchedBeanHolder[] bsArray = new BatchedBeanHolder[this.beanHoldMap.size()];
        this.beanHoldMap.values().toArray(bsArray);
        this.beanHoldMap.clear();
        this.topOrder = 0;
        return bsArray;
    }
}

