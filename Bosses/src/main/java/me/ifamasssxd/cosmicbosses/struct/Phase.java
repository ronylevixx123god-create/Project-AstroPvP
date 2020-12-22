/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.struct;

public enum Phase {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7);

    private int phase;

    public Phase getNextPhase() {
        int next = this.phase + 1;
        return Phase.getPhase(next);
    }

    public static Phase getPhase(int phase) {
        for (Phase ph : Phase.values()) {
            if (ph.getPhase() != phase) continue;
            return ph;
        }
        return ONE;
    }

    private Phase(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return this.phase;
    }
}

