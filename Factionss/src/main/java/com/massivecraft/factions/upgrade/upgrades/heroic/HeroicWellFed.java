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

public class HeroicWellFed
extends HeroicUpgrade {
    public HeroicWellFed(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 2) {
            return 5;
        }
        if (tier == 3) {
            return 5;
        }
        if (tier == 4) {
            return 10;
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
            return 5000000.0;
        }
        if (tier == 2) {
            return 1.0E7;
        }
        if (tier == 3) {
            return 2.0E7;
        }
        if (tier == 4) {
            return 3.0E7;
        }
        return 0.0;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList(this.getLorePrefix(tier, 1) + "-30% Hunger increase rate", this.getLorePrefix(tier, 2) + "-35% Hunger increase rate", this.getLorePrefix(tier, 3) + "-45% Hunger increase rate", this.getLorePrefix(tier, 4) + "-50% Hunger increase rate");
    }
}

