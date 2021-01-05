/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPistonRetractEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package com.cosmicpvp.CosmicSponges.listeners;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class SpongeModifyListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.SPONGE) {
            CosmicSpongesAPI.handleSpongeCreate(e.getBlock());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockDamage(BlockDamageEvent e) {
        if (e.getBlock().getType() == Material.SPONGE) {
            e.setInstaBreak(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.SPONGE) {
            CosmicSpongesAPI.handleSpongeDestroy(e.getBlock());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if (b.getType() != Material.SPONGE) continue;
            CosmicSpongesAPI.handleSpongeDestroy(b);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPistonPush(BlockPistonExtendEvent e) {
        for (Block b : e.getBlocks()) {
            if (b.getType() != Material.SPONGE) continue;
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPistonRetract(BlockPistonRetractEvent e) {
        BlockFace direction = e.getDirection();
        Block movedBlock = e.getBlock().getRelative(direction).getRelative(direction);
        if (movedBlock.getType() == Material.SPONGE) {
            e.setCancelled(true);
        }
    }
}

