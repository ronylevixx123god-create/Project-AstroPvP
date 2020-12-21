/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

import com.avaje.ebean.config.CompoundTypeProperty;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface CompoundType<V> {
    public V create(Object[] var1);

    public CompoundTypeProperty<V, ?>[] getProperties();
}

