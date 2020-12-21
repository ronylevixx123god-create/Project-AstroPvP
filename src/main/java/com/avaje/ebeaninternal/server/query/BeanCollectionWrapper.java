/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Query;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.util.BeanCollectionFactory;
import com.avaje.ebeaninternal.server.util.BeanCollectionParams;
import java.util.Collection;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanCollectionWrapper {
    private final boolean isMap;
    private final Query.Type queryType;
    private final String mapKey;
    private final BeanCollection<?> beanCollection;
    private final Collection<Object> collection;
    private final Map<Object, Object> map;
    private final BeanDescriptor<?> desc;
    private int rowCount;

    public BeanCollectionWrapper(RelationalQueryRequest request) {
        this.desc = null;
        this.queryType = request.getQueryType();
        this.mapKey = request.getQuery().getMapKey();
        this.isMap = Query.Type.MAP.equals((Object)this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }

    public BeanCollectionWrapper(OrmQueryRequest<?> request) {
        this.desc = request.getBeanDescriptor();
        this.queryType = request.getQueryType();
        this.mapKey = request.getQuery().getMapKey();
        this.isMap = Query.Type.MAP.equals((Object)this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }

    public BeanCollectionWrapper(BeanPropertyAssocMany<?> manyProp) {
        this.queryType = manyProp.getManyType().getQueryType();
        this.mapKey = manyProp.getMapKey();
        this.desc = manyProp.getTargetDescriptor();
        this.isMap = Query.Type.MAP.equals((Object)this.queryType);
        this.beanCollection = this.createBeanCollection(this.queryType);
        this.collection = this.getCollection(this.isMap);
        this.map = this.getMap(this.isMap);
    }

    private Map<Object, Object> getMap(boolean isMap) {
        return isMap ? (Map)((Object)this.beanCollection) : null;
    }

    private Collection<Object> getCollection(boolean isMap) {
        return isMap ? null : (Collection)((Object)this.beanCollection);
    }

    public BeanCollection<?> getBeanCollection() {
        return this.beanCollection;
    }

    private BeanCollection<?> createBeanCollection(Query.Type manyType) {
        BeanCollectionParams p = new BeanCollectionParams(manyType);
        return BeanCollectionFactory.create(p);
    }

    public boolean isMap() {
        return this.isMap;
    }

    public int size() {
        return this.rowCount;
    }

    public void add(Object bean) {
        this.add(bean, this.beanCollection);
    }

    public void add(Object bean, Object collection) {
        if (bean == null) {
            return;
        }
        ++this.rowCount;
        if (this.isMap) {
            Object keyValue = null;
            keyValue = this.mapKey != null ? this.desc.getValue(bean, this.mapKey) : this.desc.getId(bean);
            Map mapColl = (Map)collection;
            mapColl.put(keyValue, bean);
        } else {
            ((Collection)collection).add(bean);
        }
    }

    public void addToCollection(Object bean) {
        this.collection.add(bean);
    }

    public void addToMap(Object bean, Object key) {
        this.map.put(key, bean);
    }
}

