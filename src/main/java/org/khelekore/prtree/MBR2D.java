/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

public interface MBR2D {
    public double getMinX();

    public double getMinY();

    public double getMaxX();

    public double getMaxY();

    public MBR2D union(MBR2D var1);

    public boolean intersects(MBR2D var1);
}

