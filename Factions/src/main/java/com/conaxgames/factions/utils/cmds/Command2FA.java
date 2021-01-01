/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.conaxgames.factions.utils.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command2FA
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player pl = (Player)sender;
        pl.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "To enable Two-Factor Authentication on your CosmicPvP account return to the /hub and run the command " + (Object)ChatColor.UNDERLINE + "/2fa");
        return true;
    }
}

