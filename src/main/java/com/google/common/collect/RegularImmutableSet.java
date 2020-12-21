/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableSet;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true, emulated=true)
final class RegularImmutableSet<E>
extends ImmutableSet.ArrayImmutableSet<E> {
    @VisibleForTesting
    final transient Object[] table;
    private final transient int mask;
    private final transient int hashCode;

    RegularImmutableSet(Object[] elements, int hashCode, Object[] table, int mask) {
        super(elements);
        this.table = table;
        this.mask = mask;
        this.hashCode = hashCode;
    }

    @Override
    public boolean contains(Object target) {
        if (target == null) {
            return false;
        }
        int i = Hashing.smear(target.hashCode());
        Object candidate;
        while ((candidate = this.table[i & this.mask]) != null) {
            if (candidate.equals(target)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    boolean isHashCodeFast() {
        return true;
    }
}

