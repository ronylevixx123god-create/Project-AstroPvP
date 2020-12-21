/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

final class MaxFlyHeightTask
extends BukkitRunnable {
    MaxFlyHeightTask() {
    }

    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().getY() > 255.5 && p.isFlying() && !p.isOp()) {
                Location locFixed = p.getLocation();
                locFixed.setY(255.0);
                if (!locFixed.getBlock().getType().isBlock()) {
                    p.teleport(locFixed, PlayerTeleportEvent.TeleportCause.UNKNOWN);
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot fly above y:256 in this world!");
                    continue;
                }
                p.setFlying(false);
                continue;
            }
            if (!(p.getLocation().getY() < 0.0) || !p.isFlying() || p.isOp()) continue;
            p.setFlying(false);
            p.setAllowFlight(false);
            p.damage(1.0);
        }
    }
}

