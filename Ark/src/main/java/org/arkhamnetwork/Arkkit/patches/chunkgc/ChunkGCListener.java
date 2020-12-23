/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.concurrent.CopyOnWriteArrayList;

final class ChunkGCListener
        implements Listener {
    ChunkGCListener() {
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        Chunk c = e.getChunk();
        CopyOnWriteArrayList<Long> chunks = ChunkGCTask.loaded_chunks.get(e.getWorld().getName());
        Long val = LongHash.toLong(c.getX(), c.getZ());
        if (chunks != null && !chunks.contains(val)) {
            chunks.add(val);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnloaded(ChunkUnloadEvent e) {
        CopyOnWriteArrayList<Long> chunks = ChunkGCTask.loaded_chunks.get(e.getWorld().getName());
        chunks.remove(LongHash.toLong(e.getChunk().getX(), e.getChunk().getZ()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        PlayerMoveTask.async_player_locations.remove(e.getPlayer().getName());
    }
}

