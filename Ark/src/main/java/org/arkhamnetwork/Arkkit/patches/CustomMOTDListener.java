/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CustomMOTD;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

final class CustomMOTDListener
implements Listener {
    CustomMOTDListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player pl = e.getPlayer();
        if (CustomMOTD.motd_lines.size() > 0) {
            CustomMOTD.clearChat(pl);
            Arkkit.get().getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    if (pl != null && pl.isOnline()) {
                        CustomMOTD.sendMOTD(pl);
                    }
                }
            }, 11L);
        }
    }
}

