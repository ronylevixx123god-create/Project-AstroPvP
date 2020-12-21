/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanPersistListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChainedBeanPersistListener<T>
implements BeanPersistListener<T> {
    private final List<BeanPersistListener<T>> list;
    private final BeanPersistListener<T>[] chain;

    public ChainedBeanPersistListener(BeanPersistListener<T> c1, BeanPersistListener<T> c2) {
        this(ChainedBeanPersistListener.addList(c1, c2));
    }

    private static <T> List<BeanPersistListener<T>> addList(BeanPersistListener<T> c1, BeanPersistListener<T> c2) {
        ArrayList<BeanPersistListener<T>> addList = new ArrayList<BeanPersistListener<T>>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }

    public ChainedBeanPersistListener(List<BeanPersistListener<T>> list) {
        this.list = list;
        this.chain = list.toArray(new BeanPersistListener[list.size()]);
    }

    public ChainedBeanPersistListener<T> register(BeanPersistListener<T> c) {
        if (this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanPersistListener<T>(newList);
    }

    public ChainedBeanPersistListener<T> deregister(BeanPersistListener<T> c) {
        if (!this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanPersistListener<T>(newList);
    }

    @Override
    public boolean deleted(T bean) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].deleted(bean)) continue;
            notifyCluster = true;
        }
        return notifyCluster;
    }

    @Override
    public boolean inserted(T bean) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].inserted(bean)) continue;
            notifyCluster = true;
        }
        return notifyCluster;
    }

    @Override
    public void remoteDelete(Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteDelete(id);
        }
    }

    @Override
    public void remoteInsert(Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteInsert(id);
        }
    }

    @Override
    public void remoteUpdate(Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteUpdate(id);
        }
    }

    @Override
    public boolean updated(T bean, Set<String> updatedProperties) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].updated(bean, updatedProperties)) continue;
            notifyCluster = true;
        }
        return notifyCluster;
    }
}

