/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockGrowEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

final class GrowthPhysicsSanity
        implements Listener {
    GrowthPhysicsSanity() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockGrow(BlockGrowEvent e) {
        if (e.getBlock().getLocation().getY() >= 255.0) {
            e.setCancelled(true);
        }
    }
}

