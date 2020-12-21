/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

public class DistanceResult<T> {
    private final T t;
    private final double dist;

    public DistanceResult(T t, double dist) {
        this.t = t;
        this.dist = dist;
    }

    public T get() {
        return this.t;
    }

    public double getDistance() {
        return this.dist;
    }
}

