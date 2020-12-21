/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class EmptyImmutableMap
extends ImmutableMap<Object, Object> {
    static final EmptyImmutableMap INSTANCE = new EmptyImmutableMap();
    private static final long serialVersionUID = 0L;

    private EmptyImmutableMap() {
    }

    @Override
    public Object get(@Nullable Object key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return false;
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return false;
    }

    @Override
    public ImmutableSet<Map.Entry<Object, Object>> entrySet() {
        return ImmutableSet.of();
    }

    @Override
    public ImmutableSet<Object> keySet() {
        return ImmutableSet.of();
    }

    @Override
    public ImmutableCollection<Object> values() {
        return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object instanceof Map) {
            Map that = (Map)object;
            return that.isEmpty();
        }
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "{}";
    }

    Object readResolve() {
        return INSTANCE;
    }
}

