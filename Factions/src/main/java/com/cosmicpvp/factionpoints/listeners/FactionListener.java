/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.MapMaker
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.event.FPlayerLeaveEvent
 *  com.massivecraft.factions.event.FactionCreateEvent
 *  com.massivecraft.factions.event.FactionDisbandEvent
 *  com.massivecraft.factions.event.LandClaimEvent
 *  com.massivecraft.factions.event.LandUnclaimAllEvent
 *  com.massivecraft.factions.event.LandUnclaimEvent
 *  com.wimbli.WorldBorder.BorderData
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package com.cosmicpvp.factionpoints.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;
import com.google.common.cache.CacheBuilder;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.event.*;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class FactionListener
implements Listener {
    //private Map<UUID, Long> expiringMsgs = new MapMaker().expireAfterWrite(30L, TimeUnit.SECONDS).makeMap();
    private Map<UUID, Long> expiringMsgs = (Map<UUID, Long>) CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.SECONDS).build();

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        FactionsPointsAPI.removePoints(event.getFactionId());
    }

    @EventHandler
    public void onFactionDisband(FactionDisbandEvent event) {
        FactionsPointsAPI.removePoints(event.getFaction());
        FactionsCoreChunkAPI.removeCoreChunkOnDisband(event.getFaction());
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerLeave(FPlayerLeaveEvent event) {
        if (event.getFaction().getFPlayers().size() <= 1) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(P.getP(), () -> {
                if (event.getFaction().getFPlayers().size() <= 0 && !event.getFaction().attached()) {
                    FactionsPointsAPI.removePoints(event.getFaction());
                    FactionsCoreChunkAPI.removeCoreChunkOnDisband(event.getFaction());
                }
            }, 1L);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onLandClaimMonitor(LandClaimEvent event) {
        if (this.isOutsideWorldBorder(event.getLocation()) && event.getPlayer() != null && !event.getPlayer().isOp()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot claim land outside of the WorldBorder!");
        }
    }

    private boolean isOutsideWorldBorder(FLocation location) {
        WorldBorder border = (WorldBorder)Bukkit.getPluginManager().getPlugin("WorldBorder");
        if (border == null) {
            return false;
        }
        BorderData data2 = border.getWorldBorder(location.getWorldName());
        if (data2 == null) {
            return false;
        }
        return !data2.insideBorder((double)(location.getX() << 4), (double)(location.getZ() << 4)) || !data2.insideBorder((double)((location.getX() << 4) + 15L), (double)((location.getZ() << 4) + 15L));
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onLandClaim(LandClaimEvent event) {
        if (Board.getFactionCoordCount((Faction)event.getFaction()) <= 0) {
            if (P.getP().getChunkManager().isWarzoneNearby(event.getLocation())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot claim your Core Chunk within 3 chunks of the WarZone!");
                return;
            }
            if (!event.getLocation().getWorldName().equals(((World)Bukkit.getWorlds().get(0)).getName())) {
                if (!event.getPlayer().isOp()) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must have your Core Chunk claimed in the Overworld!");
                    return;
                }
                event.getPlayer().sendMessage(CC.Red + "Allowing you to claim core chunk in foreign world...");
            }
            FactionsCoreChunkAPI.setCoreChunk(event.getFaction(), event.getLocation());
        }
        FactionsCoreChunkAPI.storeLandClaimForFaction(event.getFaction(), event.getLocation());
        CoreChunkData data2 = FactionsCoreChunkAPI.getChunkData(event.getFaction());
        if (data2 != null && data2.getMasterChunk() != null) {
            if (!this.expiringMsgs.containsKey(event.getPlayer().getUniqueId()) && Board.getFactionAt((FLocation)new FLocation(event.getPlayer().getLocation())) != event.getFaction()) {
                this.expiringMsgs.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30L));
                Bukkit.getScheduler().scheduleSyncDelayedTask(P.getP(), () -> event.getPlayer().sendMessage(CC.Gray + "Any faction claims that are not connected to your Core Chunk will expire after 24 hours!"), 2L);
            }
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onLandUnclaim(LandUnclaimEvent event) {
        FLocation factionCore = FactionsCoreChunkAPI.getCoreChunk(event.getFaction());
        if (factionCore == null) {
            return;
        }
        Player player = event.getPlayer();
        if (!factionCore.equals((Object)event.getLocation())) {
            ExpiringChunkData chunkData = P.getP().getChunkManager().getChunksToExpire().get((Object)event.getLocation());
            if (chunkData != null && System.currentTimeMillis() - chunkData.getTimeClaimed() >= TimeUnit.HOURS.toMillis(24L) && chunkData.getExpirationTime() == 0L && !event.getFPlayer().getRole().isCoLeader()) {
                event.setCancelled(true);
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be Co-Leader+ to unclaim land connected to your core chunk!");
            }
            return;
        }
        int claims = Board.getFactionCoordCount((Faction)event.getFaction());
        if (claims > 1) {
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must use /f unclaimall to unclaim your Faction Core Chunk!");
            event.setCancelled(true);
            return;
        }
        FactionsCoreChunkAPI.removeCoreChunk(event.getFaction());
        P.getP().getChunkManager().removePointsFromUnclaimAll(event.getFaction());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onUnclaim(LandUnclaimEvent event) {
        P.getP().getChunkManager().onFactionUnclaimChunk(event.getFaction(), event.getLocation());
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onLandUnclaimAll(LandUnclaimAllEvent event) {
        FLocation removed = FactionsCoreChunkAPI.removeCoreChunk(event.getFaction());
        if (removed != null) {
            P.getP().log("Removing faction core chunk for " + event.getFaction().getTag() + ": " + (Object)removed);
            P.getP().getChunkManager().removePointsFromUnclaimAll(event.getFaction());
        }
    }
}

