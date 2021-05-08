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

public class HeroicDungeonLootCooldown
extends HeroicUpgrade {
    public HeroicDungeonLootCooldown(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }



    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 10;
        }
        if (tier == 2) {
            return 20;
        }
        if (tier == 3) {
            return 25;
        }
        if (tier == 4) {
            return 30;
        }
        if (tier == 5) {
            return 35;
        }
        if (tier == 6) {
            return 50;
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
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "-4h Loot Cooldown", this.getLorePrefix(currentTier, 2) + "-8h Loot Cooldown", this.getLorePrefix(currentTier, 3) + "-12h Loot Cooldown", this.getLorePrefix(currentTier, 4) + "-18h Loot Cooldown", this.getLorePrefix(currentTier, 5) + "-24h Loot Cooldown", this.getLorePrefix(currentTier, 6) + "-36h Loot Cooldown");
    }
}

