/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.plugin.Plugin;

public class ChunkGC
        extends ArkPlugin {
    public static ChunkGCLogger log = new ChunkGCLogger();

    public ChunkGC(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        ((CraftServer) Bukkit.getServer()).chunkGCPeriod = 0;
        ((CraftServer) Bukkit.getServer()).chunkGCLoadThresh = 0;
        for (World w : Bukkit.getWorlds()) {
            ((CraftWorld) w).getHandle().spigotConfig.clearChunksOnTick = true;
        }
        ChunkSyncUtil.syncChunks();
        this.registerListener(new ChunkGCListener());
        this.registerTask(new PlayerMoveTask().runTaskTimer(this.getBukkitPlugin(), 20L, 5L), PlayerMoveTask.class);
        this.registerTask(new ChunkGCTask().runTaskTimer(this.getBukkitPlugin(), 300L, 1200L), ChunkGCTask.class);
        this.registerCommand(new CommandForceChunkGC("forcechunkgc", "/<command>", "Forces a global Chunk GC"));
    }

    @Override
    public void disable() {
    }
}

