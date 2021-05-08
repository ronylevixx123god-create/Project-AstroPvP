/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  javax.annotation.Nullable
 */
package me.ifamasssxd.factionupgrades.struct;

import com.cosmicpvp.cosmicutils.utils.CC;

import javax.annotation.Nullable;
import java.util.List;

public abstract class HeroicUpgrade
extends Upgrade {
    public HeroicUpgrade(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    public abstract int getHeroicCrystalCost(int var1);

    protected String getLorePrefix(int level, int cmp) {
        return this.getPrefix(level, cmp) + this.getDescPrefix(level, cmp);
    }

    protected String getPrefix(int level, int cmp) {
        return (level >= cmp ? CC.GreenB : CC.RedB) + "* ";
    }

    protected String getDescPrefix(int level, int cmp) {
        return level >= cmp ? CC.Green + CC.Strike : CC.Gray;
    }
}

