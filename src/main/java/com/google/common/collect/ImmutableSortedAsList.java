/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import java.util.Comparator;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class ImmutableSortedAsList<E>
extends ImmutableList<E>
implements SortedIterable<E> {
    private final transient ImmutableSortedSet<E> backingSet;
    private final transient ImmutableList<E> backingList;

    ImmutableSortedAsList(ImmutableSortedSet<E> backingSet, ImmutableList<E> backingList) {
        this.backingSet = backingSet;
        this.backingList = backingList;
    }

    @Override
    public Comparator<? super E> comparator() {
        return this.backingSet.comparator();
    }

    @Override
    public boolean contains(@Nullable Object target) {
        return this.backingSet.indexOf(target) >= 0;
    }

    @Override
    public int indexOf(@Nullable Object target) {
        return this.backingSet.indexOf(target);
    }

    @Override
    public int lastIndexOf(@Nullable Object target) {
        return this.backingSet.indexOf(target);
    }

    @Override
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
        return fromIndex == toIndex ? ImmutableList.of() : new RegularImmutableSortedSet<E>(this.backingList.subList(fromIndex, toIndex), this.backingSet.comparator()).asList();
    }

    @Override
    Object writeReplace() {
        return new ImmutableAsList.SerializedForm(this.backingSet);
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.backingList.iterator();
    }

    @Override
    public E get(int index) {
        return this.backingList.get(index);
    }

    @Override
    public UnmodifiableListIterator<E> listIterator() {
        return this.backingList.listIterator();
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int index) {
        return this.backingList.listIterator(index);
    }

    @Override
    public int size() {
        return this.backingList.size();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.backingList.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.backingList.hashCode();
    }

    @Override
    boolean isPartialView() {
        return this.backingList.isPartialView();
    }
}

