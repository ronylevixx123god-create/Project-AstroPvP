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

public class EnderFarming
extends Upgrade {
    public EnderFarming(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        if (tier == 1) {
            return 5;
        }
        if (tier == 2) {
            return 10;
        }
        if (tier == 3) {
            return 20;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 1.0E7;
        }
        if (tier == 2) {
            return 2.0E7;
        }
        if (tier == 3) {
            return 6.0E7;
        }
        return Double.MAX_VALUE;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "2.0x Killstreak Multiplier", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "3.0x Killstreak Multiplier", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "5.0x Killstreak Multiplier");
    }
}

