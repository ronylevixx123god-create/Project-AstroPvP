/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.EmptyImmutableList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.SingletonImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableList<E>
extends ImmutableCollection<E>
implements List<E>,
RandomAccess {
    public static <E> ImmutableList<E> of() {
        return EmptyImmutableList.INSTANCE;
    }

    public static <E> ImmutableList<E> of(E element) {
        return new SingletonImmutableList<E>(element);
    }

    public static <E> ImmutableList<E> of(E e1, E e2) {
        return ImmutableList.construct(e1, e2);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3) {
        return ImmutableList.construct(e1, e2, e3);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4) {
        return ImmutableList.construct(e1, e2, e3, e4);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5) {
        return ImmutableList.construct(e1, e2, e3, e4, e5);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6, e7);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
        return ImmutableList.construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
    }

    public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E ... others) {
        Object[] array = new Object[12 + others.length];
        array[0] = e1;
        array[1] = e2;
        array[2] = e3;
        array[3] = e4;
        array[4] = e5;
        array[5] = e6;
        array[6] = e7;
        array[7] = e8;
        array[8] = e9;
        array[9] = e10;
        array[10] = e11;
        array[11] = e12;
        System.arraycopy(others, 0, array, 12, others.length);
        return ImmutableList.construct(array);
    }

    @Deprecated
    public static <E> ImmutableList<E> of(E[] elements) {
        return ImmutableList.copyOf(elements);
    }

    public static <E> ImmutableList<E> copyOf(Iterable<? extends E> elements) {
        Preconditions.checkNotNull(elements);
        return elements instanceof Collection ? ImmutableList.copyOf(Collections2.cast(elements)) : ImmutableList.copyOf(elements.iterator());
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements) {
        if (elements instanceof ImmutableCollection) {
            ImmutableList list = ((ImmutableCollection)elements).asList();
            return list.isPartialView() ? ImmutableList.copyFromCollection(list) : list;
        }
        return ImmutableList.copyFromCollection(elements);
    }

    public static <E> ImmutableList<E> copyOf(Iterator<? extends E> elements) {
        return ImmutableList.copyFromCollection(Lists.newArrayList(elements));
    }

    public static <E> ImmutableList<E> copyOf(E[] elements) {
        switch (elements.length) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return new SingletonImmutableList<E>(elements[0]);
            }
        }
        return ImmutableList.construct((Object[])elements.clone());
    }

    private static <E> ImmutableList<E> copyFromCollection(Collection<? extends E> collection) {
        Object[] elements = collection.toArray();
        switch (elements.length) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                SingletonImmutableList<Object> list = new SingletonImmutableList<Object>(elements[0]);
                return list;
            }
        }
        return ImmutableList.construct(elements);
    }

    private static <E> ImmutableList<E> construct(Object ... elements) {
        for (int i = 0; i < elements.length; ++i) {
            ImmutableList.checkElementNotNull(elements[i], i);
        }
        return new RegularImmutableList(elements);
    }

    private static Object checkElementNotNull(Object element, int index) {
        if (element == null) {
            throw new NullPointerException("at index " + index);
        }
        return element;
    }

    ImmutableList() {
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public UnmodifiableListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public abstract UnmodifiableListIterator<E> listIterator(int var1);

    @Override
    public abstract int indexOf(@Nullable Object var1);

    @Override
    public abstract int lastIndexOf(@Nullable Object var1);

    @Override
    public abstract ImmutableList<E> subList(int var1, int var2);

    @Override
    public final boolean addAll(int index, Collection<? extends E> newElements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<E> asList() {
        return this;
    }

    public ImmutableList<E> reverse() {
        return new ReverseImmutableList(this);
    }

    @Override
    public boolean equals(Object obj) {
        return Lists.equalsImpl(this, obj);
    }

    @Override
    public int hashCode() {
        return Lists.hashCodeImpl(this);
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class Builder<E>
    extends ImmutableCollection.Builder<E> {
        private final ArrayList<E> contents = Lists.newArrayList();

        @Override
        public Builder<E> add(E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> elements) {
            if (elements instanceof Collection) {
                Collection collection = (Collection)elements;
                this.contents.ensureCapacity(this.contents.size() + collection.size());
            }
            super.addAll(elements);
            return this;
        }

        @Override
        public Builder<E> add(E ... elements) {
            this.contents.ensureCapacity(this.contents.size() + elements.length);
            super.add(elements);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }

        @Override
        public ImmutableList<E> build() {
            return ImmutableList.copyOf(this.contents);
        }
    }

    private static class SerializedForm
    implements Serializable {
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        SerializedForm(Object[] elements) {
            this.elements = elements;
        }

        Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ReverseImmutableList<E>
    extends ImmutableList<E> {
        private final transient ImmutableList<E> forwardList;
        private final transient int size;

        ReverseImmutableList(ImmutableList<E> backingList) {
            this.forwardList = backingList;
            this.size = backingList.size();
        }

        private int reverseIndex(int index) {
            return this.size - 1 - index;
        }

        private int reversePosition(int index) {
            return this.size - index;
        }

        @Override
        public ImmutableList<E> reverse() {
            return this.forwardList;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.forwardList.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> targets) {
            return this.forwardList.containsAll(targets);
        }

        @Override
        public int indexOf(@Nullable Object object) {
            int index = this.forwardList.lastIndexOf(object);
            return index >= 0 ? this.reverseIndex(index) : -1;
        }

        @Override
        public int lastIndexOf(@Nullable Object object) {
            int index = this.forwardList.indexOf(object);
            return index >= 0 ? this.reverseIndex(index) : -1;
        }

        @Override
        public ImmutableList<E> subList(int fromIndex, int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
            return ((ImmutableList)this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex))).reverse();
        }

        @Override
        public E get(int index) {
            Preconditions.checkElementIndex(index, this.size);
            return this.forwardList.get(this.reverseIndex(index));
        }

        @Override
        public UnmodifiableListIterator<E> listIterator(int index) {
            Preconditions.checkPositionIndex(index, this.size);
            ListIterator forward = this.forwardList.listIterator(this.reversePosition(index));
            return new UnmodifiableListIterator<E>((UnmodifiableListIterator)forward){
                final /* synthetic */ UnmodifiableListIterator val$forward;
                {
                    this.val$forward = unmodifiableListIterator;
                }

                @Override
                public boolean hasNext() {
                    return this.val$forward.hasPrevious();
                }

                @Override
                public boolean hasPrevious() {
                    return this.val$forward.hasNext();
                }

                @Override
                public E next() {
                    return this.val$forward.previous();
                }

                @Override
                public int nextIndex() {
                    return ReverseImmutableList.this.reverseIndex(this.val$forward.previousIndex());
                }

                @Override
                public E previous() {
                    return this.val$forward.next();
                }

                @Override
                public int previousIndex() {
                    return ReverseImmutableList.this.reverseIndex(this.val$forward.nextIndex());
                }
            };
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public boolean isEmpty() {
            return this.forwardList.isEmpty();
        }

        @Override
        boolean isPartialView() {
            return this.forwardList.isPartialView();
        }
    }
}

