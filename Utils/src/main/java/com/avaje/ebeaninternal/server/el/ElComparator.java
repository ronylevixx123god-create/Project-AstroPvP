/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface ElComparator<T>
extends Comparator<T> {
    @Override
    public int compare(T var1, T var2);

    public int compareValue(Object var1, T var2);
}

