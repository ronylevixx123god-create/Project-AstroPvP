/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.UnsupportedEncodingException;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class SeasonalLilypadListener {
    @EventListener
    public void onMessageEvent(MessageEvent event) {
        if (event.getChannel().equals("cPermU")) {
            try {
                String msg = event.getMessageAsString();
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)msg);
                Bukkit.getLogger().info("[Arkkit (SeasonalPasses)] Executing '" + msg + "' due to receiving update packet!");
            }
            catch (UnsupportedEncodingException e) {
                Bukkit.getLogger().info("Message not available from cPermU channel!");
                e.printStackTrace();
            }
        }
    }
}

