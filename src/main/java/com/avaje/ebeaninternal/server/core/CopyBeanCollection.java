/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebean.common.BeanSet;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CopyBeanCollection<T> {
    private final BeanCollection<T> bc;
    private final BeanDescriptor<T> desc;
    private final CopyContext ctx;
    private final int maxDepth;

    public CopyBeanCollection(BeanCollection<T> bc, BeanDescriptor<T> desc, CopyContext ctx, int maxDepth) {
        this.bc = bc;
        this.desc = desc;
        this.ctx = ctx;
        this.maxDepth = maxDepth;
    }

    public BeanCollection<T> copy() {
        if (this.bc instanceof BeanList) {
            return this.copyList();
        }
        if (this.bc instanceof BeanSet) {
            return this.copySet();
        }
        if (this.bc instanceof BeanMap) {
            return this.copyMap();
        }
        String msg = "Invalid beanCollection type " + this.bc.getClass().getName();
        throw new RuntimeException(msg);
    }

    private BeanCollection<T> copyList() {
        BeanList<T> newList = new BeanList<T>();
        List actualList = ((BeanList)this.bc).getActualList();
        for (int i = 0; i < actualList.size(); ++i) {
            Object t = actualList.get(i);
            newList.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
        }
        return newList;
    }

    private BeanCollection<T> copySet() {
        BeanSet<T> newSet = new BeanSet<T>();
        Set actualSet = ((BeanSet)this.bc).getActualSet();
        for (Object t : actualSet) {
            newSet.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
        }
        return newSet;
    }

    private BeanCollection<T> copyMap() {
        BeanMap newMap = new BeanMap();
        Map actualMap = ((BeanMap)this.bc).getActualMap();
        for (Map.Entry entry : actualMap.entrySet()) {
            newMap.put(entry.getKey(), this.desc.createCopy(entry.getValue(), this.ctx, this.maxDepth));
        }
        return newMap;
    }
}

