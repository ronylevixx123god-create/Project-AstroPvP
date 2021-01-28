/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class TickRatelimit
        extends ArkPlugin {
    protected static HashSet<String> loaded_chunk_coords = new HashSet();
    protected static Set<Long> loadedChunkCoords = new HashSet<Long>();
    protected static boolean refreshing_chunks = false;
    protected static boolean allowRefreshChunks = false;
    protected static boolean requirePlayerNearby = true;
    protected static boolean monitorFlow = true;
    protected static boolean limitFlow = true;
    protected static boolean limitHopperIn = true;
    protected static boolean limitHopperOut = true;
    protected static boolean limitCreatureSpawn = true;
    protected static int blockedHopperIN = 0;
    protected static int blockedHopperOUT = 0;
    protected static int blockedCreatureSpawn = 0;

    public TickRatelimit(Plugin p) {
        super(p);
    }

    protected static boolean isLoadedChunk(Chunk chunk) {
        return loadedChunkCoords.contains(LongHash.toLong(chunk.getX(), chunk.getZ()));
    }

    protected static void trackChunk(int x, int z) {
        loadedChunkCoords.add(LongHash.toLong(x, z));
    }

    protected static void trackChunk(Chunk chunk) {
        TickRatelimit.trackChunk(chunk.getX(), chunk.getZ());
    }

    @Override
    public void enable() {
        allowRefreshChunks = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.refreshing_chunks");
        requirePlayerNearby = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.require_player_nearby");
        limitFlow = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.limitFlow");
        monitorFlow = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.monitorFlow");
        limitHopperIn = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.limitHopperIn");
        limitHopperOut = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.limitHopperOut");
        limitCreatureSpawn = Arkkit.get().getConfig().getBoolean("settings.tick-ratelimit.limitCreatureSpawn");
        this.registerListener(new TickRatelimitListener());
        this.registerTask(new TickRatelimitPlayerTrackerTask().runTaskTimerAsynchronously(this.getBukkitPlugin(), 20L, 160L), TickRatelimitListener.class);
    }

    @Override
    public void disable() {
    }
}

