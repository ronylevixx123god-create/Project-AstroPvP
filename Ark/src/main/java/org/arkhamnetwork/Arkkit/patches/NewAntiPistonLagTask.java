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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

final class NewAntiPistonLagTask
        extends BukkitRunnable {
    NewAntiPistonLag patch;

    public NewAntiPistonLagTask(NewAntiPistonLag patch) {
        this.patch = patch;
    }

    public void run() {
        for (Chunk chunk : NewAntiPistonLagListener.flaggedChunks) {
            long id = this.patch.getChunkValue(chunk);
            int events = NewAntiPistonLagListener.chunkPistonTracker.get(id);
            String cs = chunk.getX() + "x, " + chunk.getZ() + "z";
            for (Entity ent : chunk.getEntities()) {
                if (!(ent instanceof Player)) continue;
                Player p = (Player) ent;
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You have too many piston events in this chunk (" + cs + ")!");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "LIMIT: " + ChatColor.RED + this.patch.maxPistonEventsPerChunk + " piston events per chunk/" + this.patch.tickInterval + " ticks");
                p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOUR CHUNK: " + ChatColor.RED + events + " piston events in " + this.patch.tickInterval + " ticks");
                p.sendMessage(ChatColor.GRAY + "Up to " + this.patch.maxPistonEventsPerChunk + " piston extend events can occur in each chunk every " + this.patch.tickInterval + " game ticks. Any pistons that attempt physics logic after that threshold will be stopped. Optimize your build with Observer blocks, or expand your build into more chunks.");
            }
            Bukkit.getLogger().info("[SkyPatches (AntiPistonLag)] chunk: " + cs + ", events: " + events);
            Bukkit.broadcast(ChatColor.DARK_RED + "[CS] " + ChatColor.GRAY + "chunk: " + cs + ", events: " + events, "skypatches.anti_piston_lag.notify");
        }
        NewAntiPistonLagListener.flaggedChunks.clear();
        NewAntiPistonLagListener.chunkPistonTracker.clear();
    }
}

