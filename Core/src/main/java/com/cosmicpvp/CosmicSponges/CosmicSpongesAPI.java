/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 */
package com.cosmicpvp.CosmicSponges;

import com.cosmicpvp.CosmicSponges.libs.AbstractSpongeBlock;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import java.util.HashMap;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class CosmicSpongesAPI {
    private static HashMap<String, SpongeChunk> cachedSpongeChunks;
    public static boolean debug;
    public static int radius;

    public static SpongeChunk cacheSpongeChunk(Chunk c) {
        String chunkKey = c.getX() + "," + c.getZ();
        SpongeChunk found = cachedSpongeChunks.get(chunkKey);
        if (found != null) {
            return found;
        }
        SpongeChunk sc = new SpongeChunk(c);
        cachedSpongeChunks.put(chunkKey, sc);
        try {
            sc.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sc;
    }

    public static void saveSpongeChunks() {
        for (SpongeChunk sc : CosmicSpongesAPI.getCachedSpongeChunks().values()) {
            if (sc.getSponges().size() <= 0 && !sc.isForceSave()) continue;
            sc.save();
        }
    }

    public static void handleSpongeCreate(org.bukkit.block.Block block) {
        World world = block.getWorld();
        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();
        SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(block.getChunk());
        sc.getSponges().add(new AbstractSpongeBlock(block, sc));
        for (int fx = -radius; fx <= radius; ++fx) {
            for (int fy = -radius; fy <= radius; ++fy) {
                for (int fz = -radius; fz <= radius; ++fz) {
                    org.bukkit.block.Block b = world.getBlockAt(bx + fx, Math.max(0, by + fy), bz + fz);
                    if (b.getType() != Material.LAVA && b.getType() != Material.STATIONARY_LAVA && b.getType() != Material.STATIONARY_WATER && b.getType() != Material.WATER) continue;
                    b.setType(Material.AIR);
                }
            }
        }
    }

    public static void handleSpongeDestroy(org.bukkit.block.Block block) {
        World world = block.getWorld();
        WorldServer netWorld = ((CraftWorld)block.getWorld()).getHandle();
        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();
        SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(block.getChunk());
        for (AbstractSpongeBlock a : sc.getSponges()) {
            if (a.getBlockX() != bx || a.getBlockY() != by || a.getBlockZ() != bz) continue;
            a.removeEffectedCoords();
            sc.getSponges().remove((Object)a);
            sc.setForceSave(true);
        }
        for (int fx = -radius; fx <= radius; ++fx) {
            for (int fy = -radius; fy <= radius; ++fy) {
                for (int fz = -radius; fz <= radius; ++fz) {
                    org.bukkit.block.Block b = world.getBlockAt(bx + fx, Math.max(0, by + fy), bz + fz);
                    if (b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_LAVA) {
                        netWorld.applyPhysics(new BlockPosition(b.getX(), b.getY(), b.getZ()), (Block)Blocks.LAVA);
                    }
                    if (b.getType() != Material.WATER && b.getType() != Material.STATIONARY_WATER) continue;
                    netWorld.applyPhysics(new BlockPosition(b.getX(), b.getY(), b.getZ()), (Block)Blocks.WATER);
                }
            }
        }
    }

    public static void setCachedSpongeChunks(HashMap<String, SpongeChunk> cachedSpongeChunks) {
        CosmicSpongesAPI.cachedSpongeChunks = cachedSpongeChunks;
    }

    public static HashMap<String, SpongeChunk> getCachedSpongeChunks() {
        return cachedSpongeChunks;
    }

    static {
        debug = true;
        radius = 3;
    }
}

