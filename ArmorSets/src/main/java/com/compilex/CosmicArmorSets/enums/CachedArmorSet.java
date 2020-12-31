/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CosmicArmorSets.enums;

public class CachedArmorSet<T> {
    private T set;
    private long lastUpdated;

    public CachedArmorSet(T set, long lastUpdated) {
        this.set = set;
        this.lastUpdated = lastUpdated;
    }

    public T getSet() {
        return this.set;
    }

    public void setSet(T set) {
        this.set = set;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void update(T set) {
        this.lastUpdated = System.currentTimeMillis();
        this.set = set;
    }
}

