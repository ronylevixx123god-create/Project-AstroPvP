/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.UnsupportedEncodingException;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AlertSystemListener
implements Listener {
    @EventListener
    public void onSync(MessageEvent event) {
        String msg;
        if (event.getChannel().equals("ArkhamAlert.message")) {
            try {
                msg = event.getMessageAsString();
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.playSound(pl.getLocation(), Sound.CHICKEN_EGG_POP, 2.0f, 0.75f);
                }
                Bukkit.broadcastMessage((String)"");
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("broadcast " + (Object)ChatColor.BOLD + msg));
                Bukkit.broadcastMessage((String)"");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (event.getChannel().equalsIgnoreCase("Arkkit.rawMessage")) {
            try {
                msg = event.getMessageAsString();
                Bukkit.broadcastMessage((String)ChatColor.translateAlternateColorCodes((char)'&', (String)msg));
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}

