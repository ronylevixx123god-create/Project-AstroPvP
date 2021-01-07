/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 */
package me.ifamasssxd.cosmicheroicenvoys.utils;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class MiscUtils {
    private static WorldGuardPlugin worldGuard;

    private static void initWorldGuard() {
        worldGuard = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    public static Entity getEntity(UUID uuid, World world) {
        for (Entity entity : world.getEntities()) {
            if (!entity.getUniqueId().equals(uuid)) continue;
            return entity;
        }
        return null;
    }

    public static String getRegionName(Location l) {
        if (worldGuard == null) {
            MiscUtils.initWorldGuard();
            if (worldGuard == null) {
                return "";
            }
        }
        try {
            Class<?> bukkitUtil = worldGuard.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            Vector blockVector = (Vector)toVector.invoke(null, new Object[]{l.getBlock()});
            List<String> regionSet = worldGuard.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                return "";
            }
            String return_region = "";
            int return_priority = -1;
            for (String region : regionSet) {
                int region_priority = worldGuard.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_region.equalsIgnoreCase("")) {
                    return_region = worldGuard.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
                if (region_priority <= return_priority) continue;
                return_region = worldGuard.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                return_priority = region_priority;
            }
            if (return_region.contains("global")) {
                return_region = "";
            }
            return return_region;
        }
        catch (Exception exception) {
            return "";
        }
    }
}

