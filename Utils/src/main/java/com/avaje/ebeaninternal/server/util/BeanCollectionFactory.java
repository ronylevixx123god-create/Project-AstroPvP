/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.util;

import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebean.common.BeanSet;
import com.avaje.ebeaninternal.server.util.BeanCollectionParams;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BeanCollectionFactory {
    private static final int defaultListInitialCapacity = 20;
    private static final int defaultSetInitialCapacity = 32;
    private static final int defaultMapInitialCapacity = 32;

    private BeanCollectionFactory() {
    }

    public static BeanCollection<?> create(BeanCollectionParams params) {
        return BeanCollectionFactoryHolder.me.createMany(params);
    }

    private BeanCollection<?> createMany(BeanCollectionParams params) {
        Query.Type manyType = params.getManyType();
        switch (manyType) {
            case MAP: {
                return this.createMap(params);
            }
            case LIST: {
                return this.createList(params);
            }
            case SET: {
                return this.createSet(params);
            }
        }
        throw new RuntimeException("Invalid Arg " + (Object)((Object)manyType));
    }

    private BeanMap createMap(BeanCollectionParams params) {
        return new BeanMap(new LinkedHashMap(32));
    }

    private BeanSet createSet(BeanCollectionParams params) {
        return new BeanSet(new LinkedHashSet(32));
    }

    private BeanList createList(BeanCollectionParams params) {
        return new BeanList(new ArrayList(20));
    }

    private static class BeanCollectionFactoryHolder {
        private static BeanCollectionFactory me = new BeanCollectionFactory();

        private BeanCollectionFactoryHolder() {
        }
    }
}

