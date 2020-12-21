/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.ObsidianDestroyerFixes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

final class ObsidianDestroyerBootstrapListener
implements Listener {
    ObsidianDestroyerBootstrapListener() {
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent e) {
        Block detonator = e.getLocation().getBlock();
        Location detonatorLoc = e.getLocation();
        if (detonatorLoc.getWorld().getName().equals("world_koth")) {
            return;
        }
        for (int x = -3; x <= 3; ++x) {
            for (int y = 3; y >= -3; --y) {
                for (int z = -3; z <= 3; ++z) {
                    Location targetLoc = new Location(detonator.getWorld(), detonatorLoc.getX() + (double)x, detonatorLoc.getY() + (double)y, detonatorLoc.getZ() + (double)z);
                    Block b = targetLoc.getBlock();
                    if (!ObsidianDestroyerFixes.bad_blocks.contains((Object)b.getType()) || b.getType() == Material.BREWING_STAND && b.getLocation().getY() >= 255.0) continue;
                    b.setType(Material.AIR);
                }
            }
        }
    }
}

