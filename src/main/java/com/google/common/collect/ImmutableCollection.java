/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.SingletonImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public abstract class ImmutableCollection<E>
implements Collection<E>,
Serializable {
    static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION = new EmptyImmutableCollection();
    private transient ImmutableList<E> asList;

    ImmutableCollection() {
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }

    @Override
    public <T> T[] toArray(T[] other) {
        return ObjectArrays.toArrayImpl(this, other);
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return object != null && Iterators.contains(this.iterator(), object);
    }

    @Override
    public boolean containsAll(Collection<?> targets) {
        return Collections2.containsAllImpl(this, targets);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public String toString() {
        return Collections2.toStringImpl(this);
    }

    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean addAll(Collection<? extends E> newElements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean removeAll(Collection<?> oldElements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean retainAll(Collection<?> elementsToKeep) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> list = this.asList;
        return list == null ? (this.asList = this.createAsList()) : list;
    }

    ImmutableList<E> createAsList() {
        switch (this.size()) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return ImmutableList.of(this.iterator().next());
            }
        }
        return new ImmutableAsList(this.toArray(), this);
    }

    abstract boolean isPartialView();

    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class Builder<E> {
        Builder() {
        }

        public abstract Builder<E> add(E var1);

        public Builder<E> add(E ... elements) {
            for (E element : elements) {
                this.add(element);
            }
            return this;
        }

        public Builder<E> addAll(Iterable<? extends E> elements) {
            for (E element : elements) {
                this.add(element);
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> elements) {
            while (elements.hasNext()) {
                this.add(elements.next());
            }
            return this;
        }

        public abstract ImmutableCollection<E> build();
    }

    private static class SerializedForm
    implements Serializable {
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        SerializedForm(Object[] elements) {
            this.elements = elements;
        }

        Object readResolve() {
            return this.elements.length == 0 ? EMPTY_IMMUTABLE_COLLECTION : new ArrayImmutableCollection<Object>(Platform.clone(this.elements));
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ArrayImmutableCollection<E>
    extends ImmutableCollection<E> {
        private final E[] elements;

        ArrayImmutableCollection(E[] elements) {
            this.elements = elements;
        }

        @Override
        public int size() {
            return this.elements.length;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public UnmodifiableIterator<E> iterator() {
            return Iterators.forArray(this.elements);
        }

        @Override
        ImmutableList<E> createAsList() {
            return this.elements.length == 1 ? new SingletonImmutableList<E>(this.elements[0]) : new RegularImmutableList(this.elements);
        }

        @Override
        boolean isPartialView() {
            return false;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class EmptyImmutableCollection
    extends ImmutableCollection<Object> {
        private static final Object[] EMPTY_ARRAY = new Object[0];

        private EmptyImmutableCollection() {
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
        public boolean contains(@Nullable Object object) {
            return false;
        }

        @Override
        public UnmodifiableIterator<Object> iterator() {
            return Iterators.EMPTY_ITERATOR;
        }

        @Override
        public Object[] toArray() {
            return EMPTY_ARRAY;
        }

        @Override
        public <T> T[] toArray(T[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }

        @Override
        ImmutableList<Object> createAsList() {
            return ImmutableList.of();
        }

        @Override
        boolean isPartialView() {
            return false;
        }
    }
}

