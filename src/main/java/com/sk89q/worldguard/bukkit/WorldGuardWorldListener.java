/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldGuardWorldListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardWorldListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        if (cfg.activityHaltToggle) {
            int removed = 0;
            for (Entity entity : event.getChunk().getEntities()) {
                if (!BukkitUtil.isIntensiveEntity(entity)) continue;
                entity.remove();
                ++removed;
            }
            if (removed > 50) {
                this.plugin.getLogger().info("Halt-Act: " + removed + " entities (>50) auto-removed from " + event.getChunk().toString());
            }
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        this.initWorld(event.getWorld());
    }

    public void initWorld(World world) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.alwaysRaining && !wcfg.disableWeather) {
            world.setStorm(true);
        } else if (wcfg.disableWeather && !wcfg.alwaysRaining) {
            world.setStorm(false);
        }
        if (wcfg.alwaysThundering && !wcfg.disableThunder) {
            world.setThundering(true);
        } else if (wcfg.disableThunder && !wcfg.alwaysThundering) {
            world.setStorm(false);
        }
    }
}

