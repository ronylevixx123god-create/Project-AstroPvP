/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class NetherCeilingPunishmentTask
        extends BukkitRunnable {
    NetherCeilingPunishmentTask() {
    }

    public void run() {
        for (Player p : Bukkit.getWorld("world_nether").getPlayers()) {
            if (p.hasPermission("arkkit.staff.hidden") || p.getGameMode() != GameMode.SURVIVAL || !(p.getLocation().getY() >= 128.0))
                continue;
            p.damage(2.0);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "Your character cannot survive at this height in a nether dimension!");
        }
    }
}

