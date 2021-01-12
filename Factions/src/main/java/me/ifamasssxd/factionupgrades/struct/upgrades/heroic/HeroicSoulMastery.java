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

public class HeroicSoulMastery
extends HeroicUpgrade {
    public HeroicSoulMastery(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 15;
        }
        if (tier == 2) {
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
            return 7.5E7;
        }
        if (tier == 2) {
            return 1.5E8;
        }
        return 0.0;
    }

    @Override
    public long getFTopSpawnerValueRequirement() {
        return 1250000000L;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "-30% Soul Cost", this.getLorePrefix(currentTier, 2) + "-45% Soul Cost");
    }
}

