/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 */
package com.cosmicpvp.CosmicSponges.libs;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class AbstractSpongeBlock {
    private final int chunkX;
    private final int chunkZ;
    private final int blockX;
    private final int blockY;
    private final int blockZ;
    private final String worldName;
    private final SpongeChunk spongeChunk;

    public AbstractSpongeBlock(Block b, SpongeChunk sc) {
        this.worldName = b.getWorld().getName();
        this.chunkX = b.getChunk().getX();
        this.chunkZ = b.getChunk().getZ();
        this.blockX = b.getX();
        this.blockY = b.getY();
        this.blockZ = b.getZ();
        this.spongeChunk = sc;
        this.addEffectedCoords();
    }

    public AbstractSpongeBlock(String worldName, int blockX, int blockY, int blockZ, SpongeChunk sc) {
        this.worldName = worldName;
        this.chunkX = blockX / 16;
        this.chunkZ = blockZ / 16;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.spongeChunk = sc;
        this.addEffectedCoords();
    }

    public Block getBlock() {
        return Bukkit.getWorld(this.worldName).getBlockAt(this.getBlockX(), this.getBlockY(), this.getBlockZ());
    }

    public void addEffectedCoords() {
        for (int fx = -3; fx <= 3; ++fx) {
            for (int fy = -3; fy <= 3; ++fy) {
                for (int fz = -3; fz <= 3; ++fz) {
                    if (fx == 0 && fy == 0 && fz == 0) continue;
                    String s = this.getBlockX() + fx + "," + Math.max(0, this.getBlockY() + fy) + "," + (this.getBlockZ() + fz);
                    SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(Bukkit.getWorld(this.worldName).getChunkAt(new Location(Bukkit.getWorld(this.worldName), this.getBlockX() + fx, Math.max(0, this.getBlockY() + fy), this.getBlockZ() + fz)));
                    sc.getNoFlow().add(s);
                }
            }
        }
    }

    public void removeEffectedCoords() {
        for (int fx = -3; fx <= 3; ++fx) {
            for (int fy = -3; fy <= 3; ++fy) {
                for (int fz = -3; fz <= 3; ++fz) {
                    if (fx == 0 && fy == 0 && fz == 0) continue;
                    String s = this.getBlockX() + fx + "," + Math.max(0, this.getBlockY() + fy) + "," + (this.getBlockZ() + fz);
                    SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(Bukkit.getWorld(this.worldName).getChunkAt(new Location(Bukkit.getWorld(this.worldName), this.getBlockX() + fx, Math.max(0, this.getBlockY() + fy), this.getBlockZ() + fz)));
                    sc.getNoFlow().remove(s);
                }
            }
        }
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public int getBlockX() {
        return this.blockX;
    }

    public int getBlockY() {
        return this.blockY;
    }

    public int getBlockZ() {
        return this.blockZ;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public SpongeChunk getSpongeChunk() {
        return this.spongeChunk;
    }
}

