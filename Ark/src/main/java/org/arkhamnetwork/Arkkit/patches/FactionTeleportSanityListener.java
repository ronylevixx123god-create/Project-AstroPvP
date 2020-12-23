/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.arkhamnetwork.Arkkit.utils.FactionUtil;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class FactionTeleportSanityListener
        implements Listener {
    FactionTeleportSanityListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.getName().equals("Vaquxine") || !p.isOp() && !p.hasPermission("arkkit.teleportsanity.ignore")) {
            Faction at = FactionUtils.getFactionAt(p.getLocation());
            if (at != null && ((Boolean) FactionUtils.callFactionMethod(at, "isNormal", null)).booleanValue()) {
                FactionTeleportSanity.logoutTimes.put(p.getName(), System.currentTimeMillis());
            }
        } else {
            FactionTeleportSanity.logoutTimes.remove(p.getName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (FactionTeleportSanity.logoutTimes.containsKey(e.getPlayer().getName())) {
            final Player p = e.getPlayer();
            if (p.isDead()) {
                p.spigot().respawn();
            } else {
                Faction at = FactionUtils.getFactionAt(p.getLocation());
                FPlayer fplayer = FactionUtils.getFPlayer(p);
                if (at != null && fplayer != null && FactionUtil.getRelation(at, fplayer).isAtMost(Relation.TRUCE)) {
                    long logoutTime = FactionTeleportSanity.logoutTimes.get(p.getName());
                    long diff = System.currentTimeMillis() - logoutTime;
                    if (diff < 900000L) {
                        if (p.getVehicle() != null) {
                            p.getVehicle().eject();
                        }
                        CombatLog.flagCombat(p);
                        Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                            @Override
                            public void run() {
                                p.sendMessage("");
                                p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "*** YOU HAVE BEEN COMBAT TAGGED! ***");
                                p.sendMessage(ChatColor.GRAY + "Because you logged out in TRUCE, NEUTRAL, or ENEMY territory.");
                                p.sendMessage("");
                            }
                        }, 21L);
                    } else {
                        if (p.getVehicle() != null) {
                            p.getVehicle().eject();
                        }
                        p.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                        Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                            @Override
                            public void run() {
                                p.sendMessage("");
                                p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "*** YOU HAVE BEEN TELEPORTED TO SPAWN! ***");
                                p.sendMessage(ChatColor.GRAY + "Because you logged out in TRUCE, NEUTRAL, or ENEMY territory for more than 15 minutes.");
                                p.sendMessage("");
                            }
                        }, 21L);
                    }
                }
                FactionTeleportSanity.logoutTimes.remove(e.getPlayer().getName());
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/party teleport accept")) {
            Relation r;
            Player p = e.getPlayer();
            if (p.isOp() || p.hasPermission("arkkit.teleportsanity.ignore")) {
                return;
            }
            FPlayer fplayer = FactionUtils.getFPlayer(p);
            Faction destinationFaction = FactionUtils.getFactionAt(p.getLocation());
            if (destinationFaction != null && FactionUtil.isNonPermFaction(destinationFaction) && !(r = FactionUtil.getRelation(destinationFaction, fplayer)).isAlly() && !r.isMember()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot teleport to " + p.getLocation().getBlockX() + "x, " + p.getLocation().getBlockY() + "y, " + p.getLocation().getBlockZ() + "z, that location is owned by " + FactionUtil.getTag(destinationFaction));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        String destination_region;
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && (destination_region = WorldGuardUtils.getRegionName(e.getTo())).startsWith("spaw") && FactionUtils.isWarZone(e.getFrom())) {
            e.getPlayer().sendMessage(ChatColor.RED + "You " + ChatColor.UNDERLINE + "cannot" + ChatColor.RED + " teleport directly from the warzone to the settlement.");
            e.setCancelled(true);
            return;
        }
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND || e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            Relation r;
            Player p = e.getPlayer();
            if (p.isOp() || p.hasPermission("arkkit.teleportsanity.ignore")) {
                return;
            }
            FPlayer fplayer = FactionUtils.getFPlayer(p);
            Faction destinationFaction = FactionUtils.getFactionAt(e.getTo());
            if (destinationFaction != null && FactionUtil.isNonPermFaction(destinationFaction) && !(r = FactionUtil.getRelation(destinationFaction, fplayer)).isAlly() && !r.isMember()) {
                e.setCancelled(true);
                String tag = FactionUtil.getTag(destinationFaction);
                String coordString = e.getTo().getBlockX() + "x, " + e.getTo().getBlockY() + "y, " + e.getTo().getBlockZ() + "z";
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "You cannot teleport to " + coordString + ", that location is owned by " + tag);
                Bukkit.getLogger().info("[FactionSanityCheck] Cancelling teleport for " + p.getName() + " due to trying to enter " + r.name() + " (" + destinationFaction.getTag() + ") at " + coordString);
            }
        }
    }
}

