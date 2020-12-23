/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.wimbli.WorldBorder.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

final class WorldBorderFixesListener
        implements Listener {
    WorldBorderFixesListener() {
    }

    @EventHandler
    public void onPistonPushBlock(BlockPistonExtendEvent e) {
        int xMod = e.getDirection().getModX();
        int zMod = e.getDirection().getModZ();
        for (Block b : e.getBlocks()) {
            if (this.inWorldBorder(b.getLocation().add(xMod, 0.0, zMod))) continue;
            Bukkit.getLogger().info("[WorldBorderFixes] Block " + b.getLocation() + " attempted to push outside world border.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonPullBlock(BlockPistonRetractEvent e) {
        if (!this.inWorldBorder(e.getRetractLocation())) {
            Bukkit.getLogger().info("[WorldBorderFixes] Block " + e.getRetractLocation() + " outside world border, preventing retraction.");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getEntity() == null || e.getEntity().getLocation() == null) {
            Bukkit.getLogger().info("[WorldBorderFixes] Null entity or entity location passed to EntityExplodeEvent!");
            return;
        }
        if (!this.inWorldBorder(e.getEntity().getLocation())) {
            Bukkit.getLogger().info("[WorldBorderFixes] Exploding " + e.getEntity().getType().name() + " at " + e.getEntity().getLocation() + " outside world border, preventing explosion.");
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }

    private boolean inWorldBorder(Location l) {
        return WorldBorder.plugin.getWorldBorder(l.getWorld().getName()) == null || WorldBorder.plugin.getWorldBorder(l.getWorld().getName()).insideBorder(l.getX(), l.getZ(), false);
    }
}

