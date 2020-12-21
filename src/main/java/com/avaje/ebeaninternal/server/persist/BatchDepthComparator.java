/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.persist.BatchedBeanHolder;
import java.io.Serializable;
import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BatchDepthComparator
implements Comparator<BatchedBeanHolder>,
Serializable {
    private static final long serialVersionUID = 264611821665757991L;

    @Override
    public int compare(BatchedBeanHolder o1, BatchedBeanHolder o2) {
        BatchedBeanHolder b1 = o1;
        BatchedBeanHolder b2 = o2;
        if (b1.getOrder() < b2.getOrder()) {
            return -1;
        }
        if (b1.getOrder() == b2.getOrder()) {
            return 0;
        }
        return 1;
    }
}

