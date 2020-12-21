/*
 * Decompiled with CFR 0.150.
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@GwtCompatible(serializable=true)
final class CompoundOrdering<T>
extends Ordering<T>
implements Serializable {
    final ImmutableList<Comparator<? super T>> comparators;
    private static final long serialVersionUID = 0L;

    CompoundOrdering(Comparator<? super T> primary, Comparator<? super T> secondary) {
        this.comparators = ImmutableList.of(primary, secondary);
    }

    CompoundOrdering(Iterable<? extends Comparator<? super T>> comparators) {
        this.comparators = ImmutableList.copyOf(comparators);
    }

    CompoundOrdering(List<? extends Comparator<? super T>> comparators, Comparator<? super T> lastComparator) {
        this.comparators = ((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(comparators)).add(lastComparator)).build();
    }

    @Override
    public int compare(T left, T right) {
        for (Comparator comparator : this.comparators) {
            int result = comparator.compare(left, right);
            if (result == 0) continue;
            return result;
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof CompoundOrdering) {
            CompoundOrdering that = (CompoundOrdering)object;
            return this.comparators.equals(that.comparators);
        }
        return false;
    }

    public int hashCode() {
        return this.comparators.hashCode();
    }

    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}

