/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.io.netty.util.ResourceLeakDetector
 *  net.minecraft.util.io.netty.util.ResourceLeakDetector$Level
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.CreatureSpawner
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.io.netty.util.ResourceLeakDetector;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class CommandPrintMeta
extends AbstractCommand {
    public CommandPrintMeta(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Object cs;
        if (!sender.isOp()) {
            return true;
        }
        if (args[0].equalsIgnoreCase("tile")) {
            Player pSender = (Player)sender;
            Block b = pSender.getTargetBlock(null, 5);
            if (b.getState() instanceof CreatureSpawner) {
                CreatureSpawner cs2 = (CreatureSpawner)b.getState();
                pSender.sendMessage("spawnerCount: " + cs2.getSpawnerCount());
                if (args.length > 1) {
                    cs2.setSpawnerCount(Short.parseShort(args[1]));
                }
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("netty")) {
            if (args[1].equalsIgnoreCase("on")) {
                ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)ResourceLeakDetector.Level.ADVANCED);
                sender.sendMessage("resourceLeakDetection => ADVANCED");
            } else {
                ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)ResourceLeakDetector.Level.DISABLED);
                sender.sendMessage("resourceLeakDetection => DISABLED");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("all")) {
            CraftServer cs3 = (CraftServer)Bukkit.getServer();
            Bukkit.getLogger().info("[PrintMeta] entityMetadata: " + cs3.entityMetadata.metadataMap.size());
            Bukkit.getLogger().info("[PrintMeta] playerMetadata: " + cs3.playerMetadata.metadataMap.size());
            Bukkit.getLogger().info("[PrintMeta] worldMetadata: " + cs3.worldMetadata.metadataMap.size());
            for (World w : Bukkit.getWorlds()) {
                CraftWorld cw = (CraftWorld)w;
                Bukkit.getLogger().info("[PrintMeta] world:" + cw.getName() + " blockMetadata: " + cw.blockMetadata.metadataMap.size());
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("entity")) {
            cs = (CraftServer)Bukkit.getServer();
            for (Map.Entry data : cs.entityMetadata.metadataMap.entrySet()) {
                Bukkit.getLogger().info("[PrintMeta (entityMetadata)] " + (String)data.getKey() + " -> " + ((HashMap)data.getValue()).toString());
            }
        }
        if (args[0].equalsIgnoreCase("player")) {
            cs = (CraftServer)Bukkit.getServer();
            for (Map.Entry data : cs.playerMetadata.metadataMap.entrySet()) {
                Bukkit.getLogger().info("[PrintMeta (playerMetadata)] " + (String)data.getKey() + " -> " + ((HashMap)data.getValue()).toString());
            }
        }
        if (args[0].equalsIgnoreCase("world")) {
            cs = (CraftServer)Bukkit.getServer();
            for (Map.Entry data : cs.worldMetadata.metadataMap.entrySet()) {
                Bukkit.getLogger().info("[PrintMeta (worldMetadata)] " + (String)data.getKey() + " -> " + ((HashMap)data.getValue()).toString());
            }
        }
        if (args[0].equalsIgnoreCase("block")) {
            for (World w : Bukkit.getWorlds()) {
                CraftWorld cw = (CraftWorld)w;
                for (Map.Entry data : cw.blockMetadata.metadataMap.entrySet()) {
                    Bukkit.getLogger().info("[PrintMeta (blockMetadata:" + cw.getName() + "))] " + (String)data.getKey() + " -> " + ((HashMap)data.getValue()).toString());
                }
            }
        }
        if (args[0].equalsIgnoreCase("hand")) {
            Player pSender = (Player)sender;
            ItemStack i = pSender.getItemInHand();
            pSender.sendMessage(i.toString());
            Bukkit.getLogger().info(pSender.getName() + " /printmeta hand: " + i.toString());
        }
        return true;
    }
}

