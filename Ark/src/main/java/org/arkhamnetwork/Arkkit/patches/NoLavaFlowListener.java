/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockFromToEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

final class NoLavaFlowListener
        implements Listener {
    NoLavaFlowListener() {
    }

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        if (e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA) {
            e.setCancelled(true);
            return;
        }
    }
}

