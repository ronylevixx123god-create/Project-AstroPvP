/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
abstract class AbstractSetMultimap<K, V>
extends AbstractMultimap<K, V>
implements SetMultimap<K, V> {
    private static final long serialVersionUID = 7431625294878419160L;

    protected AbstractSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract Set<V> createCollection();

    @Override
    public Set<V> get(@Nullable K key) {
        return (Set)super.get(key);
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return (Set)super.entries();
    }

    @Override
    public Set<V> removeAll(@Nullable Object key) {
        return (Set)super.removeAll(key);
    }

    @Override
    public Set<V> replaceValues(@Nullable K key, Iterable<? extends V> values) {
        return (Set)super.replaceValues(key, values);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public boolean put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }
}

