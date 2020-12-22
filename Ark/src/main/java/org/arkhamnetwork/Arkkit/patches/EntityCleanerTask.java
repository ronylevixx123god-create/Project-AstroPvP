/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashSet;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkAPI;
import org.arkhamnetwork.Arkkit.patches.chunkgc.ChunkGCTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

final class EntityCleanerTask
extends BukkitRunnable {
    Arkkit plugin;
    int run_count = 0;

    public EntityCleanerTask(Arkkit ark) {
        this.plugin = ark;
    }

    public synchronized void run() {
        if (ChunkGCTask.active_workers.size() > 0) {
            Bukkit.getLogger().info("Skipping EntityCleaner, ChunkGCTask has " + ChunkGCTask.active_workers.size() + " active workers!");
            return;
        }
        int exec_count = 0;
        for (World w : this.plugin.getServer().getWorlds()) {
            if (w.getEnvironment() == World.Environment.THE_END && ChunkGCTask.isEndDragonSpawned()) continue;
            final Entity[] w_ents = w.getEntities().toArray(new Entity[0]);
            final int f_exec_count = ++exec_count;
            final String w_name = w.getName();
            if (w_ents.length <= 0) continue;
            this.plugin.getServer().getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable(){

                @Override
                public void run() {
                    HashSet<Entity> dead_entities = new HashSet<Entity>();
                    for (Entity ent : w_ents) {
                        if (ent.getType() == EntityType.FIREWORK || ent.hasMetadata("NPC") || ent.hasMetadata("cbe_boss") || ent.hasMetadata("do_not_clear") || !ChunkAPI.isIntensive(ent.getType()) && (!(ent instanceof Player) || !ent.hasMetadata("NPC") && !ent.hasMetadata("npc_type")) || ChunkAPI.isPlayerNearby(ent.getLocation(), -1)) continue;
                        dead_entities.add(ent);
                    }
                    final Entity[] sync_dead_entities = dead_entities.toArray(new Entity[0]);
                    EntityCleanerTask.this.plugin.getServer().getScheduler().runTaskLater((Plugin)EntityCleanerTask.this.plugin, new Runnable(){

                        @Override
                        public void run() {
                            EntityCleanerTask.this.syncRemoveEntities(w_name, sync_dead_entities);
                        }
                    }, 4L * (long)f_exec_count);
                }
            });
        }
    }

    public void syncRemoveEntities(String world, Entity[] le) {
        if (le.length > 0) {
            Bukkit.getLogger().info("(" + world + "'s janitor) Removing " + le.length + " out-of-range entities");
            for (Entity ent : le) {
                ent.remove();
            }
        }
    }
}

