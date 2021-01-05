/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 */
package com.cosmicpvp.CosmicSponges.listeners;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class ChunkListener
implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        CosmicSpongesAPI.cacheSpongeChunk(e.getChunk());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        String cs = e.getChunk().getX() + "," + e.getChunk().getZ();
        SpongeChunk sc = CosmicSpongesAPI.getCachedSpongeChunks().get(cs);
        if (sc.getSponges().size() > 0 || sc.isForceSave()) {
            sc.save();
        }
        CosmicSpongesAPI.getCachedSpongeChunks().remove(cs);
    }
}

