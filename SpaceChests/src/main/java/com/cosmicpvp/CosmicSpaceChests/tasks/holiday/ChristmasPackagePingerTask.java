package com.cosmicpvp.CosmicSpaceChests.tasks.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChristmasPackagePingerTask
implements Runnable {
    CosmicSpaceChestsPlugin plugin;

    public ChristmasPackagePingerTask(CosmicSpaceChestsPlugin fdp) {
        this.plugin = fdp;
    }

    @SneakyThrows
    @Override
    public void run() {
        for (Location loc : CosmicSpaceChestsPlugin.chest_locations.keySet()) {
            if (!ChunkAPI.isPlayerNearby((Location)loc, (int)0) || !loc.getWorld().getName().equals("world_arena") || loc.getBlock().getType() != Material.CHEST) continue;
            ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), loc.clone().add(0.5, 0.5, 0.5),0f,0f,0f, (float)0.075f, (int)40);
            ParticleEffect.MOB_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), loc.clone().add(0.5, 0.5, 0.5),0f,0f,0f, (float)0.15f, (int)40);
        }
    }
}

