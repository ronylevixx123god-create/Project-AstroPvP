/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.boss.type;

import java.util.Random;

public enum Bosses {
    BLAZE(new BlazeBoss(), "Blaze"),
    IRONGOLEM(new IronGolemBoss(), "Golem"),
    SLIME(new SlimeBoss(), "Slime"),
    WITHER(new WitherBoss(), "Wither"),
    PIG_ZOMBIE(new TreasureGoblinBoss(), "Goblin"),
    ELF(new EvilChristmasElf(), "Elf"),
    HEADLESS_KING(new HeadlessKingBoss(), "Horseman"),
    CHICKEN(new EvilChicken(), "Chicken");

    public me.ifamasssxd.cosmicbosses.boss.type.Boss b;
    public String name;

    private Bosses(me.ifamasssxd.cosmicbosses.boss.type.Boss bt, String sys_name) {
        this.b = bt;
        this.name = sys_name;
    }

    public static Boss getRandomBoss() {
        return Bosses.values()[new Random().nextInt((int)Bosses.values().length)].b;
    }
}

