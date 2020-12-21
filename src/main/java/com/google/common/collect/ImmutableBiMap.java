/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.RegularImmutableBiMap;
import java.util.Map;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableBiMap<K, V>
extends ImmutableMap<K, V>
implements BiMap<K, V> {
    private static final ImmutableBiMap<Object, Object> EMPTY_IMMUTABLE_BIMAP = new EmptyBiMap();

    public static <K, V> ImmutableBiMap<K, V> of() {
        return EMPTY_IMMUTABLE_BIMAP;
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1));
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2));
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }

    public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        ImmutableBiMap bimap;
        if (map instanceof ImmutableBiMap && !(bimap = (ImmutableBiMap)map).isPartialView()) {
            return bimap;
        }
        if (map.isEmpty()) {
            return ImmutableBiMap.of();
        }
        ImmutableMap<? extends K, ? extends V> immutableMap = ImmutableMap.copyOf(map);
        return new RegularImmutableBiMap<K, V>(immutableMap);
    }

    ImmutableBiMap() {
    }

    abstract ImmutableMap<K, V> delegate();

    @Override
    public abstract ImmutableBiMap<V, K> inverse();

    @Override
    public boolean containsKey(@Nullable Object key) {
        return this.delegate().containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return ((ImmutableBiMap)this.inverse()).containsKey(value);
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }

    @Override
    public V get(@Nullable Object key) {
        return this.delegate().get(key);
    }

    @Override
    public ImmutableSet<K> keySet() {
        return this.delegate().keySet();
    }

    @Override
    public ImmutableSet<V> values() {
        return ((ImmutableBiMap)this.inverse()).keySet();
    }

    @Override
    public V forcePut(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }

    @Override
    public int size() {
        return this.delegate().size();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    public String toString() {
        return this.delegate().toString();
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
        private static final long serialVersionUID = 0L;

        SerializedForm(ImmutableBiMap<?, ?> bimap) {
            super(bimap);
        }

        @Override
        Object readResolve() {
            Builder<Object, Object> builder = new Builder<Object, Object>();
            return this.createMap(builder);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    static class EmptyBiMap
    extends ImmutableBiMap<Object, Object> {
        EmptyBiMap() {
        }

        @Override
        ImmutableMap<Object, Object> delegate() {
            return ImmutableMap.of();
        }

        @Override
        public ImmutableBiMap<Object, Object> inverse() {
            return this;
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        Object readResolve() {
            return EMPTY_IMMUTABLE_BIMAP;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static final class Builder<K, V>
    extends ImmutableMap.Builder<K, V> {
        @Override
        public Builder<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }

        @Override
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        @Override
        public ImmutableBiMap<K, V> build() {
            ImmutableMap map = super.build();
            if (map.isEmpty()) {
                return ImmutableBiMap.of();
            }
            return new RegularImmutableBiMap(map);
        }
    }
}

