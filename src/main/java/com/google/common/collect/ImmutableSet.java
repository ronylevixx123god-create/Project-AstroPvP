/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.EmptyImmutableSet;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.SingletonImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableSet<E>
extends ImmutableCollection<E>
implements Set<E> {
    static final int MAX_TABLE_SIZE = 0x40000000;
    static final int CUTOFF = 0x20000000;

    public static <E> ImmutableSet<E> of() {
        return EmptyImmutableSet.INSTANCE;
    }

    public static <E> ImmutableSet<E> of(E element) {
        return new SingletonImmutableSet<E>(element);
    }

    public static <E> ImmutableSet<E> of(E e1, E e2) {
        return ImmutableSet.construct(e1, e2);
    }

    public static <E> ImmutableSet<E> of(E e1, E e2, E e3) {
        return ImmutableSet.construct(e1, e2, e3);
    }

    public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4) {
        return ImmutableSet.construct(e1, e2, e3, e4);
    }

    public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5) {
        return ImmutableSet.construct(e1, e2, e3, e4, e5);
    }

    public static <E> ImmutableSet<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E ... others) {
        int paramCount = 6;
        Object[] elements = new Object[6 + others.length];
        elements[0] = e1;
        elements[1] = e2;
        elements[2] = e3;
        elements[3] = e4;
        elements[4] = e5;
        elements[5] = e6;
        for (int i = 6; i < elements.length; ++i) {
            elements[i] = others[i - 6];
        }
        return ImmutableSet.construct(elements);
    }

    private static <E> ImmutableSet<E> construct(Object ... elements) {
        Object[] uniqueElements;
        Object element;
        int tableSize = ImmutableSet.chooseTableSize(elements.length);
        Object[] table = new Object[tableSize];
        int mask = tableSize - 1;
        ArrayList<Object> uniqueElementsList = null;
        int hashCode = 0;
        block0: for (int i = 0; i < elements.length; ++i) {
            element = elements[i];
            int hash = element.hashCode();
            int j = Hashing.smear(hash);
            while (true) {
                int index;
                Object value;
                if ((value = table[index = j & mask]) == null) {
                    if (uniqueElementsList != null) {
                        uniqueElementsList.add(element);
                    }
                    table[index] = element;
                    hashCode += hash;
                    continue block0;
                }
                if (value.equals(element)) {
                    if (uniqueElementsList != null) continue block0;
                    uniqueElementsList = new ArrayList<Object>(elements.length);
                    for (int k = 0; k < i; ++k) {
                        Object previous = elements[k];
                        uniqueElementsList.add(previous);
                    }
                    continue block0;
                }
                ++j;
            }
        }
        Object[] arrobject = uniqueElements = uniqueElementsList == null ? elements : uniqueElementsList.toArray();
        if (uniqueElements.length == 1) {
            element = uniqueElements[0];
            return new SingletonImmutableSet<Object>(element, hashCode);
        }
        if (tableSize > 2 * ImmutableSet.chooseTableSize(uniqueElements.length)) {
            return ImmutableSet.construct(uniqueElements);
        }
        return new RegularImmutableSet(uniqueElements, hashCode, table, mask);
    }

    static int chooseTableSize(int setSize) {
        if (setSize < 0x20000000) {
            return Integer.highestOneBit(setSize) << 2;
        }
        Preconditions.checkArgument(setSize < 0x40000000, "collection too large");
        return 0x40000000;
    }

    @Deprecated
    public static <E> ImmutableSet<E> of(E[] elements) {
        return ImmutableSet.copyOf(elements);
    }

    public static <E> ImmutableSet<E> copyOf(E[] elements) {
        switch (elements.length) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                return ImmutableSet.of(elements[0]);
            }
        }
        return ImmutableSet.construct((Object[])elements.clone());
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> elements) {
        return elements instanceof Collection ? ImmutableSet.copyOf(Collections2.cast(elements)) : ImmutableSet.copyOf(elements.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> elements) {
        return ImmutableSet.copyFromCollection(Lists.newArrayList(elements));
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> elements) {
        ImmutableSet set;
        if (elements instanceof ImmutableSet && !(elements instanceof ImmutableSortedSet) && !(set = (ImmutableSet)elements).isPartialView()) {
            return set;
        }
        return ImmutableSet.copyFromCollection(elements);
    }

    private static <E> ImmutableSet<E> copyFromCollection(Collection<? extends E> collection) {
        Object[] elements = collection.toArray();
        switch (elements.length) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                Object onlyElement = elements[0];
                return ImmutableSet.of(onlyElement);
            }
        }
        return ImmutableSet.construct(elements);
    }

    ImmutableSet() {
    }

    boolean isHashCodeFast() {
        return false;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ImmutableSet && this.isHashCodeFast() && ((ImmutableSet)object).isHashCodeFast() && this.hashCode() != object.hashCode()) {
            return false;
        }
        return Sets.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

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
    public static class Builder<E>
    extends ImmutableCollection.Builder<E> {
        final ArrayList<E> contents = Lists.newArrayList();

        @Override
        public Builder<E> add(E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }

        @Override
        public Builder<E> add(E ... elements) {
            this.contents.ensureCapacity(this.contents.size() + elements.length);
            super.add(elements);
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
        public Builder<E> addAll(Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }

        @Override
        public ImmutableSet<E> build() {
            return ImmutableSet.copyOf(this.contents);
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
            return ImmutableSet.copyOf(this.elements);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class TransformedImmutableSet<D, E>
    extends ImmutableSet<E> {
        final D[] source;
        final int hashCode;

        TransformedImmutableSet(D[] source, int hashCode) {
            this.source = source;
            this.hashCode = hashCode;
        }

        abstract E transform(D var1);

        @Override
        public int size() {
            return this.source.length;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public UnmodifiableIterator<E> iterator() {
            return new AbstractIndexedListIterator<E>(this.source.length){

                @Override
                protected E get(int index) {
                    return TransformedImmutableSet.this.transform(TransformedImmutableSet.this.source[index]);
                }
            };
        }

        @Override
        public Object[] toArray() {
            return this.toArray(new Object[this.size()]);
        }

        @Override
        public <T> T[] toArray(T[] array) {
            int size = this.size();
            if (array.length < size) {
                array = ObjectArrays.newArray(array, size);
            } else if (array.length > size) {
                array[size] = null;
            }
            T[] objectArray = array;
            for (int i = 0; i < this.source.length; ++i) {
                objectArray[i] = this.transform(this.source[i]);
            }
            return array;
        }

        @Override
        public final int hashCode() {
            return this.hashCode;
        }

        @Override
        boolean isHashCodeFast() {
            return true;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class ArrayImmutableSet<E>
    extends ImmutableSet<E> {
        final transient Object[] elements;

        ArrayImmutableSet(Object[] elements) {
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
        public Object[] toArray() {
            Object[] array = new Object[this.size()];
            System.arraycopy(this.elements, 0, array, 0, this.size());
            return array;
        }

        @Override
        public <T> T[] toArray(T[] array) {
            int size = this.size();
            if (array.length < size) {
                array = ObjectArrays.newArray(array, size);
            } else if (array.length > size) {
                array[size] = null;
            }
            System.arraycopy(this.elements, 0, array, 0, size);
            return array;
        }

        @Override
        public boolean containsAll(Collection<?> targets) {
            if (targets == this) {
                return true;
            }
            if (!(targets instanceof ArrayImmutableSet)) {
                return super.containsAll(targets);
            }
            if (targets.size() > this.size()) {
                return false;
            }
            for (Object target : ((ArrayImmutableSet)targets).elements) {
                if (this.contains(target)) continue;
                return false;
            }
            return true;
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        ImmutableList<E> createAsList() {
            return new ImmutableAsList(this.elements, this);
        }
    }
}

