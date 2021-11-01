/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 */
package com.compilex.CosmicKOTH.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KothInventoryListener
implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e != null) {
            if (e.getClickedInventory() != null) {
                if (e.getClickedInventory().getName().equals("King of the Hill")) {
                    e.setCancelled(true);
                    if (e.getSlot() != 4) {
                        return;
                    }
                    if (CosmicKOTHAPI.isKOTHRunning()) {
                        e.getWhoClicked().teleport(CosmicKOTHPlugin.get_().getSpawn());
                    } else {
                        e.getWhoClicked().sendMessage(CC.RedB + "(!) " + CC.Red + "No KOTH events are currently running.");
                        e.getWhoClicked().sendMessage(CC.Gray + "Use /koth to find the next scheduled KOTH.");
                    }
                }
            }
        }
    }
}

