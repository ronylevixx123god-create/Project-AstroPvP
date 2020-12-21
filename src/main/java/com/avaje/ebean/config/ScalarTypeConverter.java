/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean.config;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ScalarTypeConverter<B, S> {
    public B getNullValue();

    public B wrapValue(S var1);

    public S unwrapValue(B var1);
}

