/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
class EmptyImmutableSortedSet<E>
extends ImmutableSortedSet<E> {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    EmptyImmutableSortedSet(Comparator<? super E> comparator) {
        super(comparator);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean contains(Object target) {
        return false;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return Iterators.emptyIterator();
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public Object[] toArray() {
        return EMPTY_ARRAY;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> targets) {
        return targets.isEmpty();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Set) {
            Set that = (Set)object;
            return that.isEmpty();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "[]";
    }

    @Override
    public E first() {
        throw new NoSuchElementException();
    }

    @Override
    public E last() {
        throw new NoSuchElementException();
    }

    @Override
    ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return this;
    }

    @Override
    ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this;
    }

    @Override
    ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return this;
    }

    @Override
    int indexOf(@Nullable Object target) {
        return -1;
    }
}

