/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkLoadEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.arkhamnetwork.Arkkit.patches.SpawnerFee;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

final class NerfNetherSpawnersListener
implements Listener {
    List<String> scannedChunks = new ArrayList<String>();

    NerfNetherSpawnersListener() {
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (e.getWorld().getName().equals("world_nether")) {
            String chunkName = e.getChunk().getX() + "," + e.getChunk().getZ();
            if (this.scannedChunks.contains(chunkName)) {
                return;
            }
            this.scannedChunks.add(chunkName);
            for (BlockState bs : e.getChunk().getTileEntities()) {
                if (!(bs instanceof CreatureSpawner) || SpawnerFee.isPlayerSpawner(bs.getBlock()) || !new Random().nextBoolean()) continue;
                Bukkit.getLogger().info("[Arkkit (NerfNetherSpawners)] Removing nether spawner at " + (Object)bs.getBlock().getLocation() + "!");
                bs.setType(Material.AIR);
            }
        }
    }
}

