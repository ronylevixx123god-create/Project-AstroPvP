package com.compilex.CosmicDungeons.utils;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardUtils {
    private static WorldGuardPlugin wg = null;
    private static volatile Map<UUID, LinkedList<String>> lastKnownPlayerRegions = new MapMaker().concurrencyLevel(32).makeMap();
    private static volatile Map<String, List<ProtectedRegion>> cachedWorldRegions = new MapMaker().concurrencyLevel(32).makeMap();

    public static void hook() {
        wg = WorldGuardUtils.getWorldGuard();
        if (wg == null) {
            Bukkit.getLogger().info("Unable to hook into WorldGuard!");
        }
        Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)CosmicUtils.get(), WorldGuardUtils::updateAllPlayerRegions, 40L, 10L);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicUtils.get(), WorldGuardUtils::cacheAllRegions, 20L);
    }

    public static void cacheAllRegions() {
        cachedWorldRegions.clear();
        for (World world : Bukkit.getWorlds()) {
            ArrayList cachedRegions;
            RegionManager manager = wg.getGlobalRegionManager().get(world);
            if (manager == null || (cachedRegions = Lists.newArrayList(manager.getRegions().values())).isEmpty()) continue;
            cachedWorldRegions.put(world.getName(), cachedRegions);
        }
    }

    public static LinkedList<String> getRegionsIn(Player player) {
        return lastKnownPlayerRegions.get(player.getUniqueId());
    }

    public static String getRegionName(Player player) {
        LinkedList<String> found = WorldGuardUtils.getRegionsIn(player);
        return found != null && !found.isEmpty() ? (String)found.get(0) : null;
    }

    public static LinkedList<String> clearCachedRegions(Player player) {
        return lastKnownPlayerRegions.remove(player.getUniqueId());
    }

    public static void updateAllPlayerRegions() {
        CosmicUtils.get().getAsyncPlayerMap().values().forEach(player -> {
            List<ProtectedRegion> regionsList = cachedWorldRegions.get(player.getWorld().getName());
            Map<String, Integer> regionsInside = null;
            Location loc = player.getLocation();
            if (regionsList != null) {
                Iterator<ProtectedRegion> var4 = regionsList.iterator();
                while (var4.hasNext()) {
                    ProtectedRegion region = var4.next();
                    if (region.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                        if (regionsInside == null)
                            regionsInside = new HashMap<>();
                        regionsInside.put(region.getId(), region.getPriority());
                    }
                }
            }
            LinkedList<Map.Entry<String, Integer>> retr = (regionsInside != null) ? new LinkedList<>(regionsInside.entrySet()) : null;
            if (retr != null)
                retr.sort(Map.Entry.comparingByValue());
            LinkedList<String> sorted = (retr != null) ? new LinkedList<>() : null;
            if (sorted != null && sorted != null) {
                retr.forEach(ent -> sorted.add(ent.getKey()));
                if (!sorted.isEmpty()) {
                    lastKnownPlayerRegions.put(player.getUniqueId(), sorted);
                    return;
                }
            }
            lastKnownPlayerRegions.remove(player.getUniqueId());
        });
    }

    public static List<ProtectedRegion> getRegionsStandingInside(Location location, List<ProtectedRegion> regions) {
        return regions == null ? null : regions.stream().filter(rg -> rg.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())).collect(Collectors.toList());
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

    public static Set<String> getRegionsIn(Location l) {
        HashSet<String> retr = new HashSet<String>();
        RegionManager regionMan = wg.getGlobalRegionManager().get(l.getWorld());
        if (regionMan == null) {
            return retr;
        }
        ApplicableRegionSet regionManager = regionMan.getApplicableRegions(l);
        if (regionManager == null) {
            return retr;
        }
        for (ProtectedRegion region : regionManager) {
            retr.add(region.getId());
        }
        return retr;
    }

    public static boolean isInRegionsFast(Location location, String ... regionNames) {
        return WorldGuardUtils.isInRegionsFast(location, Lists.newArrayList(regionNames));
    }

    public static boolean isInRegionsContainsFast(Location location, List<String> regionNames) {
        RegionManager regionMan = wg.getGlobalRegionManager().get(location.getWorld());
        if (regionMan == null) {
            return false;
        }
        ApplicableRegionSet regionManager = regionMan.getApplicableRegions(location);
        if (regionManager == null) {
            return false;
        }
        for (ProtectedRegion region : regionManager) {
            for (String str : regionNames) {
                if (!region.getId().contains(str)) continue;
                return true;
            }
        }
        return false;
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
        RegionManager manager = wg.getRegionManager(l.getWorld());
        ApplicableRegionSet set = manager.getApplicableRegions(l);
        String highest = "";
        int highestP = -1;
        for (ProtectedRegion rg : set) {
            if (rg.getPriority() != -1 && rg.getPriority() <= highestP) continue;
            highest = rg.getId();
            highestP = rg.getPriority();
        }
        return highest;
    }

    @Deprecated
    public static String getRegionNameOld(Location l) {
        try {
            Class<?> bukkitUtil = wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", new Class[] { Block.class });
            Vector blockVector = (Vector)toVector.invoke(null, new Object[] { l.getBlock() });
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 0)
                return "";
            String return_region = "";
            int return_priority = -1;
            for (String region : regionSet) {
                int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_region.equalsIgnoreCase("")) {
                    return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
                if (region_priority > return_priority) {
                    return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
            }
            if (return_region.contains("global"))
                return_region = "";
            return return_region;
        } catch (Exception var8) {
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
        return highest == null || !highest.getFlags().containsKey((Object)DefaultFlag.PVP) || highest.getFlag((Flag)DefaultFlag.PVP) != StateFlag.State.DENY;
    }

    @Deprecated
    public static boolean isPvPDisabled(Location l) {
        try {
            BlockVector blockVector = BukkitUtil.toVector(l.getBlock());
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs((Vector)blockVector);
            if (regionSet.size() < 1)
                try {
                    RegionManager rm = wg.getGlobalRegionManager().get(l.getWorld());
                    if (rm == null)
                        return false;
                    if (rm.getRegion("__global__") == null)
                        return false;
                    ProtectedRegion region = rm.getRegion("__global__");
                    return (region.getFlags().containsKey(DefaultFlag.PVP) && region.getFlags().get(DefaultFlag.PVP) == StateFlag.State.DENY);
                } catch (Exception var9) {
                    var9.printStackTrace();
                    return true;
                }
            boolean return_flag = false;
            int return_priority = -1;
            for (String region2 : regionSet) {
                if (wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getFlags().containsKey(DefaultFlag.PVP)) {
                    StateFlag.State pvp_flag = (StateFlag.State)wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getFlags().get(DefaultFlag.PVP);
                    int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getPriority();
                    if (return_priority == -1) {
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                        continue;
                    }
                    if (region_priority <= return_priority)
                        continue;
                    return_flag = (pvp_flag == StateFlag.State.DENY);
                    return_priority = region_priority;
                }
            }
            return return_flag;
        } catch (Exception var11) {
            return false;
        }
    }

    public static boolean isLavaFlowDisabled(Location l) {
        try {
            BlockVector blockVector = BukkitUtil.toVector(l.getBlock());
            List<String> regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs((Vector)blockVector);
            if (regionSet.size() < 1)
                try {
                    return (wg.getGlobalRegionManager().get(l.getWorld()).getRegion("__global__").getFlags().get(DefaultFlag.LAVA_FLOW) == StateFlag.State.DENY);
                } catch (Exception var9) {
                    if (!(var9 instanceof NullPointerException))
                        var9.printStackTrace();
                    return false;
                }
            boolean return_flag = false;
            int return_priority = -1;
            for (String region : regionSet) {
                if (wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().containsKey(DefaultFlag.PVP)) {
                    StateFlag.State pvp_flag = (StateFlag.State)wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().get(DefaultFlag.PVP);
                    int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                    if (return_priority == -1) {
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                        continue;
                    }
                    if (region_priority <= return_priority)
                        continue;
                    return_flag = (pvp_flag == StateFlag.State.DENY);
                    return_priority = region_priority;
                }
            }
            return return_flag;
        } catch (Exception var11) {
            return false;
        }
    }

    private static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
        return plugin != null && plugin instanceof WorldGuardPlugin ? (WorldGuardPlugin)plugin : null;
    }
}

