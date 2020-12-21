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
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

final class CommandOEnderchest
extends AbstractCommand {
    public CommandOEnderchest(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        CraftPlayer target;
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/oenderchest <player>");
            return true;
        }
        Player p = (Player)sender;
        String playerName = args[0];
        if (Bukkit.getPlayer((String)playerName) != null) {
            sender.sendMessage(playerName + " is online locally, please use /enderchest instead.");
            return true;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer((String)playerName);
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(player.getUniqueId(), player.getName());
        EntityPlayer entity = new EntityPlayer(minecraftserver, minecraftserver.getWorldServer(0), gameprofile, new PlayerInteractManager((World)minecraftserver.getWorldServer(0)));
        CraftPlayer craftPlayer = target = entity == null ? null : entity.getBukkitEntity();
        if (target != null) {
            target.loadData();
        }
        p.openInventory(target.getEnderChest());
        return true;
    }
}

