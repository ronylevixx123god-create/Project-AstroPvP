/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerChangedWorldEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.barannouncer;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncer;
import org.arkhamnetwork.Arkkit.patches.barannouncer.BarAnnouncerParser;
import org.arkhamnetwork.Arkkit.patches.barannouncer.StatusBarAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

public class BarAnnouncerPersistanceListener
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onJoin(final PlayerJoinEvent event) {
        Arkkit.get().getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                StatusBarAPI.setStatusBar(event.getPlayer(), BarAnnouncerParser.parseAnnouncement(BarAnnouncer.getCurrentAnnouncement(), event.getPlayer()), 100.0f);
            }
        }, 2L);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void PlayerLogout(PlayerQuitEvent event) {
        StatusBarAPI.removeStatusBar(event.getPlayer());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        Arkkit.get().getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                StatusBarAPI.removeStatusBar(event.getPlayer());
                StatusBarAPI.setStatusBar(event.getPlayer(), BarAnnouncerParser.parseAnnouncement(BarAnnouncer.getCurrentAnnouncement(), event.getPlayer()), 100.0f);
            }
        }, 2L);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        Arkkit.get().getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                StatusBarAPI.removeStatusBar(event.getPlayer());
                StatusBarAPI.setStatusBar(event.getPlayer(), BarAnnouncerParser.parseAnnouncement(BarAnnouncer.getCurrentAnnouncement(), event.getPlayer()), 100.0f);
            }
        }, 2L);
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerWorldChance(final PlayerChangedWorldEvent event) {
        Arkkit.get().getServer().getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                StatusBarAPI.removeStatusBar(event.getPlayer());
                StatusBarAPI.setStatusBar(event.getPlayer(), BarAnnouncerParser.parseAnnouncement(BarAnnouncer.getCurrentAnnouncement(), event.getPlayer()), 100.0f);
            }
        }, 2L);
    }
}

