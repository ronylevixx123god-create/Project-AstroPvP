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

public class HeroicDungeonPortalCountdown
extends HeroicUpgrade {
    public HeroicDungeonPortalCountdown(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 5;
        }
        if (tier == 2) {
            return 7;
        }
        if (tier == 3) {
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
            return 2.5E7;
        }
        if (tier == 2) {
            return 3.5E7;
        }
        if (tier == 3) {
            return 5.0E7;
        }
        return 0.0;
    }



    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "90s Dungeon Portal Close Time", this.getLorePrefix(currentTier, 2) + "80s Dungeon Portal Close Time", this.getLorePrefix(currentTier, 3) + "45s Dungeon Portal Close Time");
    }
}

