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

import org.arkhamnetwork.Arkkit.patches.NewAntiPistonLag;
import org.arkhamnetwork.Arkkit.patches.NewAntiPistonLagListener;
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
                Player p = (Player)ent;
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You have too many piston events in this chunk (" + cs + ")!");
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LIMIT: " + (Object)ChatColor.RED + this.patch.maxPistonEventsPerChunk + " piston events per chunk/" + this.patch.tickInterval + " ticks");
                p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "YOUR CHUNK: " + (Object)ChatColor.RED + events + " piston events in " + this.patch.tickInterval + " ticks");
                p.sendMessage((Object)ChatColor.GRAY + "Up to " + this.patch.maxPistonEventsPerChunk + " piston extend events can occur in each chunk every " + this.patch.tickInterval + " game ticks. Any pistons that attempt physics logic after that threshold will be stopped. Optimize your build with Observer blocks, or expand your build into more chunks.");
            }
            Bukkit.getLogger().info("[SkyPatches (AntiPistonLag)] chunk: " + cs + ", events: " + events);
            Bukkit.broadcast((String)((Object)ChatColor.DARK_RED + "[CS] " + (Object)ChatColor.GRAY + "chunk: " + cs + ", events: " + events), (String)"skypatches.anti_piston_lag.notify");
        }
        NewAntiPistonLagListener.flaggedChunks.clear();
        NewAntiPistonLagListener.chunkPistonTracker.clear();
    }
}

