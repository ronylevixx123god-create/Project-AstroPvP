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

public class HeroicVKitLeveling
extends HeroicUpgrade {
    public HeroicVKitLeveling(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 2) {
            return 4;
        }
        if (tier == 3) {
            return 8;
        }
        if (tier == 4) {
            return 12;
        }
        if (tier == 5) {
            return 15;
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
            return 5.0E7;
        }
        if (tier == 2) {
            return 7.5E7;
        }
        if (tier == 3) {
            return 1.0E8;
        }
        if (tier == 4) {
            return 1.25E8;
        }
        if (tier == 5) {
            return 1.5E8;
        }
        return 0.0;
    }

    @Override
    public long getFTopSpawnerValueRequirement() {
        return 1000000000L;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "+10% chance of leveling /vkit", this.getLorePrefix(currentTier, 2) + "+15% chance of leveling /vkit", this.getLorePrefix(currentTier, 3) + "+20% chance of leveling /vkit", this.getLorePrefix(currentTier, 4) + "+30% chance of leveling /vkit", this.getLorePrefix(currentTier, 5) + "+45% chance of leveling /vkit");
    }
}

