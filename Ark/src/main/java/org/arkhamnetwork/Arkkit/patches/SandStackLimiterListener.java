/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPistonExtendEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.SandStackLimiter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

final class SandStackLimiterListener
implements Listener {
    List<Material> gravityMaterials = new ArrayList<Material>();

    SandStackLimiterListener() {
        this.gravityMaterials.add(Material.SAND);
        this.gravityMaterials.add(Material.GRAVEL);
        this.gravityMaterials.add(Material.ANVIL);
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e) {
        for (Block b : e.getBlocks()) {
            int blockCount;
            if (!this.gravityMaterials.contains((Object)b.getType())) continue;
            for (blockCount = 0; blockCount < SandStackLimiter.maxStackSize && b.getY() < 256 && this.gravityMaterials.contains((Object)b.getType()); ++blockCount) {
                b = b.getLocation().add(0.0, 1.0, 0.0).getBlock();
            }
            if (blockCount < SandStackLimiter.maxStackSize) continue;
            e.setCancelled(true);
            Bukkit.getLogger().info("[Arkkit (SandStackLimiter)] Preventing piston push at " + (Object)e.getBlock().getLocation() + " due to blockCount>=" + blockCount);
            return;
        }
    }
}

