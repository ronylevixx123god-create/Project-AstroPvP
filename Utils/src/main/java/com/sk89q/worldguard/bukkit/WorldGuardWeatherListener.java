/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldGuardWeatherListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardWeatherListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onWeatherChange(WeatherChangeEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getWorld());
        if (event.toWeatherState()) {
            if (wcfg.disableWeather) {
                event.setCancelled(true);
            }
        } else if (!wcfg.disableWeather && wcfg.alwaysRaining) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onThunderChange(ThunderChangeEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getWorld());
        if (event.toThunderState()) {
            if (wcfg.disableThunder) {
                event.setCancelled(true);
            }
        } else if (!wcfg.disableWeather && wcfg.alwaysThundering) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onLightningStrike(LightningStrikeEvent event) {
        int targetId;
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getWorld());
        if (wcfg.disallowedLightningBlocks.size() > 0 && wcfg.disallowedLightningBlocks.contains(targetId = event.getLightning().getLocation().getBlock().getTypeId())) {
            event.setCancelled(true);
        }
        Location loc = event.getLightning().getLocation();
        if (wcfg.useRegions) {
            Vector pt = BukkitUtil.toVector(loc);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(loc.getWorld());
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            if (!set.allows(DefaultFlag.LIGHTNING)) {
                event.setCancelled(true);
            }
        }
    }
}

