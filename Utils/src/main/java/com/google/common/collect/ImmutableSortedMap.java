/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMapFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedLists;
import com.google.common.collect.TransformedImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public class ImmutableSortedMap<K, V>
extends ImmutableSortedMapFauxverideShim<K, V>
implements SortedMap<K, V> {
    private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
    private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableList.of(), NATURAL_ORDER);
    final transient ImmutableList<Map.Entry<K, V>> entries;
    private final transient Comparator<? super K> comparator;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSortedSet<K> keySet;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return NATURAL_EMPTY_MAP;
    }

    private static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
        if (((Object)NATURAL_ORDER).equals(comparator)) {
            return NATURAL_EMPTY_MAP;
        }
        return new ImmutableSortedMap<K, V>(ImmutableList.<Map.Entry<K, V>>of(), comparator);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1) {
        return new ImmutableSortedMap<K, V>(ImmutableList.of(ImmutableSortedMap.entryOf(k1, v1)), Ordering.natural());
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2) {
        return ((Builder)((Builder)new Builder(Ordering.natural()).put((Object)k1, (Object)v1)).put((Object)k2, (Object)v2)).build();
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ((Builder)((Builder)((Builder)new Builder(Ordering.natural()).put((Object)k1, (Object)v1)).put((Object)k2, (Object)v2)).put((Object)k3, (Object)v3)).build();
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ((Builder)((Builder)((Builder)((Builder)new Builder(Ordering.natural()).put((Object)k1, (Object)v1)).put((Object)k2, (Object)v2)).put((Object)k3, (Object)v3)).put((Object)k4, (Object)v4)).build();
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ((Builder)((Builder)((Builder)((Builder)((Builder)new Builder(Ordering.natural()).put((Object)k1, (Object)v1)).put((Object)k2, (Object)v2)).put((Object)k3, (Object)v3)).put((Object)k4, (Object)v4)).put((Object)k5, (Object)v5)).build();
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        Ordering naturalOrder = Ordering.natural();
        return ImmutableSortedMap.copyOfInternal(map, naturalOrder);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return ImmutableSortedMap.copyOfInternal(map, Preconditions.checkNotNull(comparator));
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> map) {
        Comparator<Object> comparator = map.comparator();
        if (comparator == null) {
            comparator = NATURAL_ORDER;
        }
        return ImmutableSortedMap.copyOfInternal(map, comparator);
    }

    private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        ImmutableSortedMap kvMap;
        boolean sameComparator = false;
        if (map instanceof SortedMap) {
            SortedMap sortedMap = (SortedMap)map;
            Comparator comparator2 = sortedMap.comparator();
            boolean bl = comparator2 == null ? comparator == NATURAL_ORDER : (sameComparator = ((Object)comparator).equals(comparator2));
        }
        if (sameComparator && map instanceof ImmutableSortedMap && !(kvMap = (ImmutableSortedMap)map).isPartialView()) {
            return kvMap;
        }
        Map.Entry[] entries = map.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < entries.length; ++i) {
            Map.Entry entry = entries[i];
            entries[i] = ImmutableSortedMap.entryOf(entry.getKey(), entry.getValue());
        }
        List<Map.Entry<K, V>> list = Arrays.asList(entries);
        if (!sameComparator) {
            ImmutableSortedMap.sortEntries(list, comparator);
            ImmutableSortedMap.validateEntries(list, comparator);
        }
        return new ImmutableSortedMap<K, V>(ImmutableList.copyOf(list), comparator);
    }

    private static <K, V> void sortEntries(List<Map.Entry<K, V>> entries, final Comparator<? super K> comparator) {
        Comparator entryComparator = new Comparator<Map.Entry<K, V>>(){

            @Override
            public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
                return comparator.compare(entry1.getKey(), entry2.getKey());
            }
        };
        Collections.sort(entries, entryComparator);
    }

    private static <K, V> void validateEntries(List<Map.Entry<K, V>> entries, Comparator<? super K> comparator) {
        for (int i = 1; i < entries.size(); ++i) {
            if (comparator.compare(entries.get(i - 1).getKey(), entries.get(i).getKey()) != 0) continue;
            throw new IllegalArgumentException("Duplicate keys in mappings " + entries.get(i - 1) + " and " + entries.get(i));
        }
    }

    public static <K extends Comparable<K>, V> Builder<K, V> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder(comparator);
    }

    public static <K extends Comparable<K>, V> Builder<K, V> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    ImmutableSortedMap(ImmutableList<Map.Entry<K, V>> entries, Comparator<? super K> comparator) {
        this.entries = entries;
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    @Override
    public V get(@Nullable Object key) {
        int i;
        if (key == null) {
            return null;
        }
        try {
            i = this.index(key, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
        }
        catch (ClassCastException e) {
            return null;
        }
        return i >= 0 ? (V)((Map.Entry)this.entries.get(i)).getValue() : null;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        if (value == null) {
            return false;
        }
        return Iterators.contains(this.valueIterator(), value);
    }

    @Override
    boolean isPartialView() {
        return this.entries.isPartialView();
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return es == null ? (this.entrySet = this.createEntrySet()) : es;
    }

    private ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return this.isEmpty() ? ImmutableSet.of() : new EntrySet(this);
    }

    @Override
    public ImmutableSortedSet<K> keySet() {
        ImmutableSortedSet<K> ks = this.keySet;
        return ks == null ? (this.keySet = this.createKeySet()) : ks;
    }

    private ImmutableSortedSet<K> createKeySet() {
        if (this.isEmpty()) {
            return ImmutableSortedSet.emptySet(this.comparator);
        }
        return new RegularImmutableSortedSet<K>(new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries){

            @Override
            K transform(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        }, this.comparator);
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> v = this.values;
        return v == null ? (this.values = new Values(this)) : v;
    }

    UnmodifiableIterator<V> valueIterator() {
        Iterator entryIterator = this.entries.iterator();
        return new UnmodifiableIterator<V>((UnmodifiableIterator)entryIterator){
            final /* synthetic */ UnmodifiableIterator val$entryIterator;
            {
                this.val$entryIterator = unmodifiableIterator;
            }

            @Override
            public boolean hasNext() {
                return this.val$entryIterator.hasNext();
            }

            @Override
            public V next() {
                return ((Map.Entry)this.val$entryIterator.next()).getValue();
            }
        };
    }

    @Override
    public Comparator<? super K> comparator() {
        return this.comparator;
    }

    @Override
    public K firstKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return ((Map.Entry)this.entries.get(0)).getKey();
    }

    @Override
    public K lastKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return ((Map.Entry)this.entries.get(this.size() - 1)).getKey();
    }

    @Override
    public ImmutableSortedMap<K, V> headMap(K toKey) {
        return this.headMap(toKey, false);
    }

    ImmutableSortedMap<K, V> headMap(K toKey, boolean inclusive) {
        int index = inclusive ? this.index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1 : this.index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        return this.createSubmap(0, index);
    }

    @Override
    public ImmutableSortedMap<K, V> subMap(K fromKey, K toKey) {
        return this.subMap(fromKey, true, toKey, false);
    }

    ImmutableSortedMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        Preconditions.checkNotNull(fromKey);
        Preconditions.checkNotNull(toKey);
        Preconditions.checkArgument(this.comparator.compare(fromKey, toKey) <= 0);
        return this.tailMap(fromKey, fromInclusive).headMap(toKey, toInclusive);
    }

    @Override
    public ImmutableSortedMap<K, V> tailMap(K fromKey) {
        return this.tailMap(fromKey, true);
    }

    ImmutableSortedMap<K, V> tailMap(K fromKey, boolean inclusive) {
        int index = inclusive ? this.index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : this.index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
        return this.createSubmap(index, this.size());
    }

    private ImmutableList<K> keyList() {
        return new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries){

            @Override
            K transform(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    private int index(Object key, SortedLists.KeyPresentBehavior presentBehavior, SortedLists.KeyAbsentBehavior absentBehavior) {
        return SortedLists.binarySearch(this.keyList(), Preconditions.checkNotNull(key), this.unsafeComparator(), presentBehavior, absentBehavior);
    }

    private ImmutableSortedMap<K, V> createSubmap(int newFromIndex, int newToIndex) {
        if (newFromIndex < newToIndex) {
            return new ImmutableSortedMap<K, V>(this.entries.subList(newFromIndex, newToIndex), this.comparator);
        }
        return ImmutableSortedMap.emptyMap(this.comparator);
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SerializedForm
    extends ImmutableMap.SerializedForm {
        private final Comparator<Object> comparator;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
            super(sortedMap);
            this.comparator = sortedMap.comparator();
        }

        @Override
        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>(this.comparator);
            return this.createMap(builder);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class ValuesSerializedForm<V>
    implements Serializable {
        final ImmutableSortedMap<?, V> map;
        private static final long serialVersionUID = 0L;

        ValuesSerializedForm(ImmutableSortedMap<?, V> map) {
            this.map = map;
        }

        Object readResolve() {
            return this.map.values();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Values<V>
    extends ImmutableCollection<V> {
        private final ImmutableSortedMap<?, V> map;

        Values(ImmutableSortedMap<?, V> map) {
            this.map = map;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return this.map.valueIterator();
        }

        @Override
        public boolean contains(Object target) {
            return this.map.containsValue(target);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        Object writeReplace() {
            return new ValuesSerializedForm<V>(this.map);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class EntrySetSerializedForm<K, V>
    implements Serializable {
        final ImmutableSortedMap<K, V> map;
        private static final long serialVersionUID = 0L;

        EntrySetSerializedForm(ImmutableSortedMap<K, V> map) {
            this.map = map;
        }

        Object readResolve() {
            return this.map.entrySet();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class EntrySet<K, V>
    extends ImmutableSet<Map.Entry<K, V>> {
        final transient ImmutableSortedMap<K, V> map;

        EntrySet(ImmutableSortedMap<K, V> map) {
            this.map = map;
        }

        @Override
        boolean isPartialView() {
            return this.map.isPartialView();
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.map.entries.iterator();
        }

        @Override
        public boolean contains(Object target) {
            if (target instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)target;
                V mappedValue = this.map.get(entry.getKey());
                return mappedValue != null && mappedValue.equals(entry.getValue());
            }
            return false;
        }

        @Override
        Object writeReplace() {
            return new EntrySetSerializedForm<K, V>(this.map);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder<K, V>
    extends ImmutableMap.Builder<K, V> {
        private final Comparator<? super K> comparator;

        public Builder(Comparator<? super K> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        public Builder<K, V> put(K key, V value) {
            this.entries.add(ImmutableMap.entryOf(key, value));
            return this;
        }

        @Override
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                this.put((Object)entry.getKey(), (Object)entry.getValue());
            }
            return this;
        }

        @Override
        public ImmutableSortedMap<K, V> build() {
            ImmutableSortedMap.sortEntries(this.entries, this.comparator);
            ImmutableSortedMap.validateEntries(this.entries, this.comparator);
            return new ImmutableSortedMap(ImmutableList.copyOf(this.entries), this.comparator);
        }
    }
}

