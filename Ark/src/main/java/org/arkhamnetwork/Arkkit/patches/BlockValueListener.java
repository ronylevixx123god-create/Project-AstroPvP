/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.BlockValueLocation;
import org.arkhamnetwork.Arkkit.patches.BlockValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;

final class BlockValueListener
implements Listener {
    HashMap<Block, Long> blockPlaceEvent = new HashMap();
    HashSet<String> scannedChunks = new HashSet();

    BlockValueListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (BlockValue.blocksWithValue.contains((Object)e.getBlock().getType())) {
            BlockValue.trackedBlockValues.put(BlockValueListener.convertBlockToString(e.getBlock()), BlockValueListener.convertBlockToBlockValueLocation(e.getBlock()));
            this.blockPlaceEvent.put(e.getBlock(), System.currentTimeMillis());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent e) {
        if (BlockValue.blocksWithValue.contains((Object)e.getBlock().getType()) && BlockValue.trackedBlockValues.containsKey(BlockValueListener.convertBlockToString(e.getBlock()))) {
            BlockValue.trackedBlockValues.remove(BlockValueListener.convertBlockToString(e.getBlock()));
            this.blockPlaceEvent.remove((Object)e.getBlock());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent e) {
        final ArrayList<Location> blocksToCheck = new ArrayList<Location>();
        for (Block b : e.blockList()) {
            if (!BlockValue.blocksWithValue.contains((Object)b.getType())) continue;
            blocksToCheck.add(b.getLocation());
        }
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                for (Location l : blocksToCheck) {
                    if (BlockValue.blocksWithValue.contains((Object)l.getBlock().getType())) continue;
                    String s = BlockValueListener.convertBlockToString(l.getBlock());
                    Bukkit.getLogger().info("[Arkkit (BlockValue)] Removing " + s + " from blockValues list due to explosion.");
                    BlockValue.trackedBlockValues.remove(s);
                }
            }
        }, 5L);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onChunkLoad(ChunkLoadEvent e) {
        if (!Arkkit.get().getConfig().getBoolean("settings.blockValue.chunkLoadScan")) {
            return;
        }
        String cs = e.getChunk().getX() + "," + e.getChunk().getZ();
        if (this.scannedChunks.contains(cs)) {
            return;
        }
        FLocation fl = new FLocation(e.getChunk().getBlock(0, 0, 0));
        if (Board.getFactionAt((FLocation)fl).isNormal()) {
            int blocksFound = 0;
            for (BlockState blockState : e.getChunk().getTileEntities()) {
                String blockString;
                if (!BlockValue.blocksWithValue.contains((Object)blockState.getBlock().getType()) || BlockValue.trackedBlockValues.containsKey(blockString = BlockValueListener.convertBlockToString(blockState.getBlock()))) continue;
                BlockValue.trackedBlockValues.put(blockString, BlockValueListener.convertBlockToBlockValueLocation(blockState.getBlock()));
                ++blocksFound;
            }
            if (blocksFound > 0) {
                Bukkit.getLogger().info("[Arkkit (BlockValue)] Tracked " + blocksFound + " new valueBlocks in chunk: " + cs);
            }
        }
        this.scannedChunks.add(e.getChunk().getX() + "," + e.getChunk().getZ());
    }

    public static String convertBlockToString(Block b) {
        return b.getWorld().getName() + "," + b.getX() + "," + b.getY() + "," + b.getZ();
    }

    public static BlockValueLocation convertBlockToBlockValueLocation(Block b) {
        return new BlockValueLocation(b.getX(), b.getY(), b.getZ(), b.getWorld().getName(), b.getType(), System.currentTimeMillis());
    }
}

