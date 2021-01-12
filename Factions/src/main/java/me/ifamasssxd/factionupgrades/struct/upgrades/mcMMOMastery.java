/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.bukkit.ChatColor
 */
package me.ifamasssxd.factionupgrades.struct.upgrades;

import com.google.common.collect.Lists;
import me.ifamasssxd.factionupgrades.struct.Upgrade;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.List;

public class mcMMOMastery
extends Upgrade {
    public mcMMOMastery(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        if (tier == 1) {
            return 5;
        }
        if (tier == 2) {
            return 15;
        }
        if (tier == 3) {
            return 25;
        }
        if (tier == 4) {
            return 30;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 5000000.0;
        }
        if (tier == 2) {
            return 1.0E7;
        }
        if (tier == 3) {
            return 2.0E7;
        }
        if (tier == 4) {
            return 3.5E7;
        }
        return Double.MAX_VALUE;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((Object)(tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-2.5% mcMMO Cooldown", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-5.0% mcMMO Cooldown", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-12.5% mcMMO Cooldown", (Object)(tier >= 4 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 4 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-15.0% mcMMO Cooldown");
    }
}

