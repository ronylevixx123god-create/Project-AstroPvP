/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedAsList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.SortedIterables;
import com.google.common.collect.SortedLists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class RegularImmutableSortedSet<E>
extends ImmutableSortedSet<E> {
    private final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> elements, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = elements;
        Preconditions.checkArgument(!elements.isEmpty());
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        try {
            return this.binarySearch(o) >= 0;
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> targets) {
        if (!SortedIterables.hasSameComparator(this.comparator(), targets) || targets.size() <= 1) {
            return super.containsAll(targets);
        }
        Iterator thisIterator = this.iterator();
        Iterator<?> thatIterator = targets.iterator();
        Object target = thatIterator.next();
        try {
            while (thisIterator.hasNext()) {
                int cmp = this.unsafeCompare(thisIterator.next(), target);
                if (cmp == 0) {
                    if (!thatIterator.hasNext()) {
                        return true;
                    }
                    target = thatIterator.next();
                    continue;
                }
                if (cmp <= 0) continue;
                return false;
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        catch (ClassCastException e) {
            return false;
        }
        return false;
    }

    private int binarySearch(Object key) {
        Comparator unsafeComparator = this.comparator;
        return Collections.binarySearch(this.elements, key, unsafeComparator);
    }

    @Override
    boolean isPartialView() {
        return this.elements.isPartialView();
    }

    @Override
    public Object[] toArray() {
        return this.elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return this.elements.toArray(array);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        Set that = (Set)object;
        if (this.size() != that.size()) {
            return false;
        }
        if (SortedIterables.hasSameComparator(this.comparator, that)) {
            Iterator otherIterator = that.iterator();
            try {
                for (Object element : this) {
                    Object otherElement = otherIterator.next();
                    if (otherElement != null && this.unsafeCompare(element, otherElement) == 0) continue;
                    return false;
                }
                return true;
            }
            catch (ClassCastException e) {
                return false;
            }
            catch (NoSuchElementException e) {
                return false;
            }
        }
        return this.containsAll(that);
    }

    @Override
    public E first() {
        return this.elements.get(0);
    }

    @Override
    public E last() {
        return this.elements.get(this.size() - 1);
    }

    @Override
    ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        int index = inclusive ? SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(toElement), this.comparator(), SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(toElement), this.comparator(), SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        return this.createSubset(0, index);
    }

    @Override
    ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this.tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
    }

    @Override
    ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        int index = inclusive ? SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(fromElement), this.comparator(), SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(fromElement), this.comparator(), SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        return this.createSubset(index, this.size());
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    private ImmutableSortedSet<E> createSubset(int newFromIndex, int newToIndex) {
        if (newFromIndex == 0 && newToIndex == this.size()) {
            return this;
        }
        if (newFromIndex < newToIndex) {
            return new RegularImmutableSortedSet<E>(this.elements.subList(newFromIndex, newToIndex), this.comparator);
        }
        return RegularImmutableSortedSet.emptySet(this.comparator);
    }

    @Override
    int indexOf(@Nullable Object target) {
        int position;
        if (target == null) {
            return -1;
        }
        try {
            position = SortedLists.binarySearch(this.elements, target, this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
        }
        catch (ClassCastException e) {
            return -1;
        }
        return position >= 0 && this.elements.get(position).equals(target) ? position : -1;
    }

    @Override
    ImmutableList<E> createAsList() {
        return new ImmutableSortedAsList<E>(this, this.elements);
    }
}

