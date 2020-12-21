/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.RequestException
 *  lilypad.client.connect.api.request.impl.MessageRequest
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ContentCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ContentCreatorListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        String channelURL;
        String channelName = ContentCreator.getChannelName(e.getPlayer().getUniqueId());
        if (channelName != null) {
            e.getPlayer().setMetadata("contentCreatorName", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)channelName));
        }
        if ((channelURL = ContentCreator.getChannelURL(e.getPlayer().getUniqueId())) != null) {
            e.getPlayer().setMetadata("contentCreatorURL", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)channelURL));
        }
    }

    @EventListener
    public void onLilypadMessageRecieved(MessageEvent event) {
        if (event.getChannel().equals(ContentCreator.contentCreator_lilypadChannel)) {
            try {
                String[] nameAndURL = event.getMessageAsString().split(",");
                String uuid = nameAndURL[0];
                String name = nameAndURL[1];
                String url = nameAndURL[2];
                Arkkit.get().getConfig().set("settings.contentCreator." + uuid + ".channelName", (Object)name);
                Arkkit.get().getConfig().set("settings.contentCreator." + uuid + ".channelURL", (Object)url);
                Bukkit.getLogger().info("[ContentCreator] Setting " + uuid + "'s channel name: " + name + ", url: " + url);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        String pName;
        String msgLower = e.getMessage().toLowerCase();
        if (msgLower.startsWith("/pex user ") && msgLower.contains("remove") && (msgLower.contains("youtube") || msgLower.contains("twitch")) && e.getPlayer().isOp() && Bukkit.getPlayer((String)(pName = e.getMessage().split(" ")[2])) != null) {
            Player p = Bukkit.getPlayer((String)pName);
            p.removeMetadata("contentCreatorURL", (Plugin)Arkkit.get());
            p.removeMetadata("contentCreatorURL", (Plugin)Arkkit.get());
            Arkkit.get().getConfig().set("settings.contentCreator." + p.getUniqueId() + ".channelName", null);
            Arkkit.get().getConfig().set("settings.contentCreator." + p.getUniqueId() + ".channelURL", null);
            e.getPlayer().sendMessage((Object)ChatColor.YELLOW + "removed local channel data for " + p.getName() + ", clear their settings.contentCreator." + p.getUniqueId() + ".channelName/URL values on all planets");
        }
    }

    public static void requestAll(String channel, String message) {
        try {
            MessageRequest request = new MessageRequest(new ArrayList(), channel, message);
            ContentCreator.lilypadConnect.request((Request)request);
        }
        catch (UnsupportedEncodingException | RequestException e) {
            e.printStackTrace();
        }
    }
}

