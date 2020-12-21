/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true)
class RegularImmutableMultiset<E>
extends ImmutableMultiset<E> {
    private final transient ImmutableMap<E, Integer> map;
    private final transient int size;

    RegularImmutableMultiset(ImmutableMap<E, Integer> map, int size) {
        this.map = map;
        this.size = size;
    }

    @Override
    boolean isPartialView() {
        return this.map.isPartialView();
    }

    @Override
    public int count(@Nullable Object element) {
        Integer value = this.map.get(element);
        return value == null ? 0 : value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean contains(@Nullable Object element) {
        return this.map.containsKey(element);
    }

    @Override
    public ImmutableSet<E> elementSet() {
        return this.map.keySet();
    }

    @Override
    UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
        final Iterator mapIterator = ((ImmutableSet)this.map.entrySet()).iterator();
        return new UnmodifiableIterator<Multiset.Entry<E>>(){

            @Override
            public boolean hasNext() {
                return mapIterator.hasNext();
            }

            @Override
            public Multiset.Entry<E> next() {
                Map.Entry mapEntry = (Map.Entry)mapIterator.next();
                return Multisets.immutableEntry(mapEntry.getKey(), (Integer)mapEntry.getValue());
            }
        };
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    int distinctElements() {
        return this.map.size();
    }
}

