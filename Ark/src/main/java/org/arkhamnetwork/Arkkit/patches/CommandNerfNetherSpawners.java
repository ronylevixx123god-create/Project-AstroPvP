/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.Chunk
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.CraftChunk
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;

final class CommandNerfNetherSpawners
extends AbstractCommand {
    public CommandNerfNetherSpawners(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        int scanned = 0;
        int removed = 0;
        if (args.length > 0 && args[0].equals("glowstone")) {
            int chunks = Integer.parseInt(args[0]);
            World w = Bukkit.getWorld((String)"world_nether");
            int GLOWSTONE = Material.GLOWSTONE.getId();
            for (int x = -chunks; x < chunks; ++x) {
                for (int z = -chunks; z < chunks; ++z) {
                    org.bukkit.Chunk c = w.getChunkAt(x, z);
                    c.load();
                    ++scanned;
                    Chunk chunk = ((CraftChunk)c).getHandle();
                    for (int chunkX = 0; chunkX < 16; ++chunkX) {
                        for (int chunkZ = 0; chunkZ < 16; ++chunkZ) {
                            for (int y = 0; y < 128; ++y) {
                                Block block = chunk.getType(chunkX, y, chunkZ);
                                if (block == null || block == Blocks.AIR || block.getId() != GLOWSTONE || !chunk.a(chunkX, y, chunkZ, block, 0)) continue;
                                ++removed;
                            }
                        }
                    }
                    c.unload(true, false);
                }
            }
            sender.sendMessage("Scan complete, removed " + removed + " / " + scanned + " glowstone in world_nether.");
            return true;
        }
        if (args.length > 0 && args[0].equals("convertnbt")) {
            World w = Bukkit.getWorld((String)args[1]);
            int chunks = Integer.parseInt(args[2]);
            for (int x = -chunks; x < chunks; ++x) {
                for (int z = -chunks; z < chunks; ++z) {
                    org.bukkit.Chunk c = w.getChunkAt(x, z);
                    c.load();
                    for (BlockState bs : c.getTileEntities()) {
                        CreatureSpawner cs;
                        if (!(bs instanceof CreatureSpawner) || SpawnerFee.isPlayerSpawner(bs.getBlock()) || (cs = (CreatureSpawner)bs).getSpawnerCount() != 4) continue;
                        ++scanned;
                        Bukkit.getLogger().info("[Arkkit (NerfNetherSpawners)] Set spawnerCount=1 at " + (Object)bs.getBlock().getLocation() + "!");
                        ((CreatureSpawner)bs).setSpawnerCount((short)1);
                    }
                    c.unload(true, false);
                }
            }
            return true;
        }
        int chunks = Integer.parseInt(args[0]);
        World w = Bukkit.getWorld((String)"world_nether");
        for (int x = -chunks; x < chunks; ++x) {
            for (int z = -chunks; z < chunks; ++z) {
                org.bukkit.Chunk c = w.getChunkAt(x, z);
                c.load();
                for (BlockState bs : c.getTileEntities()) {
                    if (!(bs instanceof CreatureSpawner) || SpawnerFee.isPlayerSpawner(bs.getBlock())) continue;
                    ++scanned;
                    if (!new Random().nextBoolean()) continue;
                    Bukkit.getLogger().info("[Arkkit (NerfNetherSpawners)] Removing nether spawner at " + (Object)bs.getBlock().getLocation() + "!");
                    bs.setType(Material.AIR);
                    ++removed;
                }
                c.unload(true, false);
            }
        }
        sender.sendMessage("Scan complete, removed " + removed + " / " + scanned + " spawners in world_nether.");
        return true;
    }
}

