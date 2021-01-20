package me.ifamasssxd.cosmicbosses.utils;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
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

import java.lang.reflect.Method;
import java.util.List;

public class WorldGuardUtils {
    private static WorldGuardPlugin wg;

    public static void hook() {
        WorldGuardUtils.wg = getWorldGuard();
    }

    public static boolean inRegion(final Location l, final String rgName) {
        return getRegionName(l).equals(rgName);
    }

    public static boolean hasFlagDisabled(final Location l, final Flag<?> flag) {
        final RegionManager regionMan = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld());
        if (regionMan == null) {
            return true;
        }
        final ApplicableRegionSet regionManager = regionMan.getApplicableRegions(l);
        if (regionManager == null) {
            return true;
        }
        for (final ProtectedRegion region : regionManager) {
            if (region.getFlags().containsKey(flag) && region.getFlag((Flag) flag) == StateFlag.State.DENY) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInRegionsFast(final Location location, final String... regionNames) {
        return isInRegionsFast(location, Lists.newArrayList(regionNames));
    }

    public static boolean isInRegionsFast(final Location location, final List<String> regionNames) {
        final RegionManager regionMan = WorldGuardUtils.wg.getGlobalRegionManager().get(location.getWorld());
        if (regionMan == null) {
            return false;
        }
        final ApplicableRegionSet regionManager = regionMan.getApplicableRegions(location);
        if (regionManager == null) {
            return false;
        }
        for (final ProtectedRegion region : regionManager) {
            if (regionNames.contains(region.getId())) {
                return true;
            }
        }
        return false;
    }

    public static String getRegionName(final Location l) {
        try {
            final Class<?> bukkitUtil = WorldGuardUtils.wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            final Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            final Vector blockVector = (Vector) toVector.invoke(null, l.getBlock());
            final List<String> regionSet = (List<String>) WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 0) {
                return "";
            }
            String return_region = "";
            int return_priority = -1;
            for (final String region : regionSet) {
                final int region_priority = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_region.equalsIgnoreCase("")) {
                    return_region = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
                if (region_priority > return_priority) {
                    return_region = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
            }
            if (return_region.contains("global")) {
                return_region = "";
            }
            return return_region;
        } catch (Exception ex) {
            return "";
        }
    }

    public static boolean isPvPEnabled(final Location l) {
        final RegionManager regionMan = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld());
        if (regionMan == null) {
            System.out.println("Region Manager Null: true");
            return true;
        }
        final ApplicableRegionSet regionManager = regionMan.getApplicableRegions(l);
        if (regionManager == null) {
            return true;
        }
        ProtectedRegion highest = null;
        for (final ProtectedRegion region : regionManager) {
            if (highest == null || (highest != null && region.getPriority() > highest.getPriority())) {
                highest = region;
            }
        }
        return highest == null || !highest.getFlags().containsKey(DefaultFlag.PVP) || highest.getFlag((Flag) DefaultFlag.PVP) != StateFlag.State.DENY;
    }

    public static boolean isPvPDisabled(final Location l) {
        try {
            final Vector blockVector = (Vector) BukkitUtil.toVector(l.getBlock());
            final List<String> regionSet = (List<String>) WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                try {
                    final RegionManager rm = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld());
                    if (rm == null) {
                        return false;
                    }
                    if (rm.getRegion("__global__") == null) {
                        return false;
                    }
                    final ProtectedRegion region = rm.getRegion("__global__");
                    return region.getFlags().containsKey(DefaultFlag.PVP) && region.getFlags().get(DefaultFlag.PVP) == StateFlag.State.DENY;
                } catch (Exception err) {
                    err.printStackTrace();
                    return true;
                }
            }
            boolean return_flag = false;
            int return_priority = -1;
            for (final String region2 : regionSet) {
                if (WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getFlags().containsKey(DefaultFlag.PVP)) {
                    final StateFlag.State pvp_flag = (StateFlag.State) WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getFlags().get(DefaultFlag.PVP);
                    final int region_priority = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region2).getPriority();
                    if (return_priority == -1) {
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                    } else {
                        if (region_priority <= return_priority) {
                            continue;
                        }
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                    }
                }
            }
            return return_flag;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isLavaFlowDisabled(final Location l) {
        try {
            final Vector blockVector = (Vector) BukkitUtil.toVector(l.getBlock());
            final List<String> regionSet = (List<String>) WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                try {
                    return WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion("__global__").getFlags().get(DefaultFlag.LAVA_FLOW) == StateFlag.State.DENY;
                } catch (Exception err) {
                    if (!(err instanceof NullPointerException)) {
                        err.printStackTrace();
                    }
                    return false;
                }
            }
            boolean return_flag = false;
            int return_priority = -1;
            for (final String region : regionSet) {
                if (WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().containsKey(DefaultFlag.PVP)) {
                    final StateFlag.State pvp_flag = (StateFlag.State) WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getFlags().get(DefaultFlag.PVP);
                    final int region_priority = WorldGuardUtils.wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                    if (return_priority == -1) {
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                    } else {
                        if (region_priority <= return_priority) {
                            continue;
                        }
                        return_flag = (pvp_flag == StateFlag.State.DENY);
                        return_priority = region_priority;
                    }
                }
            }
            return return_flag;
        } catch (Exception ex) {
            return false;
        }
    }

    public static WorldGuardPlugin getWorldGuard() {
        final Plugin plugin = CosmicBosses.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }

    static {
        WorldGuardUtils.wg = null;
    }
}
