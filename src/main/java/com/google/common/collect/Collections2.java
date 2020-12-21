/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public final class Collections2 {
    static final Joiner STANDARD_JOINER = Joiner.on(", ");

    private Collections2() {
    }

    public static <E> Collection<E> filter(Collection<E> unfiltered, Predicate<? super E> predicate) {
        if (unfiltered instanceof FilteredCollection) {
            return ((FilteredCollection)unfiltered).createCombined(predicate);
        }
        return new FilteredCollection<E>(Preconditions.checkNotNull(unfiltered), Preconditions.checkNotNull(predicate));
    }

    static boolean safeContains(Collection<?> collection, Object object) {
        try {
            return collection.contains(object);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    public static <F, T> Collection<T> transform(Collection<F> fromCollection, Function<? super F, T> function) {
        return new TransformedCollection<F, T>(fromCollection, function);
    }

    static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
        Preconditions.checkNotNull(self);
        for (Object o : c) {
            if (self.contains(o)) continue;
            return false;
        }
        return true;
    }

    static String toStringImpl(final Collection<?> collection) {
        StringBuilder sb = Collections2.newStringBuilderForCollection(collection.size()).append('[');
        STANDARD_JOINER.appendTo(sb, Iterables.transform(collection, new Function<Object, Object>(){

            @Override
            public Object apply(Object input) {
                return input == collection ? "(this Collection)" : input;
            }
        }));
        return sb.append(']').toString();
    }

    static StringBuilder newStringBuilderForCollection(int size) {
        Preconditions.checkArgument(size >= 0, "size must be non-negative");
        return new StringBuilder((int)Math.min((long)size * 8L, 0x40000000L));
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection)iterable;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class TransformedCollection<F, T>
    extends AbstractCollection<T> {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;

        TransformedCollection(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
            this.fromCollection = Preconditions.checkNotNull(fromCollection);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromCollection.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }

        @Override
        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }

        @Override
        public int size() {
            return this.fromCollection.size();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class FilteredCollection<E>
    implements Collection<E> {
        final Collection<E> unfiltered;
        final Predicate<? super E> predicate;

        FilteredCollection(Collection<E> unfiltered, Predicate<? super E> predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }

        FilteredCollection<E> createCombined(Predicate<? super E> newPredicate) {
            return new FilteredCollection<E>(this.unfiltered, Predicates.and(this.predicate, newPredicate));
        }

        @Override
        public boolean add(E element) {
            Preconditions.checkArgument(this.predicate.apply(element));
            return this.unfiltered.add(element);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            for (E element : collection) {
                Preconditions.checkArgument(this.predicate.apply(element));
            }
            return this.unfiltered.addAll(collection);
        }

        @Override
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        @Override
        public boolean contains(Object element) {
            try {
                Object e = element;
                return this.predicate.apply(e) && this.unfiltered.contains(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            for (Object element : collection) {
                if (this.contains(element)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean isEmpty() {
            return !Iterators.any(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public boolean remove(Object element) {
            try {
                Object e = element;
                return this.predicate.apply(e) && this.unfiltered.remove(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public boolean removeAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Predicate combinedPredicate = new Predicate<E>(){

                @Override
                public boolean apply(E input) {
                    return FilteredCollection.this.predicate.apply(input) && collection.contains(input);
                }
            };
            return Iterables.removeIf(this.unfiltered, combinedPredicate);
        }

        @Override
        public boolean retainAll(final Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            Predicate combinedPredicate = new Predicate<E>(){

                @Override
                public boolean apply(E input) {
                    return FilteredCollection.this.predicate.apply(input) && !collection.contains(input);
                }
            };
            return Iterables.removeIf(this.unfiltered, combinedPredicate);
        }

        @Override
        public int size() {
            return Iterators.size(this.iterator());
        }

        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }

        @Override
        public <T> T[] toArray(T[] array) {
            return Lists.newArrayList(this.iterator()).toArray(array);
        }

        public String toString() {
            return Iterators.toString(this.iterator());
        }
    }
}

