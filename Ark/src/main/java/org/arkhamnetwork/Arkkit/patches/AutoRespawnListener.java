/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AutoRespawn;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

final class AutoRespawnListener
implements Listener {
    static int visibleDistance = Bukkit.getServer().getViewDistance() * 16;
    static int minVisible = 4096;

    AutoRespawnListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        final UUID playerUUID = e.getEntity().getUniqueId();
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                Player p = Bukkit.getPlayer((UUID)playerUUID);
                if (p != null && p.isDead()) {
                    Bukkit.getPlayer((UUID)playerUUID).spigot().respawn();
                }
            }
        }, (long)AutoRespawn.respawnDelaySeconds * 20L);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isDead()) {
            e.getPlayer().spigot().respawn();
        }
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        if (((Essentials)Bukkit.getPluginManager().getPlugin("Essentials")).getUser(player).isVanished()) {
            return;
        }
        for (Entity ent : player.getNearbyEntities(48.0, 256.0, 48.0)) {
            Player pNear;
            if (!(ent instanceof Player) || !(pNear = (Player)ent).canSee(player)) continue;
            pNear.hidePlayer(player);
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    if (pNear == null || !pNear.isValid()) {
                        return;
                    }
                    if (player == null || !player.isValid()) {
                        return;
                    }
                    pNear.showPlayer(player);
                }
            }, 4L);
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        final Player player = e.getPlayer();
        if (((Essentials)Bukkit.getPluginManager().getPlugin("Essentials")).getUser(player).isVanished()) {
            return;
        }
        for (Entity ent : player.getNearbyEntities(48.0, 256.0, 48.0)) {
            Player pNear;
            if (!(ent instanceof Player) || !(pNear = (Player)ent).canSee(player)) continue;
            pNear.hidePlayer(player);
            Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    if (pNear == null || !pNear.isValid()) {
                        return;
                    }
                    if (player == null || !player.isValid()) {
                        return;
                    }
                    pNear.showPlayer(player);
                }
            }, 4L);
        }
    }
}

