/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

final class XPSanityListener
        implements Listener {
    XPSanityListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getLevel() >= 1001) {
            Bukkit.getLogger().info("Set player " + e.getPlayer() + " to level 0, old level => " + e.getPlayer().getLevel());
            e.getPlayer().setLevel(0);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getPlayer().getName() + " EXP exploit, contact administration for item removal.");
        }
        ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
        for (ItemStack i : e.getPlayer().getInventory()) {
            if (i == null || i.getType() != Material.EXP_BOTTLE || ExpBottles.getXpAmount(i) <= 1000000) continue;
            toRemove.add(i);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + e.getPlayer().getName() + " EXP exploit, contact administration for item removal.");
            Bukkit.getLogger().info("Removing EXP_BOTTLE with an xp value of " + ExpBottles.getXpAmount(i) + " from " + e.getPlayer().getName() + "!");
        }
        for (ItemStack i : toRemove) {
            e.getPlayer().getInventory().remove(i);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
        for (ItemStack i : e.getInventory()) {
            if (i == null || i.getType() != Material.EXP_BOTTLE || ExpBottles.getXpAmount(i) <= 1000000) continue;
            toRemove.add(i);
            Bukkit.getLogger().info("Removing EXP_BOTTLE with an xp value of " + ExpBottles.getXpAmount(i) + " from " + e.getPlayer().getName() + "!");
        }
        for (ItemStack i : toRemove) {
            e.getInventory().remove(i);
        }
    }
}

