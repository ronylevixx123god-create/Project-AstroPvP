package com.massivecraft.factions.upgrade;

import java.util.List;
import javax.annotation.Nullable;

public abstract class Upgrade {
    protected String defaultName;

    protected List<String> defaultLore;

    private int maxUpgradeLevel;

    public Upgrade(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        this.defaultLore = defaultLore;
        this.defaultName = defaultName;
        this.maxUpgradeLevel = maxUpgradeLevel;
    }

    public abstract int getTokenCost(int paramInt);

    public abstract double getMoneyCost(int paramInt);

    public String getDisplayName(int tier) {
        return this.defaultName;
    }

    public List<String> getLore(int tier) {
        return this.defaultLore;
    }

    public abstract List<String> getPerkList(int paramInt);

    public int getMaxUpgradeLevel() {
        return this.maxUpgradeLevel;
    }
}
