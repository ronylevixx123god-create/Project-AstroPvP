/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.plugin.Plugin
 *  org.spigotmc.SpigotWorldConfig
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.plugin.Plugin;
import org.spigotmc.SpigotWorldConfig;

import java.util.HashMap;

public class WorldTickOptimizer
        extends ArkPlugin {
    private static final HashMap<World, SpigotWorldConfig> defaults = new HashMap();

    public WorldTickOptimizer(Plugin p) {
        super(p);
    }

    protected static void optimizeWorld(World w) {
        if (!defaults.containsKey(w)) {
            Bukkit.getLogger().info("Stored default spigotConfig for world:" + w.getName());
            defaults.put(w, ((CraftWorld) w).getHandle().spigotConfig);
        }
        Bukkit.getLogger().info(w.getName() + "'s unloadQueue size = " + ((CraftWorld) w).getHandle().chunkProviderServer.unloadQueue.size());
        ((CraftWorld) w).getHandle().spigotConfig.viewDistance = 3;
        ((CraftWorld) w).getHandle().spigotConfig.clearChunksOnTick = true;
        ((CraftWorld) w).getHandle().spigotConfig.hopperCheck = 32;
        ((CraftWorld) w).getHandle().spigotConfig.hopperTransfer = 32;
        ((CraftWorld) w).getHandle().spigotConfig.hopperAmount = 4;
        if (((CraftWorld) w).getHandle().spigotConfig.animalActivationRange > 32) {
            ((CraftWorld) w).getHandle().spigotConfig.animalActivationRange = 32;
        }
        if (((CraftWorld) w).getHandle().spigotConfig.monsterActivationRange > 32) {
            ((CraftWorld) w).getHandle().spigotConfig.monsterActivationRange = 32;
        }
        if (((CraftWorld) w).getHandle().spigotConfig.animalTrackingRange > 32) {
            ((CraftWorld) w).getHandle().spigotConfig.animalTrackingRange = 32;
        }
        if (((CraftWorld) w).getHandle().spigotConfig.monsterTrackingRange > 32) {
            ((CraftWorld) w).getHandle().spigotConfig.monsterTrackingRange = 32;
        }
        if (((CraftWorld) w).getHandle().spigotConfig.playerTrackingRange > 32) {
            ((CraftWorld) w).getHandle().spigotConfig.playerTrackingRange = 32;
        }
        if (((CraftWorld) w).getHandle().spigotConfig.chunksPerTick != 0) {
            ((CraftWorld) w).getHandle().spigotConfig.chunksPerTick = 24;
            ((CraftWorld) w).getHandle().spigotConfig.saplingModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.cactusModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.caneModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.melonModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.mushroomModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.pumpkinModifier = 500000;
            ((CraftWorld) w).getHandle().spigotConfig.wheatModifier = 500000;
        }
    }

    @Override
    public void enable() {
        for (World w : Bukkit.getWorlds()) {
            WorldTickOptimizer.optimizeWorld(w);
        }
        this.registerListener(new TickOptimizerListener());
    }

    @Override
    public void disable() {
        for (World w : Bukkit.getWorlds()) {
            this.restoreWorld(w);
        }
        this.unregisterListeners();
    }

    protected void restoreWorld(World w) {
        if (!defaults.containsKey(w)) {
            Bukkit.getLogger().info("No default spigotConfig found for world:" + w.getName() + " .. cannot restore settings.");
            return;
        }
        SpigotWorldConfig default_config = defaults.get(w);
        ((CraftWorld) w).getHandle().spigotConfig.viewDistance = default_config.viewDistance;
        ((CraftWorld) w).getHandle().spigotConfig.clearChunksOnTick = default_config.clearChunksOnTick;
        ((CraftWorld) w).getHandle().spigotConfig.hopperCheck = default_config.hopperCheck;
        ((CraftWorld) w).getHandle().spigotConfig.hopperTransfer = default_config.hopperTransfer;
        ((CraftWorld) w).getHandle().spigotConfig.hopperAmount = default_config.hopperAmount;
        ((CraftWorld) w).getHandle().spigotConfig.animalActivationRange = default_config.animalActivationRange;
        ((CraftWorld) w).getHandle().spigotConfig.monsterActivationRange = default_config.monsterActivationRange;
        ((CraftWorld) w).getHandle().spigotConfig.animalTrackingRange = default_config.animalTrackingRange;
        ((CraftWorld) w).getHandle().spigotConfig.monsterTrackingRange = default_config.monsterTrackingRange;
        ((CraftWorld) w).getHandle().spigotConfig.playerTrackingRange = default_config.playerTrackingRange;
        ((CraftWorld) w).getHandle().spigotConfig.chunksPerTick = default_config.chunksPerTick;
        ((CraftWorld) w).getHandle().spigotConfig.saplingModifier = default_config.saplingModifier;
        ((CraftWorld) w).getHandle().spigotConfig.cactusModifier = default_config.cactusModifier;
        ((CraftWorld) w).getHandle().spigotConfig.caneModifier = default_config.caneModifier;
        ((CraftWorld) w).getHandle().spigotConfig.melonModifier = default_config.melonModifier;
        ((CraftWorld) w).getHandle().spigotConfig.mushroomModifier = default_config.mushroomModifier;
        ((CraftWorld) w).getHandle().spigotConfig.pumpkinModifier = default_config.pumpkinModifier;
        ((CraftWorld) w).getHandle().spigotConfig.wheatModifier = default_config.wheatModifier;
    }
}

