/*
 * Decompiled with CFR 0.150.
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

class DimensionalDamageTracker {
    private int hitsTaken;
    private long lastHitTaken;

    DimensionalDamageTracker() {
    }

    public boolean trackHit() {
        this.checkHit();
        ++this.hitsTaken;
        return this.hitsTaken >= 4;
    }

    public boolean checkHit() {
        if (this.lastHitTaken == 0L || System.currentTimeMillis() - this.lastHitTaken > 10000L) {
            this.hitsTaken = 0;
            this.lastHitTaken = System.currentTimeMillis();
        }
        return this.hitsTaken >= 4;
    }
}

