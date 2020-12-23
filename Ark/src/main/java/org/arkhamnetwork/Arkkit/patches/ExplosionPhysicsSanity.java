/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;

final class ExplosionPhysicsSanity
        implements Listener {
    ExplosionPhysicsSanity() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent e) {
        double srcY = e.getEntity().getLocation().getY();
        if (srcY <= 2.0) {
            ArrayList<Block> toRemove = new ArrayList<Block>();
            for (Block b : e.blockList()) {
                if (!(b.getLocation().getY() > 250.0)) continue;
                Bukkit.getLogger().info("[ExplosionPhysicsSanity] Removing impossible heightBlock from blockList (" + b.getLocation().getWorld().getName() + "," + b.getLocation().getX() + "," + b.getLocation().getY() + "," + b.getLocation().getZ() + ")");
                toRemove.add(b);
            }
            for (Block b : toRemove) {
                e.blockList().remove(b);
            }
        }
    }
}

