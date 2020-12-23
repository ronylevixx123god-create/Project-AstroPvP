/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class AntiRedstoneLagTask
        extends BukkitRunnable {
    public static ConcurrentHashMap<String, Integer> laggingChunks = new ConcurrentHashMap();
    public static ConcurrentHashMap<Long, Integer> fastLaggingChunks = new ConcurrentHashMap();
    private int iteration = 0;

    AntiRedstoneLagTask() {
    }

    public void run() {
        for (Map.Entry<Long, Integer> entry : AntiRedstoneLagListener.fastRedstoneTracker.entrySet()) {
            if (entry.getValue() < AntiRedstoneLag.max_redstone_per_iteration) continue;
            Integer lagCounter = fastLaggingChunks.get(entry.getKey());
            lagCounter = lagCounter == null ? 1 : lagCounter + 1;
            fastLaggingChunks.put(entry.getKey(), lagCounter);
            int chunkX = LongHash.msw(entry.getKey());
            int chunkZ = LongHash.lsw(entry.getKey());
            String displ = chunkX + "," + chunkZ;
            Bukkit.getLogger().info("(AntiRedstoneLag) " + entry.getValue() / lagCounter + "x redstone events in <30s at .. " + displ + " (" + entry.getKey() + ") [#" + lagCounter + "]");
            Bukkit.broadcast(ChatColor.WHITE + "(AntiRedstoneLag) " + ChatColor.GRAY + entry.getValue() / Math.min(50, lagCounter) + "x redstone events in <30s at .. " + chunkX + "," + chunkZ + " [#" + lagCounter + "]", "arkkit.anti_redstone_lag.notify");
            Chunk c = Bukkit.getWorld("world").getChunkAt(chunkX, chunkZ);
            for (Entity e : c.getEntities()) {
                if (!(e instanceof Player)) continue;
                Player p = (Player) e;
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "Redstone in this chunk is being ratelimited by the server.");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "LIMIT: " + ChatColor.RED + Math.max(5, (int) (100.0 - Math.min(100.0, (double) lagCounter.intValue() / 20.0 * 100.0))) + "% normal redstone");
                p.sendMessage(ChatColor.GRAY + "This happens when the server detects you have too many redstone events per game tick in 1 chunk. " + ChatColor.UNDERLINE + "Optimize your farm/cannon!");
                p.sendMessage(ChatColor.GRAY + "This chunk will be ratelimited for another: " + ChatColor.RED + lagCounter * 30 * 4 + "s");
            }
        }
        AntiRedstoneLagListener.fastRedstoneTracker.clear();
        if (this.iteration++ % 4 == 0 && fastLaggingChunks.size() > 0) {
            Bukkit.getLogger().info("(AntiRedstoneLag) Decreasing laggingChunks list by 1: " + fastLaggingChunks);
            for (Map.Entry<Long, Integer> data : fastLaggingChunks.entrySet()) {
                if (data.getValue() == 1) {
                    fastLaggingChunks.remove(data.getKey());
                    continue;
                }
                fastLaggingChunks.put(data.getKey(), data.getValue() - 1);
            }
            Bukkit.getLogger().info("(AntiRedstoneLag) New lagginChunks: " + fastLaggingChunks);
        }
    }
}

