/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 */
package com.compilex.CosmicCrate.listeners;

import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener
implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (CCAPI.getCosmicCrateVersion(e.getItemInHand()) != null) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
        }
    }
}

