/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
class RegularImmutableBiMap<K, V>
extends ImmutableBiMap<K, V> {
    final transient ImmutableMap<K, V> delegate;
    final transient ImmutableBiMap<V, K> inverse;

    RegularImmutableBiMap(ImmutableMap<K, V> delegate) {
        this.delegate = delegate;
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (Map.Entry entry : delegate.entrySet()) {
            builder.put(entry.getValue(), entry.getKey());
        }
        ImmutableMap backwardMap = builder.build();
        this.inverse = new RegularImmutableBiMap<V, K>(backwardMap, this);
    }

    RegularImmutableBiMap(ImmutableMap<K, V> delegate, ImmutableBiMap<V, K> inverse) {
        this.delegate = delegate;
        this.inverse = inverse;
    }

    @Override
    ImmutableMap<K, V> delegate() {
        return this.delegate;
    }

    @Override
    public ImmutableBiMap<V, K> inverse() {
        return this.inverse;
    }

    @Override
    boolean isPartialView() {
        return this.delegate.isPartialView() || this.inverse.delegate().isPartialView();
    }
}

