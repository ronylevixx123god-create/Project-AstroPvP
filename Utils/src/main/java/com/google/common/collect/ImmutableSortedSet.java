/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableSortedSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSetFauxverideShim;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.UnmodifiableIterator;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableSortedSet<E>
extends ImmutableSortedSetFauxverideShim<E>
implements SortedSet<E>,
SortedIterable<E> {
    private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
    private static final ImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new EmptyImmutableSortedSet<Comparable>(NATURAL_ORDER);
    final transient Comparator<? super E> comparator;

    private static <E> ImmutableSortedSet<E> emptySet() {
        return NATURAL_EMPTY_SET;
    }

    static <E> ImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
        if (((Object)NATURAL_ORDER).equals(comparator)) {
            return ImmutableSortedSet.emptySet();
        }
        return new EmptyImmutableSortedSet<E>(comparator);
    }

    public static <E> ImmutableSortedSet<E> of() {
        return ImmutableSortedSet.emptySet();
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E element) {
        return new RegularImmutableSortedSet<E>(ImmutableList.of(element), Ordering.natural());
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), Arrays.asList(e1, e2));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3, e4));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4, E e5) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), Arrays.asList(e1, e2, e3, e4, e5));
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E ... remaining) {
        int size = remaining.length + 6;
        ArrayList all = new ArrayList(size);
        Collections.addAll(all, e1, e2, e3, e4, e5, e6);
        Collections.addAll(all, remaining);
        return ImmutableSortedSet.copyOf(Ordering.natural(), all);
    }

    @Deprecated
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E[] elements) {
        return ImmutableSortedSet.copyOf(elements);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] elements) {
        return ImmutableSortedSet.copyOf(Ordering.natural(), Arrays.asList(elements));
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> elements) {
        Ordering naturalOrder = Ordering.natural();
        return ImmutableSortedSet.copyOf(naturalOrder, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> elements) {
        Ordering naturalOrder = Ordering.natural();
        return ImmutableSortedSet.copyOf(naturalOrder, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> elements) {
        Ordering naturalOrder = Ordering.natural();
        return ImmutableSortedSet.copyOfInternal(naturalOrder, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return ImmutableSortedSet.copyOfInternal(comparator, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return ImmutableSortedSet.copyOfInternal(comparator, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return ImmutableSortedSet.copyOfInternal(comparator, elements);
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
        Comparator<Object> comparator = sortedSet.comparator();
        if (comparator == null) {
            comparator = NATURAL_ORDER;
        }
        return ImmutableSortedSet.copyOfInternal(comparator, sortedSet);
    }

    private static <E> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> comparator, Iterable<? extends E> elements) {
        ImmutableSortedSet original;
        boolean hasSameComparator = SortedIterables.hasSameComparator(comparator, elements);
        if (hasSameComparator && elements instanceof ImmutableSortedSet && !(original = (ImmutableSortedSet)elements).isPartialView()) {
            return original;
        }
        ImmutableList<E> list = ImmutableList.copyOf(SortedIterables.sortedUnique(comparator, elements));
        return list.isEmpty() ? ImmutableSortedSet.emptySet(comparator) : new RegularImmutableSortedSet<E>(list, comparator);
    }

    private static <E> ImmutableSortedSet<E> copyOfInternal(Comparator<? super E> comparator, Iterator<? extends E> elements) {
        ImmutableList<E> list = ImmutableList.copyOf(SortedIterables.sortedUnique(comparator, elements));
        return list.isEmpty() ? ImmutableSortedSet.emptySet(comparator) : new RegularImmutableSortedSet<E>(list, comparator);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }

    public static <E extends Comparable<E>> Builder<E> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    public static <E extends Comparable<E>> Builder<E> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    int unsafeCompare(Object a, Object b) {
        return ImmutableSortedSet.unsafeCompare(this.comparator, a, b);
    }

    static int unsafeCompare(Comparator<?> comparator, Object a, Object b) {
        Comparator<?> unsafeComparator = comparator;
        return unsafeComparator.compare(a, b);
    }

    ImmutableSortedSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public ImmutableSortedSet<E> headSet(E toElement) {
        return this.headSet(toElement, false);
    }

    ImmutableSortedSet<E> headSet(E toElement, boolean inclusive) {
        return this.headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
    }

    @Override
    public ImmutableSortedSet<E> subSet(E fromElement, E toElement) {
        return this.subSet(fromElement, true, toElement, false);
    }

    ImmutableSortedSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(this.comparator.compare(fromElement, toElement) <= 0);
        return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public ImmutableSortedSet<E> tailSet(E fromElement) {
        return this.tailSet(fromElement, true);
    }

    ImmutableSortedSet<E> tailSet(E fromElement, boolean inclusive) {
        return this.tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
    }

    abstract ImmutableSortedSet<E> headSetImpl(E var1, boolean var2);

    abstract ImmutableSortedSet<E> subSetImpl(E var1, boolean var2, E var3, boolean var4);

    abstract ImmutableSortedSet<E> tailSetImpl(E var1, boolean var2);

    abstract int indexOf(@Nullable Object var1);

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override
    Object writeReplace() {
        return new SerializedForm<E>(this.comparator, this.toArray());
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SerializedForm<E>
    implements Serializable {
        final Comparator<? super E> comparator;
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        public SerializedForm(Comparator<? super E> comparator, Object[] elements) {
            this.comparator = comparator;
            this.elements = elements;
        }

        Object readResolve() {
            return ((Builder)new Builder<E>(this.comparator).add(this.elements)).build();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class Builder<E>
    extends ImmutableSet.Builder<E> {
        private final Comparator<? super E> comparator;

        public Builder(Comparator<? super E> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        public Builder<E> add(E element) {
            super.add((Object)element);
            return this;
        }

        @Override
        public Builder<E> add(E ... elements) {
            super.add((Object[])elements);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> elements) {
            super.addAll((Iterable)elements);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> elements) {
            super.addAll((Iterator)elements);
            return this;
        }

        @Override
        public ImmutableSortedSet<E> build() {
            return ImmutableSortedSet.copyOfInternal(this.comparator, this.contents.iterator());
        }
    }
}

