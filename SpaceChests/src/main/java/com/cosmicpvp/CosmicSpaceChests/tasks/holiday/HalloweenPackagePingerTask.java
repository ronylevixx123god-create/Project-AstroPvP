package com.cosmicpvp.CosmicSpaceChests.tasks.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class HalloweenPackagePingerTask
implements Runnable {
    CosmicSpaceChestsPlugin plugin;

    public HalloweenPackagePingerTask(CosmicSpaceChestsPlugin fdp) {
        this.plugin = fdp;
    }

    @SneakyThrows
    @Override
    public void run() {
        for (Location loc : CosmicSpaceChestsPlugin.chest_locations.keySet()) {
            if (!ChunkAPI.isPlayerNearby(loc, (int)0) || !loc.getWorld().getName().equals("world_arena") || loc.getBlock().getType() != Material.PUMPKIN && loc.getBlock().getType() != Material.JACK_O_LANTERN) continue;
            de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), loc.clone().add(0.0, 1.0, 0.0),0f,0f,0f, (float)0.15f, (int)25);

        }
    }
}

