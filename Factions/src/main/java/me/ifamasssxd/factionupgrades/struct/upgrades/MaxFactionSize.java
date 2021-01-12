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

public class MaxFactionSize
extends Upgrade {
    public MaxFactionSize(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        return tier * 2;
    }

    @Override
    public double getMoneyCost(int tier) {
        return tier * 1000000;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((Object)(tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+1 Max Faction Size", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+2 Max Faction Size", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+3 Max Faction Size", (Object)(tier >= 4 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 4 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+4 Max Faction Size", (Object)(tier >= 5 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 5 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+5 Max Faction Size", (Object)(tier >= 6 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 6 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+6 Max Faction Size", (Object)(tier >= 7 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 7 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+7 Max Faction Size", (Object)(tier >= 8 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 8 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+8 Max Faction Size", (Object)(tier >= 9 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 9 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+9 Max Faction Size", (Object)(tier >= 10 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 10 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "+10 Max Faction Size");
    }
}

