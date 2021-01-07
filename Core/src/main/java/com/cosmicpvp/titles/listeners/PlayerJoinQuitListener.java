/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package com.cosmicpvp.titles.listeners;

import com.cosmicpvp.titles.PlayerTitleAPI;
import com.cosmicpvp.titles.events.TitleChangeEvent;
import com.cosmicpvp.titles.sql.TitleDatabaseManager;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener
implements Listener {
    private final TitleDatabaseManager dbM;

    public PlayerJoinQuitListener(TitleDatabaseManager dm) {
        this.dbM = dm;
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            return;
        }
        UUID uuid = e.getUniqueId();
        Bukkit.getPluginManager().callEvent(new TitleChangeEvent(uuid, this.dbM.getEquippedTitle(uuid)));
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        PlayerTitleAPI.getUnlockedTitles(uuid);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.dbM.getUnlockedTitles().remove(e.getPlayer().getUniqueId());
    }
}

