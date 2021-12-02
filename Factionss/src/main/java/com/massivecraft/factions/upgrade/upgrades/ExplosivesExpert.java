/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.bukkit.ChatColor
 */
package com.massivecraft.factions.upgrade.upgrades;

import com.google.common.collect.Lists;
import com.massivecraft.factions.upgrade.Upgrade;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.List;

public class ExplosivesExpert
extends Upgrade {
    public ExplosivesExpert(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        if (tier == 1) {
            return 15;
        }
        if (tier == 2) {
            return 20;
        }
        if (tier == 3) {
            return 30;
        }
        if (tier == 4) {
            return 50;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 1.0E7;
        }
        if (tier == 2) {
            return 2.5E7;
        }
        if (tier == 3) {
            return 3.5E7;
        }
        if (tier == 4) {
            return 5.0E7;
        }
        return Double.MAX_VALUE;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((Object)(tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "Costs 13gp/per TNT", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "Costs 10gp/per TNT", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "Costs 8gp/per TNT", (Object)(tier >= 4 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 4 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "Chance to get Custom TNT");
    }
}

