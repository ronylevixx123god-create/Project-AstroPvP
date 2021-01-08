/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 */
package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LauncherTask
implements Runnable {
    public static int countdown = -1;

    public LauncherTask(int secondsUntilKoth) {
        countdown = secondsUntilKoth;
    }

    @Override
    public void run() {
        if (CosmicKOTHAPI.isKOTHRunning()) {
            return;
        }
        if (--countdown == 300) {
            this.notifyPlayers("5 MINUTES");
        }
        if (countdown == 240) {
            this.notifyPlayers("4 MINUTES");
        }
        if (countdown == 180) {
            this.notifyPlayers("3 MINUTES");
        }
        if (countdown == 120) {
            this.notifyPlayers("2 MINUTES");
        }
        if (countdown == 60) {
            this.notifyPlayers("1 MINUTE");
        }
        if (countdown <= 10 && countdown > 0) {
            this.notifyPlayers(countdown + " SECONDS");
        }
        if (countdown == 0) {
            CosmicKOTHAPI.resetKOTH();
        }
    }

    private void notifyPlayers(String delay) {
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** KOTH EVENT STARTING IN " + ChatColor.YELLOW.toString() + (Object)ChatColor.UNDERLINE + delay + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " ***"));
        Bukkit.broadcastMessage((String)((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.GREEN + "/koth" + (Object)ChatColor.GRAY + " to view event information!"));
        Bukkit.broadcastMessage((String)"");
    }
}

