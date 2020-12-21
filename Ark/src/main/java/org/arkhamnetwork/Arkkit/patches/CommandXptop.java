/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.server.v1_7_R4.PlayerInteractManager
 *  net.minecraft.server.v1_7_R4.World
 *  net.minecraft.util.com.mojang.authlib.GameProfile
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.plugin.Plugin;

final class CommandXptop
extends AbstractCommand {
    public CommandXptop(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/xptop <min level to display>");
            return true;
        }
        final int minLevel = Integer.parseInt(args[0]);
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    CraftPlayer target;
                    MinecraftServer minecraftserver = MinecraftServer.getServer();
                    GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
                    EntityPlayer entity = new EntityPlayer(minecraftserver, minecraftserver.getWorldServer(0), gameprofile, new PlayerInteractManager((World)minecraftserver.getWorldServer(0)));
                    CraftPlayer craftPlayer = target = entity == null ? null : entity.getBukkitEntity();
                    if (target != null) {
                        target.loadData();
                    }
                    if (target.getLevel() < minLevel) continue;
                    sender.sendMessage(target.getName() + " has level " + target.getLevel());
                }
                sender.sendMessage("Scanning complete, results displayed above, if there are any.");
            }
        });
        return true;
    }
}

