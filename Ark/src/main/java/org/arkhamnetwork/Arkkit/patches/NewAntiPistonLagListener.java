/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPistonExtendEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

final class NewAntiPistonLagListener
        implements Listener {
    protected static Map<Long, Integer> chunkPistonTracker = new HashMap<Long, Integer>();
    protected static HashSet<Chunk> flaggedChunks = new HashSet();
    NewAntiPistonLag patch;

    public NewAntiPistonLagListener(NewAntiPistonLag patch) {
        this.patch = patch;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e) {
        long chunk = this.patch.getChunkValue(e.getBlock().getChunk());
        int tracked = chunkPistonTracker.getOrDefault(chunk, 0);
        if (tracked++ >= this.patch.maxPistonEventsPerChunk) {
            if (tracked == this.patch.maxPistonEventsPerChunk) {
                flaggedChunks.add(e.getBlock().getChunk());
            }
            e.setCancelled(true);
        }
        chunkPistonTracker.put(chunk, tracked);
    }
}

