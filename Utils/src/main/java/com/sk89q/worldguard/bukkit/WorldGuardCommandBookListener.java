/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.commandbook.InfoComponent;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldGuardCommandBookListener
implements Listener {
    private final WorldGuardPlugin plugin;

    public WorldGuardCommandBookListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerWhois(InfoComponent.PlayerWhoisEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player)event.getPlayer();
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            if (this.plugin.getGlobalStateManager().get((World)player.getWorld()).useRegions) {
                ApplicableRegionSet regions = this.plugin.getGlobalRegionManager().get(player.getWorld()).getApplicableRegions(player.getLocation());
                StringBuilder regionStr = new StringBuilder();
                boolean first = true;
                for (ProtectedRegion region : regions) {
                    if (!first) {
                        regionStr.append(", ");
                    }
                    if (region.isOwner(localPlayer)) {
                        regionStr.append("+");
                    } else if (region.isMemberOnly(localPlayer)) {
                        regionStr.append("-");
                    }
                    regionStr.append(region.getId());
                    first = false;
                }
                if (regions.size() > 0) {
                    event.addWhoisInformation("Current Regions", regionStr);
                }
                event.addWhoisInformation("Can build", regions.canBuild(localPlayer));
            }
        }
    }
}

