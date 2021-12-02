/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package com.massivecraft.factions.upgrade.upgrades.heroic;

import com.google.common.collect.Lists;
import com.massivecraft.factions.upgrade.HeroicUpgrade;

import javax.annotation.Nullable;
import java.util.List;

public class HeroicBossMastery
extends HeroicUpgrade {
    public HeroicBossMastery(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 16;
        }
        if (tier == 2) {
            return 32;
        }
        if (tier == 3) {
            return 64;
        }
        return 0;
    }

    @Override
    public int getTokenCost(int tier) {
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        return 0.0;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "+105% Boss Damage", this.getLorePrefix(currentTier, 2) + "+115% Boss Damage", this.getLorePrefix(currentTier, 3) + "+125% Boss Damage");
    }
}

