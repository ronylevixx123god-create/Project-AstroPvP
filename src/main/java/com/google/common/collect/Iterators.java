/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class Iterators {
    static final UnmodifiableIterator<Object> EMPTY_ITERATOR = new UnmodifiableIterator<Object>(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }
    };
    private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator<Object>(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
    };

    private Iterators() {
    }

    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return EMPTY_ITERATOR;
    }

    static <T> Iterator<T> emptyModifiableIterator() {
        return EMPTY_MODIFIABLE_ITERATOR;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        if (iterator instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator)iterator;
        }
        return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        };
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> iterator) {
        return Preconditions.checkNotNull(iterator);
    }

    public static int size(Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            ++count;
        }
        return count;
    }

    public static boolean contains(Iterator<?> iterator, @Nullable Object element) {
        if (element == null) {
            while (iterator.hasNext()) {
                if (iterator.next() != null) continue;
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!element.equals(iterator.next())) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
        Preconditions.checkNotNull(elementsToRemove);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (!elementsToRemove.contains(removeFrom.next())) continue;
            removeFrom.remove();
            modified = true;
        }
        return modified;
    }

    public static <T> boolean removeIf(Iterator<T> removeFrom, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (!predicate.apply(removeFrom.next())) continue;
            removeFrom.remove();
            modified = true;
        }
        return modified;
    }

    public static boolean retainAll(Iterator<?> removeFrom, Collection<?> elementsToRetain) {
        Preconditions.checkNotNull(elementsToRetain);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (elementsToRetain.contains(removeFrom.next())) continue;
            removeFrom.remove();
            modified = true;
        }
        return modified;
    }

    public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
        while (iterator1.hasNext()) {
            Object o2;
            if (!iterator2.hasNext()) {
                return false;
            }
            Object o1 = iterator1.next();
            if (Objects.equal(o1, o2 = iterator2.next())) continue;
            return false;
        }
        return !iterator2.hasNext();
    }

    public static String toString(Iterator<?> iterator) {
        if (!iterator.hasNext()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append('[').append(iterator.next());
        while (iterator.hasNext()) {
            builder.append(", ").append(iterator.next());
        }
        return builder.append(']').toString();
    }

    public static <T> T getOnlyElement(Iterator<T> iterator) {
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <" + first);
        for (int i = 0; i < 4 && iterator.hasNext(); ++i) {
            sb.append(", " + iterator.next());
        }
        if (iterator.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> T getOnlyElement(Iterator<T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? Iterators.getOnlyElement(iterator) : defaultValue;
    }

    @GwtIncompatible(value="Array.newInstance(Class, int)")
    public static <T> T[] toArray(Iterator<? extends T> iterator, Class<T> type) {
        ArrayList<? extends T> list = Lists.newArrayList(iterator);
        return Iterables.toArray(list, type);
    }

    public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        Preconditions.checkNotNull(addTo);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    public static int frequency(Iterator<?> iterator, @Nullable Object element) {
        int result = 0;
        if (element == null) {
            while (iterator.hasNext()) {
                if (iterator.next() != null) continue;
                ++result;
            }
        } else {
            while (iterator.hasNext()) {
                if (!element.equals(iterator.next())) continue;
                ++result;
            }
        }
        return result;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>(){
            Iterator<T> iterator = Iterators.emptyIterator();
            Iterator<T> removeFrom;

            @Override
            public boolean hasNext() {
                if (!this.iterator.hasNext()) {
                    this.iterator = iterable.iterator();
                }
                return this.iterator.hasNext();
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.iterator;
                return this.iterator.next();
            }

            @Override
            public void remove() {
                Preconditions.checkState(this.removeFrom != null, "no calls to next() since last call to remove()");
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }

    public static <T> Iterator<T> cycle(T ... elements) {
        return Iterators.cycle(Lists.newArrayList(elements));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        return Iterators.concat(Arrays.asList(a, b).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        return Iterators.concat(Arrays.asList(a, b, c).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(d);
        return Iterators.concat(Arrays.asList(a, b, c, d).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> ... inputs) {
        return Iterators.concat(ImmutableList.copyOf(inputs).iterator());
    }

    public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
        Preconditions.checkNotNull(inputs);
        return new Iterator<T>(){
            Iterator<? extends T> current = Iterators.emptyIterator();
            Iterator<? extends T> removeFrom;

            @Override
            public boolean hasNext() {
                boolean currentHasNext;
                while (!(currentHasNext = Preconditions.checkNotNull(this.current).hasNext()) && inputs.hasNext()) {
                    this.current = (Iterator)inputs.next();
                }
                return currentHasNext;
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.current;
                return this.current.next();
            }

            @Override
            public void remove() {
                Preconditions.checkState(this.removeFrom != null, "no calls to next() since last call to remove()");
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator, int size) {
        return Iterators.partitionImpl(iterator, size, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator, int size) {
        return Iterators.partitionImpl(iterator, size, true);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(size > 0);
        return new UnmodifiableIterator<List<T>>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public List<T> next() {
                int count;
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                Object[] array = new Object[size];
                for (count = 0; count < size && iterator.hasNext(); ++count) {
                    array[count] = iterator.next();
                }
                for (int i = count; i < size; ++i) {
                    array[i] = null;
                }
                List<Object> list = Collections.unmodifiableList(Arrays.asList(array));
                return pad || count == size ? list : list.subList(0, count);
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>(){

            @Override
            protected T computeNext() {
                while (unfiltered.hasNext()) {
                    Object element = unfiltered.next();
                    if (!predicate.apply(element)) continue;
                    return element;
                }
                return this.endOfData();
            }
        };
    }

    @GwtIncompatible(value="Class.isInstance")
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> unfiltered, Class<T> type) {
        return Iterators.filter(unfiltered, Predicates.instanceOf(type));
    }

    public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (!predicate.apply(element)) continue;
            return true;
        }
        return false;
    }

    public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (predicate.apply(element)) continue;
            return false;
        }
        return true;
    }

    public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) {
        return (T)Iterators.filter(iterator, predicate).next();
    }

    public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate, @Nullable T defaultValue) {
        UnmodifiableIterator<T> filteredIterator = Iterators.filter(iterator, predicate);
        return (T)(filteredIterator.hasNext() ? filteredIterator.next() : defaultValue);
    }

    public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (iterator.hasNext()) {
            T current = iterator.next();
            if (predicate.apply(current)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    public static <F, T> Iterator<T> transform(final Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(fromIterator);
        Preconditions.checkNotNull(function);
        return new Iterator<T>(){

            @Override
            public boolean hasNext() {
                return fromIterator.hasNext();
            }

            @Override
            public T next() {
                Object from = fromIterator.next();
                return function.apply(from);
            }

            @Override
            public void remove() {
                fromIterator.remove();
            }
        };
    }

    public static <T> T get(Iterator<T> iterator, int position) {
        Iterators.checkNonnegative(position);
        int skipped = 0;
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (skipped++ != position) continue;
            return t;
        }
        throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
    }

    private static void checkNonnegative(int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
        }
    }

    public static <T> T get(Iterator<T> iterator, int position, @Nullable T defaultValue) {
        Iterators.checkNonnegative(position);
        try {
            return Iterators.get(iterator, position);
        }
        catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    public static <T> T getNext(Iterator<T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    public static <T> T getLast(Iterator<T> iterator) {
        T current;
        do {
            current = iterator.next();
        } while (iterator.hasNext());
        return current;
    }

    public static <T> T getLast(Iterator<T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? Iterators.getLast(iterator) : defaultValue;
    }

    @Beta
    public static <T> int skip(Iterator<T> iterator, int numberToSkip) {
        int i;
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(numberToSkip >= 0, "number to skip cannot be negative");
        for (i = 0; i < numberToSkip && iterator.hasNext(); ++i) {
            iterator.next();
        }
        return i;
    }

    public static <T> Iterator<T> limit(final Iterator<T> iterator, final int limitSize) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(limitSize >= 0, "limit is negative");
        return new Iterator<T>(){
            private int count;

            @Override
            public boolean hasNext() {
                return this.count < limitSize && iterator.hasNext();
            }

            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.count;
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                Object next = iterator.next();
                iterator.remove();
                return next;
            }
        };
    }

    static void clear(Iterator<?> iterator) {
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public static <T> UnmodifiableIterator<T> forArray(final T ... array) {
        Preconditions.checkNotNull(array);
        return new AbstractIndexedListIterator<T>(array.length){

            @Override
            protected T get(int index) {
                return array[index];
            }
        };
    }

    static <T> UnmodifiableIterator<T> forArray(final T[] array, final int offset, int length) {
        Preconditions.checkArgument(length >= 0);
        int end = offset + length;
        Preconditions.checkPositionIndexes(offset, end, array.length);
        return new AbstractIndexedListIterator<T>(length){

            @Override
            protected T get(int index) {
                return array[offset + index];
            }
        };
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(final @Nullable T value) {
        return new UnmodifiableIterator<T>(){
            boolean done;

            @Override
            public boolean hasNext() {
                return !this.done;
            }

            @Override
            public T next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return value;
            }
        };
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return enumeration.nextElement();
            }
        };
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new Enumeration<T>(){

            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public T nextElement() {
                return iterator.next();
            }
        };
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
        if (iterator instanceof PeekingImpl) {
            PeekingImpl peeking = (PeekingImpl)iterator;
            return peeking;
        }
        return new PeekingImpl<T>(iterator);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> iterator) {
        return Preconditions.checkNotNull(iterator);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class PeekingImpl<E>
    implements PeekingIterator<E> {
        private final Iterator<? extends E> iterator;
        private boolean hasPeeked;
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> iterator) {
            this.iterator = Preconditions.checkNotNull(iterator);
        }

        @Override
        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }

        @Override
        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E result = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return result;
        }

        @Override
        public void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }

        @Override
        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }
}

