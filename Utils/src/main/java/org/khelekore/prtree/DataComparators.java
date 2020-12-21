/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Comparator;
import org.khelekore.prtree.MBRConverter;
import org.khelekore.prtree.NodeComparators;

class DataComparators<T>
implements NodeComparators<T> {
    private final MBRConverter<T> converter;

    public DataComparators(MBRConverter<T> converter) {
        this.converter = converter;
    }

    @Override
    public Comparator<T> getMinComparator(final int axis) {
        return new Comparator<T>(){

            @Override
            public int compare(T t1, T t2) {
                double d1 = DataComparators.this.converter.getMin(axis, t1);
                double d2 = DataComparators.this.converter.getMin(axis, t2);
                return Double.compare(d1, d2);
            }
        };
    }

    @Override
    public Comparator<T> getMaxComparator(final int axis) {
        return new Comparator<T>(){

            @Override
            public int compare(T t1, T t2) {
                double d1 = DataComparators.this.converter.getMax(axis, t1);
                double d2 = DataComparators.this.converter.getMax(axis, t2);
                return Double.compare(d1, d2);
            }
        };
    }
}

