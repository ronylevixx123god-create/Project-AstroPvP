/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Hopper
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryMoveItemEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

final class NoHoppersListener
implements Listener {
    NoHoppersListener() {
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
        if (e.getSource().getHolder() instanceof Hopper || e.getDestination().getHolder() instanceof Hopper) {
            e.setCancelled(true);
        }
    }
}

