/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.utils;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import java.lang.reflect.Method;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtil {
    private static WorldGuardPlugin wg = null;

    public static void hook() {
        wg = WorldGuardUtil.getWorldGuard();
    }

    public static String getRegionName(Location l) {
        if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            return "";
        }
        try {
            Class<?> bukkitUtil = wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            Vector blockVector = (Vector)toVector.invoke(null, l.getBlock());
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                return "";
            }
            String return_region = "";
            int return_priority = -1;
            for (String region : regionSet) {
                int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_region.equalsIgnoreCase("")) {
                    return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
                if (region_priority <= return_priority) continue;
                return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
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

    public static boolean isPvPDisabled(Location l) {
        try {
            Class<?> bukkitUtil = wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            Vector blockVector = (Vector)toVector.invoke(null, l.getBlock());
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                try {
                    return (StateFlag.State)((Object)wg.getGlobalRegionManager().get(l.getWorld()).getRegion("__global__").getFlags().get(DefaultFlag.PVP)) == StateFlag.State.DENY;
                }
                catch (Exception err) {
                    err.printStackTrace();
                    return true;
                }
            }
            boolean return_flag = false;
            int return_priority = -1;
            for (String region : regionSet) {
                if (!wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().containsKey(DefaultFlag.PVP)) continue;
                StateFlag.State pvp_flag = (StateFlag.State)((Object)wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().get(DefaultFlag.PVP));
                int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_priority == -1) {
                    return_flag = pvp_flag == StateFlag.State.DENY;
                    return_priority = region_priority;
                    continue;
                }
                if (region_priority <= return_priority) continue;
                return_flag = pvp_flag == StateFlag.State.DENY;
                return_priority = region_priority;
            }
            return return_flag;
        }
        catch (Exception exception) {
            return true;
        }
    }

    private static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = CosmicBossEggsPlugin.getPlugin().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }
}

