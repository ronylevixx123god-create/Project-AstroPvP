/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.UnmodifiableIterator;
import javax.annotation.Nullable;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true)
final class EmptyImmutableMultiset
extends ImmutableMultiset<Object> {
    static final EmptyImmutableMultiset INSTANCE = new EmptyImmutableMultiset();
    private static final long serialVersionUID = 0L;

    EmptyImmutableMultiset() {
    }

    @Override
    public int count(@Nullable Object element) {
        return 0;
    }

    @Override
    public ImmutableSet<Object> elementSet() {
        return ImmutableSet.of();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    UnmodifiableIterator<Multiset.Entry<Object>> entryIterator() {
        return Iterators.emptyIterator();
    }

    @Override
    int distinctElements() {
        return 0;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    ImmutableSet<Multiset.Entry<Object>> createEntrySet() {
        return ImmutableSet.of();
    }
}

