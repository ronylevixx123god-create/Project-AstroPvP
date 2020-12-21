/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.util.UUID;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MarriageListener
implements Listener {
    @EventHandler
    public void onPlayerQuitPendingRequests(PlayerQuitEvent event) {
        Marriage.pending_requests.remove(event.getPlayer().getUniqueId());
        if (Marriage.pending_requests.containsValue(event.getPlayer().getUniqueId())) {
            for (UUID uuid : Marriage.pending_requests.keySet()) {
                if (Marriage.pending_requests.get(uuid) != event.getPlayer().getUniqueId()) continue;
                Marriage.pending_requests.remove(uuid);
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId()) == null) {
            return;
        }
        if (!Marriage.marriage_chatters.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())) == null) {
            event.getPlayer().sendMessage(Marriage.prefix + "Your partner must be online in order to use marriage chat.");
            return;
        }
        event.getPlayer().sendMessage((Object)ChatColor.LIGHT_PURPLE + "Marriage> " + (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + event.getPlayer().getName() + " " + (Object)ChatColor.LIGHT_PURPLE + event.getMessage().replace("<3", "\u00e2\u00a7\u00a4"));
        Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())).sendMessage((Object)ChatColor.LIGHT_PURPLE + "Marriage> " + (Object)ChatColor.WHITE + (Object)ChatColor.BOLD + event.getPlayer().getName() + " " + (Object)ChatColor.LIGHT_PURPLE + event.getMessage().replace("<3", "\u00e2\u00a7\u00a4"));
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId()) == null) {
            return;
        }
        if (Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())) != null) {
            Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())).sendMessage(Marriage.prefix + (Object)ChatColor.YELLOW + "Your partner is now " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + "online" + (Object)ChatColor.GRAY + ".");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId()) == null) {
            return;
        }
        if (Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())) != null) {
            Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(event.getPlayer().getUniqueId())).sendMessage(Marriage.prefix + (Object)ChatColor.YELLOW + "Your partner is now " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "offline" + (Object)ChatColor.GRAY + ".");
        }
        if (Marriage.marriage_chatters.contains(event.getPlayer().getUniqueId())) {
            Marriage.marriage_chatters.remove(event.getPlayer().getUniqueId());
        }
    }
}

