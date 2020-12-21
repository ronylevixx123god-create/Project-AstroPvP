/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableMap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableMap;
import com.google.common.collect.SingletonImmutableMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableMap<K, V>
implements Map<K, V>,
Serializable {
    public static <K, V> ImmutableMap<K, V> of() {
        return EmptyImmutableMap.INSTANCE;
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
        return new SingletonImmutableMap<K, V>(Preconditions.checkNotNull(k1), Preconditions.checkNotNull(v1));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) {
        return new RegularImmutableMap(ImmutableMap.entryOf(k1, v1), ImmutableMap.entryOf(k2, v2));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new RegularImmutableMap(ImmutableMap.entryOf(k1, v1), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new RegularImmutableMap(ImmutableMap.entryOf(k1, v1), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3), ImmutableMap.entryOf(k4, v4));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new RegularImmutableMap(ImmutableMap.entryOf(k1, v1), ImmutableMap.entryOf(k2, v2), ImmutableMap.entryOf(k3, v3), ImmutableMap.entryOf(k4, v4), ImmutableMap.entryOf(k5, v5));
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    static <K, V> Map.Entry<K, V> entryOf(K key, V value) {
        return Maps.immutableEntry(Preconditions.checkNotNull(key, "null key"), Preconditions.checkNotNull(value, "null value"));
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        ImmutableMap kvMap;
        if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap) && !(kvMap = (ImmutableMap)map).isPartialView()) {
            return kvMap;
        }
        Map.Entry[] entries = map.entrySet().toArray(new Map.Entry[0]);
        switch (entries.length) {
            case 0: {
                return ImmutableMap.of();
            }
            case 1: {
                return new SingletonImmutableMap(ImmutableMap.entryOf(entries[0].getKey(), entries[0].getValue()));
            }
        }
        for (int i = 0; i < entries.length; ++i) {
            Object k = entries[i].getKey();
            Object v = entries[i].getValue();
            entries[i] = ImmutableMap.entryOf(k, v);
        }
        return new RegularImmutableMap(entries);
    }

    ImmutableMap() {
    }

    @Override
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return this.get(key) != null;
    }

    @Override
    public abstract boolean containsValue(@Nullable Object var1);

    @Override
    public abstract V get(@Nullable Object var1);

    @Override
    public abstract ImmutableSet<Map.Entry<K, V>> entrySet();

    @Override
    public abstract ImmutableSet<K> keySet();

    @Override
    public abstract ImmutableCollection<V> values();

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Map) {
            Map that = (Map)object;
            return ((ImmutableSet)this.entrySet()).equals(that.entrySet());
        }
        return false;
    }

    abstract boolean isPartialView();

    @Override
    public int hashCode() {
        return ((ImmutableSet)this.entrySet()).hashCode();
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    Object writeReplace() {
        return new SerializedForm(this);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class SerializedForm
    implements Serializable {
        private final Object[] keys;
        private final Object[] values;
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableMap<?, ?> map) {
            this.keys = new Object[map.size()];
            this.values = new Object[map.size()];
            int i = 0;
            for (Map.Entry entry : map.entrySet()) {
                this.keys[i] = entry.getKey();
                this.values[i] = entry.getValue();
                ++i;
            }
        }

        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>();
            return this.createMap(builder);
        }

        Object createMap(Builder<Object, Object> builder) {
            for (int i = 0; i < this.keys.length; ++i) {
                builder.put(this.keys[i], this.values[i]);
            }
            return builder.build();
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder<K, V> {
        final ArrayList<Map.Entry<K, V>> entries = Lists.newArrayList();

        public Builder<K, V> put(K key, V value) {
            this.entries.add(ImmutableMap.entryOf(key, value));
            return this;
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            this.entries.ensureCapacity(this.entries.size() + map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableMap<K, V> build() {
            return Builder.fromEntryList(this.entries);
        }

        private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> entries) {
            int size = entries.size();
            switch (size) {
                case 0: {
                    return ImmutableMap.of();
                }
                case 1: {
                    return new SingletonImmutableMap<K, V>(Iterables.getOnlyElement(entries));
                }
            }
            Map.Entry[] entryArray = entries.toArray(new Map.Entry[entries.size()]);
            return new RegularImmutableMap(entryArray);
        }
    }
}

