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

public class WellFed
extends Upgrade {
    public WellFed(int maxUpgradeLevel, String defaultName, @Nullable List<String> defaultLore) {
        super(maxUpgradeLevel, defaultName, defaultLore);
    }

    @Override
    public List<String> getLore(int tier) {
        return super.getLore(tier);
    }

    @Override
    public int getTokenCost(int tier) {
        return tier * 2;
    }

    @Override
    public double getMoneyCost(int tier) {
        if (tier == 1) {
            return 500000.0;
        }
        if (tier == 2) {
            return 2000000.0;
        }
        if (tier == 3) {
            return 4000000.0;
        }
        if (tier == 4) {
            return 1.0E7;
        }
        return Double.MAX_VALUE;
    }

    @Override
    public List<String> getPerkList(int tier) {
        return Lists.newArrayList((Object)(tier >= 1 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 1 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-5% Hunger increase rate", (Object)(tier >= 2 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 2 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-10% Hunger increase rate", (Object)(tier >= 3 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 3 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-15% Hunger increase rate", (Object)(tier >= 4 ? ChatColor.GREEN : ChatColor.RED) + ChatColor.BOLD.toString() + "* " + (tier >= 4 ? (Object)ChatColor.GREEN + "" + (Object)ChatColor.STRIKETHROUGH : ChatColor.GRAY) + "-25% Hunger increase rate");
    }
}

