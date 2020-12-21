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
import org.arkhamnetwork.Arkkit.patches.SeasonalPrefixes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

final class SeasonalPrefixesListener
implements Listener {
    SeasonalPrefixesListener() {
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (SeasonalPrefixes.getSeasonalWins(e.getPlayer()) > 0) {
            String format = e.getFormat();
            Player p = e.getPlayer();
            String seasonalPrefix = SeasonalPrefixes.getSeasonalPrefix(p);
            format = format.substring(0, 11) + seasonalPrefix + " " + format.substring(11, format.length());
            Bukkit.getLogger().info(format);
            e.setFormat(format);
        }
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String pName;
        if (e.getMessage().toLowerCase().startsWith("/pex user ") && Bukkit.getPlayer((String)(pName = e.getMessage().split(" ")[2])) != null) {
            Player p = Bukkit.getPlayer((String)pName);
            p.removeMetadata("seasonalPrefix", (Plugin)Arkkit.get());
            p.removeMetadata("seasonalWins", (Plugin)Arkkit.get());
        }
    }
}

