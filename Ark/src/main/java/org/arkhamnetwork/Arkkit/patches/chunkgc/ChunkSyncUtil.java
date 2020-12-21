/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Chunk
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.server.v1_7_R4.Chunk;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGC;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGCTask;
import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

public class ChunkSyncUtil {
    Arkkit plugin = Arkkit.get();

    public static void syncChunks() {
        int loaded_chunks;
        int tracked_loaded_chunks = ChunkSyncUtil.getTrackedLoadedChunks();
        int chunk_offset = Math.abs(tracked_loaded_chunks - (loaded_chunks = ChunkSyncUtil.getLoadedChunkCount()));
        if (chunk_offset < 10 && loaded_chunks > 10) {
            if (chunk_offset > 0) {
                ChunkGC.log.debug("Chunk offset = " + chunk_offset + "<10, no resync.", ChunkSyncUtil.class);
            }
            return;
        }
        ChunkGCTask.loaded_chunks.clear();
        ChunkSyncUtil.populateLoadedChunkList();
        int dif = loaded_chunks - tracked_loaded_chunks;
        if (Math.abs(dif) > 0) {
            ChunkGC.log.debug("Chunk offset (" + (dif > 0 ? "+" : "") + dif + ")", ChunkSyncUtil.class);
        }
    }

    private static void populateLoadedChunkList() {
        for (World w : Arkkit.get().getServer().getWorlds()) {
            if (w.getName().startsWith("dungeon_")) continue;
            Iterator it = ((CraftWorld)w).getHandle().chunkProviderServer.a().iterator();
            CopyOnWriteArrayList<Long> cslist = new CopyOnWriteArrayList<Long>();
            while (it.hasNext()) {
                Chunk c = (Chunk)it.next();
                cslist.add(LongHash.toLong(c.locX, c.locZ));
            }
            ChunkGCTask.loaded_chunks.put(w.getName(), cslist);
        }
    }

    private static int getLoadedChunkCount() {
        int i;
        block3: {
            i = 0;
            try {
                for (World w : Arkkit.get().getServer().getWorlds()) {
                    i += w.getLoadedChunks().length;
                }
            }
            catch (Exception err) {
                err.printStackTrace();
                if (!(err instanceof NoSuchElementException) && !(err instanceof NullPointerException)) break block3;
                Bukkit.getLogger().info("[Arkkit] Forcing Arkreboot due to null pointer in chunk data!");
                AntiCrash.restartServer(60);
            }
        }
        return i;
    }

    private static int getTrackedLoadedChunks() {
        int tracked_chunks = 0;
        for (CopyOnWriteArrayList<Long> l : ChunkGCTask.loaded_chunks.values()) {
            tracked_chunks += l.size();
        }
        return tracked_chunks;
    }
}

