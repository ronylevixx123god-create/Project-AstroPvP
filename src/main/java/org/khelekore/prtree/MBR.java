/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import org.khelekore.prtree.MBRConverter;

public interface MBR {
    public int getDimensions();

    public double getMin(int var1);

    public double getMax(int var1);

    public MBR union(MBR var1);

    public boolean intersects(MBR var1);

    public <T> boolean intersects(T var1, MBRConverter<T> var2);
}

