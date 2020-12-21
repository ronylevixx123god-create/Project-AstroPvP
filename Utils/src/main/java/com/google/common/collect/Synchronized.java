/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
final class Synchronized {
    private Synchronized() {
    }

    private static <E> Collection<E> collection(Collection<E> collection, @Nullable Object mutex) {
        return new SynchronizedCollection(collection, mutex);
    }

    @VisibleForTesting
    static <E> Set<E> set(Set<E> set, @Nullable Object mutex) {
        return new SynchronizedSet<E>(set, mutex);
    }

    private static <E> SortedSet<E> sortedSet(SortedSet<E> set, @Nullable Object mutex) {
        return new SynchronizedSortedSet<E>(set, mutex);
    }

    private static <E> List<E> list(List<E> list, @Nullable Object mutex) {
        return list instanceof RandomAccess ? new SynchronizedRandomAccessList<E>(list, mutex) : new SynchronizedList<E>(list, mutex);
    }

    static <E> Multiset<E> multiset(Multiset<E> multiset, @Nullable Object mutex) {
        if (multiset instanceof SynchronizedMultiset || multiset instanceof ImmutableMultiset) {
            return multiset;
        }
        return new SynchronizedMultiset<E>(multiset, mutex);
    }

    static <K, V> Multimap<K, V> multimap(Multimap<K, V> multimap, @Nullable Object mutex) {
        if (multimap instanceof SynchronizedMultimap || multimap instanceof ImmutableMultimap) {
            return multimap;
        }
        return new SynchronizedMultimap<K, V>(multimap, mutex);
    }

    static <K, V> ListMultimap<K, V> listMultimap(ListMultimap<K, V> multimap, @Nullable Object mutex) {
        if (multimap instanceof SynchronizedListMultimap || multimap instanceof ImmutableListMultimap) {
            return multimap;
        }
        return new SynchronizedListMultimap<K, V>(multimap, mutex);
    }

    static <K, V> SetMultimap<K, V> setMultimap(SetMultimap<K, V> multimap, @Nullable Object mutex) {
        if (multimap instanceof SynchronizedSetMultimap || multimap instanceof ImmutableSetMultimap) {
            return multimap;
        }
        return new SynchronizedSetMultimap<K, V>(multimap, mutex);
    }

