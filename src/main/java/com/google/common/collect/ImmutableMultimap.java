/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(emulated=true)
public abstract class ImmutableMultimap<K, V>
implements Multimap<K, V>,
Serializable {
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
    final transient int size;
    private transient ImmutableCollection<Map.Entry<K, V>> entries;
    private transient ImmutableMultiset<K> keys;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;

    public static <K, V> ImmutableMultimap<K, V> of() {
        return ImmutableListMultimap.of();
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1) {
        return ImmutableListMultimap.of(k1, v1);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2) {
        return ImmutableListMultimap.of(k1, v1, k2, v2);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        ImmutableMultimap kvMultimap;
        if (multimap instanceof ImmutableMultimap && !(kvMultimap = (ImmutableMultimap)multimap).isPartialView()) {
            return kvMultimap;
        }
        return ImmutableListMultimap.copyOf(multimap);
    }

    ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> map, int size) {
        this.map = map;
        this.size = size;
    }

    @Override
    public ImmutableCollection<V> removeAll(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableCollection<V> replaceValues(K key, Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract ImmutableCollection<V> get(K var1);

    @Override
    public boolean put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putAll(K key, Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    boolean isPartialView() {
        return this.map.isPartialView();
    }

    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        Collection values = this.map.get(key);
        return values != null && values.contains(value);
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        for (ImmutableCollection valueCollection : this.map.values()) {
            if (!valueCollection.contains(value)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Multimap) {
            Multimap that = (Multimap)object;
            return this.map.equals(that.asMap());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return this.map.toString();
    }

    @Override
    public ImmutableSet<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public ImmutableMap<K, Collection<V>> asMap() {
        return this.map;
    }

    @Override
    public ImmutableCollection<Map.Entry<K, V>> entries() {
        ImmutableCollection<Map.Entry<K, V>> result = this.entries;
        return result == null ? (this.entries = new EntryCollection(this)) : result;
    }

    @Override
    public ImmutableMultiset<K> keys() {
        ImmutableMultiset<K> result = this.keys;
        return result == null ? (this.keys = this.createKeys()) : result;
    }

    private ImmutableMultiset<K> createKeys() {
        ImmutableMultiset.Builder builder = ImmutableMultiset.builder();
        for (Map.Entry entry : this.map.entrySet()) {
            builder.addCopies(entry.getKey(), ((ImmutableCollection)entry.getValue()).size());
        }
        return builder.build();
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> result = this.values;
        return result == null ? (this.values = new Values(this)) : result;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Values<V>
    extends ImmutableCollection<V> {
        final ImmutableMultimap<?, V> multimap;
        private static final long serialVersionUID = 0L;

        Values(ImmutableMultimap<?, V> multimap) {
            this.multimap = multimap;
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            final Iterator entryIterator = ((ImmutableCollection)this.multimap.entries()).iterator();
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
            return this.multimap.size();
        }

        @Override
        boolean isPartialView() {
            return true;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class EntryCollection<K, V>
    extends ImmutableCollection<Map.Entry<K, V>> {
        final ImmutableMultimap<K, V> multimap;
        private static final long serialVersionUID = 0L;

        EntryCollection(ImmutableMultimap<K, V> multimap) {
            this.multimap = multimap;
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            final Iterator mapIterator = ((ImmutableSet)this.multimap.map.entrySet()).iterator();
            return new UnmodifiableIterator<Map.Entry<K, V>>(){
                K key;
                Iterator<V> valueIterator;

                @Override
                public boolean hasNext() {
                    return this.key != null && this.valueIterator.hasNext() || mapIterator.hasNext();
                }

                @Override
                public Map.Entry<K, V> next() {
                    if (this.key == null || !this.valueIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry)mapIterator.next();
                        this.key = entry.getKey();
                        this.valueIterator = ((ImmutableCollection)entry.getValue()).iterator();
                    }
                    return Maps.immutableEntry(this.key, this.valueIterator.next());
                }
            };
        }

        @Override
        boolean isPartialView() {
            return this.multimap.isPartialView();
        }

        @Override
        public int size() {
            return this.multimap.size();
        }

        @Override
        public boolean contains(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry)object;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
    }

    @GwtIncompatible(value="java serialization is not supported")
    static class FieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
        static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");

        FieldSettersHolder() {
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder<K, V> {
        Multimap<K, V> builderMultimap = new BuilderMultimap();
        Comparator<? super V> valueComparator;

        public Builder<K, V> put(K key, V value) {
            this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
            return this;
        }

        public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
            Collection<V> valueList = this.builderMultimap.get(Preconditions.checkNotNull(key));
            for (V value : values) {
                valueList.add(Preconditions.checkNotNull(value));
            }
            return this;
        }

        public Builder<K, V> putAll(K key, V ... values) {
            return this.putAll(key, (Iterable<? extends V>)Arrays.asList(values));
        }

        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
                this.putAll(entry.getKey(), (Iterable)entry.getValue());
            }
            return this;
        }

        @Beta
        public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
            this.builderMultimap = new SortedKeyBuilderMultimap<K, V>(Preconditions.checkNotNull(keyComparator), this.builderMultimap);
            return this;
        }

        @Beta
        public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
            this.valueComparator = Preconditions.checkNotNull(valueComparator);
            return this;
        }

        public ImmutableMultimap<K, V> build() {
            if (this.valueComparator != null) {
                for (Collection<V> values : this.builderMultimap.asMap().values()) {
                    List list = (List)values;
                    Collections.sort(list, this.valueComparator);
                }
            }
            return ImmutableMultimap.copyOf(this.builderMultimap);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class SortedKeyBuilderMultimap<K, V>
    extends AbstractMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        SortedKeyBuilderMultimap(Comparator<? super K> keyComparator, Multimap<K, V> multimap) {
            super(new TreeMap(keyComparator));
            this.putAll(multimap);
        }

        @Override
        Collection<V> createCollection() {
            return Lists.newArrayList();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class BuilderMultimap<K, V>
    extends AbstractMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        BuilderMultimap() {
            super(new LinkedHashMap());
        }

        @Override
        Collection<V> createCollection() {
            return Lists.newArrayList();
        }
    }
}

