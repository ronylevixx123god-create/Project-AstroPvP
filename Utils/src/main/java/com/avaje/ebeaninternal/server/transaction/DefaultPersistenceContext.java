/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.Monitor;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import com.avaje.ebeaninternal.server.transaction.WeakValueMap;
import java.util.HashMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DefaultPersistenceContext
implements PersistenceContext {
    private final HashMap<String, ClassContext> typeCache = new HashMap();
    private final Monitor monitor = new Monitor();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void put(Object id, Object bean) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            this.getClassContext(bean.getClass()).put(id, bean);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object putIfAbsent(Object id, Object bean) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            return this.getClassContext(bean.getClass()).putIfAbsent(id, bean);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object get(Class<?> beanType, Object id) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            return this.getClassContext(beanType).get(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size(Class<?> beanType) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            ClassContext classMap = this.typeCache.get(beanType.getName());
            return classMap == null ? 0 : classMap.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            this.typeCache.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear(Class<?> beanType) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            ClassContext classMap = this.typeCache.get(beanType.getName());
            if (classMap != null) {
                classMap.clear();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear(Class<?> beanType, Object id) {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            ClassContext classMap = this.typeCache.get(beanType.getName());
            if (classMap != null && id != null) {
                classMap.remove(id);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        Monitor monitor = this.monitor;
        synchronized (monitor) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, ClassContext> entry : this.typeCache.entrySet()) {
                if (entry.getValue().size() <= 0) continue;
                sb.append(entry.getKey() + ":" + entry.getValue().size() + "; ");
            }
            return sb.toString();
        }
    }

    private ClassContext getClassContext(Class<?> beanType) {
        String clsName = SubClassUtil.getSuperClassName(beanType.getName());
        ClassContext classMap = this.typeCache.get(clsName);
        if (classMap == null) {
            classMap = new ClassContext();
            this.typeCache.put(clsName, classMap);
        }
        return classMap;
    }

    private static class ClassContext {
        private final WeakValueMap<Object, Object> map = new WeakValueMap();

        private ClassContext() {
        }

        private Object get(Object id) {
            return this.map.get(id);
        }

        private Object putIfAbsent(Object id, Object bean) {
            return this.map.putIfAbsent(id, bean);
        }

        private void put(Object id, Object b) {
            this.map.put(id, b);
        }

        private int size() {
            return this.map.size();
        }

        private void clear() {
            this.map.clear();
        }

        private Object remove(Object id) {
            return this.map.remove(id);
        }
    }
}

