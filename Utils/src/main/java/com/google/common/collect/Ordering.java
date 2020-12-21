/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ByFunctionOrdering;
import com.google.common.collect.ComparatorOrdering;
import com.google.common.collect.CompoundOrdering;
import com.google.common.collect.ExplicitOrdering;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LexicographicalOrdering;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.NullsFirstOrdering;
import com.google.common.collect.NullsLastOrdering;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.ReverseOrdering;
import com.google.common.collect.UsingToStringOrdering;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public abstract class Ordering<T>
implements Comparator<T> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    @GwtCompatible(serializable=true)
    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> from(Comparator<T> comparator) {
        return comparator instanceof Ordering ? (Ordering<T>)comparator : new ComparatorOrdering<T>(comparator);
    }

    @Deprecated
    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> from(Ordering<T> ordering) {
        return Preconditions.checkNotNull(ordering);
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> explicit(List<T> valuesInOrder) {
        return new ExplicitOrdering<T>(valuesInOrder);
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> explicit(T leastValue, T ... remainingValuesInOrder) {
        return Ordering.explicit(Lists.asList(leastValue, remainingValuesInOrder));
    }

    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }

    @GwtCompatible(serializable=true)
    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }

    @GwtCompatible(serializable=true)
    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> comparators) {
        return new CompoundOrdering(comparators);
    }

    protected Ordering() {
    }

    @GwtCompatible(serializable=true)
    public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) {
        return new CompoundOrdering<U>(this, Preconditions.checkNotNull(secondaryComparator));
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new ByFunctionOrdering<F, T>(function, this);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new LexicographicalOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering(this);
    }

    @GwtCompatible(serializable=true)
    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering(this);
    }

    @Override
    public abstract int compare(@Nullable T var1, @Nullable T var2);

    @Beta
    public <E extends T> List<E> leastOf(Iterable<E> iterable, int k) {
        Object[] resultArray;
        Preconditions.checkArgument(k >= 0, "%d is negative", k);
        Object[] values = Iterables.toArray(iterable);
        if (values.length <= k) {
            Arrays.sort(values, this);
            resultArray = values;
        } else {
            this.quicksortLeastK(values, 0, values.length - 1, k);
            Object[] tmp = new Object[k];
            resultArray = tmp;
            System.arraycopy(values, 0, resultArray, 0, k);
        }
        return Collections.unmodifiableList(Arrays.asList(resultArray));
    }

    @Beta
    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int k) {
        return this.reverse().leastOf(iterable, k);
    }

    private <E extends T> void quicksortLeastK(E[] values, int left, int right, int k) {
        if (right > left) {
            int pivotIndex = left + right >>> 1;
            int pivotNewIndex = this.partition(values, left, right, pivotIndex);
            this.quicksortLeastK(values, left, pivotNewIndex - 1, k);
            if (pivotNewIndex < k) {
                this.quicksortLeastK(values, pivotNewIndex + 1, right, k);
            }
        }
    }

    private <E extends T> int partition(E[] values, int left, int right, int pivotIndex) {
        E pivotValue = values[pivotIndex];
        values[pivotIndex] = values[right];
        values[right] = pivotValue;
        int storeIndex = left;
        for (int i = left; i < right; ++i) {
            if (this.compare(values[i], pivotValue) >= 0) continue;
            ObjectArrays.swap(values, storeIndex, i);
            ++storeIndex;
        }
        ObjectArrays.swap(values, right, storeIndex);
        return storeIndex;
    }

    public int binarySearch(List<? extends T> sortedList, @Nullable T key) {
        return Collections.binarySearch(sortedList, key, this);
    }

    public <E extends T> List<E> sortedCopy(Iterable<E> iterable) {
        ArrayList<E> list = Lists.newArrayList(iterable);
        Collections.sort(list, this);
        return list;
    }

    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.copyOf(this.sortedCopy(iterable));
    }

    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<T> it = iterable.iterator();
        if (it.hasNext()) {
            T prev = it.next();
            while (it.hasNext()) {
                T next = it.next();
                if (this.compare(prev, next) > 0) {
                    return false;
                }
                prev = next;
            }
        }
        return true;
    }

    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<T> it = iterable.iterator();
        if (it.hasNext()) {
            T prev = it.next();
            while (it.hasNext()) {
                T next = it.next();
                if (this.compare(prev, next) >= 0) {
                    return false;
                }
                prev = next;
            }
        }
        return true;
    }

    public <E extends T> E max(Iterable<E> iterable) {
        Iterator<E> iterator = iterable.iterator();
        E maxSoFar = iterator.next();
        while (iterator.hasNext()) {
            maxSoFar = this.max(maxSoFar, iterator.next());
        }
        return maxSoFar;
    }

    public <E extends T> E max(@Nullable E a, @Nullable E b, @Nullable E c, E ... rest) {
        E maxSoFar = this.max(this.max(a, b), c);
        for (E r : rest) {
            maxSoFar = this.max(maxSoFar, r);
        }
        return maxSoFar;
    }

    public <E extends T> E max(@Nullable E a, @Nullable E b) {
        return this.compare(a, b) >= 0 ? a : b;
    }

    public <E extends T> E min(Iterable<E> iterable) {
        Iterator<E> iterator = iterable.iterator();
        E minSoFar = iterator.next();
        while (iterator.hasNext()) {
            minSoFar = this.min(minSoFar, iterator.next());
        }
        return minSoFar;
    }

    public <E extends T> E min(@Nullable E a, @Nullable E b, @Nullable E c, E ... rest) {
        E minSoFar = this.min(this.min(a, b), c);
        for (E r : rest) {
            minSoFar = this.min(minSoFar, r);
        }
        return minSoFar;
    }

    public <E extends T> E min(@Nullable E a, @Nullable E b) {
        return this.compare(a, b) <= 0 ? a : b;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @VisibleForTesting
    static class ArbitraryOrdering
    extends Ordering<Object> {
        private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function<Object, Integer>(){
            final AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Integer apply(Object from) {
                return this.counter.getAndIncrement();
            }
        });

        ArbitraryOrdering() {
        }

        @Override
        public int compare(Object left, Object right) {
            int rightCode;
            if (left == right) {
                return 0;
            }
            int leftCode = this.identityHashCode(left);
            if (leftCode != (rightCode = this.identityHashCode(right))) {
                return leftCode < rightCode ? -1 : 1;
            }
            int result = this.uids.get(left).compareTo(this.uids.get(right));
            if (result == 0) {
                throw new AssertionError();
            }
            return result;
        }

        public String toString() {
            return "Ordering.arbitrary()";
        }

        int identityHashCode(Object object) {
            return System.identityHashCode(object);
        }
    }

    private static class ArbitraryOrderingHolder {
        static final Ordering<Object> ARBITRARY_ORDERING = new ArbitraryOrdering();

        private ArbitraryOrderingHolder() {
        }
    }

    @VisibleForTesting
    static class IncomparableValueException
    extends ClassCastException {
        final Object value;
        private static final long serialVersionUID = 0L;

        IncomparableValueException(Object value) {
            super("Cannot compare value: " + value);
            this.value = value;
        }
    }
}

