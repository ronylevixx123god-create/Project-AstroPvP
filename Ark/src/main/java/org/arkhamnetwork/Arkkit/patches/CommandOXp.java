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
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.UUID;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.plugin.Plugin;

final class CommandOXp
extends AbstractCommand {
    public CommandOXp(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/oxp <player>");
            return true;
        }
        final String playerName = args[0];
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                CraftPlayer target;
                MinecraftServer minecraftserver = MinecraftServer.getServer();
                UUID playerUUID = Bukkit.getOfflinePlayer((String)playerName).getUniqueId();
                GameProfile gameprofile = new GameProfile(playerUUID, playerName);
                EntityPlayer entity = new EntityPlayer(minecraftserver, minecraftserver.getWorldServer(0), gameprofile, new PlayerInteractManager((World)minecraftserver.getWorldServer(0)));
                CraftPlayer craftPlayer = target = entity == null ? null : entity.getBukkitEntity();
                if (target != null) {
                    target.loadData();
                    sender.sendMessage(target.getName() + " has LEVEL " + target.getLevel() + " and " + target.getExp() + " XP");
                }
            }
        });
        return true;
    }
}

