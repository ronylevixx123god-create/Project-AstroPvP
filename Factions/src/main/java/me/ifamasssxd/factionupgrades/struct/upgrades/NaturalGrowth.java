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

public class NaturalGrowth
extends Upgrade {
    public NaturalGrowth(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public int getTokenCost(int tier) {
        if (tier == 3) {
            return 2;
        }
        if (tier == 3) {
            return 4;
        }
        if (tier == 4) {
            return 6;
        }
        return 0;
    }

    @Override
    public double getMoneyCost(int tier) {
        return tier * 5000000;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((Object)(tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "125% Growth Speed", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "150% Growth Speed", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "200% Growth Speed", (Object)(tier >= 4 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 4 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "250% Growth Speed", (Object)(tier >= 5 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 5 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "300% Growth Speed");
    }
}

