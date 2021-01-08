/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

final class SeasonalPrefixesListener
        implements Listener {
    SeasonalPrefixesListener() {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (SeasonalPrefixes.getSeasonalWins(e.getPlayer()) > 0) {
            String format = e.getFormat();
            Player p = e.getPlayer();
            String seasonalPrefix = SeasonalPrefixes.getSeasonalPrefix(p);
            format = format.substring(0, 11) + seasonalPrefix + " " + format.substring(11);
            Bukkit.getLogger().info(format);
            e.setFormat(format);
        }
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String pName;
        if (e.getMessage().toLowerCase().startsWith("/pex user ") && Bukkit.getPlayer(pName = e.getMessage().split(" ")[2]) != null) {
            Player p = Bukkit.getPlayer(pName);
            p.removeMetadata("seasonalPrefix", Arkkit.get());
            p.removeMetadata("seasonalWins", Arkkit.get());
        }
    }
}

