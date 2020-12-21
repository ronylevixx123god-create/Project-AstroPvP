/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.wimbli.WorldBorder.WorldBorder;
import java.text.DecimalFormat;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.TPWarmup;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class TPWarmupListener
implements Listener {
    private DecimalFormat formatter = new DecimalFormat("#.000");

    TPWarmupListener() {
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN || e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            if (e.getFrom().getWorld().getName().equals("world_duels") || e.getTo().getWorld().getName().equals("world_duels")) {
                return;
            }
            if (e.getFrom().getWorld().getName().equals("world_duels2") || e.getTo().getWorld().getName().equals("world_duels2")) {
                return;
            }
            if (WorldBorder.plugin.getWorldBorder(e.getFrom().getWorld().getName()) != null && !WorldBorder.plugin.getWorldBorder(e.getFrom().getWorld().getName()).insideBorder(e.getPlayer().getLocation())) {
                return;
            }
            try {
                if (WorldGuardUtils.isPvPDisabled(e.getFrom())) {
                    if (WorldGuardUtils.isPvPDisabled(e.getTo())) {
                        return;
                    }
                    if (!this.hasRespawnedRecently(e.getPlayer()) && !e.getPlayer().hasMetadata("bannerTp")) {
                        return;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            double teleportWarmup = e.getPlayer().hasMetadata("bannerTp") ? 5.0 : TPWarmup.getTeleportWarmupDelay(e.getPlayer());
            Player p = e.getPlayer();
            if (p.hasMetadata("queuedTeleportPass") && ((Location)((MetadataValue)p.getMetadata("queuedTeleportPass").get(0)).value()).equals((Object)e.getTo())) {
                p.removeMetadata("queuedTeleportPass", (Plugin)Arkkit.get());
                return;
            }
            if (p.hasPermission("arkkit.xpbottles.immune") || p.getGameMode() == GameMode.CREATIVE) {
                if (p.getName().equals("Woofless") || p.getName().equals("TBNRfrags")) {
                    if (p.getGameMode() == GameMode.CREATIVE) {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (p.hasMetadata("queuedTeleportTaskID")) {
                TPWarmup.cancelQueuedTeleport(p);
            }
            TPWarmup.queuedTeleport(p, e.getTo(), e.getCause());
            p.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You will be teleported in " + (Object)ChatColor.UNDERLINE + this.formatter.format(teleportWarmup) + "s" + (Object)ChatColor.YELLOW + "... DON'T MOVE!");
            if (!p.hasMetadata("bannerTp")) {
                p.sendMessage((Object)ChatColor.GRAY + "Decrease this wait time by holding more vanilla XP.");
            }
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getEntity().hasMetadata("queuedTeleport") && e.getDamage() > 0.0) {
            if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                return;
            }
            TPWarmup.cancelQueuedTeleport((Player)e.getEntity());
            ((Player)e.getEntity()).sendMessage((Object)ChatColor.DARK_RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.DARK_RED + "Pending teleport request cancelled due to combat.");
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.getPlayer().setMetadata("lastRespawnEvent", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        if (e.getPlayer().hasPermission("essentials.tpo") && msg.toLowerCase().startsWith("/tpo ") && msg.split(" ").length == 3) {
            String targetPlayer = msg.split(" ")[1];
            String destPlayer = msg.split(" ")[2];
            if (Bukkit.getPlayer((String)targetPlayer) != null && Bukkit.getPlayer((String)destPlayer) != null) {
                e.setCancelled(true);
                Bukkit.getPlayer((String)targetPlayer).teleport((Entity)Bukkit.getPlayer((String)destPlayer), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                e.getPlayer().sendMessage((Object)ChatColor.DARK_RED + "Forcing " + targetPlayer + " to TELEPORT to " + destPlayer + "!");
            }
        }
    }

    public boolean hasRespawnedRecently(Player p) {
        return p.hasMetadata("lastRespawnEvent") ? System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastRespawnEvent").get(0)).asLong() <= 300000L : false;
    }
}

