/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.event.LandClaimEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.LandClaimEvent;
import java.util.HashSet;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.AdminEvents;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class AdminEventsListener
implements Listener {
    protected static HashSet<String> eventWorldHomes;
    protected static boolean canClaimInEventWorld;

    public AdminEventsListener() {
        eventWorldHomes = new HashSet();
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (AdminEvents.isAdminEventRunning && e.getPlayer().isOnline()) {
            final Player p = e.getPlayer();
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    if (p != null && p.isOnline()) {
                        p.sendMessage("");
                        p.sendMessage((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "** WORLD EVENT: " + AdminEvents.eventName + (Object)ChatColor.DARK_RED + (Object)ChatColor.BOLD + " **");
                        p.sendMessage((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "OBJECTIVE: " + (Object)ChatColor.RED + (AdminEvents.eventURL != null ? AdminEvents.eventURL : "???"));
                        p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.DARK_RED + "/worldevent help" + (Object)ChatColor.GRAY + " for more information!");
                        p.sendMessage("");
                    }
                }
            }, 60L);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!AdminEvents.isAdminEventRunning) {
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == to.getBlockZ()) {
            return;
        }
        String regionName = WorldGuardUtils.getRegionName(to);
        if (regionName.contains("world_event")) {
            e.getPlayer().teleport(Bukkit.getWorld((String)"world_event").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            ParticleEffect.playEffect(ParticleEffect.RED_DUST, e.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0.8f, 25);
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (!AdminEvents.isAdminEventRunning && !e.getPlayer().isOp() && e.getPlayer().getWorld().getName().equals("world_event")) {
            e.getPlayer().teleport(Bukkit.getWorld((String)"world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player p;
        String cmd = e.getMessage().toLowerCase();
        if ((cmd.startsWith("/sethome") || cmd.startsWith("/esethome") || cmd.startsWith("/createhome") || cmd.startsWith("/ecreatehome")) && (p = e.getPlayer()).getWorld().getName().equals("world_event")) {
            String homeName = cmd.contains(" ") ? cmd.split(" ")[1] : "home";
            eventWorldHomes.add(p.getName() + ":" + homeName);
            Bukkit.getLogger().info("[AdminEvents] Logged /sethome events: " + p.getName() + ":" + homeName);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onFactionClaim(LandClaimEvent event) {
        String worldName;
        if (!AdminEvents.isAdminEventRunning) {
            return;
        }
        if (!canClaimInEventWorld && event.getPlayer() != null && !event.getPlayer().isOp() && (worldName = event.getLocation().getWorldName()).equals("world_event")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.RedB + "(!)" + CC.Red + " You currently cannot claim land in this event!");
        }
        if (AdminEvents.maxClaims != -1 && event.getPlayer() != null && !event.getPlayer().isOp() && (worldName = event.getLocation().getWorldName()).equals("world_event")) {
            Faction f = ((FPlayer)FPlayers.i.get((OfflinePlayer)event.getPlayer())).getFaction();
            int claims = Board.getFactionCoordCountInWorld((Faction)f, (String)"world_event");
            if (claims++ > AdminEvents.maxClaims) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + " Your faction already has the max claims (" + AdminEvents.maxClaims + ") for this event.");
            }
        }
    }

    static {
        canClaimInEventWorld = false;
    }
}

