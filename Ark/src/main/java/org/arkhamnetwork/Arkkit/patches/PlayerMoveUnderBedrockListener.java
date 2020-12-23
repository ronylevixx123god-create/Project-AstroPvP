/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

final class PlayerMoveUnderBedrockListener
        implements Listener {
    PlayerMoveUnderBedrockListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMoveUnderBedrock(PlayerMoveEvent e) {
        Player p;
        if (e.getTo().getY() < 0.0 && (p = e.getPlayer()).isFlying() && !p.isOp()) {
            p.setFlying(false);
            p.setAllowFlight(false);
            p.teleport(Bukkit.getWorld("world").getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You were found in an invalid location and force teleported to /spawn.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractUnderBedrock(PlayerInteractEvent e) {
        if (e.getPlayer().getLocation().getY() < 0.0 && !e.getPlayer().isOp()) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.getPlayer().sendMessage(ChatColor.RED + "You cannot interact with the world at this location.");
        }
    }
}

