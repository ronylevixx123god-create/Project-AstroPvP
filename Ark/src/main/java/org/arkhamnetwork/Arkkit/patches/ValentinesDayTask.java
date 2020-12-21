/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class ValentinesDayTask
extends BukkitRunnable {
    public List<String> players_with_dates = new ArrayList<String>();

    public void run() {
        if (Bukkit.getOnlinePlayers().size() <= 0) {
            return;
        }
        Player rand_p1 = (Player)Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
        Player rand_p2 = (Player)Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
        Bukkit.broadcastMessage((String)"");
        if (rand_p1.getName().equals("TBNRfrags") && Bukkit.getPlayer((String)"Woofless") != null) {
            rand_p2 = Bukkit.getPlayer((String)"Woofless");
        } else if (rand_p1.getName().equals("Woofless") && Bukkit.getPlayer((String)"TBNRfrags") != null) {
            rand_p2 = Bukkit.getPlayer((String)"TBNRfrags");
        }
        if (this.players_with_dates.contains(rand_p1.getName()) && this.players_with_dates.contains(rand_p2.getName())) {
            return;
        }
        if (rand_p1.equals((Object)rand_p2)) {
            Bukkit.broadcastMessage((String)(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "<3 LOVE ALERT <3 " + (Object)ChatColor.WHITE + "The Cosmic Fates have discovered no one wants to date " + (Object)ChatColor.RED + rand_p1.getName() + "!"));
        } else {
            Bukkit.broadcastMessage((String)(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "<3 LOVE ALERT <3 " + (Object)ChatColor.WHITE + "The Cosmic Fates have chosen " + (Object)ChatColor.RED + rand_p1.getName() + (Object)ChatColor.WHITE + " as the valentine date for " + (Object)ChatColor.RED + rand_p2.getName() + "!"));
        }
        Bukkit.broadcastMessage((String)"");
        this.players_with_dates.add(rand_p1.getName());
        this.players_with_dates.add(rand_p2.getName());
    }
}

