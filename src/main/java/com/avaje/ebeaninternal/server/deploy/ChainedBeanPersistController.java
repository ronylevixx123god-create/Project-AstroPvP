/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebean.event.BeanPersistRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChainedBeanPersistController
implements BeanPersistController {
    private static final Sorter SORTER = new Sorter();
    private final List<BeanPersistController> list;
    private final BeanPersistController[] chain;

    public ChainedBeanPersistController(BeanPersistController c1, BeanPersistController c2) {
        this(ChainedBeanPersistController.addList(c1, c2));
    }

    private static List<BeanPersistController> addList(BeanPersistController c1, BeanPersistController c2) {
        ArrayList<BeanPersistController> addList = new ArrayList<BeanPersistController>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }

    public ChainedBeanPersistController(List<BeanPersistController> list) {
        this.list = list;
        BeanPersistController[] c = list.toArray(new BeanPersistController[list.size()]);
        Arrays.sort(c, SORTER);
        this.chain = c;
    }

    public ChainedBeanPersistController register(BeanPersistController c) {
        if (this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanPersistController(newList);
    }

    public ChainedBeanPersistController deregister(BeanPersistController c) {
        if (!this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanPersistController(newList);
    }

    @Override
    public int getExecutionOrder() {
        return 0;
    }

    @Override
    public boolean isRegisterFor(Class<?> cls) {
        return false;
    }

    @Override
    public void postDelete(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postDelete(request);
        }
    }

    @Override
    public void postInsert(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postInsert(request);
        }
    }

    @Override
    public void postLoad(Object bean, Set<String> includedProperties) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postLoad(bean, includedProperties);
        }
    }

    @Override
    public void postUpdate(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postUpdate(request);
        }
    }

    @Override
    public boolean preDelete(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].preDelete(request)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean preInsert(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].preInsert(request)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean preUpdate(BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].preUpdate(request)) continue;
            return false;
        }
        return true;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Sorter
    implements Comparator<BeanPersistController> {
        private Sorter() {
        }

        @Override
        public int compare(BeanPersistController o1, BeanPersistController o2) {
            int i2;
            int i1 = o1.getExecutionOrder();
            return i1 < (i2 = o2.getExecutionOrder()) ? -1 : (i1 == i2 ? 0 : 1);
        }
    }
}

