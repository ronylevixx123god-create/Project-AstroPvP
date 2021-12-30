/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 */
package com.compilex.CosmicKOTH.tasks;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LauncherTask implements Runnable {
    public static int countdown = -1;

    public LauncherTask(int secondsUntilKoth) {
        countdown = secondsUntilKoth;
    }

    public void run() {
        if (!CosmicKOTHAPI.isKOTHRunning()) {
            --countdown;

            if (countdown == 3560) {
                this.notifyPlayers("69 MINUTES");
            }

            if (countdown == 1800) {
                this.notifyPlayers("30 MINUTES");
            }

            if (countdown == 900) {
                this.notifyPlayers("15 MINUTES");
            }

            if (countdown == 600) {
                this.notifyPlayers("10 MINUTES");
            }

            if (countdown == 300) {
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
    }

    private void notifyPlayers(String delay) {
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)(CC.translate("&c&l*&6&l*&e&l* &f&lA KOTH EVENT IS STARTING IN &d&n" + delay + "&r &e&l*&6&l*&c&l*")));
        Bukkit.broadcastMessage((String)((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.LIGHT_PURPLE + "/koth" + (Object)ChatColor.GRAY + " to view event information!"));
        Bukkit.broadcastMessage((String)"");
    }
}

