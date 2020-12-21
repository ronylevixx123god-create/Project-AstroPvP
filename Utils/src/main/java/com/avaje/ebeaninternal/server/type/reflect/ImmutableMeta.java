/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ImmutableMeta {
    private final Constructor<?> constructor;
    private final Method[] readers;

    public ImmutableMeta(Constructor<?> constructor, Method[] readers) {
        this.constructor = constructor;
        this.readers = readers;
    }

    public Constructor<?> getConstructor() {
        return this.constructor;
    }

    public Method[] getReaders() {
        return this.readers;
    }

    public boolean isCompoundType() {
        return this.readers.length > 1;
    }
}

