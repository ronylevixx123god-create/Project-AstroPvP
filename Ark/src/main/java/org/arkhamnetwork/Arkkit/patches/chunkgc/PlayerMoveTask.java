/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerMoveTask
        extends BukkitRunnable {
    public static volatile ConcurrentHashMap<String, Location> async_player_locations = new ConcurrentHashMap();
    private final Arkkit plugin = Arkkit.get();

    public void run() {
        for (Player pl : this.plugin.getServer().getOnlinePlayers()) {
            async_player_locations.put(pl.getName(), pl.getLocation());
        }
    }
}

