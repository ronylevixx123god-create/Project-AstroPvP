/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.xenforosyncaccount;

import java.sql.SQLException;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.xenforosyncaccount.XenforoSyncAccount;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

final class SyncAccountListener
implements Listener {
    int pendingLogins = 0;

    SyncAccountListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player pl = e.getPlayer();
        ++this.pendingLogins;
        if (this.pendingLogins > 10) {
            return;
        }
        Arkkit.get().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                if (pl != null && pl.isOnline()) {
                    try {
                        String forum_username = XenforoSyncAccount.getForumUsernameFromPlayerUUID(pl.getUniqueId());
                        if (forum_username == null) {
                            pl.sendMessage(ChatColor.GOLD.toString() + (Object)ChatColor.BOLD + "COSMIC FORUMS");
                            pl.sendMessage((Object)ChatColor.GRAY + "Hello " + (Object)ChatColor.GOLD + pl.getName() + (Object)ChatColor.GRAY + ", we have noticed you have not yet used /syncaccount to link your minecraft account to a " + (Object)ChatColor.UNDERLINE + "CosmicPvP Forum Account!" + (Object)ChatColor.GRAY + " Visit " + (Object)ChatColor.GOLD + "https://forum.cosmicpvp.com" + (Object)ChatColor.GRAY + " today, register an account, then use " + (Object)ChatColor.GOLD + "/syncaccount" + (Object)ChatColor.GRAY + " to link it with your in-game character!");
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                --SyncAccountListener.this.pendingLogins;
            }
        }, 40L);
    }
}

