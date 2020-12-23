/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class LegacyBossBarTask
        extends BukkitRunnable {
    List<String> bossBarMessages = new ArrayList<String>(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&b&lCosmic&d&lPvP &4&l> &fTell your &b&nfriends&f about us!"), ChatColor.translateAlternateColorCodes('&', "&b&lCosmic&d&lPvP &4&l> &fWelcome back, &e&l{playerName}!")));
    int index = 0;

    LegacyBossBarTask() {
    }

    public void run() {
        if (this.index > this.bossBarMessages.size()) {
            this.index = 0;
        }
        String m = this.bossBarMessages.get(this.index);
        List pvpDisabledPlayers = Collections.emptyList();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (WorldGuardUtils.isPvPEnabled(p.getLocation())) {
                return;
            }
            pvpDisabledPlayers.add(p);
        });
        ++this.index;
    }
}

