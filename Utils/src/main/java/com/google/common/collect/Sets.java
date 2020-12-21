/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableEnumSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class Sets {
    private Sets() {
    }

    @GwtCompatible(serializable=true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E anElement, E ... otherElements) {
        return new ImmutableEnumSet<E[]>(EnumSet.of(anElement, otherElements));
    }

    @GwtCompatible(serializable=true)
    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> elements) {
        Iterator<E> iterator = elements.iterator();
        if (!iterator.hasNext()) {
            return ImmutableSet.of();
        }
        if (elements instanceof EnumSet) {
            EnumSet enumSetClone = EnumSet.copyOf((EnumSet)elements);
            return new ImmutableEnumSet(enumSetClone);
        }
        Enum first = (Enum)iterator.next();
        EnumSet<Enum> set = EnumSet.of(first);
        while (iterator.hasNext()) {
            set.add((Enum)iterator.next());
        }
        return new ImmutableEnumSet<Enum>(set);
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> elementType) {
        Preconditions.checkNotNull(iterable);
        EnumSet<E> set = EnumSet.noneOf(elementType);
        Iterables.addAll(set, iterable);
        return set;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static <E> HashSet<E> newHashSet(E ... elements) {
        HashSet<E> set = Sets.newHashSetWithExpectedSize(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int expectedSize) {
        return new HashSet(Maps.capacity(expectedSize));
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> elements) {
        return elements instanceof Collection ? new HashSet<E>(Collections2.cast(elements)) : Sets.newHashSet(elements.iterator());
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> elements) {
        HashSet<E> set = Sets.newHashSet();
        while (elements.hasNext()) {
            set.add(elements.next());
        }
        return set;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return new LinkedHashSet<E>(Collections2.cast(elements));
        }
        LinkedHashSet<E> set = Sets.newLinkedHashSet();
        for (E element : elements) {
            set.add(element);
        }
        return set;
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> elements) {
        TreeSet<Comparable> set = Sets.newTreeSet();
        for (Comparable element : elements) {
            set.add(element);
        }
        return set;
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<E>(Preconditions.checkNotNull(comparator));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Sets.newSetFromMap(Maps.newIdentityHashMap());
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet)collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
        Class type = ((Enum)collection.iterator().next()).getDeclaringClass();
        return Sets.makeComplementByHand(collection, type);
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> type) {
        Preconditions.checkNotNull(collection);
        return collection instanceof EnumSet ? EnumSet.complementOf((EnumSet)collection) : Sets.makeComplementByHand(collection, type);
    }

    private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> type) {
        EnumSet<E> result = EnumSet.allOf(type);
        result.removeAll(collection);
        return result;
    }

    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return new SetFromMap<E>(map);
    }

    public static <E> SetView<E> union(final Set<? extends E> set1, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set1, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final SetView<? extends E> set2minus1 = Sets.difference(set2, set1);
        return new SetView<E>(){

            @Override
            public int size() {
                return set1.size() + set2minus1.size();
            }

            @Override
            public boolean isEmpty() {
                return set1.isEmpty() && set2.isEmpty();
            }

            @Override
            public Iterator<E> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(set1.iterator(), set2minus1.iterator()));
            }

            @Override
            public boolean contains(Object object) {
                return set1.contains(object) || set2.contains(object);
            }

            @Override
            public <S extends Set<E>> S copyInto(S set) {
                set.addAll(set1);
                set.addAll(set2);
                return set;
            }

            @Override
            public ImmutableSet<E> immutableCopy() {
                return ((ImmutableSet.Builder)((ImmutableSet.Builder)new ImmutableSet.Builder().addAll((Iterable)set1)).addAll((Iterable)set2)).build();
            }
        };
    }

    public static <E> SetView<E> intersection(final Set<E> set1, final Set<?> set2) {
        Preconditions.checkNotNull(set1, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final Predicate<?> inSet2 = Predicates.in(set2);
        return new SetView<E>(){

            @Override
            public Iterator<E> iterator() {
                return Iterators.filter(set1.iterator(), inSet2);
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

            @Override
            public boolean isEmpty() {
                return !this.iterator().hasNext();
            }

            @Override
            public boolean contains(Object object) {
                return set1.contains(object) && set2.contains(object);
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return set1.containsAll(collection) && set2.containsAll(collection);
            }
        };
    }

    public static <E> SetView<E> difference(final Set<E> set1, final Set<?> set2) {
        Preconditions.checkNotNull(set1, "set1");
        Preconditions.checkNotNull(set2, "set2");
        final Predicate<?> notInSet2 = Predicates.not(Predicates.in(set2));
        return new SetView<E>(){

            @Override
            public Iterator<E> iterator() {
                return Iterators.filter(set1.iterator(), notInSet2);
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

            @Override
            public boolean isEmpty() {
                return set2.containsAll(set1);
            }

            @Override
            public boolean contains(Object element) {
                return set1.contains(element) && !set2.contains(element);
            }
        };
    }

    public static <E> SetView<E> symmetricDifference(Set<? extends E> set1, Set<? extends E> set2) {
        Preconditions.checkNotNull(set1, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return Sets.difference(Sets.union(set1, set2), Sets.intersection(set1, set2));
    }

    public static <E> Set<E> filter(Set<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered instanceof FilteredSet) {
            FilteredSet filtered = (FilteredSet)unfiltered;
            Predicate<? super E> combinedPredicate = Predicates.and(filtered.predicate, predicate);
            return new FilteredSet<E>((Set)filtered.unfiltered, combinedPredicate);
        }
        return new FilteredSet<E>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> sets) {
        for (Set<B> set : sets) {
            if (!set.isEmpty()) continue;
            return ImmutableSet.of();
        }
        CartesianSet cartesianSet = new CartesianSet(sets);
        return cartesianSet;
    }

    public static <B> Set<List<B>> cartesianProduct(Set<? extends B> ... sets) {
        return Sets.cartesianProduct(Arrays.asList(sets));
    }

    @GwtCompatible(serializable=false)
    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        ImmutableSet<E> input = ImmutableSet.copyOf(set);
        Preconditions.checkArgument(input.size() <= 30, "Too many elements to create power set: %s > 30", input.size());
        return new PowerSet<E>(input);
    }

    static int hashCodeImpl(Set<?> s) {
        int hashCode = 0;
        for (Object o : s) {
            hashCode += o != null ? o.hashCode() : 0;
        }
        return hashCode;
    }

    static boolean equalsImpl(Set<?> s, @Nullable Object object) {
        if (s == object) {
            return true;
        }
        if (object instanceof Set) {
            Set o = (Set)object;
            try {
                return s.size() == o.size() && s.containsAll(o);
            }
            catch (NullPointerException ignored) {
                return false;
            }
            catch (ClassCastException ignored) {
                return false;
            }
        }
        return false;
    }

    static <A, B> Set<B> transform(Set<A> set, InvertibleFunction<A, B> bijection) {
        return new TransformedSet<A, B>(Preconditions.checkNotNull(set, "set"), Preconditions.checkNotNull(bijection, "bijection"));
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class TransformedSet<A, B>
    extends AbstractSet<B> {
        final Set<A> delegate;
        final InvertibleFunction<A, B> bijection;

        TransformedSet(Set<A> delegate, InvertibleFunction<A, B> bijection) {
            this.delegate = delegate;
            this.bijection = bijection;
        }

        @Override
        public Iterator<B> iterator() {
            return Iterators.transform(this.delegate.iterator(), this.bijection);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        @Override
        public boolean contains(Object o) {
            Object b = o;
            A a = this.bijection.invert(b);
            return this.delegate.contains(a) && Objects.equal(this.bijection.apply(a), o);
        }

        @Override
        public boolean add(B b) {
            return this.delegate.add(this.bijection.invert(b));
        }

        @Override
        public boolean remove(Object o) {
            return this.contains(o) && this.delegate.remove(this.bijection.invert(o));
        }

        @Override
        public void clear() {
            this.delegate.clear();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class InvertibleFunction<A, B>
    implements Function<A, B> {
        InvertibleFunction() {
        }

        abstract A invert(B var1);

        public InvertibleFunction<B, A> inverse() {
            return new InvertibleFunction<B, A>(){

                @Override
                public A apply(B b) {
                    return InvertibleFunction.this.invert(b);
                }

                @Override
                B invert(A a) {
                    return InvertibleFunction.this.apply(a);
                }

                @Override
                public InvertibleFunction<A, B> inverse() {
                    return InvertibleFunction.this;
                }
            };
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class PowerSet<E>
    extends AbstractSet<Set<E>> {
        final ImmutableSet<E> inputSet;
        final ImmutableList<E> inputList;
        final int powerSetSize;

        PowerSet(ImmutableSet<E> input) {
            this.inputSet = input;
            this.inputList = input.asList();
            this.powerSetSize = 1 << input.size();
        }

        @Override
        public int size() {
            return this.powerSetSize;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(this.powerSetSize){

                @Override
                protected Set<E> get(final int setBits) {
                    return new AbstractSet<E>(){

                        @Override
                        public int size() {
                            return Integer.bitCount(setBits);
                        }

                        @Override
                        public Iterator<E> iterator() {
                            return new BitFilteredSetIterator(PowerSet.this.inputList, setBits);
                        }
                    };
                }
            };
        }

        @Override
        public boolean contains(@Nullable Object obj) {
            if (obj instanceof Set) {
                Set set = (Set)obj;
                return this.inputSet.containsAll(set);
            }
            return false;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof PowerSet) {
                PowerSet that = (PowerSet)obj;
                return this.inputSet.equals(that.inputSet);
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return this.inputSet.hashCode() << this.inputSet.size() - 1;
        }

        @Override
        public String toString() {
            return "powerSet(" + this.inputSet + ")";
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private static final class BitFilteredSetIterator<E>
        extends UnmodifiableIterator<E> {
            final ImmutableList<E> input;
            int remainingSetBits;

            BitFilteredSetIterator(ImmutableList<E> input, int allSetBits) {
                this.input = input;
                this.remainingSetBits = allSetBits;
            }

            @Override
            public boolean hasNext() {
                return this.remainingSetBits != 0;
            }

            @Override
            public E next() {
                int index = Integer.numberOfTrailingZeros(this.remainingSetBits);
                if (index == 32) {
                    throw new NoSuchElementException();
                }
                int currentElementMask = 1 << index;
                this.remainingSetBits &= ~currentElementMask;
                return this.input.get(index);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class CartesianSet<B>
    extends AbstractSet<List<B>> {
        final ImmutableList<Axis> axes;
        final int size;

        CartesianSet(List<? extends Set<? extends B>> sets) {
            long dividend = 1L;
            ImmutableList.Builder builder = ImmutableList.builder();
            for (Set<B> set : sets) {
                Axis axis = new Axis(set, (int)dividend);
                builder.add(axis);
                Preconditions.checkArgument((dividend *= (long)axis.size()) <= Integer.MAX_VALUE, "cartesian product is too big");
            }
            this.axes = builder.build();
            this.size = Ints.checkedCast(dividend);
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public UnmodifiableIterator<List<B>> iterator() {
            return new UnmodifiableIterator<List<B>>(){
                int index;

                @Override
                public boolean hasNext() {
                    return this.index < CartesianSet.this.size;
                }

                @Override
                public List<B> next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Object[] tuple = new Object[CartesianSet.this.axes.size()];
                    for (int i = 0; i < tuple.length; ++i) {
                        tuple[i] = ((Axis)CartesianSet.this.axes.get(i)).getForIndex(this.index);
                    }
                    ++this.index;
                    ImmutableList<Object> result = ImmutableList.copyOf(tuple);
                    return result;
                }
            };
        }

        @Override
        public boolean contains(Object element) {
            if (!(element instanceof List)) {
                return false;
            }
            List tuple = (List)element;
            int dimensions = this.axes.size();
            if (tuple.size() != dimensions) {
                return false;
            }
            for (int i = 0; i < dimensions; ++i) {
                if (((Axis)this.axes.get(i)).contains(tuple.get(i))) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof CartesianSet) {
                CartesianSet that = (CartesianSet)object;
                return this.axes.equals(that.axes);
            }
            return super.equals(object);
        }

        @Override
        public int hashCode() {
            int adjust = this.size - 1;
            for (int i = 0; i < this.axes.size(); ++i) {
                adjust *= 31;
            }
            return this.axes.hashCode() + adjust;
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private class Axis {
            final ImmutableSet<? extends B> choices;
            final ImmutableList<? extends B> choicesList;
            final int dividend;

            Axis(Set<? extends B> set, int dividend) {
                this.choices = ImmutableSet.copyOf(set);
                this.choicesList = this.choices.asList();
                this.dividend = dividend;
            }

            int size() {
                return this.choices.size();
            }

            B getForIndex(int index) {
                return this.choicesList.get(index / this.dividend % this.size());
            }

            boolean contains(Object target) {
                return this.choices.contains(target);
            }

            public boolean equals(Object obj) {
                if (obj instanceof Axis) {
                    Axis that = (Axis)obj;
                    return this.choices.equals(that.choices);
                }
                return false;
            }

            public int hashCode() {
                return CartesianSet.this.size / this.choices.size() * this.choices.hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class FilteredSet<E>
    extends Collections2.FilteredCollection<E>
    implements Set<E> {
        FilteredSet(Set<E> unfiltered, Predicate<? super E> predicate) {
            super(unfiltered, predicate);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static abstract class SetView<E>
    extends AbstractSet<E> {
        private SetView() {
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf(this);
        }

        public <S extends Set<E>> S copyInto(S set) {
            set.addAll(this);
            return set;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SetFromMap<E>
    extends AbstractSet<E>
    implements Set<E>,
    Serializable {
        private final Map<E, Boolean> m;
        private transient Set<E> s;
        @GwtIncompatible(value="not needed in emulated source")
        private static final long serialVersionUID = 0L;

        SetFromMap(Map<E, Boolean> map) {
            Preconditions.checkArgument(map.isEmpty(), "Map is non-empty");
            this.m = map;
            this.s = map.keySet();
        }

        @Override
        public void clear() {
            this.m.clear();
        }

        @Override
        public int size() {
            return this.m.size();
        }

        @Override
        public boolean isEmpty() {
            return this.m.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return this.m.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            return this.m.remove(o) != null;
        }

        @Override
        public boolean add(E e) {
            return this.m.put(e, Boolean.TRUE) == null;
        }

        @Override
        public Iterator<E> iterator() {
            return this.s.iterator();
        }

        @Override
        public Object[] toArray() {
            return this.s.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return this.s.toArray(a);
        }

        @Override
        public String toString() {
            return this.s.toString();
        }

        @Override
        public int hashCode() {
            return ((Object)this.s).hashCode();
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return this == object || ((Object)this.s).equals(object);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.s.containsAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return this.s.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return this.s.retainAll(c);
        }

        @GwtIncompatible(value="java.io.ObjectInputStream")
        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.s = this.m.keySet();
        }
    }
}

