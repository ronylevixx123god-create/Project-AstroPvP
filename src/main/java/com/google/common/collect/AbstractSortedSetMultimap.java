/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible
abstract class AbstractSortedSetMultimap<K, V>
extends AbstractSetMultimap<K, V>
implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    protected AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override
    abstract SortedSet<V> createCollection();

    @Override
    public SortedSet<V> get(@Nullable K key) {
        return (SortedSet)super.get((Object)key);
    }

    @Override
    public SortedSet<V> removeAll(@Nullable Object key) {
        return (SortedSet)super.removeAll(key);
    }

    @Override
    public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
        return (SortedSet)super.replaceValues((Object)key, (Iterable)values);
    }

    @Override
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }
}

