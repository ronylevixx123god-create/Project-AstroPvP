/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.TickRatelimit;
import org.arkhamnetwork.Arkkit.patches.TickRatelimitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class TickRatelimitPlayerTrackerTask
extends BukkitRunnable {
    TickRatelimitPlayerTrackerTask() {
    }

    public void run() {
        TickRatelimit.refreshing_chunks = true;
        TickRatelimit.loadedChunkCoords.clear();
        TickRatelimit.loaded_chunk_coords.clear();
        if (TickRatelimit.blockedHopperIN > 0) {
            Bukkit.getLogger().info("[TickRatelimit] Filtered " + TickRatelimit.blockedHopperIN + " Hopper IN events.");
        }
        if (TickRatelimit.blockedHopperOUT > 0) {
            Bukkit.getLogger().info("[TickRatelimit] Filtered " + TickRatelimit.blockedHopperOUT + " Hopper OUT events.");
        }
        if (TickRatelimit.blockedCreatureSpawn > 0) {
            Bukkit.getLogger().info("[TickRatelimit] Filtered " + TickRatelimit.blockedCreatureSpawn + " CreatureSpawn events.");
        }
        TickRatelimit.blockedHopperIN = 0;
        TickRatelimit.blockedHopperOUT = 0;
        TickRatelimit.blockedCreatureSpawn = 0;
        for (Player pl : Bukkit.getOnlinePlayers()) {
            int cx = pl.getLocation().getChunk().getX();
            int cz = pl.getLocation().getChunk().getZ();
            for (int x = -3; x < 3; ++x) {
                for (int z = -3; z < 3; ++z) {
                    TickRatelimit.trackChunk(cx + x, cz + z);
                }
            }
        }
        TickRatelimitListener.flow_counts.clear();
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                TickRatelimit.refreshing_chunks = false;
            }
        }, 20L);
    }
}

