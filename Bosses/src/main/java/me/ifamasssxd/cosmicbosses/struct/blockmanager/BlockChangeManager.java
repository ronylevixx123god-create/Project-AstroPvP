/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.struct.blockmanager;

import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.StoredBlock;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class BlockChangeManager {
    private ConcurrentHashMap<Location, StoredBlock> storedBlocks = new ConcurrentHashMap();
    public List<Material> validBreakables = Lists.newArrayList(Material.STONE, Material.GRASS, Material.COAL_BLOCK, Material.DIRT, Material.STAINED_CLAY, Material.ICE, Material.IRON_BLOCK, Material.DIAMOND_BLOCK, Material.LOG, Material.LEAVES, Material.COBBLESTONE, Material.SMOOTH_BRICK, Material.CACTUS, Material.SANDSTONE, Material.SAND);
    private List<BlockFace> faces = Lists.newArrayList(BlockFace.EAST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
    private List<Material> flowers = Lists.newArrayList(Material.LONG_GRASS, Material.BROWN_MUSHROOM, Material.RED_ROSE, Material.RED_MUSHROOM, Material.YELLOW_FLOWER, Material.CACTUS, Material.SUGAR_CANE_BLOCK);
    Random random = new Random();

    public BlockChangeManager() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)CosmicBosses.getInstance(), this::checkStoredBlocks, 20L, 20L);
    }

    private void checkStoredBlocks() {
        for (Map.Entry<Location, StoredBlock> entry : this.storedBlocks.entrySet()) {
            StoredBlock stored = entry.getValue();
            if (stored.getResetTime() == -1L || stored.getResetTime() > System.currentTimeMillis()) continue;
            this.restoreBlock(entry.getKey());
        }
    }

    public void restoreAllBlocks() {
        (this.storedBlocks.keySet()).forEach(this::restoreBlock);
    }

    public void restoreAllBlocks(int bossId) {
        this.storedBlocks.entrySet().stream().filter(entry -> ((StoredBlock)entry.getValue()).getBossId() == bossId).forEach(entry -> this.restoreBlock((Location)entry.getKey()));
    }

    public void createCustomExplosion(Location location, int radius, int yRadius, int bossId) {
        int currentX = location.getBlockX();
        int currentY = location.getBlockY();
        int currentZ = location.getBlockZ();
        location.getWorld().playSound(location, Sound.EXPLODE, 1.0f, 1.0f);
        int destroyed = 0;
        double r2 = radius * radius;
        long start = System.currentTimeMillis();
        HashSet<Block> blocks = new HashSet<Block>();
        for (int x = -radius; x <= radius; ++x) {
            for (int y = -yRadius; y <= yRadius; ++y) {
                for (int z = -radius; z <= radius; ++z) {
                    Block block;
                    if (destroyed > 400) {
                        System.out.println("Too many blocks.");
                        return;
                    }
                    if ((double)(x * x + y * y + z * z + x + y + z) + 0.75 > r2 || !(block = location.getWorld().getBlockAt(currentX + x, currentY + y, currentZ + z)).getType().isSolid() || !this.validBreakables.contains((Object)block.getType()) || WorldGuardUtils.getRegionName(block.getLocation()).startsWith("spawn_")) continue;
                    ++destroyed;
                    blocks.add(block);
                }
            }
        }
        block3: for (Block block : blocks) {
            for (BlockFace face : this.faces) {
                Block nextTo = block.getRelative(face);
                if (nextTo.getType() != Material.WATER && nextTo.getType() != Material.STATIONARY_WATER && nextTo.getType() != Material.LAVA && nextTo.getType() != Material.STATIONARY_LAVA && (face != BlockFace.UP || !this.flowers.contains((Object)nextTo.getType()))) continue;
                continue block3;
            }
            this.destroyBlock(block, bossId, 300, true);
        }
        System.out.println("Created explosion took " + (System.currentTimeMillis() - start) + "ms");
    }

    public void destroyBlock(Block block, int bossId, int seconds, boolean throwBlock) {
        if (block.getType() != Material.AIR) {
            StoredBlock storedBlock = new StoredBlock(bossId, block.getType(), block.getData(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
            this.storedBlocks.put(block.getLocation(), storedBlock);
            block.setTypeIdAndData(0, (byte)0, false);
            if (throwBlock && this.random.nextInt(10) > 1) {
                FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), storedBlock.getMaterial(), storedBlock.getBlockData());
                fallingBlock.setDropItem(false);
                fallingBlock.setMetadata("boss_block", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                Vector velocity = new Vector(this.random.nextBoolean() ? -this.random.nextDouble() : this.random.nextDouble(), 1.0 + this.random.nextDouble(), this.random.nextBoolean() ? -this.random.nextDouble() : this.random.nextDouble()).normalize();
                fallingBlock.setVelocity(velocity);
            }
        }
    }

    public void replaceBlock(Block block, int bossId, int seconds, Material replace, byte data) {
        StoredBlock storedBlock = new StoredBlock(bossId, block.getType(), block.getData(), seconds == -1 ? -1L : System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
        this.storedBlocks.put(block.getLocation(), storedBlock);
        block.setTypeIdAndData(replace.getId(), data, false);
    }

    public void restoreBlock(Location location, StoredBlock block) {
        location.getBlock().setTypeIdAndData(block.getMaterial().getId(), block.getBlockData(), false);
    }

    public void restoreBlock(Location location) {
        StoredBlock block = this.storedBlocks.remove((Object)location);
        if (block == null) {
            return;
        }
        location.getBlock().setTypeIdAndData(block.getMaterial().getId(), block.getBlockData(), false);
    }

    public ConcurrentHashMap<Location, StoredBlock> getStoredBlocks() {
        return this.storedBlocks;
    }
}

