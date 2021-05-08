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

public class HeroicEnderPearlCooldown
extends HeroicUpgrade {
    public HeroicEnderPearlCooldown(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 5;
        }
        if (tier == 2) {
            return 10;
        }
        if (tier == 3) {
            return 12;
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
            return 2.5E7;
        }
        if (tier == 2) {
            return 5.0E7;
        }
        if (tier == 3) {
            return 7.5E7;
        }
        if (tier == 4) {
            return 1.25E8;
        }
        return 0.0;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "-0.5s Enderpearl Cooldown", this.getLorePrefix(currentTier, 2) + "-1.0s Enderpearl Cooldown", this.getLorePrefix(currentTier, 3) + "-1.25s Enderpearl Cooldown", this.getLorePrefix(currentTier, 4) + "-2.0s Enderpearl Cooldown");
    }
}

