/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
public final class Multisets {
    private Multisets() {
    }

    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        if (multiset instanceof UnmodifiableMultiset || multiset instanceof ImmutableMultiset) {
            Multiset<? extends E> result = multiset;
            return result;
        }
        return new UnmodifiableMultiset<E>(Preconditions.checkNotNull(multiset));
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> multiset) {
        return Preconditions.checkNotNull(multiset);
    }

    public static <E> Multiset.Entry<E> immutableEntry(@Nullable E e, int n) {
        return new ImmutableEntry<E>(e, n);
    }

    static <E> Multiset<E> forSet(Set<E> set) {
        return new SetMultiset<E>(set);
    }

    static int inferDistinctElements(Iterable<?> elements) {
        if (elements instanceof Multiset) {
            return ((Multiset)elements).elementSet().size();
        }
        return 11;
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset1, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset1);
        Preconditions.checkNotNull(multiset2);
        return new AbstractMultiset<E>(){

            @Override
            public int count(Object element) {
                int count1 = multiset1.count(element);
                return count1 == 0 ? 0 : Math.min(count1, multiset2.count(element));
            }

            @Override
            Set<E> createElementSet() {
                return Sets.intersection(multiset1.elementSet(), multiset2.elementSet());
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                final Iterator iterator1 = multiset1.entrySet().iterator();
                return new AbstractIterator<Multiset.Entry<E>>(){

                    @Override
                    protected Multiset.Entry<E> computeNext() {
                        while (iterator1.hasNext()) {
                            Multiset.Entry entry1 = (Multiset.Entry)iterator1.next();
                            Object element = entry1.getElement();
                            int count = Math.min(entry1.getCount(), multiset2.count(element));
                            if (count <= 0) continue;
                            return Multisets.immutableEntry(element, count);
                        }
                        return (Multiset.Entry)this.endOfData();
                    }
                };
            }

            @Override
            int distinctElements() {
                return this.elementSet().size();
            }
        };
    }

    @Beta
    public static boolean containsOccurrences(Multiset<?> superMultiset, Multiset<?> subMultiset) {
        Preconditions.checkNotNull(superMultiset);
        Preconditions.checkNotNull(subMultiset);
        for (Multiset.Entry<?> entry : subMultiset.entrySet()) {
            int superCount = superMultiset.count(entry.getElement());
            if (superCount >= entry.getCount()) continue;
            return false;
        }
        return true;
    }

    @Beta
    public static boolean retainOccurrences(Multiset<?> multisetToModify, Multiset<?> multisetToRetain) {
        return Multisets.retainOccurrencesImpl(multisetToModify, multisetToRetain);
    }

    private static <E> boolean retainOccurrencesImpl(Multiset<E> multisetToModify, Multiset<?> occurrencesToRetain) {
        Preconditions.checkNotNull(multisetToModify);
        Preconditions.checkNotNull(occurrencesToRetain);
        Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
        boolean changed = false;
        while (entryIterator.hasNext()) {
            Multiset.Entry<E> entry = entryIterator.next();
            int retainCount = occurrencesToRetain.count(entry.getElement());
            if (retainCount == 0) {
                entryIterator.remove();
                changed = true;
                continue;
            }
            if (retainCount >= entry.getCount()) continue;
            multisetToModify.setCount(entry.getElement(), retainCount);
            changed = true;
        }
        return changed;
    }

    @Beta
    public static boolean removeOccurrences(Multiset<?> multisetToModify, Multiset<?> occurrencesToRemove) {
        return Multisets.removeOccurrencesImpl(multisetToModify, occurrencesToRemove);
    }

    private static <E> boolean removeOccurrencesImpl(Multiset<E> multisetToModify, Multiset<?> occurrencesToRemove) {
        Preconditions.checkNotNull(multisetToModify);
        Preconditions.checkNotNull(occurrencesToRemove);
        boolean changed = false;
        Iterator<Multiset.Entry<E>> entryIterator = multisetToModify.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Multiset.Entry<E> entry = entryIterator.next();
            int removeCount = occurrencesToRemove.count(entry.getElement());
            if (removeCount >= entry.getCount()) {
                entryIterator.remove();
                changed = true;
                continue;
            }
            if (removeCount <= 0) continue;
            multisetToModify.remove(entry.getElement(), removeCount);
            changed = true;
        }
        return changed;
    }

    static boolean equalsImpl(Multiset<?> multiset, @Nullable Object object) {
        if (object == multiset) {
            return true;
        }
        if (object instanceof Multiset) {
            Multiset that = (Multiset)object;
            if (multiset.size() != that.size() || multiset.entrySet().size() != that.entrySet().size()) {
                return false;
            }
            for (Multiset.Entry entry : that.entrySet()) {
                if (multiset.count(entry.getElement()) == entry.getCount()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    static <E> boolean addAllImpl(Multiset<E> self, Collection<? extends E> elements) {
        if (elements.isEmpty()) {
            return false;
        }
        if (elements instanceof Multiset) {
            Multiset<E> that = Multisets.cast(elements);
            for (Multiset.Entry<E> entry : that.entrySet()) {
                self.add(entry.getElement(), entry.getCount());
            }
        } else {
            Iterators.addAll(self, elements.iterator());
        }
        return true;
    }

    static boolean removeAllImpl(Multiset<?> self, Collection<?> elementsToRemove) {
        Collection<?> collection = elementsToRemove instanceof Multiset ? ((Multiset)elementsToRemove).elementSet() : elementsToRemove;
        return self.elementSet().removeAll(collection);
    }

    static boolean retainAllImpl(Multiset<?> self, Collection<?> elementsToRetain) {
        Collection<?> collection = elementsToRetain instanceof Multiset ? ((Multiset)elementsToRetain).elementSet() : elementsToRetain;
        return self.elementSet().retainAll(collection);
    }

    static <E> int setCountImpl(Multiset<E> self, E element, int count) {
        Multisets.checkNonnegative(count, "count");
        int oldCount = self.count(element);
        int delta = count - oldCount;
        if (delta > 0) {
            self.add(element, delta);
        } else if (delta < 0) {
            self.remove(element, -delta);
        }
        return oldCount;
    }

    static <E> boolean setCountImpl(Multiset<E> self, E element, int oldCount, int newCount) {
        Multisets.checkNonnegative(oldCount, "oldCount");
        Multisets.checkNonnegative(newCount, "newCount");
        if (self.count(element) == oldCount) {
            self.setCount(element, newCount);
            return true;
        }
        return false;
    }

    static <E> Iterator<E> iteratorImpl(Multiset<E> multiset) {
        return new MultisetIteratorImpl<E>(multiset, multiset.entrySet().iterator());
    }

    static int sizeImpl(Multiset<?> multiset) {
        long size = 0L;
        for (Multiset.Entry<?> entry : multiset.entrySet()) {
            size += (long)entry.getCount();
        }
        return Ints.saturatedCast(size);
    }

    static void checkNonnegative(int count, String name) {
        Preconditions.checkArgument(count >= 0, "%s cannot be negative: %s", name, count);
    }

    static <T> Multiset<T> cast(Iterable<T> iterable) {
        return (Multiset)iterable;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class MultisetIteratorImpl<E>
    implements Iterator<E> {
        private final Multiset<E> multiset;
        private final Iterator<Multiset.Entry<E>> entryIterator;
        private Multiset.Entry<E> currentEntry;
        private int laterCount;
        private int totalCount;
        private boolean canRemove;

        MultisetIteratorImpl(Multiset<E> multiset, Iterator<Multiset.Entry<E>> entryIterator) {
            this.multiset = multiset;
            this.entryIterator = entryIterator;
        }

        @Override
        public boolean hasNext() {
            return this.laterCount > 0 || this.entryIterator.hasNext();
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            if (this.laterCount == 0) {
                this.currentEntry = this.entryIterator.next();
                this.totalCount = this.laterCount = this.currentEntry.getCount();
            }
            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }

        @Override
        public void remove() {
            Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
            if (this.totalCount == 1) {
                this.entryIterator.remove();
            } else {
                this.multiset.remove(this.currentEntry.getElement());
            }
            --this.totalCount;
            this.canRemove = false;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class EntrySet<E>
    extends AbstractSet<Multiset.Entry<E>> {
        EntrySet() {
        }

        abstract Multiset<E> multiset();

        @Override
        public boolean contains(@Nullable Object o) {
            if (o instanceof Multiset.Entry) {
                Multiset.Entry entry = (Multiset.Entry)o;
                if (entry.getCount() <= 0) {
                    return false;
                }
                int count = this.multiset().count(entry.getElement());
                return count == entry.getCount();
            }
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return this.contains(o) && this.multiset().elementSet().remove(((Multiset.Entry)o).getElement());
        }

        @Override
        public void clear() {
            this.multiset().clear();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class ElementSet<E>
    extends AbstractSet<E> {
        ElementSet() {
        }

        abstract Multiset<E> multiset();

        @Override
        public void clear() {
            this.multiset().clear();
        }

        @Override
        public boolean contains(Object o) {
            return this.multiset().contains(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.multiset().containsAll(c);
        }

        @Override
        public boolean isEmpty() {
            return this.multiset().isEmpty();
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.transform(this.multiset().entrySet().iterator(), new Function<Multiset.Entry<E>, E>(){

                @Override
                public E apply(Multiset.Entry<E> entry) {
                    return entry.getElement();
                }
            });
        }

        @Override
        public boolean remove(Object o) {
            int count = this.multiset().count(o);
            if (count > 0) {
                this.multiset().remove(o, count);
                return true;
            }
            return false;
        }

        @Override
        public int size() {
            return this.multiset().entrySet().size();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class AbstractEntry<E>
    implements Multiset.Entry<E> {
        AbstractEntry() {
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof Multiset.Entry) {
                Multiset.Entry that = (Multiset.Entry)object;
                return this.getCount() == that.getCount() && Objects.equal(this.getElement(), that.getElement());
            }
            return false;
        }

        @Override
        public int hashCode() {
            Object e = this.getElement();
            return (e == null ? 0 : e.hashCode()) ^ this.getCount();
        }

        @Override
        public String toString() {
            String text = String.valueOf(this.getElement());
            int n = this.getCount();
            return n == 1 ? text : text + " x " + n;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SetMultiset<E>
    extends ForwardingCollection<E>
    implements Multiset<E>,
    Serializable {
        final Set<E> delegate;
        transient Set<E> elementSet;
        transient Set<Multiset.Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;

        SetMultiset(Set<E> set) {
            this.delegate = Preconditions.checkNotNull(set);
        }

        @Override
        protected Set<E> delegate() {
            return this.delegate;
        }

        @Override
        public int count(Object element) {
            return this.delegate.contains(element) ? 1 : 0;
        }

        @Override
        public int add(E element, int occurrences) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int remove(Object element, int occurrences) {
            if (occurrences == 0) {
                return this.count(element);
            }
            Preconditions.checkArgument(occurrences > 0);
            return this.delegate.remove(element) ? 1 : 0;
        }

        @Override
        public Set<E> elementSet() {
            ElementSet es = this.elementSet;
            return es == null ? (this.elementSet = new ElementSet()) : es;
        }

        @Override
        public Set<Multiset.Entry<E>> entrySet() {
            EntrySet es = this.entrySet;
            if (es == null) {
                es = this.entrySet = new EntrySet<E>(){

                    @Override
                    Multiset<E> multiset() {
                        return SetMultiset.this;
                    }

                    @Override
                    public Iterator<Multiset.Entry<E>> iterator() {
                        return Iterators.transform(SetMultiset.this.delegate.iterator(), new Function<E, Multiset.Entry<E>>(){

                            @Override
                            public Multiset.Entry<E> apply(E elem) {
                                return Multisets.immutableEntry(elem, 1);
                            }
                        });
                    }

                    @Override
                    public int size() {
                        return SetMultiset.this.delegate.size();
                    }
                };
            }
            return es;
        }

        @Override
        public boolean add(E o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int setCount(E element, int count) {
            Multisets.checkNonnegative(count, "count");
            if (count == this.count(element)) {
                return count;
            }
            if (count == 0) {
                this.remove(element);
                return 1;
            }
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setCount(E element, int oldCount, int newCount) {
            return Multisets.setCountImpl(this, element, oldCount, newCount);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof Multiset) {
                Multiset that = (Multiset)object;
                return this.size() == that.size() && ((Object)this.delegate).equals(that.elementSet());
            }
            return false;
        }

        @Override
        public int hashCode() {
            int sum = 0;
            for (Object e : this) {
                sum += (e == null ? 0 : e.hashCode()) ^ 1;
            }
            return sum;
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        class ElementSet
        extends ForwardingSet<E> {
            ElementSet() {
            }

            @Override
            protected Set<E> delegate() {
                return SetMultiset.this.delegate;
            }

            @Override
            public boolean add(E o) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> c) {
                throw new UnsupportedOperationException();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static final class ImmutableEntry<E>
    extends AbstractEntry<E>
    implements Serializable {
        @Nullable
        final E element;
        final int count;
        private static final long serialVersionUID = 0L;

        ImmutableEntry(@Nullable E element, int count) {
            this.element = element;
            this.count = count;
            Preconditions.checkArgument(count >= 0);
        }

        @Override
        @Nullable
        public E getElement() {
            return this.element;
        }

        @Override
        public int getCount() {
            return this.count;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class UnmodifiableMultiset<E>
    extends ForwardingMultiset<E>
    implements Serializable {
        final Multiset<? extends E> delegate;
        transient Set<E> elementSet;
        transient Set<Multiset.Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;

        UnmodifiableMultiset(Multiset<? extends E> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Multiset<E> delegate() {
            return this.delegate;
        }

        Set<E> createElementSet() {
            return Collections.unmodifiableSet(this.delegate.elementSet());
        }

        @Override
        public Set<E> elementSet() {
            Set<E> es = this.elementSet;
            return es == null ? (this.elementSet = this.createElementSet()) : es;
        }

        @Override
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<Multiset.Entry<E>>> es = this.entrySet;
            return es == null ? (this.entrySet = Collections.unmodifiableSet(this.delegate.entrySet())) : es;
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.delegate.iterator());
        }

        @Override
        public boolean add(E element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int add(E element, int occurences) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> elementsToAdd) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int remove(Object element, int occurrences) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> elementsToRemove) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> elementsToRetain) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int setCount(E element, int count) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setCount(E element, int oldCount, int newCount) {
            throw new UnsupportedOperationException();
        }
    }
}

