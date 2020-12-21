/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface CompoundTypeProperty<V, P> {
    public String getName();

    public P getValue(V var1);

    public int getDbType();
}

