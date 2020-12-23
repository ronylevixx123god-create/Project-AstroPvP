/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.FactionWrapper
 *  com.massivecraft.factions.FactionWrappers
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.World$Environment
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.*;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class AntiBlockGlitchListener
        implements Listener {
    AntiBlockGlitchListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onBlockPlaceFail(BlockPlaceEvent e) {
        if (e.isCancelled() || !e.canBuild()) {
            final Player p = e.getPlayer();
            final Location l = p.getLocation();
            if (CombatLog.inCombat(p)) {
                return;
            }
            Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                @Override
                public void run() {
                    p.teleport(l, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                }
            }, 2L);
        }
    }

    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e) {
        if (e.getBlock().getWorld().getEnvironment() == World.Environment.NETHER && e.getBlock().getY() >= 120 && e.getDirection() == BlockFace.UP) {
            for (Block b : e.getBlocks()) {
                if (!(b.getLocation().getY() >= 124.0)) continue;
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerBlockGlitchWaterLava(PlayerInteractEvent e) {
        if (e.hasBlock() && e.hasItem() && e.getItem().getType() == Material.BUCKET && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Material m1 = e.getClickedBlock().getType();
            Material m2 = e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).getBlock().getType();
            Material m3 = e.getClickedBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType();
            if (m1 == Material.LAVA || m1 == Material.STATIONARY_LAVA || m1 == Material.WATER || m1 == Material.STATIONARY_WATER || m2 == Material.LAVA || m2 == Material.STATIONARY_LAVA || m2 == Material.WATER || m2 == Material.STATIONARY_WATER || m3 == Material.LAVA || m3 == Material.STATIONARY_LAVA || m3 == Material.WATER || m3 == Material.STATIONARY_WATER) {
                FPlayer fp = FPlayers.i.get(e.getPlayer());
                FLocation fl = new FLocation(e.getClickedBlock().getLocation());
                FactionWrapper wrapper = FactionWrappers.get(Board.getFactionAt(fl).getId());
                if (!(fp.isInOwnTerritory() || wrapper.doesPlayerHaveChunkAccess(fl, fp, true) || e.getPlayer().isOp())) {
                    e.setCancelled(true);
                    e.setUseInteractedBlock(Event.Result.DENY);
                    e.setUseItemInHand(Event.Result.DENY);
                    Bukkit.getLogger().info("[AntiBlockGlitch] Cancelling PlayerInteractEvent on liquid in enemy territory for " + e.getPlayer().getName() + " at " + e.getClickedBlock().getLocation());
                    return;
                }
            }
        }
    }
}

