/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPistonExtendEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

final class AntiPistonListener
implements Listener {
    AntiPistonListener() {
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPistonMoveBlock(BlockPistonExtendEvent event) {
        Block push = event.getBlock().getRelative(event.getDirection(), event.getLength() + 1);
        if (push.getType() == Material.MELON_BLOCK || push.getType() == Material.PUMPKIN || push.getType() == Material.SUGAR_CANE_BLOCK || push.getType() == Material.COCOA) {
            event.setCancelled(true);
        }
    }
}

