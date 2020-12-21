/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public abstract class ChunkGenerator {
    @Deprecated
    public byte[] generate(World world, Random random, int x, int z) {
        throw new UnsupportedOperationException("Custom generator is missing required methods: generate(), generateBlockSections() and generateExtBlockSections()");
    }

    @Deprecated
    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return null;
    }

    @Deprecated
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return null;
    }

    public boolean canSpawn(World world, int x, int z) {
        Block highest = world.getBlockAt(x, world.getHighestBlockYAt(x, z), z);
        switch (world.getEnvironment()) {
            case NETHER: {
                return true;
            }
            case THE_END: {
                return highest.getType() != Material.AIR && highest.getType() != Material.WATER && highest.getType() != Material.LAVA;
            }
        }
        return highest.getType() == Material.SAND || highest.getType() == Material.GRAVEL;
    }

    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>();
    }

    public Location getFixedSpawnLocation(World world, Random random) {
        return null;
    }

    public static interface BiomeGrid {
        public Biome getBiome(int var1, int var2);

        public void setBiome(int var1, int var2, Biome var3);
    }
}

