/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebean;

import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface QueryIterator<T>
extends Iterator<T> {
    @Override
    public boolean hasNext();

    @Override
    public T next();

    @Override
    public void remove();

    public void close();
}

