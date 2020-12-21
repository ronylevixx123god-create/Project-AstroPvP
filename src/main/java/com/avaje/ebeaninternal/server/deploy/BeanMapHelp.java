/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.BeanCollectionAdd;
import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebeaninternal.server.deploy.BeanCollectionHelp;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanMapHelp<T>
implements BeanCollectionHelp<T> {
    private final BeanPropertyAssocMany<T> many;
    private final BeanDescriptor<T> targetDescriptor;
    private final BeanProperty beanProperty;
    private BeanCollectionLoader loader;

    public BeanMapHelp(BeanDescriptor<T> targetDescriptor, String mapKey) {
        this(null, targetDescriptor, mapKey);
    }

    public BeanMapHelp(BeanPropertyAssocMany<T> many) {
        this(many, many.getTargetDescriptor(), many.getMapKey());
    }

    private BeanMapHelp(BeanPropertyAssocMany<T> many, BeanDescriptor<T> targetDescriptor, String mapKey) {
        this.many = many;
        this.targetDescriptor = targetDescriptor;
        this.beanProperty = targetDescriptor.getBeanProperty(mapKey);
    }

    @Override
    public Iterator<?> getIterator(Object collection) {
        return ((Map)collection).values().iterator();
    }

    @Override
    public void setLoader(BeanCollectionLoader loader) {
        this.loader = loader;
    }

    @Override
    public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
        if (mapKey == null) {
            mapKey = this.many.getMapKey();
        }
        BeanProperty beanProp = this.targetDescriptor.getBeanProperty(mapKey);
        if (bc instanceof BeanMap) {
            BeanMap bm = (BeanMap)bc;
            Map<Object, Object> actualMap = bm.getActualMap();
            if (actualMap == null) {
                actualMap = new LinkedHashMap();
                bm.setActualMap(actualMap);
            }
            return new Adder(beanProp, actualMap);
        }
        if (bc instanceof Map) {
            return new Adder(beanProp, (Map)bc);
        }
        throw new RuntimeException("Unhandled type " + bc);
    }

    @Override
    public Object copyCollection(Object source, CopyContext ctx, int maxDepth, Object parentBean) {
        if (!(source instanceof Map)) {
            return null;
        }
        Map m = ctx.isVanillaMode() ? new LinkedHashMap() : new BeanMap();
        Map sourceMap = (Map)source;
        if (!(source instanceof BeanMap)) {
            for (Map.Entry entry : sourceMap.entrySet()) {
                m.put(entry.getKey(), entry.getValue());
            }
            return m;
        }
        BeanMap bc = (BeanMap)source;
        if (!bc.isPopulated()) {
            if (ctx.isVanillaMode() || parentBean == null) {
                return null;
            }
            return this.createReference(parentBean, this.many.getName());
        }
        Map actual = bc.getActualMap();
        for (Map.Entry entry : actual.entrySet()) {
            Object sourceDetail = entry.getValue();
            T destDetail = this.targetDescriptor.createCopy(sourceDetail, ctx, maxDepth - 1);
            m.put(entry.getKey(), destDetail);
        }
        return m;
    }

    @Override
    public Object createEmpty(boolean vanilla) {
        return vanilla ? new LinkedHashMap() : new BeanMap();
    }

    @Override
    public void add(BeanCollection<?> collection, Object bean) {
        Object keyValue = this.beanProperty.getValueIntercept(bean);
        Map map = (Map)((Object)collection);
        map.put(keyValue, bean);
    }

    @Override
    public BeanCollection<T> createReference(Object parentBean, String propertyName) {
        return new BeanMap(this.loader, parentBean, propertyName);
    }

    @Override
    public ArrayList<InvalidValue> validate(Object manyValue) {
        ArrayList<InvalidValue> errs = null;
        Map m = (Map)manyValue;
        for (Object detailBean : m.values()) {
            InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
            if (invalid == null) continue;
            if (errs == null) {
                errs = new ArrayList<InvalidValue>();
            }
            errs.add(invalid);
        }
        return errs;
    }

    @Override
    public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
        BeanMap newBeanMap = (BeanMap)server.findMap(query, t);
        this.refresh(newBeanMap, parentBean);
    }

    @Override
    public void refresh(BeanCollection<?> bc, Object parentBean) {
        BeanMap newBeanMap = (BeanMap)bc;
        Map current = (Map)this.many.getValueUnderlying(parentBean);
        newBeanMap.setModifyListening(this.many.getModifyListenMode());
        if (current == null) {
            this.many.setValue(parentBean, newBeanMap);
        } else if (current instanceof BeanMap) {
            BeanMap currentBeanMap = (BeanMap)current;
            currentBeanMap.setActualMap(newBeanMap.getActualMap());
            currentBeanMap.setModifyListening(this.many.getModifyListenMode());
        } else {
            this.many.setValue(parentBean, newBeanMap);
        }
    }

    @Override
    public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
        Map map;
        if (collection instanceof BeanCollection) {
            BeanMap bc = (BeanMap)collection;
            if (!bc.isPopulated()) {
                if (explicitInclude) {
                    bc.size();
                } else {
                    return;
                }
            }
            map = bc.getActualMap();
        } else {
            map = (Map)collection;
        }
        int count = 0;
        ctx.beginAssocMany(name);
        for (Map.Entry entry : map.entrySet()) {
            if (count++ > 0) {
                ctx.appendComma();
            }
            Object detailBean = entry.getValue();
            this.targetDescriptor.jsonWrite(ctx, detailBean);
        }
        ctx.endAssocMany();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class Adder
    implements BeanCollectionAdd {
        private final BeanProperty beanProperty;
        private final Map<Object, Object> map;

        Adder(BeanProperty beanProperty, Map<Object, Object> map) {
            this.beanProperty = beanProperty;
            this.map = map;
        }

        @Override
        public void addBean(Object bean) {
            Object keyValue = this.beanProperty.getValue(bean);
            this.map.put(keyValue, bean);
        }
    }
}

