/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  com.sk89q.worldguard.protection.flags.StateFlag$State
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockFromToEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

final class NoLavaFlowRegionListener
implements Listener {
    private HashMap<String, List<ProtectedRegion>> noLavaFlowRegionWorlds = new HashMap();
    private List<ProtectedRegion> noLavaFlowRegions = new ArrayList<ProtectedRegion>();

    NoLavaFlowRegionListener() {
        try {
            for (World w : Bukkit.getWorlds()) {
                ArrayList<ProtectedRegion> localNoLavaFlowRegions = new ArrayList<ProtectedRegion>();
                for (ProtectedRegion pr : WorldGuardUtils.getWorldGuard().getGlobalRegionManager().get(w).getRegions().values()) {
                    StateFlag.State lava_flag;
                    if (!pr.getFlags().containsKey((Object)DefaultFlag.LAVA_FLOW) || (lava_flag = (StateFlag.State)pr.getFlags().get((Object)DefaultFlag.LAVA_FLOW)) != StateFlag.State.DENY) continue;
                    this.noLavaFlowRegions.add(pr);
                    localNoLavaFlowRegions.add(pr);
                    Bukkit.getLogger().info("[Arkkit (NoLavaFlowRegion) ProtectedRegion " + pr.getId() + " lava_flow: disabled in world: " + w.getName());
                }
                this.noLavaFlowRegionWorlds.put(w.getName(), localNoLavaFlowRegions);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("[Arkkit (NoLavaFlowRegion)] Loaded " + this.noLavaFlowRegions.size() + " regions with lava_flow disabled in " + this.noLavaFlowRegionWorlds.size() + " worlds.");
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onBlockFromTo(BlockFromToEvent e) {
        if ((e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA || e.getBlock().getType() == Material.WATER || e.getBlock().getType() == Material.STATIONARY_WATER) && this.isLavaFlowDisabledCached(e.getToBlock().getLocation())) {
            e.setCancelled(true);
            return;
        }
    }

    private boolean isLavaFlowDisabledCached(Location l) {
        String worldName = l.getWorld().getName();
        if (this.noLavaFlowRegionWorlds.containsKey(worldName)) {
            for (ProtectedRegion pr : this.noLavaFlowRegionWorlds.get(worldName)) {
                if (!pr.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ())) continue;
                return true;
            }
        }
        return false;
    }
}

