/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.Count;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
abstract class AbstractMapBasedMultiset<E>
extends AbstractMultiset<E>
implements Serializable {
    private transient Map<E, Count> backingMap;
    private transient long size;
    @GwtIncompatible(value="not needed in emulated source.")
    private static final long serialVersionUID = -2250766705698539974L;

    protected AbstractMapBasedMultiset(Map<E, Count> backingMap) {
        this.backingMap = Preconditions.checkNotNull(backingMap);
        this.size = super.size();
    }

    Map<E, Count> backingMap() {
        return this.backingMap;
    }

    void setBackingMap(Map<E, Count> backingMap) {
        this.backingMap = backingMap;
    }

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        return super.entrySet();
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Map.Entry<E, Count>> backingEntries = this.backingMap.entrySet().iterator();
        return new Iterator<Multiset.Entry<E>>(){
            Map.Entry<E, Count> toRemove;

            @Override
            public boolean hasNext() {
                return backingEntries.hasNext();
            }

            @Override
            public Multiset.Entry<E> next() {
                Map.Entry mapEntry;
                this.toRemove = mapEntry = (Map.Entry)backingEntries.next();
                return new Multisets.AbstractEntry<E>(){

                    @Override
                    public E getElement() {
                        return mapEntry.getKey();
                    }

                    @Override
                    public int getCount() {
                        Count frequency;
                        int count = ((Count)mapEntry.getValue()).get();
                        if (count == 0 && (frequency = (Count)AbstractMapBasedMultiset.this.backingMap.get(this.getElement())) != null) {
                            count = frequency.get();
                        }
                        return count;
                    }
                };
            }

            @Override
            public void remove() {
                Preconditions.checkState(this.toRemove != null, "no calls to next() since the last call to remove()");
                AbstractMapBasedMultiset.this.size -= this.toRemove.getValue().getAndSet(0);
                backingEntries.remove();
                this.toRemove = null;
            }
        };
    }

    @Override
    public void clear() {
        for (Count frequency : this.backingMap.values()) {
            frequency.set(0);
        }
        this.backingMap.clear();
        this.size = 0L;
    }

    @Override
    int distinctElements() {
        return this.backingMap.size();
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.size);
    }

    @Override
    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator();
    }

    @Override
    public int count(@Nullable Object element) {
        try {
            Count frequency = this.backingMap.get(element);
            return frequency == null ? 0 : frequency.get();
        }
        catch (NullPointerException e) {
            return 0;
        }
        catch (ClassCastException e) {
            return 0;
        }
    }

    @Override
    public int add(@Nullable E element, int occurrences) {
        int oldCount;
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
        Count frequency = this.backingMap.get(element);
        if (frequency == null) {
            oldCount = 0;
            this.backingMap.put(element, new Count(occurrences));
        } else {
            oldCount = frequency.get();
            long newCount = (long)oldCount + (long)occurrences;
            Preconditions.checkArgument(newCount <= Integer.MAX_VALUE, "too many occurrences: %s", newCount);
            frequency.getAndAdd(occurrences);
        }
        this.size += (long)occurrences;
        return oldCount;
    }

    @Override
    public int remove(@Nullable Object element, int occurrences) {
        int numberRemoved;
        if (occurrences == 0) {
            return this.count(element);
        }
        Preconditions.checkArgument(occurrences > 0, "occurrences cannot be negative: %s", occurrences);
        Count frequency = this.backingMap.get(element);
        if (frequency == null) {
            return 0;
        }
        int oldCount = frequency.get();
        if (oldCount > occurrences) {
            numberRemoved = occurrences;
        } else {
            numberRemoved = oldCount;
            this.backingMap.remove(element);
        }
        frequency.addAndGet(-numberRemoved);
        this.size -= (long)numberRemoved;
        return oldCount;
    }

    @Override
    public int setCount(E element, int count) {
        int oldCount;
        Multisets.checkNonnegative(count, "count");
        if (count == 0) {
            Count existingCounter = this.backingMap.remove(element);
            oldCount = AbstractMapBasedMultiset.getAndSet(existingCounter, count);
        } else {
            Count existingCounter = this.backingMap.get(element);
            oldCount = AbstractMapBasedMultiset.getAndSet(existingCounter, count);
            if (existingCounter == null) {
                this.backingMap.put(element, new Count(count));
            }
        }
        this.size += (long)(count - oldCount);
        return oldCount;
    }

    private static int getAndSet(Count i, int count) {
        if (i == null) {
            return 0;
        }
        return i.getAndSet(count);
    }

    private int removeAllOccurrences(@Nullable Object element, Map<E, Count> map) {
        Count frequency = map.remove(element);
        if (frequency == null) {
            return 0;
        }
        int numberRemoved = frequency.getAndSet(0);
        this.size -= (long)numberRemoved;
        return numberRemoved;
    }

    @Override
    Set<E> createElementSet() {
        return new MapBasedElementSet(this.backingMap);
    }

    @GwtIncompatible(value="java.io.ObjectStreamException")
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class MapBasedElementSet
    extends ForwardingSet<E> {
        private final Map<E, Count> map;
        private final Set<E> delegate;

        MapBasedElementSet(Map<E, Count> map) {
            this.map = map;
            this.delegate = map.keySet();
        }

        @Override
        protected Set<E> delegate() {
            return this.delegate;
        }

        @Override
        public Iterator<E> iterator() {
            final Iterator entries = this.map.entrySet().iterator();
            return new Iterator<E>(){
                Map.Entry<E, Count> toRemove;

                @Override
                public boolean hasNext() {
                    return entries.hasNext();
                }

                @Override
                public E next() {
                    this.toRemove = (Map.Entry)entries.next();
                    return this.toRemove.getKey();
                }

                @Override
                public void remove() {
                    Preconditions.checkState(this.toRemove != null, "no calls to next() since the last call to remove()");
                    AbstractMapBasedMultiset.this.size -= this.toRemove.getValue().getAndSet(0);
                    entries.remove();
                    this.toRemove = null;
                }
            };
        }

        @Override
        public boolean remove(Object element) {
            return AbstractMapBasedMultiset.this.removeAllOccurrences(element, this.map) != 0;
        }

        @Override
        public boolean removeAll(Collection<?> elementsToRemove) {
            return Iterators.removeAll(this.iterator(), elementsToRemove);
        }

        @Override
        public boolean retainAll(Collection<?> elementsToRetain) {
            return Iterators.retainAll(this.iterator(), elementsToRetain);
        }

        @Override
        public void clear() {
            if (this.map == AbstractMapBasedMultiset.this.backingMap) {
                AbstractMapBasedMultiset.this.clear();
            } else {
                Iterator i = this.iterator();
                while (i.hasNext()) {
                    i.next();
                    i.remove();
                }
            }
        }

        public Map<E, Count> getMap() {
            return this.map;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class MapBasedMultisetIterator
    implements Iterator<E> {
        final Iterator<Map.Entry<E, Count>> entryIterator;
        Map.Entry<E, Count> currentEntry;
        int occurrencesLeft;
        boolean canRemove;

        MapBasedMultisetIterator() {
            this.entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }

        @Override
        public E next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = this.entryIterator.next();
                this.occurrencesLeft = this.currentEntry.getValue().get();
            }
            --this.occurrencesLeft;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }

        @Override
        public void remove() {
            Preconditions.checkState(this.canRemove, "no calls to next() since the last call to remove()");
            int frequency = this.currentEntry.getValue().get();
            if (frequency <= 0) {
                throw new ConcurrentModificationException();
            }
            if (this.currentEntry.getValue().addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.this.size--;
            this.canRemove = false;
        }
    }
}

