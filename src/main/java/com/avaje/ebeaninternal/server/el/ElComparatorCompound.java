/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.el;

import com.avaje.ebeaninternal.server.el.ElComparator;
import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ElComparatorCompound<T>
implements Comparator<T>,
ElComparator<T> {
    private final ElComparator<T>[] array;

    public ElComparatorCompound(ElComparator<T>[] array) {
        this.array = array;
    }

    @Override
    public int compare(T o1, T o2) {
        for (int i = 0; i < this.array.length; ++i) {
            int ret = this.array[i].compare(o1, o2);
            if (ret == 0) continue;
            return ret;
        }
        return 0;
    }

    @Override
    public int compareValue(Object value, T o2) {
        for (int i = 0; i < this.array.length; ++i) {
            int ret = this.array[i].compareValue(value, o2);
            if (ret == 0) continue;
            return ret;
        }
        return 0;
    }
}

