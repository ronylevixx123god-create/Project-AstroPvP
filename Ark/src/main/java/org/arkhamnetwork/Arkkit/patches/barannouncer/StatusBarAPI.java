/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.confuser.barapi.BarAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatusBarAPI {
    protected static boolean hasStatusBar(Player player) {
        return BarAPI.hasBar((Player)player);
    }

    protected static void removeStatusBar(Player player) {
        if (BarAPI.hasBar((Player)player)) {
            BarAPI.removeBar((Player)player);
        }
    }

    protected static void setStatusBar(Player player, String text, float percent) {
        StatusBarAPI.removeStatusBar(player);
        BarAPI.setMessage((Player)player, (String)text, (float)percent);
    }

    protected static void removeAllStatusBars() {
        for (Player each : Bukkit.getOnlinePlayers()) {
            StatusBarAPI.removeStatusBar(each);
        }
    }

    protected static void setAllStatusBars(String text, float percent) {
        for (Player each : Bukkit.getOnlinePlayers()) {
            StatusBarAPI.setStatusBar(each, text, percent);
        }
    }
}

