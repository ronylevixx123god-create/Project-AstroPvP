/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package me.ifamasssxd.factionupgrades.struct;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Upgrade {
    protected String defaultName;
    protected List<String> defaultLore;
    private int maxUpgradeLevel;

    public Upgrade(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        this.defaultLore = defaultLore;
        this.defaultName = defaultName;
        this.maxUpgradeLevel = maxUpgradeLevel;
    }

    public abstract int getTokenCost(int var1);

    public abstract double getMoneyCost(int var1);

    public String getDisplayName(int tier) {
        return this.defaultName;
    }

    public List<String> getLore(int tier) {
        return this.defaultLore;
    }

    public abstract List<String> getPerkList(int var1);

    public int getMaxUpgradeLevel() {
        return this.maxUpgradeLevel;
    }
}