    static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> multimap, @Nullable Object mutex) {
        if (multimap instanceof SynchronizedSortedSetMultimap) {
            return multimap;
        }
        return new SynchronizedSortedSetMultimap<K, V>(multimap, mutex);
    }

    private static <E> Collection<E> typePreservingCollection(Collection<E> collection, @Nullable Object mutex) {
        if (collection instanceof SortedSet) {
            return Synchronized.sortedSet((SortedSet)collection, mutex);
        }
        if (collection instanceof Set) {
            return Synchronized.set((Set)collection, mutex);
        }
        if (collection instanceof List) {
            return Synchronized.list((List)collection, mutex);
        }
        return Synchronized.collection(collection, mutex);
    }

    private static <E> Set<E> typePreservingSet(Set<E> set, @Nullable Object mutex) {
        if (set instanceof SortedSet) {
            return Synchronized.sortedSet((SortedSet)set, mutex);
        }
        return Synchronized.set(set, mutex);
    }

    @VisibleForTesting
    static <K, V> Map<K, V> map(Map<K, V> map, @Nullable Object mutex) {
        return new SynchronizedMap<K, V>(map, mutex);
    }

    static <K, V> SortedMap<K, V> sortedMap(SortedMap<K, V> sortedMap, @Nullable Object mutex) {
        return new SynchronizedSortedMap<K, V>(sortedMap, mutex);
    }

    static <K, V> BiMap<K, V> biMap(BiMap<K, V> bimap, @Nullable Object mutex) {
        if (bimap instanceof SynchronizedBiMap || bimap instanceof ImmutableBiMap) {
            return bimap;
        }
        return new SynchronizedBiMap(bimap, mutex, null);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedAsMapValues<V>
    extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0L;

        SynchronizedAsMapValues(Collection<Collection<V>> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        public Iterator<Collection<V>> iterator() {
            final Iterator iterator = super.iterator();
            return new ForwardingIterator<Collection<V>>(){

                @Override
                protected Iterator<Collection<V>> delegate() {
                    return iterator;
                }

                @Override
                public Collection<V> next() {
                    return Synchronized.typePreservingCollection((Collection)iterator.next(), SynchronizedAsMapValues.this.mutex);
                }
            };
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedAsMap<K, V>
    extends SynchronizedMap<K, Collection<V>> {
        transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;
        transient Collection<Collection<V>> asMapValues;
        private static final long serialVersionUID = 0L;

        SynchronizedAsMap(Map<K, Collection<V>> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> get(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                Collection collection = (Collection)super.get(key);
                return collection == null ? null : Synchronized.typePreservingCollection(collection, this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMapEntrySet == null) {
                    this.asMapEntrySet = new SynchronizedAsMapEntries(this.delegate().entrySet(), this.mutex);
                }
                return this.asMapEntrySet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<Collection<V>> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMapValues == null) {
                    this.asMapValues = new SynchronizedAsMapValues(this.delegate().values(), this.mutex);
                }
                return this.asMapValues;
            }
        }

        @Override
        public boolean containsValue(Object o) {
            return this.values().contains(o);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @VisibleForTesting
    static class SynchronizedBiMap<K, V>
    extends SynchronizedMap<K, V>
    implements BiMap<K, V>,
    Serializable {
        private transient Set<V> valueSet;
        private transient BiMap<V, K> inverse;
        private static final long serialVersionUID = 0L;

        private SynchronizedBiMap(BiMap<K, V> delegate, @Nullable Object mutex, @Nullable BiMap<V, K> inverse) {
            super(delegate, mutex);
            this.inverse = inverse;
        }

        @Override
        BiMap<K, V> delegate() {
            return (BiMap)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.valueSet == null) {
                    this.valueSet = Synchronized.set(this.delegate().values(), this.mutex);
                }
                return this.valueSet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V forcePut(K key, V value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().forcePut(key, value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public BiMap<V, K> inverse() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.inverse == null) {
                    this.inverse = new SynchronizedBiMap<K, V>(this.delegate().inverse(), this.mutex, this);
                }
                return this.inverse;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class SynchronizedSortedMap<K, V>
    extends SynchronizedMap<K, V>
    implements SortedMap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedMap(SortedMap<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        SortedMap<K, V> delegate() {
            return (SortedMap)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super K> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().comparator();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K firstKey() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().firstKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedMap<K, V> headMap(K toKey) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedMap(this.delegate().headMap(toKey), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public K lastKey() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().lastKey();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedMap(this.delegate().subMap(fromKey, toKey), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedMap<K, V> tailMap(K fromKey) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedMap(this.delegate().tailMap(fromKey), this.mutex);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedMap<K, V>
    extends SynchronizedObject
    implements Map<K, V> {
        transient Set<K> keySet;
        transient Collection<V> values;
        transient Set<Map.Entry<K, V>> entrySet;
        private static final long serialVersionUID = 0L;

        SynchronizedMap(Map<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        Map<K, V> delegate() {
            return (Map)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsKey(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(Object value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsValue(value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(this.delegate().entrySet(), this.mutex);
                }
                return this.entrySet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V get(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().get(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<K> keySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.set(this.delegate().keySet(), this.mutex);
                }
                return this.keySet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V put(K key, V value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().put(key, value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().putAll(map);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public V remove(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().remove(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.values == null) {
                    this.values = Synchronized.collection(this.delegate().values(), this.mutex);
                }
                return this.values;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedAsMapEntries<K, V>
    extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0L;

        SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            final Iterator iterator = super.iterator();
            return new ForwardingIterator<Map.Entry<K, Collection<V>>>(){

                @Override
                protected Iterator<Map.Entry<K, Collection<V>>> delegate() {
                    return iterator;
                }

                @Override
                public Map.Entry<K, Collection<V>> next() {
                    final Map.Entry entry = (Map.Entry)iterator.next();
                    return new ForwardingMapEntry<K, Collection<V>>(){

                        @Override
                        protected Map.Entry<K, Collection<V>> delegate() {
                            return entry;
                        }

                        @Override
                        public Collection<V> getValue() {
                            return Synchronized.typePreservingCollection((Collection)entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }
            };
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object[] toArray() {
            Object object = this.mutex;
            synchronized (object) {
                return ObjectArrays.toArrayImpl(this.delegate());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] array) {
            Object object = this.mutex;
            synchronized (object) {
                return ObjectArrays.toArrayImpl(this.delegate(), array);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return Maps.containsEntryImpl(this.delegate(), o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return Collections2.containsAllImpl(this.delegate(), c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return Sets.equalsImpl(this.delegate(), o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return Maps.removeEntryImpl(this.delegate(), o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return Iterators.removeAll(this.delegate().iterator(), c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return Iterators.retainAll(this.delegate().iterator(), c);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedSortedSetMultimap<K, V>
    extends SynchronizedSetMultimap<K, V>
    implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<V> get(K key) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedSet(this.delegate().get(key), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<V> removeAll(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().replaceValues(key, values);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super V> valueComparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().valueComparator();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedSetMultimap<K, V>
    extends SynchronizedMultimap<K, V>
    implements SetMultimap<K, V> {
        transient Set<Map.Entry<K, V>> entrySet;
        private static final long serialVersionUID = 0L;

        SynchronizedSetMultimap(SetMultimap<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        SetMultimap<K, V> delegate() {
            return (SetMultimap)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<V> get(K key) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.set(this.delegate().get(key), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<V> removeAll(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<V> replaceValues(K key, Iterable<? extends V> values) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().replaceValues(key, values);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<Map.Entry<K, V>> entries() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(this.delegate().entries(), this.mutex);
                }
                return this.entrySet;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedListMultimap<K, V>
    extends SynchronizedMultimap<K, V>
    implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedListMultimap(ListMultimap<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        ListMultimap<K, V> delegate() {
            return (ListMultimap)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<V> get(K key) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.list(this.delegate().get(key), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<V> removeAll(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<V> replaceValues(K key, Iterable<? extends V> values) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().replaceValues(key, values);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedMultimap<K, V>
    extends SynchronizedObject
    implements Multimap<K, V> {
        transient Set<K> keySet;
        transient Collection<V> valuesCollection;
        transient Collection<Map.Entry<K, V>> entries;
        transient Map<K, Collection<V>> asMap;
        transient Multiset<K> keys;
        private static final long serialVersionUID = 0L;

        @Override
        Multimap<K, V> delegate() {
            return (Multimap)super.delegate();
        }

        SynchronizedMultimap(Multimap<K, V> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsKey(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsKey(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsValue(Object value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsValue(value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsEntry(Object key, Object value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsEntry(key, value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> get(K key) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.typePreservingCollection(this.delegate().get(key), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean put(K key, V value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().put(key, value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putAll(K key, Iterable<? extends V> values) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().putAll(key, values);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().putAll(multimap);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().replaceValues(key, values);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object key, Object value) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().remove(key, value);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> removeAll(Object key) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(key);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<K> keySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.typePreservingSet(this.delegate().keySet(), this.mutex);
                }
                return this.keySet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.valuesCollection == null) {
                    this.valuesCollection = Synchronized.collection(this.delegate().values(), this.mutex);
                }
                return this.valuesCollection;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Collection<Map.Entry<K, V>> entries() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entries == null) {
                    this.entries = Synchronized.typePreservingCollection(this.delegate().entries(), this.mutex);
                }
                return this.entries;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Map<K, Collection<V>> asMap() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMap == null) {
                    this.asMap = new SynchronizedAsMap(this.delegate().asMap(), this.mutex);
                }
                return this.asMap;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Multiset<K> keys() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keys == null) {
                    this.keys = Synchronized.multiset(this.delegate().keys(), this.mutex);
                }
                return this.keys;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedMultiset<E>
    extends SynchronizedCollection<E>
    implements Multiset<E> {
        transient Set<E> elementSet;
        transient Set<Multiset.Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;

        SynchronizedMultiset(Multiset<E> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        Multiset<E> delegate() {
            return (Multiset)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int count(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().count(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int add(E e, int n) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().add(e, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int remove(Object o, int n) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().remove(o, n);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int setCount(E element, int count) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().setCount(element, count);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean setCount(E element, int oldCount, int newCount) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().setCount(element, oldCount, newCount);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<E> elementSet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.elementSet == null) {
                    this.elementSet = Synchronized.typePreservingSet(this.delegate().elementSet(), this.mutex);
                }
                return this.elementSet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Set<Multiset.Entry<E>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.typePreservingSet(this.delegate().entrySet(), this.mutex);
                }
                return this.entrySet;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedRandomAccessList<E>
    extends SynchronizedList<E>
    implements RandomAccess {
        private static final long serialVersionUID = 0L;

        SynchronizedRandomAccessList(List<E> list, @Nullable Object mutex) {
            super(list, mutex);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SynchronizedList<E>
    extends SynchronizedCollection<E>
    implements List<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedList(List<E> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        List<E> delegate() {
            return (List)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void add(int index, E element) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().add(index, element);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().addAll(index, c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public E get(int index) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().get(index);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int indexOf(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().indexOf(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int lastIndexOf(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().lastIndexOf(o);
            }
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.delegate().listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return this.delegate().listIterator(index);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public E remove(int index) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().remove(index);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public E set(int index, E element) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().set(index, element);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.list(this.delegate().subList(fromIndex, toIndex), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class SynchronizedSortedSet<E>
    extends SynchronizedSet<E>
    implements SortedSet<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedSet(SortedSet<E> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        SortedSet<E> delegate() {
            return (SortedSet)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Comparator<? super E> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().comparator();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<E> subSet(E fromElement, E toElement) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedSet(this.delegate().subSet(fromElement, toElement), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<E> headSet(E toElement) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedSet(this.delegate().headSet(toElement), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public SortedSet<E> tailSet(E fromElement) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.sortedSet(this.delegate().tailSet(fromElement), this.mutex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public E first() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public E last() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().last();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class SynchronizedSet<E>
    extends SynchronizedCollection<E>
    implements Set<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedSet(Set<E> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        Set<E> delegate() {
            return (Set)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).equals(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return ((Object)this.delegate()).hashCode();
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @VisibleForTesting
    static class SynchronizedCollection<E>
    extends SynchronizedObject
    implements Collection<E> {
        private static final long serialVersionUID = 0L;

        private SynchronizedCollection(Collection<E> delegate, @Nullable Object mutex) {
            super(delegate, mutex);
        }

        @Override
        Collection<E> delegate() {
            return (Collection)super.delegate();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().add(e);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().addAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean contains(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().contains(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        @Override
        public Iterator<E> iterator() {
            return this.delegate().iterator();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Object o) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().remove(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().retainAll(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object[] toArray() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().toArray();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().toArray(a);
            }
        }
    }

    static class SynchronizedObject
    implements Serializable {
        final Object delegate;
        final Object mutex;
        @GwtIncompatible(value="not needed in emulated source")
        private static final long serialVersionUID = 0L;

        SynchronizedObject(Object delegate, @Nullable Object mutex) {
            this.delegate = Preconditions.checkNotNull(delegate);
            this.mutex = mutex == null ? this : mutex;
        }

        Object delegate() {
            return this.delegate;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate.toString();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @GwtIncompatible(value="java.io.ObjectOutputStream")
        private void writeObject(ObjectOutputStream stream) throws IOException {
            Object object = this.mutex;
            synchronized (object) {
                stream.defaultWriteObject();
            }
        }
    }
}

