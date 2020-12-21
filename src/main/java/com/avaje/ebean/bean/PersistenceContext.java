/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.bean;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface PersistenceContext {
    public void put(Object var1, Object var2);

    public Object putIfAbsent(Object var1, Object var2);

    public Object get(Class<?> var1, Object var2);

    public void clear();

    public void clear(Class<?> var1);

    public void clear(Class<?> var1, Object var2);

    public int size(Class<?> var1);
}

