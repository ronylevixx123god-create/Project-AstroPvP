/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.massivecraft.factions.upgrade.upgrades.disabled;

import com.massivecraft.factions.upgrade.Upgrade;

import javax.annotation.Nullable;
import java.util.List;

public class PowerResisilence
extends Upgrade {
    public PowerResisilence(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        return 100000.0;
    }

    @Override
    public List<String> getPerkList(int currentTier) {
        return null;
    }
}

