/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import java.util.Arrays;

public enum DragonPhase {
    FIREBALL(1),
    BLAZES(2),
    TNT(3),
    SWOOPING(4);

    private int phase;

    public static DragonPhase getDragonPhase(int phase) {
        return Arrays.stream(DragonPhase.values()).filter(ph -> ph.getPhase() == phase).findFirst().orElse(null);
    }

    private DragonPhase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return this.phase;
    }
}

