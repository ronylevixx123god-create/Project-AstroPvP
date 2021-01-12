/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package me.ifamasssxd.factionupgrades.struct.upgrades.heroic;

import com.google.common.collect.Lists;
import me.ifamasssxd.factionupgrades.struct.HeroicUpgrade;

import javax.annotation.Nullable;
import java.util.List;

public class HeroicCombatTag
extends HeroicUpgrade {
    public HeroicCombatTag(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public long getFTopSpawnerValueRequirement() {
        return 500000000L;
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 8;
        }
        if (tier == 2) {
            return 12;
        }
        if (tier == 3) {
            return 16;
        }
        if (tier == 4) {
            return 20;
        }
        return 0;
    }

    @Override
    public int getTokenCost(int tier) {
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 2.0E7;
        }
        if (tier == 2) {
            return 4.0E7;
        }
        if (tier == 3) {
            return 6.0E7;
        }
        if (tier == 4) {
            return 1.0E8;
        }
        return 0.0;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "-0.25s Combat Tag Timer", this.getLorePrefix(currentTier, 2) + "-0.5s Combat Tag Timer", this.getLorePrefix(currentTier, 3) + "-1.0s Combat Tag Timer", this.getLorePrefix(currentTier, 4) + "-2.0s Combat Tag Timer");
    }
}

