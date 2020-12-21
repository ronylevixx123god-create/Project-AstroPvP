/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.Equivalences;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Sets;
import com.google.common.collect.Synchronized;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public final class Maps {
    static final Joiner.MapJoiner STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");

    private Maps() {
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap(Maps.capacity(expectedSize));
    }

    static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            Preconditions.checkArgument(expectedSize >= 0);
            return expectedSize + 1;
        }
        if (expectedSize < 0x40000000) {
            return expectedSize + expectedSize / 3;
        }
        return Integer.MAX_VALUE;
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new MapMaker().makeMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap<K, V>(map);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable Comparator<C> comparator) {
        return new TreeMap(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
        return new EnumMap(Preconditions.checkNotNull(type));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap();
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> bimap) {
        return Synchronized.biMap(bimap, null);
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
        return Maps.difference(left, right, Equivalences.equals());
    }

    @Beta
    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right, Equivalence<? super V> valueEquivalence) {
        Preconditions.checkNotNull(valueEquivalence);
        HashMap<K, V> onlyOnLeft = Maps.newHashMap();
        HashMap<K, V> onlyOnRight = new HashMap<K, V>(right);
        HashMap<K, V> onBoth = Maps.newHashMap();
        HashMap<K, MapDifference.ValueDifference<V>> differences = Maps.newHashMap();
        boolean eq = true;
        for (Map.Entry<K, V> entry : left.entrySet()) {
            K leftKey = entry.getKey();
            V leftValue = entry.getValue();
            if (right.containsKey(leftKey)) {
                Object rightValue = onlyOnRight.remove(leftKey);
                if (valueEquivalence.equivalent(leftValue, rightValue)) {
                    onBoth.put(leftKey, leftValue);
                    continue;
                }
                eq = false;
                differences.put(leftKey, ValueDifferenceImpl.create(leftValue, rightValue));
                continue;
            }
            eq = false;
            onlyOnLeft.put(leftKey, leftValue);
        }
        boolean areEqual = eq && onlyOnRight.isEmpty();
        return Maps.mapDifference(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
    }

    private static <K, V> MapDifference<K, V> mapDifference(boolean areEqual, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
        return new MapDifferenceImpl<K, V>(areEqual, Collections.unmodifiableMap(onlyOnLeft), Collections.unmodifiableMap(onlyOnRight), Collections.unmodifiableMap(onBoth), Collections.unmodifiableMap(differences));
    }

    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
        return Maps.uniqueIndex(values.iterator(), keyFunction);
    }

    @Deprecated
    @Beta
    public static <K, V, I extends Object & Iterator<V>> ImmutableMap<K, V> uniqueIndex(I values, Function<? super V, K> keyFunction) {
        Iterable valuesIterable = (Iterable)Preconditions.checkNotNull(values);
        return Maps.uniqueIndex(valuesIterable, keyFunction);
    }

    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> values, Function<? super V, K> keyFunction) {
        Preconditions.checkNotNull(keyFunction);
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (values.hasNext()) {
            V value = values.next();
            builder.put(keyFunction.apply(value), value);
        }
        return builder.build();
    }

    @GwtIncompatible(value="java.util.Properties")
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String)e.nextElement();
            builder.put(key, properties.getProperty(key));
        }
        return builder.build();
    }

    @GwtCompatible(serializable=true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable K key, @Nullable V value) {
        return new ImmutableEntry<K, V>(key, value);
    }

    static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> entrySet) {
        return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet(entrySet));
    }

    static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<K, V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>(){

            @Override
            public K getKey() {
                return entry.getKey();
            }

            @Override
            public V getValue() {
                return entry.getValue();
            }
        };
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> bimap) {
        return new UnmodifiableBiMap<K, V>(bimap, null);
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        EntryTransformer transformer = new EntryTransformer<K, V1, V2>(){

            @Override
            public V2 transformEntry(K key, V1 value) {
                return function.apply(value);
            }
        };
        return Maps.transformEntries(fromMap, transformer);
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesMap<K, V1, V2>(fromMap, transformer);
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        Preconditions.checkNotNull(keyPredicate);
        Predicate entryPredicate = new Predicate<Map.Entry<K, V>>(){

            @Override
            public boolean apply(Map.Entry<K, V> input) {
                return keyPredicate.apply(input.getKey());
            }
        };
        return unfiltered instanceof AbstractFilteredMap ? Maps.filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredKeyMap<K, V>(Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate);
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
        Preconditions.checkNotNull(valuePredicate);
        Predicate entryPredicate = new Predicate<Map.Entry<K, V>>(){

            @Override
            public boolean apply(Map.Entry<K, V> input) {
                return valuePredicate.apply(input.getValue());
            }
        };
        return Maps.filterEntries(unfiltered, entryPredicate);
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        return unfiltered instanceof AbstractFilteredMap ? Maps.filterFiltered((AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredEntryMap<K, V>(Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Predicate<? super Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
        return new FilteredEntryMap(map.unfiltered, predicate);
    }

    static <V> V safeGet(Map<?, V> map, Object key) {
        try {
            return map.get(key);
        }
        catch (ClassCastException e) {
            return null;
        }
    }

    static boolean safeContainsKey(Map<?, ?> map, Object key) {
        try {
            return map.containsKey(key);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        return c.contains(Maps.unmodifiableEntry((Map.Entry)o));
    }

    static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        return c.remove(Maps.unmodifiableEntry((Map.Entry)o));
    }

    static boolean equalsImpl(Map<?, ?> map, Object object) {
        if (map == object) {
            return true;
        }
        if (object instanceof Map) {
            Map o = (Map)object;
            return ((Object)map.entrySet()).equals(o.entrySet());
        }
        return false;
    }

    static int hashCodeImpl(Map<?, ?> map) {
        return Sets.hashCodeImpl(map.entrySet());
    }

    static String toStringImpl(Map<?, ?> map) {
        StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
        STANDARD_JOINER.appendTo(sb, map);
        return sb.append('}').toString();
    }

    static <K, V> void putAllImpl(Map<K, V> self, Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            self.put(entry.getKey(), entry.getValue());
        }
    }

    static boolean containsKeyImpl(Map<?, ?> map, @Nullable Object key) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!Objects.equal(entry.getKey(), key)) continue;
            return true;
        }
        return false;
    }

    static boolean containsValueImpl(Map<?, ?> map, @Nullable Object value) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!Objects.equal(entry.getValue(), value)) continue;
            return true;
        }
        return false;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class EntrySet<K, V>
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        abstract Map<K, V> map();

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        @Override
        public boolean contains(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)o;
                Object key = entry.getKey();
                V value = this.map().get(key);
                return Objects.equal(value, entry.getValue()) && (value != null || this.map().containsKey(key));
            }
            return false;
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean remove(Object o) {
            if (this.contains(o)) {
                Map.Entry entry = (Map.Entry)o;
                return this.map().keySet().remove(entry.getKey());
            }
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                boolean changed = true;
                for (Object o : c) {
                    changed |= this.remove(o);
                }
                return changed;
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                HashSet keys = Sets.newHashSetWithExpectedSize(c.size());
                for (Object o : c) {
                    if (!this.contains(o)) continue;
                    Map.Entry entry = (Map.Entry)o;
                    keys.add(entry.getKey());
                }
                return this.map().keySet().retainAll(keys);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class Values<K, V>
    extends AbstractCollection<V> {
        Values() {
        }

        abstract Map<K, V> map();

        @Override
        public Iterator<V> iterator() {
            return Iterators.transform(this.map().entrySet().iterator(), new Function<Map.Entry<K, V>, V>(){

                @Override
                public V apply(Map.Entry<K, V> entry) {
                    return entry.getValue();
                }
            });
        }

        @Override
        public boolean remove(Object o) {
            try {
                return super.remove(o);
            }
            catch (UnsupportedOperationException e) {
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!Objects.equal(o, entry.getValue())) continue;
                    this.map().remove(entry.getKey());
                    return true;
                }
                return false;
            }
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                HashSet<K> toRemove = Sets.newHashSet();
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!c.contains(entry.getValue())) continue;
                    toRemove.add(entry.getKey());
                }
                return this.map().keySet().removeAll(toRemove);
            }
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                HashSet<K> toRetain = Sets.newHashSet();
                for (Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (!c.contains(entry.getValue())) continue;
                    toRetain.add(entry.getKey());
                }
                return this.map().keySet().retainAll(toRetain);
            }
        }

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return this.map().containsValue(o);
        }

        @Override
        public void clear() {
            this.map().clear();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static abstract class KeySet<K, V>
    extends AbstractSet<K> {
        KeySet() {
        }

        abstract Map<K, V> map();

        @Override
        public Iterator<K> iterator() {
            return Iterators.transform(this.map().entrySet().iterator(), new Function<Map.Entry<K, V>, K>(){

                @Override
                public K apply(Map.Entry<K, V> entry) {
                    return entry.getKey();
                }
            });
        }

        @Override
        public int size() {
            return this.map().size();
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return this.map().containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            if (this.contains(o)) {
                this.map().remove(o);
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return super.removeAll(Preconditions.checkNotNull(c));
        }

        @Override
        public void clear() {
            this.map().clear();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @GwtCompatible
    static abstract class ImprovedAbstractMap<K, V>
    extends AbstractMap<K, V> {
        private Set<Map.Entry<K, V>> entrySet;
        private Set<K> keySet;
        private Collection<V> values;

        ImprovedAbstractMap() {
        }

        protected abstract Set<Map.Entry<K, V>> createEntrySet();

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            if (result == null) {
                this.entrySet = result = this.createEntrySet();
            }
            return result;
        }

        @Override
        public Set<K> keySet() {
            Set<K> result = this.keySet;
            if (result == null) {
                this.keySet = new KeySet<K, V>(){

                    @Override
                    Map<K, V> map() {
                        return ImprovedAbstractMap.this;
                    }
                };
                return this.keySet;
            }
            return result;
        }

        @Override
        public Collection<V> values() {
            Collection<V> result = this.values;
            if (result == null) {
                this.values = new Values<K, V>(){

                    @Override
                    Map<K, V> map() {
                        return ImprovedAbstractMap.this;
                    }
                };
                return this.values;
            }
            return result;
        }

        @Override
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class FilteredEntryMap<K, V>
    extends AbstractFilteredMap<K, V> {
        final Set<Map.Entry<K, V>> filteredEntrySet;
        Set<Map.Entry<K, V>> entrySet;
        Set<K> keySet;

        FilteredEntryMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            EntrySet result = this.entrySet;
            return result == null ? (this.entrySet = new EntrySet()) : result;
        }

        @Override
        public Set<K> keySet() {
            KeySet result = this.keySet;
            return result == null ? (this.keySet = new KeySet()) : result;
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private class KeySet
        extends AbstractSet<K> {
            private KeySet() {
            }

            @Override
            public Iterator<K> iterator() {
                final Iterator iterator = FilteredEntryMap.this.filteredEntrySet.iterator();
                return new UnmodifiableIterator<K>(){

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public K next() {
                        return ((Map.Entry)iterator.next()).getKey();
                    }
                };
            }

            @Override
            public int size() {
                return FilteredEntryMap.this.filteredEntrySet.size();
            }

            @Override
            public void clear() {
                FilteredEntryMap.this.filteredEntrySet.clear();
            }

            @Override
            public boolean contains(Object o) {
                return FilteredEntryMap.this.containsKey(o);
            }

            @Override
            public boolean remove(Object o) {
                if (FilteredEntryMap.this.containsKey(o)) {
                    FilteredEntryMap.this.unfiltered.remove(o);
                    return true;
                }
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                for (Object obj : collection) {
                    changed |= this.remove(obj);
                }
                return changed;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                Iterator iterator = FilteredEntryMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    if (collection.contains(entry.getKey()) || !FilteredEntryMap.this.predicate.apply(entry)) continue;
                    iterator.remove();
                    changed = true;
                }
                return changed;
            }

            @Override
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return Lists.newArrayList(this.iterator()).toArray(array);
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        private class EntrySet
        extends ForwardingSet<Map.Entry<K, V>> {
            private EntrySet() {
            }

            @Override
            protected Set<Map.Entry<K, V>> delegate() {
                return FilteredEntryMap.this.filteredEntrySet;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                final Iterator iterator = FilteredEntryMap.this.filteredEntrySet.iterator();
                return new UnmodifiableIterator<Map.Entry<K, V>>(){

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Map.Entry<K, V> next() {
                        final Map.Entry entry = (Map.Entry)iterator.next();
                        return new ForwardingMapEntry<K, V>(){

                            @Override
                            protected Map.Entry<K, V> delegate() {
                                return entry;
                            }

                            @Override
                            public V setValue(V value) {
                                Preconditions.checkArgument(FilteredEntryMap.this.apply(entry.getKey(), value));
                                return super.setValue(value);
                            }
                        };
                    }
                };
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class FilteredKeyMap<K, V>
    extends AbstractFilteredMap<K, V> {
        Predicate<? super K> keyPredicate;
        Set<Map.Entry<K, V>> entrySet;
        Set<K> keySet;

        FilteredKeyMap(Map<K, V> unfiltered, Predicate<? super K> keyPredicate, Predicate<Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.keyPredicate = keyPredicate;
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            return result == null ? (this.entrySet = Sets.filter(this.unfiltered.entrySet(), this.predicate)) : result;
        }

        @Override
        public Set<K> keySet() {
            Set<K> result = this.keySet;
            return result == null ? (this.keySet = Sets.filter(this.unfiltered.keySet(), this.keyPredicate)) : result;
        }

        @Override
        public boolean containsKey(Object key) {
            return this.unfiltered.containsKey(key) && this.keyPredicate.apply(key);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static abstract class AbstractFilteredMap<K, V>
    extends AbstractMap<K, V> {
        final Map<K, V> unfiltered;
        final Predicate<? super Map.Entry<K, V>> predicate;
        Collection<V> values;

        AbstractFilteredMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }

        boolean apply(Object key, V value) {
            Object k = key;
            return this.predicate.apply(Maps.immutableEntry(k, value));
        }

        @Override
        public V put(K key, V value) {
            Preconditions.checkArgument(this.apply(key, value));
            return this.unfiltered.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }

        @Override
        public boolean containsKey(Object key) {
            return this.unfiltered.containsKey(key) && this.apply(key, this.unfiltered.get(key));
        }

        @Override
        public V get(Object key) {
            V value = this.unfiltered.get(key);
            return value != null && this.apply(key, value) ? (V)value : null;
        }

        @Override
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }

        @Override
        public V remove(Object key) {
            return this.containsKey(key) ? (V)this.unfiltered.remove(key) : null;
        }

        @Override
        public Collection<V> values() {
            Values result = this.values;
            return result == null ? (this.values = new Values()) : result;
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        class Values
        extends AbstractCollection<V> {
            Values() {
            }

            @Override
            public Iterator<V> iterator() {
                final Iterator entryIterator = AbstractFilteredMap.this.entrySet().iterator();
                return new UnmodifiableIterator<V>(){

                    @Override
                    public boolean hasNext() {
                        return entryIterator.hasNext();
                    }

                    @Override
                    public V next() {
                        return ((Map.Entry)entryIterator.next()).getValue();
                    }
                };
            }

            @Override
            public int size() {
                return AbstractFilteredMap.this.entrySet().size();
            }

            @Override
            public void clear() {
                AbstractFilteredMap.this.entrySet().clear();
            }

            @Override
            public boolean isEmpty() {
                return AbstractFilteredMap.this.entrySet().isEmpty();
            }

            @Override
            public boolean remove(Object o) {
                Iterator iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    if (!Objects.equal(o, entry.getValue()) || !AbstractFilteredMap.this.predicate.apply(entry)) continue;
                    iterator.remove();
                    return true;
                }
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                Iterator iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    if (!collection.contains(entry.getValue()) || !AbstractFilteredMap.this.predicate.apply(entry)) continue;
                    iterator.remove();
                    changed = true;
                }
                return changed;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                Iterator iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    if (collection.contains(entry.getValue()) || !AbstractFilteredMap.this.predicate.apply(entry)) continue;
                    iterator.remove();
                    changed = true;
                }
                return changed;
            }

            @Override
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return Lists.newArrayList(this.iterator()).toArray(array);
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class TransformedEntriesMap<K, V1, V2>
    extends AbstractMap<K, V2> {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;
        Set<Map.Entry<K, V2>> entrySet;
        Collection<V2> values;

        TransformedEntriesMap(Map<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
            this.fromMap = Preconditions.checkNotNull(fromMap);
            this.transformer = Preconditions.checkNotNull(transformer);
        }

        @Override
        public int size() {
            return this.fromMap.size();
        }

        @Override
        public boolean containsKey(Object key) {
            return this.fromMap.containsKey(key);
        }

        @Override
        public V2 get(Object key) {
            V1 value = this.fromMap.get(key);
            return (V2)(value != null || this.fromMap.containsKey(key) ? this.transformer.transformEntry(key, value) : null);
        }

        @Override
        public V2 remove(Object key) {
            return this.fromMap.containsKey(key) ? (V2)this.transformer.transformEntry((K)key, (V1)this.fromMap.remove(key)) : null;
        }

        @Override
        public void clear() {
            this.fromMap.clear();
        }

        @Override
        public Set<K> keySet() {
            return this.fromMap.keySet();
        }

        @Override
        public Set<Map.Entry<K, V2>> entrySet() {
            EntrySet result = this.entrySet;
            if (result == null) {
                this.entrySet = result = new EntrySet<K, V2>(){

                    @Override
                    Map<K, V2> map() {
                        return TransformedEntriesMap.this;
                    }

                    @Override
                    public Iterator<Map.Entry<K, V2>> iterator() {
                        Iterator backingIterator = TransformedEntriesMap.this.fromMap.entrySet().iterator();
                        return Iterators.transform(backingIterator, new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>(){

                            @Override
                            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                                return Maps.immutableEntry(entry.getKey(), TransformedEntriesMap.this.transformer.transformEntry(entry.getKey(), entry.getValue()));
                            }
                        });
                    }
                };
            }
            return result;
        }

        @Override
        public Collection<V2> values() {
            Collection<V2> result = this.values;
            if (result == null) {
                this.values = new Values<K, V2>(){

                    @Override
                    Map<K, V2> map() {
                        return TransformedEntriesMap.this;
                    }
                };
                return this.values;
            }
            return result;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static interface EntryTransformer<K, V1, V2> {
        public V2 transformEntry(@Nullable K var1, @Nullable V1 var2);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class UnmodifiableBiMap<K, V>
    extends ForwardingMap<K, V>
    implements BiMap<K, V>,
    Serializable {
        final Map<K, V> unmodifiableMap;
        final BiMap<? extends K, ? extends V> delegate;
        transient BiMap<V, K> inverse;
        transient Set<V> values;
        private static final long serialVersionUID = 0L;

        UnmodifiableBiMap(BiMap<? extends K, ? extends V> delegate, @Nullable BiMap<V, K> inverse) {
            this.unmodifiableMap = Collections.unmodifiableMap(delegate);
            this.delegate = delegate;
            this.inverse = inverse;
        }

        @Override
        protected Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        @Override
        public V forcePut(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BiMap<V, K> inverse() {
            BiMap<K, V> result = this.inverse;
            return result == null ? (this.inverse = new UnmodifiableBiMap<V, K>(this.delegate.inverse(), this)) : result;
        }

        @Override
        public Set<V> values() {
            Set<V> result = this.values;
            return result == null ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class UnmodifiableEntrySet<K, V>
    extends UnmodifiableEntries<K, V>
    implements Set<Map.Entry<K, V>> {
        UnmodifiableEntrySet(Set<Map.Entry<K, V>> entries) {
            super(entries);
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
    static class UnmodifiableEntries<K, V>
    extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> entries;

        UnmodifiableEntries(Collection<Map.Entry<K, V>> entries) {
            this.entries = entries;
        }

        @Override
        protected Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            final Iterator delegate = super.iterator();
            return new ForwardingIterator<Map.Entry<K, V>>(){

                @Override
                public Map.Entry<K, V> next() {
                    return Maps.unmodifiableEntry((Map.Entry)super.next());
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                protected Iterator<Map.Entry<K, V>> delegate() {
                    return delegate;
                }
            };
        }

        @Override
        public boolean add(Map.Entry<K, V> element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] array) {
            return this.standardToArray(array);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class ValueDifferenceImpl<V>
    implements MapDifference.ValueDifference<V> {
        private final V left;
        private final V right;

        static <V> MapDifference.ValueDifference<V> create(@Nullable V left, @Nullable V right) {
            return new ValueDifferenceImpl<V>(left, right);
        }

        private ValueDifferenceImpl(@Nullable V left, @Nullable V right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public V leftValue() {
            return this.left;
        }

        @Override
        public V rightValue() {
            return this.right;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof MapDifference.ValueDifference) {
                MapDifference.ValueDifference that = (MapDifference.ValueDifference)object;
                return Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }

        public String toString() {
            return "(" + this.left + ", " + this.right + ")";
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class MapDifferenceImpl<K, V>
    implements MapDifference<K, V> {
        final boolean areEqual;
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;
        final Map<K, V> onBoth;
        final Map<K, MapDifference.ValueDifference<V>> differences;

        MapDifferenceImpl(boolean areEqual, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
            this.areEqual = areEqual;
            this.onlyOnLeft = onlyOnLeft;
            this.onlyOnRight = onlyOnRight;
            this.onBoth = onBoth;
            this.differences = differences;
        }

        @Override
        public boolean areEqual() {
            return this.areEqual;
        }

        @Override
        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }

        @Override
        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }

        @Override
        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }

        @Override
        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof MapDifference) {
                MapDifference other = (MapDifference)object;
                return ((Object)this.entriesOnlyOnLeft()).equals(other.entriesOnlyOnLeft()) && ((Object)this.entriesOnlyOnRight()).equals(other.entriesOnlyOnRight()) && ((Object)this.entriesInCommon()).equals(other.entriesInCommon()) && ((Object)this.entriesDiffering()).equals(other.entriesDiffering());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
        }

        public String toString() {
            if (this.areEqual) {
                return "equal";
            }
            StringBuilder result = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                result.append(": only on left=").append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                result.append(": only on right=").append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                result.append(": value differences=").append(this.differences);
            }
            return result.toString();
        }
    }
}

