/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.arkhamnetwork.Arkkit.utils.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

class FactionMuteListener
        implements Listener {
    private final ExtraMute instance;

    public FactionMuteListener(ExtraMute instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String factionID = FactionUtils.getFactionId(player);
        if (factionID != null && !player.isOp() && this.instance.getFactionsMuted().containsKey(factionID)) {
            long time = this.instance.getFactionsMuted().get(factionID);
            if (time > System.currentTimeMillis() / 1000L) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your faction has been muted and you cannot speak.");
                player.sendMessage(ChatColor.GRAY + "Faction Mute Expires in " + TimeUtils.formatTime(time) + "!");
                return;
            }
            this.instance.getFactionsMuted().remove(factionID);
        }
        String ip = player.getAddress().getAddress().getHostAddress();
        if (this.instance.getIpsMuted().containsKey(ip)) {
            MuteReason reason = this.instance.getIpsMuted().get(ip);
            if (reason.getExpire() != -1L && reason.getExpire() < System.currentTimeMillis()) {
                this.instance.getIpsMuted().remove(ip);
                return;
            }
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your IP has been muted for " + reason.getReason() + "!");
            if (reason.getExpire() == -1L) {
                player.sendMessage(ChatColor.GRAY + "This mute will never expire.");
            } else {
                player.sendMessage(ChatColor.GRAY + "Mute will expire in " + TimeUtils.formatTime(reason.getExpire() / 1000L) + "!");
            }
        }
    }
}

