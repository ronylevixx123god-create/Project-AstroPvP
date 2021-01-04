/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.killtracker.utils;

import java.util.ArrayList;
import java.util.List;
import me.ifamasssxd.killtracker.KillTracker;
import me.ifamasssxd.killtracker.listeners.Tracker;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    private static String SOUL_STRING = "Souls Harvested";

    private static String getSoulString(Tracker tracker) {
        return tracker.trackMobKills() ? "Monster " + SOUL_STRING : SOUL_STRING;
    }

    public static boolean isTrackable(ItemStack is) {
        String name = is.getType().name().toLowerCase();
        return name.endsWith("_sword") || name.endsWith("bow") || name.endsWith("_axe");
    }

    public static int getTrackedKills(ItemStack is) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            for (String s : is.getItemMeta().getLore()) {
                if (!s.contains("Souls Harvested") || !s.contains(KillTracker.LORE_SUFFIX)) continue;
                return Integer.parseInt(ChatColor.stripColor((String)s.split(KillTracker.LORE_SUFFIX)[1]));
            }
        }
        return -1;
    }

    public static void removeTracker(ItemStack is, Tracker tracker) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            List lore = im.getLore();
            for (String s : new ArrayList(lore)) {
                if (!s.contains(ItemUtils.getSoulString(tracker)) || !s.contains(KillTracker.LORE_SUFFIX)) continue;
                lore.remove(s);
                break;
            }
            im.setLore(lore);
            is.setItemMeta(im);
        }
    }

    public static boolean containsString(List<String> lore, String goal) {
        for (String s : lore) {
            if (!s.contains(goal)) continue;
            return true;
        }
        return false;
    }

    public static void setTrackedKills(ItemStack is, Tracker tracker, int amount) {
        List lore;
        if (is.getItemMeta() == null) {
            return;
        }
        ItemMeta im = is.getItemMeta();
        boolean alreadySet = false;
        if (is.getItemMeta().hasLore() && im.getLore().size() > 0 && ItemUtils.containsString(im.getLore(), ItemUtils.getSoulString(tracker))) {
            alreadySet = true;
        }
        List list = alreadySet ? (im.hasLore() ? im.getLore() : new ArrayList()) : (lore = im.hasLore() ? im.getLore() : new ArrayList());
        if (alreadySet) {
            for (int i = 0; i < lore.size(); ++i) {
                String s = (String)lore.get(i);
                if (!s.contains(ItemUtils.getSoulString(tracker)) || !s.contains(KillTracker.LORE_SUFFIX)) continue;
                if (i != lore.size() - 1) {
                    lore.remove(i);
                    lore.add((Object)tracker.getColor() + ChatColor.BOLD.toString() + ItemUtils.getSoulString(tracker) + (Object)tracker.getColor() + KillTracker.LORE_SUFFIX + (Object)tracker.getColor() + (Object)ChatColor.BOLD + amount);
                    continue;
                }
                lore.set(i, (Object)tracker.getColor() + ChatColor.BOLD.toString() + ItemUtils.getSoulString(tracker) + (Object)tracker.getColor() + KillTracker.LORE_SUFFIX + (Object)tracker.getColor() + (Object)ChatColor.BOLD + amount);
            }
        } else {
            lore.add((Object)tracker.getColor() + ChatColor.BOLD.toString() + ItemUtils.getSoulString(tracker) + (Object)tracker.getColor() + KillTracker.LORE_SUFFIX + (Object)tracker.getColor() + (Object)ChatColor.BOLD + amount);
        }
        im.setLore(lore);
        is.setItemMeta(im);
    }
}

