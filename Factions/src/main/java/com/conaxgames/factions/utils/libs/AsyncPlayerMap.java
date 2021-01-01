/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.utils.libs;

import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class AsyncPlayerMap
implements Runnable,
Listener {
    private volatile ConcurrentHashMap<String, Player> players = new ConcurrentHashMap();
    private volatile ConcurrentHashMap<String, Location> locations = new ConcurrentHashMap();

    public AsyncPlayerMap(Plugin bukkitPlugin) {
        Bukkit.getPluginManager().registerEvents((Listener)this, bukkitPlugin);
        Bukkit.getScheduler().runTaskTimer(bukkitPlugin, (Runnable)this, 20L, 20L);
    }

    @Override
    public void run() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            this.locations.put(pl.getName(), pl.getLocation());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.players.put(e.getPlayer().getName(), e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.players.remove(e.getPlayer().getName());
    }

    public ConcurrentHashMap<String, Player> getPlayers() {
        return this.players;
    }

    public ConcurrentHashMap<String, Location> getLocations() {
        return this.locations;
    }
}

