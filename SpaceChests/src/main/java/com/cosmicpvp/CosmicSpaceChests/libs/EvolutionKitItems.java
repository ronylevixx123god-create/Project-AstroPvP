/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.cosmicpvp.CosmicSpaceChests.libs;

import com.cosmicpvp.CosmicSpaceChests.utils.ItemUtils;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKits;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EvolutionKitItems {
    public static ItemStack getKitItem(EvolutionKits kit) {
        return ItemUtils.buildItem(Material.DIAMOND, (short)0, ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "EVOLUTION KIT \"" + (Object)ChatColor.DARK_PURPLE + kit.getKit().getItemName() + ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "\"", (Object)ChatColor.GRAY + "Right-click this gem to unlock ", (Object)ChatColor.GRAY + "the " + kit.getKit().getItemName() + (Object)ChatColor.GRAY + " /vkit on this planet.");
    }

    public static EvolutionKits getKitFromItemstack(ItemStack i) {
        if (i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().startsWith(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "EVOLUTION KIT ")) {
            String gkitName = EvolutionKitItems.getKitItemName(i);
            for (EvolutionKits gkit : EvolutionKits.values()) {
                String cleanedName = ChatColor.stripColor((String)gkit.getKit().getItemName());
                if (!cleanedName.equals(gkitName)) continue;
                return gkit;
            }
        }
        return null;
    }

    private static String getKitItemName(ItemStack i) {
        return ChatColor.stripColor((String)i.getItemMeta().getDisplayName().replace(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "EVOLUTION KIT \"" + (Object)ChatColor.DARK_PURPLE, "").replace(ChatColor.WHITE.toString() + ChatColor.BOLD.toString() + "\"", ""));
    }
}

