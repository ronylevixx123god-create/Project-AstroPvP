/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.IronGolem
 *  org.bukkit.entity.ItemFrame
 *  org.bukkit.entity.Player
 */
package com.conaxgames.factions.utils.cmds;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

public class CommandCTool
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int c;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            Bukkit.getLogger().info("/ctool <elist/eclear/igclear/bslist/igspawners/resetspookycd>");
            return true;
        }
        if (args[0].equalsIgnoreCase("elist")) {
            for (World w : Bukkit.getWorlds()) {
                for (Entity ent : w.getEntities()) {
                    Bukkit.getLogger().info(ent.getType().name() + " at " + ent.getWorld().getName() + "(" + ent.getLocation().getBlockX() + "," + ent.getLocation().getBlockY() + "," + ent.getLocation().getBlockZ() + ")");
                }
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("telist")) {
            HashMap<Material, Integer> blockStateCounts = new HashMap<Material, Integer>();
            for (World world : Bukkit.getWorlds()) {
                for (Chunk c2 : world.getLoadedChunks()) {
                    for (BlockState bs : c2.getTileEntities()) {
                        blockStateCounts.put(bs.getBlock().getType(), blockStateCounts.containsKey((Object)bs.getBlock().getType()) ? (Integer)blockStateCounts.get((Object)bs.getBlock().getType()) + 1 : 1);
                        Bukkit.getLogger().info(bs.getType().name() + " at " + bs.getWorld().getName() + "(" + bs.getLocation().getBlockX() + "," + bs.getLocation().getBlockY() + "," + bs.getLocation().getBlockZ() + ")");
                    }
                }
            }
            for (Map.Entry entry : blockStateCounts.entrySet()) {
                Bukkit.getLogger().info(entry.getKey() + ": " + entry.getValue());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("eclear")) {
            c = 0;
            for (World world : Bukkit.getWorlds()) {
                for (Entity ent : world.getEntities()) {
                    if (!(ent instanceof Entity) || ent instanceof Player || ent instanceof ItemFrame) continue;
                    Bukkit.getLogger().info("Killing " + ent.getType().name() + " at " + ent.getWorld().getName() + "(" + ent.getLocation().getBlockX() + "," + ent.getLocation().getBlockY() + "," + ent.getLocation().getBlockZ() + ")");
                    ent.remove();
                    ++c;
                }
            }
            Bukkit.getLogger().info("Killed " + c + " LivingEntities!");
        }
        if (args[0].equalsIgnoreCase("igclear")) {
            c = 0;
            for (World world : Bukkit.getWorlds()) {
                for (Entity ent : world.getEntities()) {
                    if (!(ent instanceof IronGolem)) continue;
                    Bukkit.getLogger().info("Killing " + ent.getType().name() + " at " + ent.getWorld().getName() + "(" + ent.getLocation().getBlockX() + "," + ent.getLocation().getBlockY() + "," + ent.getLocation().getBlockZ() + ")");
                    ent.remove();
                    ++c;
                }
            }
            Bukkit.getLogger().info("Killed " + c + " IronGolems!");
        }
        if (args[0].equalsIgnoreCase("igspawners")) {
            for (World w : Bukkit.getWorlds()) {
                for (Chunk c3 : w.getLoadedChunks()) {
                    for (BlockState bs : c3.getTileEntities()) {
                        CreatureSpawner mp;
                        if (bs.getType() != Material.MOB_SPAWNER || (mp = (CreatureSpawner)bs).getSpawnedType() != EntityType.IRON_GOLEM) continue;
                        Bukkit.getLogger().info("IRON_GOLEM spawner at " + bs.getWorld().getName() + "(" + bs.getLocation().getBlockX() + "," + bs.getLocation().getBlockY() + "," + bs.getLocation().getBlockZ() + ")");
                    }
                }
            }
        }
        return true;
    }
}

