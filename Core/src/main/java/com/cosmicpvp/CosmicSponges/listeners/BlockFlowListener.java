/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockFromToEvent
 */
package com.cosmicpvp.CosmicSponges.listeners;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFlowListener
implements Listener {
    List<Material> liquidMaterials = Arrays.asList(new Material[]{Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA});

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onWaterLavaUpdate(BlockFromToEvent e) {
        Block fromBlock = e.getBlock();
        if (!this.liquidMaterials.contains((Object)fromBlock.getType())) {
            return;
        }
        SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(e.getToBlock().getChunk());
        if (!sc.canLiquidFlow(e.getToBlock())) {
            e.setCancelled(true);
            return;
        }
    }
}

