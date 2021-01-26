/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.utils.BossUtils
 *  net.minecraft.server.v1_7_R4.Chunk
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.LongHash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChunkGCTask
        extends BukkitRunnable {
    public static final int block_viewdistance_raw = Arkkit.get().getServer().getViewDistance();
    public static final int block_viewdistance = block_viewdistance_raw * 16;
    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<Long>> chunks_to_unload = new ConcurrentHashMap();
    public static volatile ConcurrentHashMap<String, CopyOnWriteArrayList<Long>> loaded_chunks = new ConcurrentHashMap();
    public static volatile HashMap<String, Integer> world_viewdistances = new HashMap();
    public static boolean bossesEnabled = false;
    public static HashSet<Integer> active_workers = new HashSet();
    private static Class<? extends Boss> endDragonClass;
    private final Arkkit plugin = Arkkit.get();

    public ChunkGCTask() {
        for (World w : Bukkit.getWorlds()) {
            try {
                ((CraftWorld) w).getHandle().keepSpawnInMemory = false;
                int w_view_distance = ((CraftWorld) w).getHandle().spigotConfig != null ? ((CraftWorld) w).getHandle().spigotConfig.viewDistance : block_viewdistance_raw;
                int adjusted_w_view_distance = w_view_distance * 16;
                world_viewdistances.put(w.getName(), adjusted_w_view_distance);
                ChunkGC.log.debug("Set " + w.getName() + " view_distance to " + w_view_distance + " (b" + adjusted_w_view_distance + ")!", ((Object) this).getClass());
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
        bossesEnabled = new File("plugins/Bosses.jar").exists();
    }

    public static boolean isEndDragonSpawned() {
        if (endDragonClass == null && bossesEnabled) {
            try {
                endDragonClass = (Class<? extends Boss>) Class.forName("me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bossesEnabled && !BossUtils.getAllBossesFromType(endDragonClass).isEmpty();
    }

    private static boolean canUnloadChunk(org.bukkit.Chunk chunk) {
        if (chunk != null) {
            for (Entity ent : chunk.getEntities()) {
                if (ent instanceof EnderPearl) {
                    if (ent.getTicksLived() < 600) {
                        return false;
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Arkkit.get(), ((Entity) ent)::remove);
                    Bukkit.getLogger().info("[ChunkGCTask] Removing old enderpearl and unloading chunk due to living for " + ent.getTicksLived() + " at " + ent.getLocation().toString());
                    continue;
                }
                if (!bossesEnabled || !(ent instanceof LivingEntity) || (!ent.hasMetadata("boss") || Boss.bosses.get(ent.getUniqueId()) == null) && !ent.hasMetadata("cbe_boss"))
                    continue;
                Bukkit.getLogger().info("[ChunkGCTask] Cancelling unload of chunk due to boss being inside at x" + chunk.getX() + ", z" + chunk.getZ());
                return false;
            }
        }
        return true;
    }

    public static void doChunkGC() {
        String w_name;
        chunks_to_unload.clear();
        final AtomicInteger skipped = new AtomicInteger(0);
        for (Map.Entry<String, CopyOnWriteArrayList<Long>> data : loaded_chunks.entrySet()) {
            w_name = data.getKey();
            if (w_name.equals("dungeon_yijki_a") || w_name.equals("dungeon_destroyed_outpost")) {
                loaded_chunks.remove(w_name);
                continue;
            }
            CopyOnWriteArrayList<Long> chunkList = data.getValue();
            World world = Bukkit.getWorld(w_name);
            if (world == null) {
                loaded_chunks.remove(w_name);
                continue;
            }
            WorldServer nmsWorld = ((CraftWorld) world).getHandle();
            for (Long cs : chunkList) {
                CopyOnWriteArrayList clist;
                int z;
                int x;
                int cx = LongHash.msw(cs);
                int cz = LongHash.lsw(cs);
                org.bukkit.Chunk c = ChunkGCTask.getChunkFromLong(cs, nmsWorld);
                if (c == null) continue;
                if (!ChunkGCTask.canUnloadChunk(c)) {
                    skipped.incrementAndGet();
                    continue;
                }
                Location c_loc = new Location(world == null ? Bukkit.getWorld(w_name) : world, x = cx * 16, 0.0, z = cz * 16);
                boolean nearby = ChunkAPI.isPlayerNearby(c_loc, 0);
                if (nearby || (clist = chunks_to_unload.computeIfAbsent(w_name, e -> new CopyOnWriteArrayList())).contains(cs))
                    continue;
                clist.add(cs);
            }
        }
        for (Map.Entry<String, CopyOnWriteArrayList<Long>> data : chunks_to_unload.entrySet()) {
            w_name = data.getKey();
            if (w_name.equals("dungeon_yijki_a") || w_name.equals("dungeon_destroyed_outpost")) {
                chunks_to_unload.remove(w_name);
                continue;
            }
            final World w = Arkkit.get().getServer().getWorld(w_name);
            CopyOnWriteArrayList<Long> slist = data.getValue();
            int exec_count = 0;
            int chunkInterval = Math.min(Math.max(100, slist.size() / 100), 500);
            Bukkit.getLogger().info("[ChunkGC (scanning)] Marked " + slist.size() + " chunks for unload in batches of " + chunkInterval);
            for (int x = 0; x <= slist.size(); x += chunkInterval) {
                int x_end = Math.min(x + chunkInterval, slist.size());
                final CopyOnWriteArrayList<Long> chunk_batch = new CopyOnWriteArrayList<Long>(slist.subList(x, x_end));
                final int current_exec = ++exec_count;
                active_workers.add(current_exec);
                String finalW_name = w_name;
                Arkkit.get().getServer().getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                    @Override
                    public void run() {
                        World world = Arkkit.get().getServer().getWorld(finalW_name);
                        for (Long s : chunk_batch) {
                            int cx = LongHash.msw(s);
                            int cz = LongHash.lsw(s);
                            world.unloadChunk(cx, cz, true, true);
                        }
                        ChunkGC.log.debug(finalW_name + " worker_" + current_exec + ": Unloading " + chunk_batch.size() + " chunks...", this.getClass());
                        active_workers.remove(current_exec);
                    }
                }, exec_count * 6);
            }
            String finalW_name1 = w_name;
            Arkkit.get().getServer().getScheduler().runTaskLater(Arkkit.get(), new Runnable() {

                @Override
                public void run() {
                    int csize = ((CraftWorld) w).getHandle().chunkProviderServer.unloadQueue.size();
                    ((CraftWorld) w).getHandle().chunkProviderServer.unloadQueue.clear();
                    ChunkGC.log.debug("(" + finalW_name1 + ") Cleared " + csize + " unloadQueue chunks, skipped " + skipped.get() + " chunks from unloading.", this.getClass());
                }
            }, ++exec_count * 6);
        }
    }

    private static org.bukkit.Chunk getChunkFromLong(long val, WorldServer world) {
        int z;
        int x = LongHash.msw(val);
        Chunk chunk = world.getChunkIfLoaded(x, z = LongHash.lsw(val));
        return chunk == null ? null : chunk.bukkitChunk;
    }

    public synchronized void run() {
        if (active_workers.size() > 0) {
            ChunkGC.log.notice("Skipping ChunkGCTask; " + active_workers.size() + " active_workers!", ((Object) this).getClass());
            return;
        }
        ChunkSyncUtil.syncChunks();
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new Runnable() {

            @Override
            public void run() {
                ChunkGCTask.doChunkGC();
            }
        });
    }

    private Long getLongHash(int x, int z) {
        return LongHash.toLong(x, z);
    }

    private org.bukkit.Chunk getChunkFromLong(int x, int z, World world) {
        return world.getChunkAt(x, z);
    }
}

