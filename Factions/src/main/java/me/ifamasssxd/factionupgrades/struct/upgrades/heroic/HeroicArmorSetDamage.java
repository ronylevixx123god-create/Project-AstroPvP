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

public class HeroicArmorSetDamage
extends HeroicUpgrade {
    public HeroicArmorSetDamage(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        if (tier == 1) {
            return 25;
        }
        if (tier == 2) {
            return 40;
        }
        if (tier == 3) {
            return 60;
        }
        if (tier == 4) {
            return 80;
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
            return 1.5E8;
        }
        if (tier == 2) {
            return 2.5E8;
        }
        if (tier == 3) {
            return 4.0E8;
        }
        if (tier == 4) {
            return 5.0E8;
        }
        return 0.0;
    }

    @Override
    public long getFTopSpawnerValueRequirement() {
        return 3000000000L;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "-10% Damage from Armor Sets", this.getLorePrefix(currentTier, 2) + "-15% Damage from Armor Sets", this.getLorePrefix(currentTier, 3) + "-20% Damage from Armor Sets", this.getLorePrefix(currentTier, 4) + "-25% Damage from Armor Sets");
    }
}

