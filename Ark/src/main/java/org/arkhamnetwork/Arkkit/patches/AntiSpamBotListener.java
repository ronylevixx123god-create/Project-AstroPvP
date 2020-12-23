/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;
import java.util.HashSet;

final class AntiSpamBotListener
        implements Listener {
    protected static HashMap<String, Integer> IPMessageCount;
    int maxMessageUsesPerScan = 2;
    HashSet<String> ipBlacklist;
    HashSet<String> uuidBlacklist;

    public AntiSpamBotListener() {
        AntiSpamBot.messageUses = new HashMap();
        AntiSpamBot.UUIDLoginCount = new HashMap();
        AntiSpamBot.IPLoginCount = new HashMap();
        IPMessageCount = new HashMap();
        this.ipBlacklist = new HashSet();
        this.uuidBlacklist = new HashSet();
        this.maxMessageUsesPerScan = Arkkit.get().getConfig().getInt("settings.anti_spam_bot.maxMessageUsesPerScan");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (AntiSpamBot.vipOnly && !e.getPlayer().hasPermission("chatlimiter.vip")) {
            e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) NON-VIP CHAT IS TEMPORARILY DISABLED!");
            e.setCancelled(true);
            e.setMessage("");
            return;
        }
        String msg = e.getMessage();
        if (msg.contains(" ")) {
            msg = msg.substring(0, msg.lastIndexOf(" "));
        }
        if (!AntiSpamBot.messageUses.containsKey(msg)) {
            AntiSpamBot.messageUses.put(msg, 1);
            return;
        }
        AntiSpamBot.messageUses.put(msg, AntiSpamBot.messageUses.get(msg) + 1);
        int uses = AntiSpamBot.messageUses.get(msg);
        if (uses >= this.maxMessageUsesPerScan) {
            e.setCancelled(true);
            Bukkit.getLogger().info("[AntiSpamBot] Blocked message \"" + e.getMessage() + "\" due to messageUses=" + uses + " from " + e.getPlayer().getName() + " [" + e.getPlayer().getAddress().getHostString() + "]");
            e.setMessage("");
            return;
        }
        String IP = e.getPlayer().getAddress().getHostString();
        int IP_messages = (IPMessageCount.containsKey(IP) ? IPMessageCount.get(IP) : 0) + 1;
        IPMessageCount.put(IP, IP_messages);
        if (IP_messages >= 3) {
            e.setCancelled(true);
            Bukkit.getLogger().info("[AntiSpamBot] Blocked message \"" + e.getMessage() + "\" due to IP_messages=" + IP_messages + " from " + e.getPlayer().getName() + " [IP: " + e.getPlayer().getAddress().getHostString() + "]");
            e.setMessage("");
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        String uuid = e.getUniqueId().toString();
        String ip = e.getAddress().getHostAddress();
        if (this.uuidBlacklist.contains(uuid) || this.ipBlacklist.contains(ip)) {
            Bukkit.getLogger().info("[AntiSpamBot] Blocking blacklisted login from " + uuid + " on " + ip);
            e.setKickMessage("Too many login events from your UUID and/or IP.");
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }
        int uuid_attempts = (AntiSpamBot.UUIDLoginCount.containsKey(uuid) ? AntiSpamBot.UUIDLoginCount.get(uuid) : 0) + 1;
        int ip_attempts = (AntiSpamBot.IPLoginCount.containsKey(ip) ? AntiSpamBot.IPLoginCount.get(ip) : 0) + 1;
        if (uuid_attempts >= 4) {
            this.uuidBlacklist.add(uuid);
        }
        if (ip_attempts >= 3) {
            this.ipBlacklist.add(ip);
        }
    }
}

