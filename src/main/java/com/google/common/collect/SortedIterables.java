/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedIterable;
import com.google.common.collect.TreeMultiset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
final class SortedIterables {
    private SortedIterables() {
    }

    public static boolean hasSameComparator(Comparator<?> comparator, Iterable<?> elements) {
        Comparator<Object> comparator2;
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(elements);
        if (elements instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)elements;
            comparator2 = sortedSet.comparator();
            if (comparator2 == null) {
                comparator2 = Ordering.natural();
            }
        } else {
            comparator2 = elements instanceof SortedIterable ? ((SortedIterable)elements).comparator() : null;
        }
        return ((Object)comparator).equals(comparator2);
    }

    public static <E> Collection<E> sortedUnique(Comparator<? super E> comparator, Iterator<E> elements) {
        TreeSet<? super E> sortedSet = Sets.newTreeSet(comparator);
        Iterators.addAll(sortedSet, elements);
        return sortedSet;
    }

    public static <E> Collection<E> sortedUnique(Comparator<? super E> comparator, Iterable<E> elements) {
        if (elements instanceof Multiset) {
            elements = ((Multiset)elements).elementSet();
        }
        if (elements instanceof Set) {
            if (SortedIterables.hasSameComparator(comparator, elements)) {
                return (Set)elements;
            }
            ArrayList<E> list = Lists.newArrayList(elements);
            Collections.sort(list, comparator);
            return list;
        }
        Object[] array = Iterables.toArray(elements);
        if (!SortedIterables.hasSameComparator(comparator, elements)) {
            Arrays.sort(array, comparator);
        }
        return SortedIterables.uniquifySortedArray(comparator, array);
    }

    private static <E> Collection<E> uniquifySortedArray(Comparator<? super E> comparator, E[] array) {
        if (array.length == 0) {
            return Collections.emptySet();
        }
        int length = 1;
        for (int i = 1; i < array.length; ++i) {
            int cmp = comparator.compare(array[i], array[length - 1]);
            if (cmp == 0) continue;
            array[length++] = array[i];
        }
        if (length < array.length) {
            array = ObjectArrays.arraysCopyOf(array, length);
        }
        return Arrays.asList(array);
    }

    public static <E> Collection<Multiset.Entry<E>> sortedCounts(Comparator<? super E> comparator, Iterator<E> elements) {
        TreeMultiset<E> multiset = TreeMultiset.create(comparator);
        Iterators.addAll(multiset, elements);
        return multiset.entrySet();
    }

    public static <E> Collection<Multiset.Entry<E>> sortedCounts(Comparator<? super E> comparator, Iterable<E> elements) {
        if (elements instanceof Multiset) {
            Multiset multiset = (Multiset)elements;
            if (SortedIterables.hasSameComparator(comparator, elements)) {
                return multiset.entrySet();
            }
            ArrayList entries = Lists.newArrayList(multiset.entrySet());
            Collections.sort(entries, Ordering.from(comparator).onResultOf(new Function<Multiset.Entry<E>, E>(){

                @Override
                public E apply(Multiset.Entry<E> entry) {
                    return entry.getElement();
                }
            }));
            return entries;
        }
        if (elements instanceof Set) {
            ArrayList<E> sortedElements;
            if (SortedIterables.hasSameComparator(comparator, elements)) {
                sortedElements = (ArrayList<E>)elements;
            } else {
                ArrayList<E> list = Lists.newArrayList(elements);
                Collections.sort(list, comparator);
                sortedElements = list;
            }
            return SortedIterables.singletonEntries(sortedElements);
        }
        if (SortedIterables.hasSameComparator(comparator, elements)) {
            Object current = null;
            int currentCount = 0;
            ArrayList<Multiset.Entry<Multiset.Entry<Object>>> sortedEntries = Lists.newArrayList();
            for (E e : elements) {
                if (currentCount > 0) {
                    if (comparator.compare(current, e) == 0) {
                        ++currentCount;
                        continue;
                    }
                    sortedEntries.add(Multisets.immutableEntry(current, currentCount));
                    current = e;
                    currentCount = 1;
                    continue;
                }
                current = e;
                currentCount = 1;
            }
            if (currentCount > 0) {
                sortedEntries.add(Multisets.immutableEntry(current, currentCount));
            }
            return sortedEntries;
        }
        TreeMultiset<E> multiset = TreeMultiset.create(comparator);
        Iterables.addAll(multiset, elements);
        return multiset.entrySet();
    }

    static <E> Collection<Multiset.Entry<E>> singletonEntries(Collection<E> set) {
        return Collections2.transform(set, new Function<E, Multiset.Entry<E>>(){

            @Override
            public Multiset.Entry<E> apply(E elem) {
                return Multisets.immutableEntry(elem, 1);
            }
        });
    }
}

