/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CandyGramGUI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

final class CandyGramListener
implements Listener {
    CandyGramListener() {
    }

    @EventHandler
    public void onPlayerClickNPC(PlayerInteractEntityEvent event) {
        Player clicked;
        if (event.getRightClicked() instanceof Player && ChatColor.stripColor((String)(clicked = (Player)event.getRightClicked()).getName()).equalsIgnoreCase("Candy Man")) {
            event.setCancelled(true);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.4f);
            new CandyGramGUI(event.getPlayer()).openGUI(Arkkit.get());
        }
    }

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals("Select a Player") || event.getInventory().getName().equals("Candy Gram Messages") || event.getInventory().getName().equals("Confirm Candy Gram") || event.getInventory().getName().equals("Confirm Candy Gram") || event.getInventory().getName().equals("Send a Candy Gram") || event.getInventory().getName().equals("Your Candy Grams") || event.getInventory().getName().equals("Select Sender Name")) {
            event.setCancelled(true);
        }
    }
}

