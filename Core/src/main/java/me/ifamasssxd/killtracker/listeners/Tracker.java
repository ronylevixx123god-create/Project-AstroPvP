/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.killtracker.listeners;

import java.util.List;

import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Tracker {
    Simple(ChatColor.WHITE, ChatColor.BOLD.toString() + "Simple", 1),
    Unique(ChatColor.GREEN, ChatColor.BOLD.toString() + "Unique", 2),
    Elite(ChatColor.AQUA, ChatColor.BOLD.toString() + "Elite", 3),
    Ultimate(ChatColor.YELLOW, ChatColor.BOLD.toString() + "Ultimate", 4),
    Legendary(ChatColor.GOLD, ChatColor.BOLD.toString() + "Legendary", 5),
    Godly(ChatColor.RED, ChatColor.BOLD.toString() + "Godly", 8),
    Monster(ChatColor.DARK_AQUA, ChatColor.BOLD.toString() + "Monster", 0, true);

    ChatColor color;
    boolean trackMobKills = false;
    int rank;
    String name;

    private Tracker(ChatColor color, String name, int rank) {
        this.color = color;
        this.name = name;
        this.rank = rank;
    }

    private Tracker(ChatColor color, String name, int rank, boolean mobTracker) {
        this(color, name, rank);
        this.trackMobKills = mobTracker;
    }

    public int getRank() {
        return this.rank;
    }

    public String getName() {
        return (Object)this.getColor() + this.name;
    }

    public boolean trackMobKills() {
        return this.trackMobKills;
    }

    public static Tracker getTrackerType(ItemStack is) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            List<String> lore = is.getItemMeta().getLore();
            for (String s : lore) {
                if (!s.contains(AstroCore.LORE_SUFFIX)) continue;
                for (Tracker tracker : Tracker.values()) {
                    if (!s.startsWith((Object)tracker.getColor() + ChatColor.BOLD.toString() + (tracker.trackMobKills() ? "Monster " : "") + "Souls Harvested" + (Object)tracker.getColor() + AstroCore.LORE_SUFFIX)) continue;
                    return tracker;
                }
            }
        }
        return null;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public boolean isTrackMobKills() {
        return this.trackMobKills;
    }
}

