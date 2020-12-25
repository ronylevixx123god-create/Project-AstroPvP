/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.bukkit.BukkitUtil
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  com.sk89q.worldguard.protection.ApplicableRegionSet
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  com.sk89q.worldguard.protection.flags.Flag
 *  com.sk89q.worldguard.protection.flags.StateFlag$State
 *  com.sk89q.worldguard.protection.managers.RegionManager
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.utils;

import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtils {
    private static WorldGuardPlugin wg = null;

    public static void hook() {
        wg = WorldGuardUtils.getWorldGuard();
    }

    public static boolean inRegion(Location l, String rgName) {
        return WorldGuardUtils.getRegionName(l).equals(rgName);
    }

    public static boolean hasFlagDisabled(Location l, Flag<?> flag) {
        RegionManager regionMan = wg.getGlobalRegionManager().get(l.getWorld());
        if (regionMan == null) {
            return true;
        }
        ApplicableRegionSet regionManager = regionMan.getApplicableRegions(l);
        if (regionManager == null) {
            return true;
        }
        for (ProtectedRegion region : regionManager) {
            if (!region.getFlags().containsKey(flag) || region.getFlag(flag) != StateFlag.State.DENY) continue;
            return true;
        }
        return false;
    }

    public static boolean isInRegionsFast(Location location, String ... regionNames) {
        return WorldGuardUtils.isInRegionsFast(location, Lists.newArrayList(regionNames));
    }

    public static boolean isInRegionsFast(Location location, List<String> regionNames) {
        RegionManager regionMan = wg.getGlobalRegionManager().get(location.getWorld());
        if (regionMan == null) {
            return false;
        }
        ApplicableRegionSet regionManager = regionMan.getApplicableRegions(location);
        if (regionManager == null) {
            return false;
        }
        for (ProtectedRegion region : regionManager) {
            if (!regionNames.contains(region.getId())) continue;
            return true;
        }
        return false;
    }

    public static String getRegionName(Location l) {
        try {
            Class<?> bukkitUtil = wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            Vector blockVector = (Vector)toVector.invoke(null, new Object[]{l.getBlock()});
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 0) {
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

    public static boolean isPvPEnabled(Location l) {
        RegionManager regionMan = wg.getGlobalRegionManager().get(l.getWorld());
        if (regionMan == null) {
            System.out.println("Region Manager Null: true");
            return true;
        }
        ApplicableRegionSet regionManager = regionMan.getApplicableRegions(l);
        if (regionManager == null) {
            return true;
        }
        ProtectedRegion highest = null;
        for (ProtectedRegion region : regionManager) {
            if (highest != null && (highest == null || region.getPriority() <= highest.getPriority())) continue;
            highest = region;
        }
        return highest == null || !highest.getFlags().containsKey(DefaultFlag.PVP) || highest.getFlag(DefaultFlag.PVP) != StateFlag.State.DENY;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isPvPDisabled(Location l) {
        try {
            BlockVector blockVector = BukkitUtil.toVector(l.getBlock());
            List regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs((Vector)blockVector);
            if (regionSet.size() < 1) {
                try {
                    RegionManager rm = wg.getGlobalRegionManager().get(l.getWorld());
                    if (rm == null) {
                        return false;
                    }
                    if (rm.getRegion("__global__") == null) {
                        return false;
                    }
                    ProtectedRegion region = rm.getRegion("__global__");
                    if (!region.getFlags().containsKey((Object)DefaultFlag.PVP)) return false;
                    if ((StateFlag.State)region.getFlags().get((Object)DefaultFlag.PVP) != StateFlag.State.DENY) return false;
                    return true;
                }
                catch (Exception err) {
                    err.printStackTrace();
                    return true;
                }
            }
            boolean return_flag = false;
            int return_priority = -1;
            Iterator iterator = regionSet.iterator();
            while (iterator.hasNext()) {
                String region = (String)iterator.next();
                if (!wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().containsKey((Object)DefaultFlag.PVP)) continue;
                StateFlag.State pvp_flag = (StateFlag.State)wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().get((Object)DefaultFlag.PVP);
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
            return false;
        }
    }

    public static boolean isLavaFlowDisabled(Location l) {
        try {
            com.sk89q.worldedit.BlockVector blockVector = BukkitUtil.toVector((Block)l.getBlock());
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs((Vector)blockVector);
            if (regionSet.size() < 1) {
                try {
                    return (StateFlag.State)wg.getGlobalRegionManager().get(l.getWorld()).getRegion("__global__").getFlags().get((Object)DefaultFlag.LAVA_FLOW) == StateFlag.State.DENY;
                }
                catch (Exception err) {
                    if (!(err instanceof NullPointerException)) {
                        err.printStackTrace();
                    }
                    return false;
                }
            }
            boolean return_flag = false;
            int return_priority = -1;
            for (String region : regionSet) {
                if (!wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().containsKey((Object)DefaultFlag.PVP)) continue;
                StateFlag.State pvp_flag = (StateFlag.State)wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().get((Object)DefaultFlag.PVP);
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
            return false;
        }
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = CosmicBosses.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }
}

