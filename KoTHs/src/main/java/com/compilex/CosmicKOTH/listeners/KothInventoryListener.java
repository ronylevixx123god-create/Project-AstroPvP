/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 */
package com.compilex.CosmicKOTH.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KothInventoryListener
implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getName() == "King of the Hill") {
            e.setCancelled(true);
            if (e.getSlot() != 4) {
                return;
            }
            if (CosmicKOTHAPI.isKOTHRunning()) {
                e.getWhoClicked().teleport(CosmicKOTHPlugin.get_().getSpawn());
            } else {
                ((Player)e.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "No KOTH events are currently running.");
                ((Player)e.getWhoClicked()).sendMessage(CC.Gray + "Use /koth to find the next scheduled KOTH.");
            }
        }
    }
}

