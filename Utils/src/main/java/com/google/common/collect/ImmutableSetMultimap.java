/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.EmptyImmutableSetMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public class ImmutableSetMultimap<K, V>
extends ImmutableMultimap<K, V>
implements SetMultimap<K, V> {
    private final transient ImmutableSortedSet<V> emptySet;
    private transient ImmutableSet<Map.Entry<K, V>> entries;
    @GwtIncompatible(value="not needed in emulated source.")
    private static final long serialVersionUID = 0L;

    public static <K, V> ImmutableSetMultimap<K, V> of() {
        return EmptyImmutableSetMultimap.INSTANCE;
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k1, (Object)v1);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k1, (Object)v1);
        builder.put((Object)k2, (Object)v2);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k1, (Object)v1);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k1, (Object)v1);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k1, (Object)v1);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        builder.put((Object)k5, (Object)v5);
        return builder.build();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        return ImmutableSetMultimap.copyOf(multimap, null);
    }

    private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> valueComparator) {
        ImmutableSetMultimap kvMultimap;
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && valueComparator == null) {
            return ImmutableSetMultimap.of();
        }
        if (multimap instanceof ImmutableSetMultimap && !(kvMultimap = (ImmutableSetMultimap)multimap).isPartialView()) {
            return kvMultimap;
        }
        ImmutableMap.Builder<K, ImmutableSet<? extends V>> builder = ImmutableMap.builder();
        int size = 0;
        for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
            K key = entry.getKey();
            Collection<? extends V> values = entry.getValue();
            ImmutableSet<Object> set = valueComparator == null ? ImmutableSet.copyOf(values) : ImmutableSortedSet.copyOf(valueComparator, values);
            if (set.isEmpty()) continue;
            builder.put(key, set);
            size += set.size();
        }
        return new ImmutableSetMultimap(builder.build(), size, valueComparator);
    }

    ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> map, int size, @Nullable Comparator<? super V> valueComparator) {
        super(map, size);
        this.emptySet = valueComparator == null ? null : ImmutableSortedSet.emptySet(valueComparator);
    }

    @Override
    public ImmutableSet<V> get(@Nullable K key) {
        ImmutableSet set = (ImmutableSet)this.map.get(key);
        if (set != null) {
            return set;
        }
        if (this.emptySet != null) {
            return this.emptySet;
        }
        return ImmutableSet.of();
    }

    @Override
    public ImmutableSet<V> removeAll(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSet<V> replaceValues(K key, Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entries() {
        ImmutableSet<Map.Entry<K, V>> result = this.entries;
        return result == null ? (this.entries = ImmutableSet.copyOf(super.entries())) : result;
    }

    @GwtIncompatible(value="java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMultimap(this, stream);
    }

    @GwtIncompatible(value="java.io.ObjectInputStream")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        ImmutableMap tmpMap;
        stream.defaultReadObject();
        int keyCount = stream.readInt();
        if (keyCount < 0) {
            throw new InvalidObjectException("Invalid key count " + keyCount);
        }
        ImmutableMap.Builder<Object, ImmutableSet<Object>> builder = ImmutableMap.builder();
        int tmpSize = 0;
        for (int i = 0; i < keyCount; ++i) {
            Object key = stream.readObject();
            int valueCount = stream.readInt();
            if (valueCount <= 0) {
                throw new InvalidObjectException("Invalid value count " + valueCount);
            }
            Object[] array = new Object[valueCount];
            for (int j = 0; j < valueCount; ++j) {
                array[j] = stream.readObject();
            }
            ImmutableSet<Object> valueSet = ImmutableSet.copyOf(array);
            if (valueSet.size() != array.length) {
                throw new InvalidObjectException("Duplicate key-value pairs exist for key " + key);
            }
            builder.put(key, valueSet);
            tmpSize += valueCount;
        }
        try {
            tmpMap = builder.build();
        }
        catch (IllegalArgumentException e) {
            throw (InvalidObjectException)new InvalidObjectException(e.getMessage()).initCause(e);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ImmutableMultimap)this, tmpMap);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ImmutableMultimap)this, tmpSize);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class Builder<K, V>
    extends ImmutableMultimap.Builder<K, V> {
        public Builder() {
            this.builderMultimap = new BuilderMultimap();
        }

        @Override
        public Builder<K, V> put(K key, V value) {
            this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
            return this;
        }

        @Override
        public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
            Collection collection = this.builderMultimap.get(Preconditions.checkNotNull(key));
            for (V value : values) {
                collection.add(Preconditions.checkNotNull(value));
            }
            return this;
        }

        @Override
        public Builder<K, V> putAll(K key, V ... values) {
            return this.putAll((Object)key, Arrays.asList(values));
        }

        @Override
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry<K, Collection<V>> entry : multimap.asMap().entrySet()) {
                this.putAll((Object)entry.getKey(), entry.getValue());
            }
            return this;
        }

        @Override
        @Beta
        public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
            this.builderMultimap = new SortedKeyBuilderMultimap(Preconditions.checkNotNull(keyComparator), this.builderMultimap);
            return this;
        }

        @Override
        @Beta
        public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
            super.orderValuesBy(valueComparator);
            return this;
        }

        @Override
        public ImmutableSetMultimap<K, V> build() {
            return ImmutableSetMultimap.copyOf(this.builderMultimap, this.valueComparator);
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
            return Sets.newLinkedHashSet();
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
            return Sets.newLinkedHashSet();
        }
    }
}

