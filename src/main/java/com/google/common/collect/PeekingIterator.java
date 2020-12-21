/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public interface PeekingIterator<E>
extends Iterator<E> {
    public E peek();

    @Override
    public E next();

    @Override
    public void remove();
}

