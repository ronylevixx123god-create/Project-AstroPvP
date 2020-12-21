/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

final class NoTallGrassListener
implements Listener {
    NoTallGrassListener() {
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Material m;
        if (e.hasBlock() && e.getAction() == Action.LEFT_CLICK_BLOCK && (m = e.getClickedBlock().getType()) == Material.DOUBLE_PLANT) {
            e.getClickedBlock().setType(Material.AIR);
        }
    }
}

