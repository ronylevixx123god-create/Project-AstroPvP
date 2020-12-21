/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollectionLoader;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.common.AbstractBeanCollection;
import com.avaje.ebean.common.ModifyCollection;
import com.avaje.ebean.common.ModifySet;
import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class BeanMap<K, E>
extends AbstractBeanCollection<E>
implements Map<K, E> {
    private Map<K, E> map;

    public BeanMap(Map<K, E> map) {
        this.map = map;
    }

    public BeanMap() {
        this(new LinkedHashMap());
    }

    public BeanMap(BeanCollectionLoader ebeanServer, Object ownerBean, String propertyName) {
        super(ebeanServer, ownerBean, propertyName);
    }

    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.map;
        }
        return this;
    }

    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.map;
        }
        return this;
    }

    @Override
    public void internalAdd(Object bean) {
        throw new RuntimeException("Not allowed for map");
    }

    @Override
    public boolean isPopulated() {
        return this.map != null;
    }

    @Override
    public boolean isReference() {
        return this.map == null;
    }

    @Override
    public boolean checkEmptyLazyLoad() {
        if (this.map == null) {
            this.map = new LinkedHashMap<K, E>();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initClear() {
        BeanMap beanMap = this;
        synchronized (beanMap) {
            if (this.map == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                } else {
                    this.map = new LinkedHashMap<K, E>();
                }
            }
            this.touched();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init() {
        BeanMap beanMap = this;
        synchronized (beanMap) {
            if (this.map == null) {
                this.lazyLoadCollection(false);
            }
            this.touched();
        }
    }

    public void setActualMap(Map<?, ?> map) {
        this.map = map;
    }

    public Map<K, E> getActualMap() {
        return this.map;
    }

    @Override
    public Collection<E> getActualDetails() {
        return this.map.values();
    }

    @Override
    public Object getActualCollection() {
        return this.map;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BeanMap ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        } else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.map == null) {
            sb.append("deferred ");
        } else {
            sb.append("size[").append(this.map.size()).append("]");
            sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
            sb.append(" map").append(this.map);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        this.init();
        return ((Object)this.map).equals(obj);
    }

    @Override
    public int hashCode() {
        this.init();
        return ((Object)this.map).hashCode();
    }

    @Override
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (K key : this.map.keySet()) {
                E o = this.map.remove(key);
                this.modifyRemoval(o);
            }
        }
        this.map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        this.init();
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        this.init();
        return this.map.containsValue(value);
    }

    @Override
    public Set<Map.Entry<K, E>> entrySet() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableSet(this.map.entrySet());
        }
        if (this.modifyListening) {
            Set<Map.Entry<K, E>> s = this.map.entrySet();
            return new ModifySet<Map.Entry<K, E>>(this, s);
        }
        return this.map.entrySet();
    }

    @Override
    public E get(Object key) {
        this.init();
        return this.map.get(key);
    }

    @Override
    public boolean isEmpty() {
        this.init();
        return this.map.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableSet(this.map.keySet());
        }
        return this.map.keySet();
    }

    @Override
    public E put(K key, E value) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            E o = this.map.put(key, value);
            this.modifyAddition(value);
            this.modifyRemoval(o);
        }
        return this.map.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends E> t) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            for (Map.Entry<K, E> entry : t.entrySet()) {
                E o = this.map.put(entry.getKey(), entry.getValue());
                this.modifyAddition(entry.getValue());
                this.modifyRemoval(o);
            }
        }
        this.map.putAll(t);
    }

    @Override
    public E remove(Object key) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            E o = this.map.remove(key);
            this.modifyRemoval(o);
            return o;
        }
        return this.map.remove(key);
    }

    @Override
    public int size() {
        this.init();
        return this.map.size();
    }

    @Override
    public Collection<E> values() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableCollection(this.map.values());
        }
        if (this.modifyListening) {
            Collection<E> c = this.map.values();
            return new ModifyCollection<E>(this, c);
        }
        return this.map.values();
    }
}

