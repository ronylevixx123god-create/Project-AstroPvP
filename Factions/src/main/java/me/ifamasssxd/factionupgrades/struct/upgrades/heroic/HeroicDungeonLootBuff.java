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

public class HeroicDungeonLootBuff
extends HeroicUpgrade {
    public HeroicDungeonLootBuff(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getHeroicCrystalCost(int tier) {
        return 0;
    }

    @Override
    public int getTokenCost(int tier) {
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 1.0E8;
        }
        if (tier == 2) {
            return 2.0E8;
        }
        if (tier == 3) {
            return 3.0E8;
        }
        if (tier == 4) {
            return 5.0E8;
        }
        return 0.0;
    }


    @Override
    public List<String> getPerkList(int currentTier) {
        return Lists.newArrayList(this.getLorePrefix(currentTier, 1) + "5% chance for +1 Lootbag item", this.getLorePrefix(currentTier, 2) + "8% chance for +1 Lootbag item", this.getLorePrefix(currentTier, 3) + "15% chance for +1 Lootbag item", this.getLorePrefix(currentTier, 4) + "25% chance for +1 Lootbag item");
    }
}

