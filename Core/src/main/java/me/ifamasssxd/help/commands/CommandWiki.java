/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package me.ifamasssxd.help.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandWiki
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "OFFICIAL WIKI");
        sender.sendMessage(ChatColor.WHITE.toString() + ChatColor.UNDERLINE.toString() + "https://discord.astropvp.net");
        sender.sendMessage((Object)ChatColor.GRAY + "Click the link above to visit the AstroPvP Wiki!");
        sender.sendMessage("");
        return false;
    }
}

