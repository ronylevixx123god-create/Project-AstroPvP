/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.io.UnsupportedEncodingException;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NetworkPrivateMessagesListener
implements Listener {
    public static void sendPlayerCallback(Player p, String to, String from, String message, String fromServer) {
        if (p != null) {
            NetworkEvents.lastMessaged.put(from, to);
            String chat = ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.network_private_messages.message-sent").replace("{player}", to).replace("{server}", fromServer));
            p.sendMessage(chat.replace("{message}", ChatUtils.getFilteredMessage(message, p, true)));
        }
    }

    @EventListener
    public void onSync(MessageEvent event) {
        if (event.getChannel().equals("NetworkPrivateMessages.message.callback")) {
            try {
                String code = event.getMessageAsString();
                String[] tofrommessage = code.split(NetworkEvents.packet_split);
                String to = tofrommessage[0];
                String from = tofrommessage[1];
                String message = tofrommessage[2];
                String fromServer = tofrommessage[3];
                Player p = Arkkit.get().getServer().getPlayer(from);
                NetworkPrivateMessagesListener.sendPlayerCallback(p, to, from, message, fromServer);
            }
            catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            return;
        }
        if (event.getChannel().equals("NetworkPrivateMessages.message")) {
            try {
                String code = event.getMessageAsString();
                String[] tofrommessage = code.split(NetworkEvents.packet_split);
                String to = tofrommessage[0];
                String from = tofrommessage[1];
                String message = tofrommessage[2];
                String fromServer = tofrommessage[3];
                Player p = Bukkit.getServer().getPlayer(to);
                if (IgnoreUtils.isIgnored(p, from)) {
                    NetworkEvents.request(fromServer, "NetworkPrivateMessages.message.callback", p.getName() + NetworkEvents.packet_split + from + NetworkEvents.packet_split + (Object)ChatColor.RED + to + " is not currently accepting messages." + NetworkEvents.packet_split + NetworkEvents.getUsername());
                    return;
                }
                if (p != null) {
                    if (NetworkEventsListener.msgToggled.contains(p.getUniqueId())) {
                        if (fromServer != null) {
                            NetworkEvents.request(fromServer, "NetworkPrivateMessages.message.callback", p.getName() + NetworkEvents.packet_split + from + NetworkEvents.packet_split + (Object)ChatColor.RED + to + " is not currently accepting messages." + NetworkEvents.packet_split + NetworkEvents.getUsername());
                        }
                        return;
                    }
                    message = ChatUtils.getFilteredMessage(message, p, true);
                    String chat = ChatColor.translateAlternateColorCodes((char)'&', (String)Arkkit.get().getConfig().getString("language.network_private_messages.message-from").replace("{player}", from).replace("{server}", fromServer));
                    p.sendMessage(chat.replace("{message}", ChatUtils.getFilteredMessage(message, p, true)));
                    NetworkEvents.lastMessaged.put(p.getName(), from);
                    NetworkEvents.request(fromServer, "NetworkPrivateMessages.message.callback", p.getName() + NetworkEvents.packet_split + from + NetworkEvents.packet_split + message + NetworkEvents.packet_split + NetworkEvents.getUsername());
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}

