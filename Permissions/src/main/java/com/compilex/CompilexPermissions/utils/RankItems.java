/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CompilexPermissions.utils;

import com.compilex.CompilexPermissions.structure.PermissionGroup;
import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RankItems {
    public static HashMap<Integer, String> donorTiers = new HashMap();

    public static ItemStack getRankItem(String rank) {
        return ItemUtils.buildItem((Material)Material.PAPER, (short)0, (String)(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "RANK \"" + (Object)ChatColor.WHITE + StringUtils.capitalize((String)rank) + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "\""), (String[])new String[]{(Object)ChatColor.GRAY + "Right-click this item to unlock ", (Object)ChatColor.GRAY + "the " + StringUtils.capitalize((String)rank) + " Rank on this planet."});
    }

    public static String getRankFromItemstack(ItemStack i) {
        if (i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().startsWith(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "RANK ")) {
            return ChatColor.stripColor((String)i.getItemMeta().getDisplayName().split(" ")[1].replace("\"", "")).toLowerCase();
        }
        return null;
    }

    public static boolean isAboveNormalRank(String groupName) {
        return groupName.equals("helper") || groupName.equals("mod") || groupName.contains("admin") || groupName.equals("developer") || groupName.contains("owner") || groupName.equals("parkourking") || groupName.equals("goodness") || groupName.startsWith("youtuber") || groupName.startsWith("streamer") || groupName.equals("chatmod") || groupName.equals("moderator") || groupName.equals("srmod") || groupName.contains("banteam") || groupName.equals("aussieadmin") || groupName.equals("videographer");
    }

    public static void findDonorRanks() {
        for (Map.Entry<String, PermissionGroup> entry : ArkhamPermissions.getInstance().getPermissionManager().getCachedLoadedGroups().entrySet()) {
            String groupName = entry.getKey();
            PermissionGroup group = entry.getValue();
            if (groupName.equals("default") || RankItems.isAboveNormalRank(groupName) || groupName.contains("_temp")) continue;
            groupName = groupName.replace("_temp", "");
            if (group.getPermissions().contains("cosmicpvp.heroic")) continue;
            if (group.getPermissions().contains("enchantments.max.20")) {
                donorTiers.put(5, groupName);
                continue;
            }
            if (group.getPermissions().contains("enchantments.max.12")) {
                donorTiers.put(4, groupName);
                continue;
            }
            if (group.getPermissions().contains("enchantments.max.9")) {
                donorTiers.put(3, groupName);
                continue;
            }
            if (group.getPermissions().contains("enchantments.max.6")) {
                donorTiers.put(2, groupName);
                continue;
            }
            if (!group.getPermissions().contains("enchantments.max.3")) continue;
            donorTiers.put(1, groupName);
        }
        for (Map.Entry<Integer, String> entry : donorTiers.entrySet()) {
            Bukkit.getLogger().info("[CosmicLootCrates] Set groupName " + (String)entry.getValue() + " as ID " + entry.getKey());
        }
    }

    public static String getRank(int donorTier) {
        return donorTiers.containsKey(donorTier) ? donorTiers.get(donorTier) : null;
    }

    public static int getRankID(String rankName) {
        rankName = rankName.replace("_temp", "");
        for (Map.Entry<Integer, String> data : donorTiers.entrySet()) {
            if (!data.getValue().equalsIgnoreCase(rankName)) continue;
            return data.getKey();
        }
        return -1;
    }
}

