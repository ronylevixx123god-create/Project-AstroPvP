/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ListIterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class UnmodifiableListIterator<E>
extends UnmodifiableIterator<E>
implements ListIterator<E> {
    protected UnmodifiableListIterator() {
    }

    @Override
    public final void add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void set(E e) {
        throw new UnsupportedOperationException();
    }
}

