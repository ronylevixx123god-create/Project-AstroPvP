/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicevolutionkits.utils;

import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKits;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class InventoryUtils {
    public static int getRoundedSize(int size) {
        return size <= 9 ? 9 : (size <= 18 ? 18 : (size <= 27 ? 27 : (size <= 36 ? 36 : (size <= 45 ? 45 : (size <= 54 ? 54 : 54)))));
    }

    public static boolean giveItem(Player player, ItemStack item) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
            return true;
        }
        Item i = player.getWorld().dropItem(player.getLocation(), item);
        i.setMetadata("no_pickup", new FixedMetadataValue(CosmicGlobalKits.getPlugin(), player.getName()));
        return false;
    }
}

