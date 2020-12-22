/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkAPI
 *  org.arkhamnetwork.enchantments.util.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.Material
 */
package com.cosmicpvp.CosmicSpaceChests.tasks.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkAPI;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;

public class ChristmasPackagePingerTask
implements Runnable {
    CosmicSpaceChestsPlugin plugin;

    public ChristmasPackagePingerTask(CosmicSpaceChestsPlugin fdp) {
        this.plugin = fdp;
    }

    @Override
    public void run() {
        for (Location loc : CosmicSpaceChestsPlugin.chest_locations.keySet()) {
            if (!ChunkAPI.isPlayerNearby((Location)loc, (int)0) || !loc.getWorld().getName().equals("world_arena") || loc.getBlock().getType() != Material.CHEST) continue;
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)loc.clone().add(0.5, 0.5, 0.5), (float)0.075f, (int)40);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.MOB_SPELL, (Location)loc.clone().add(0.5, 0.5, 0.5), (float)0.15f, (int)40);
        }
    }
}

