/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class WorldGuardVehicleListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardVehicleListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        Vehicle vehicle = event.getVehicle();
        Entity destroyer = event.getAttacker();
        if (!(destroyer instanceof Player)) {
            return;
        }
        Player player = (Player)destroyer;
        World world = vehicle.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            Vector pt = BukkitUtil.toVector(vehicle.getLocation());
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            if (!(this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.DESTROY_VEHICLE, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to destroy vehicles here.");
                event.setCancelled(true);
                return;
            }
        }
    }
}

