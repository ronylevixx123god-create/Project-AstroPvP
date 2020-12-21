/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.intellectualcrafters.plot.api.PlotAPI
 *  com.intellectualcrafters.plot.object.Plot
 *  com.intellectualcrafters.plot.object.PlotPlayer
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import java.io.File;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ChatLimiter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ChatLimiterListener
implements Listener {
    private boolean plotSquaredEnabled = new File("plugins/PlotSquared.jar").exists() || new File("plugins/PlotSquared-3.2.23.jar").exists();

    @EventHandler(priority=EventPriority.LOW)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        PlotPlayer player;
        PlotAPI api;
        Plot current;
        Player pl = e.getPlayer();
        boolean vip = pl.hasPermission("chatlimiter.vip") || pl.hasPermission("arkhamchat.vip");
        boolean plotChat = false;
        if (this.plotSquaredEnabled && (current = (api = new PlotAPI()).getPlot(pl.getLocation())) != null && (player = PlotPlayer.get((String)pl.getName())) != null && player.getAttribute("chat")) {
            plotChat = true;
        }
        long chat_delay = vip ? ChatLimiter.vip_delay : ChatLimiter.default_delay;
        long last_chat_message = pl.hasMetadata("last_chat_message") ? ((MetadataValue)pl.getMetadata("last_chat_message").get(0)).asLong() : 0L;
        long dif = System.currentTimeMillis() - last_chat_message;
        if (dif < (plotChat ? 250L : chat_delay * 1000L)) {
            pl.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You cannot send another message for " + (chat_delay * 1000L - dif) + "ms.");
            if (!vip) {
                pl.sendMessage((Object)ChatColor.GRAY + "Purchase a rank at " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + ChatLimiter.store_url + (Object)ChatColor.GRAY + " to reduce the delay between chat messages!");
            }
            e.setCancelled(true);
            return;
        }
        pl.setMetadata("last_chat_message", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
    }
}

