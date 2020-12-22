/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.rarity;

public enum BossRarity {
    COMMON(1),
    UNCOMMON(2),
    RARE(3),
    ULTRA_RARE(4),
    LEGENDARY(5);

    public int i;

    private BossRarity(int rarity_int) {
        this.i = rarity_int;
    }
}

