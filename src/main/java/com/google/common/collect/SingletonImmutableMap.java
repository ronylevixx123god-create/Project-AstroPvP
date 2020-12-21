/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class SingletonImmutableMap<K, V>
extends ImmutableMap<K, V> {
    final transient K singleKey;
    final transient V singleValue;
    private transient Map.Entry<K, V> entry;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableCollection<V> values;

    SingletonImmutableMap(K singleKey, V singleValue) {
        this.singleKey = singleKey;
        this.singleValue = singleValue;
    }

    SingletonImmutableMap(Map.Entry<K, V> entry) {
        this.entry = entry;
        this.singleKey = entry.getKey();
        this.singleValue = entry.getValue();
    }

    private Map.Entry<K, V> entry() {
        Map.Entry<K, V> e = this.entry;
        return e == null ? (this.entry = Maps.immutableEntry(this.singleKey, this.singleValue)) : e;
    }

    @Override
    public V get(@Nullable Object key) {
        return this.singleKey.equals(key) ? (V)this.singleValue : null;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return this.singleKey.equals(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return this.singleValue.equals(value);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return es == null ? (this.entrySet = ImmutableSet.of(this.entry())) : es;
    }

    @Override
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> ks = this.keySet;
        return ks == null ? (this.keySet = ImmutableSet.of(this.singleKey)) : ks;
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> v = this.values;
        return v == null ? (this.values = new Values<V>(this.singleValue)) : v;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Map) {
            Map that = (Map)object;
            if (that.size() != 1) {
                return false;
            }
            Map.Entry entry = that.entrySet().iterator().next();
            return this.singleKey.equals(entry.getKey()) && this.singleValue.equals(entry.getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.singleKey.hashCode() ^ this.singleValue.hashCode();
    }

    @Override
    public String toString() {
        return '{' + this.singleKey.toString() + '=' + this.singleValue.toString() + '}';
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class Values<V>
    extends ImmutableCollection<V> {
        final V singleValue;

        Values(V singleValue) {
            this.singleValue = singleValue;
        }

        @Override
        public boolean contains(Object object) {
            return this.singleValue.equals(object);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return Iterators.singletonIterator(this.singleValue);
        }

        @Override
        boolean isPartialView() {
            return true;
        }
    }
}

