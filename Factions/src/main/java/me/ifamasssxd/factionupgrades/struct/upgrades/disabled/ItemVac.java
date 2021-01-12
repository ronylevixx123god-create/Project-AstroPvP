/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package me.ifamasssxd.factionupgrades.struct.upgrades.disabled;

import me.ifamasssxd.factionupgrades.struct.Upgrade;

import javax.annotation.Nullable;
import java.util.List;

public class ItemVac
extends Upgrade {
    public ItemVac(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        return 10000000 * tier;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return null;
    }
}

