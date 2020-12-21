/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketListener
 *  net.minecraft.server.v1_7_R4.AsyncPacketSenderWorker
 *  net.minecraft.server.v1_7_R4.EntityTrackerEntry
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.server.v1_7_R4.PlayerPacketList
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.spigotmc.WatchdogThread
 */
package org.arkhamnetwork.Arkkit.patches;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_7_R4.AsyncPacketSenderWorker;
import net.minecraft.server.v1_7_R4.EntityTrackerEntry;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PlayerPacketList;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.spigotmc.WatchdogThread;

final class CommandForceGC
extends AbstractCommand {
    public CommandForceGC(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        block10: {
            block13: {
                block12: {
                    block11: {
                        block9: {
                            if (!sender.isOp()) {
                                return true;
                            }
                            if (args.length != 0) break block9;
                            sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "RUNNING SYSTEM.GC() !");
                            Bukkit.getLogger().info("FREE BYTES: " + new File("/minecraft").getUsableSpace());
                            System.gc();
                            break block10;
                        }
                        if (args.length != 1) break block11;
                        if (args[0].equalsIgnoreCase("dump")) {
                            WatchdogThread.dumpMainThread();
                        } else {
                            HashMap<String, Integer> totalPacketCounts = new HashMap<String, Integer>();
                            for (Map.Entry packetData : AsyncPacketSenderWorker.pendingPackets.entrySet()) {
                                for (Map.Entry playerPacketCounts : ((PlayerPacketList)packetData.getValue()).packetCounts.entrySet()) {
                                    String packetName;
                                    totalPacketCounts.put(packetName, totalPacketCounts.containsKey(packetName = (String)playerPacketCounts.getKey()) ? Integer.valueOf((Integer)totalPacketCounts.get(packetName) + (Integer)playerPacketCounts.getValue()) : (Integer)playerPacketCounts.getValue());
                                }
                            }
                            for (Map.Entry data : totalPacketCounts.entrySet()) {
                                sender.sendMessage((String)data.getKey() + " => " + data.getValue());
                            }
                        }
                        break block10;
                    }
                    if (args.length != 2) break block12;
                    for (Map.Entry packetData : AsyncPacketSenderWorker.pendingPackets.entrySet()) {
                        ((PlayerPacketList)packetData.getValue()).printPacketCounts();
                    }
                    break block10;
                }
                if (args.length != 3) break block13;
                int x = Integer.parseInt(args[0]);
                Bukkit.getLogger().info("[ForceGC] entityTrackerSet: size=" + MinecraftServer.getServer().getWorldServer((int)x).tracker.entityTrackerSet.size());
                Bukkit.getLogger().info("[ForceGC] playerTrackerSet: size=" + MinecraftServer.getServer().getWorldServer((int)x).tracker.playerTrackerSet.size());
                for (EntityTrackerEntry ete : MinecraftServer.getServer().getWorldServer((int)x).tracker.entityTrackerSet) {
                    Bukkit.getLogger().info("[entityTrackerSet] trackedPlayers=" + ete.trackedPlayers.size() + " for " + (Object)ete.tracker + " = " + ete.toString());
                }
                break block10;
            }
            if (args.length != 4) break block10;
            for (PacketListener listener : ProtocolLibrary.getProtocolManager().getPacketListeners()) {
                Bukkit.getLogger().info(listener.getPlugin().getName() + " = recieving:" + listener.getReceivingWhitelist().getTypes() + ", sending:" + listener.getSendingWhitelist().getTypes());
            }
        }
        return true;
    }
}

