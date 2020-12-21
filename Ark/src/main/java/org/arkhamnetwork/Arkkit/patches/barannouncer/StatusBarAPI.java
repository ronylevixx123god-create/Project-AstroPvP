package org.arkhamnetwork.Arkkit.patches.barannouncer;

import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatusBarAPI {
    protected static boolean hasStatusBar(Player player) {
        return BarAPI.hasBar(player);
    }

    protected static void removeStatusBar(Player player) {
        if (BarAPI.hasBar(player)) {
            BarAPI.removeBar(player);
        }
    }

    protected static void setStatusBar(Player player, String text, float percent) {
        StatusBarAPI.removeStatusBar(player);
        BarAPI.setMessage(player, text, percent);
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

