/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.UnmodifiableListIterator;
import java.util.List;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
abstract class TransformedImmutableList<D, E>
extends ImmutableList<E> {
    private final transient ImmutableList<D> backingList;

    TransformedImmutableList(ImmutableList<D> backingList) {
        this.backingList = Preconditions.checkNotNull(backingList);
    }

    abstract E transform(D var1);

    @Override
    public int indexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (!this.get(i).equals(object)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(@Nullable Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = this.size() - 1; i >= 0; --i) {
            if (!this.get(i).equals(object)) continue;
            return i;
        }
        return -1;
    }

    @Override
    public E get(int index) {
        return this.transform(this.backingList.get(index));
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int index) {
        return new AbstractIndexedListIterator<E>(this.size(), index){

            @Override
            protected E get(int index) {
                return TransformedImmutableList.this.get(index);
            }
        };
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        return new TransformedView(this.backingList.subList(fromIndex, toIndex));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List)obj;
            return this.size() == list.size() && Iterators.elementsEqual(this.iterator(), list.iterator());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Object e : this) {
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return ObjectArrays.toArrayImpl(this, array);
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class TransformedView
    extends TransformedImmutableList<D, E> {
        TransformedView(ImmutableList<D> backingList) {
            super(backingList);
        }

        @Override
        E transform(D d) {
            return TransformedImmutableList.this.transform(d);
        }
    }
}

