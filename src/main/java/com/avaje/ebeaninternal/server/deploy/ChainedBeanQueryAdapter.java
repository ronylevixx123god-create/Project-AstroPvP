/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.event.BeanQueryRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ChainedBeanQueryAdapter
implements BeanQueryAdapter {
    private static final Sorter SORTER = new Sorter();
    private final List<BeanQueryAdapter> list;
    private final BeanQueryAdapter[] chain;

    public ChainedBeanQueryAdapter(BeanQueryAdapter c1, BeanQueryAdapter c2) {
        this(ChainedBeanQueryAdapter.addList(c1, c2));
    }

    private static List<BeanQueryAdapter> addList(BeanQueryAdapter c1, BeanQueryAdapter c2) {
        ArrayList<BeanQueryAdapter> addList = new ArrayList<BeanQueryAdapter>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }

    public ChainedBeanQueryAdapter(List<BeanQueryAdapter> list) {
        this.list = list;
        BeanQueryAdapter[] c = list.toArray(new BeanQueryAdapter[list.size()]);
        Arrays.sort(c, SORTER);
        this.chain = c;
    }

    public ChainedBeanQueryAdapter register(BeanQueryAdapter c) {
        if (this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanQueryAdapter(newList);
    }

    public ChainedBeanQueryAdapter deregister(BeanQueryAdapter c) {
        if (!this.list.contains(c)) {
            return this;
        }
        ArrayList<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanQueryAdapter(newList);
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
    public void preQuery(BeanQueryRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].preQuery(request);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Sorter
    implements Comparator<BeanQueryAdapter> {
        private Sorter() {
        }

        @Override
        public int compare(BeanQueryAdapter o1, BeanQueryAdapter o2) {
            int i2;
            int i1 = o1.getExecutionOrder();
            return i1 < (i2 = o2.getExecutionOrder()) ? -1 : (i1 == i2 ? 0 : 1);
        }
    }
}

