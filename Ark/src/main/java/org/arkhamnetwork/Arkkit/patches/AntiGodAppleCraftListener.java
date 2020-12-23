/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.CraftItemEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

final class AntiGodAppleCraftListener
        implements Listener {
    AntiGodAppleCraftListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCraftItem(CraftItemEvent event) {
        ItemStack input = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        if (event.getRecipe().getResult().getType() == Material.HOPPER) {
            event.setResult(null);
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot craft Hoppers in the Cosmoverse.");
        }
        if (input.getType() == Material.GOLDEN_APPLE && input.getDurability() == 1) {
            event.setCancelled(true);
            event.setCurrentItem(null);
            player.updateInventory();
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + ChatColor.RED + " You cannot craft God Apples in the Cosmoverse.");
        }
    }
}

