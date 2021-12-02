package com.massivecraft.factions.upgrade;

import com.massivecraft.factions.util.CC;

import java.util.List;
import javax.annotation.Nullable;

public abstract class HeroicUpgrade extends Upgrade {
    public HeroicUpgrade(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    public abstract int getHeroicCrystalCost(int paramInt);

    protected String getLorePrefix(int level, int cmp) {
        return getPrefix(level, cmp) + getDescPrefix(level, cmp);
    }

    protected String getPrefix(int level, int cmp) {
        return ((level >= cmp) ? CC.GreenB : CC.RedB) + "* ";
    }

    protected String getDescPrefix(int level, int cmp) {
        return (level >= cmp) ? (CC.Green + CC.Strike) : CC.Gray;
    }
}

