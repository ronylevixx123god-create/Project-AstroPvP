/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.UnmodifiableIterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@Beta
@GwtCompatible
public abstract class AbstractLinkedIterator<T>
extends UnmodifiableIterator<T> {
    private T nextOrNull;

    protected AbstractLinkedIterator(@Nullable T firstOrNull) {
        this.nextOrNull = firstOrNull;
    }

    protected abstract T computeNext(T var1);

    @Override
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            T t = this.nextOrNull;
            return t;
        }
        finally {
            this.nextOrNull = this.computeNext(this.nextOrNull);
        }
    }
}

