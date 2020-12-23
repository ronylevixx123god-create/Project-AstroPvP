/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

final class GlobalGamemodeListener
        implements Listener {
    GlobalGamemodeListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (!e.getFrom().getWorld().equals(e.getTo().getWorld())) {
            final GameMode gm = e.getPlayer().getGameMode();
            final Player p = e.getPlayer();
            if (gm == GameMode.CREATIVE) {
                Bukkit.getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                    @Override
                    public void run() {
                        if (p.getGameMode() != gm) {
                            p.setGameMode(gm);
                        }
                    }
                }, 2L);
            }
        }
    }
}

