/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import java.util.Arrays;
import org.khelekore.prtree.MBR;
import org.khelekore.prtree.MBRConverter;

public class SimpleMBR
implements MBR {
    private final double[] values;

    private SimpleMBR(int dimensions) {
        this.values = new double[dimensions * 2];
    }

    public SimpleMBR(double ... values) {
        this.values = (double[])values.clone();
    }

    public <T> SimpleMBR(T t, MBRConverter<T> converter) {
        int dims = converter.getDimensions();
        this.values = new double[dims * 2];
        int p = 0;
        for (int i = 0; i < dims; ++i) {
            this.values[p++] = converter.getMin(i, t);
            this.values[p++] = converter.getMax(i, t);
        }
    }

    @Override
    public int getDimensions() {
        return this.values.length / 2;
    }

    @Override
    public double getMin(int axis) {
        return this.values[axis * 2];
    }

    @Override
    public double getMax(int axis) {
        return this.values[axis * 2 + 1];
    }

    @Override
    public MBR union(MBR mbr) {
        int dims = this.getDimensions();
        SimpleMBR n = new SimpleMBR(dims);
        int p = 0;
        for (int i = 0; i < dims; ++i) {
            n.values[p] = Math.min(this.getMin(i), mbr.getMin(i));
            n.values[++p] = Math.max(this.getMax(i), mbr.getMax(i));
            ++p;
        }
        return n;
    }

    @Override
    public boolean intersects(MBR other) {
        for (int i = 0; i < this.getDimensions(); ++i) {
            if (!(other.getMax(i) < this.getMin(i)) && !(other.getMin(i) > this.getMax(i))) continue;
            return false;
        }
        return true;
    }

    @Override
    public <T> boolean intersects(T t, MBRConverter<T> converter) {
        for (int i = 0; i < this.getDimensions(); ++i) {
            if (!(converter.getMax(i, t) < this.getMin(i)) && !(converter.getMin(i, t) > this.getMax(i))) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "{values: " + Arrays.toString(this.values) + "}";
    }
}

