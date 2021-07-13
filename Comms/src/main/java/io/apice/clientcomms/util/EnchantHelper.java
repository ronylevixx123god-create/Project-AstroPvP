/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.enchantments.Enchantment
 */
package io.apice.clientcomms.util;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;

public class EnchantHelper {
    public static boolean isVanillaEnchant(String name) {
        try {
            if (Enchantment.getByName((String)name) != null) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static ChatColor getTierColor(int tier) {
        if (tier == 1) {
            return ChatColor.GRAY;
        }
        if (tier == 2) {
            return ChatColor.GREEN;
        }
        if (tier == 3) {
            return ChatColor.AQUA;
        }
        if (tier == 4) {
            return ChatColor.YELLOW;
        }
        if (tier == 5) {
            return ChatColor.GOLD;
        }
        if (tier == 6) {
            return ChatColor.RED;
        }
        if (tier == 7) {
            return ChatColor.LIGHT_PURPLE;
        }
        if (tier == 8) {
            return ChatColor.DARK_RED;
        }
        return ChatColor.GRAY;
    }

    public static String getTierName(int tier) {
        switch (tier) {
            case 1: {
                return "Simple";
            }
            case 2: {
                return "Unique";
            }
            case 3: {
                return "Elite";
            }
            case 4: {
                return "Ultimate";
            }
            case 5: {
                return "Legendary";
            }
            case 6: {
                return "Soul";
            }
            case 7: {
                return "Heroic";
            }
            case 8: {
                return "Mastery";
            }
        }
        return "Unknown";
    }
}

