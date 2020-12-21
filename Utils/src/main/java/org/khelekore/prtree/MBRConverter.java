/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

public interface MBRConverter<T> {
    public int getDimensions();

    public double getMin(int var1, T var2);

    public double getMax(int var1, T var2);
}

