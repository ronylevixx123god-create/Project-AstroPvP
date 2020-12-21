/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.transaction.BeanDelta;
import com.avaje.ebeaninternal.server.transaction.BeanDeltaList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanDeltaMap {
    private Map<String, BeanDeltaList> deltaMap = new HashMap<String, BeanDeltaList>();

    public BeanDeltaMap() {
    }

    public BeanDeltaMap(List<BeanDelta> deltaBeans) {
        if (deltaBeans != null) {
            for (int i = 0; i < deltaBeans.size(); ++i) {
                BeanDelta deltaBean = deltaBeans.get(i);
                this.addBeanDelta(deltaBean);
            }
        }
    }

    public String toString() {
        return this.deltaMap.values().toString();
    }

    public void addBeanDelta(BeanDelta beanDelta) {
        BeanDescriptor<?> d = beanDelta.getBeanDescriptor();
        BeanDeltaList list = this.getDeltaBeanList(d);
        list.add(beanDelta);
    }

    public Collection<BeanDeltaList> deltaLists() {
        return this.deltaMap.values();
    }

    private BeanDeltaList getDeltaBeanList(BeanDescriptor<?> d) {
        BeanDeltaList deltaList = this.deltaMap.get(d.getFullName());
        if (deltaList == null) {
            deltaList = new BeanDeltaList(d);
            this.deltaMap.put(d.getFullName(), deltaList);
        }
        return deltaList;
    }
}

