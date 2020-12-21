/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockRedstoneEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import java.util.Map;
import org.arkhamnetwork.Arkkit.patches.AntiRedstoneLag;
import org.arkhamnetwork.Arkkit.patches.AntiRedstoneLagTask;
import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

final class AntiRedstoneLagListener
implements Listener {
    protected static HashMap<String, Integer> redstone_tracker = new HashMap();
    protected static Map<Long, Integer> fastRedstoneTracker = new HashMap<Long, Integer>();

    AntiRedstoneLagListener() {
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onBlockRedstoneEvent(BlockRedstoneEvent e) {
        Integer flag;
        long chunk = this.getChunkValue(e.getBlock().getChunk());
        int tracked = fastRedstoneTracker.getOrDefault(chunk, 0);
        if (tracked >= AntiRedstoneLag.max_redstone_per_iteration) {
            e.setNewCurrent(0);
        }
        if ((flag = AntiRedstoneLagTask.fastLaggingChunks.get(chunk)) != null && flag > 2) {
            tracked += Math.min(50, flag - 2);
        }
        fastRedstoneTracker.put(chunk, ++tracked);
    }

    private long getChunkValue(Chunk c) {
        return LongHash.toLong(c.getX(), c.getZ());
    }

    private String getChunkString(Chunk c) {
        return c.getX() + "," + c.getZ();
    }
}

