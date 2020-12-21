/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

public enum Rotation {
    NONE,
    CLOCKWISE,
    FLIPPED,
    COUNTER_CLOCKWISE;

    private static final Rotation[] rotations;

    public Rotation rotateClockwise() {
        return rotations[this.ordinal() + 1 & 3];
    }

    public Rotation rotateCounterClockwise() {
        return rotations[this.ordinal() - 1 & 3];
    }

    static {
        rotations = Rotation.values();
    }
}

