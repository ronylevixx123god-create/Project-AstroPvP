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

import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAllowTp
implements CommandExecutor {
    public static HashSet<String> preston_slaves = new HashSet();
    public static HashSet<String> woofless_slaves = new HashSet();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        Player pl = (Player)sender;
        if (args.length != 1) {
            pl.sendMessage("/allowtp <playername>");
            pl.sendMessage("Toggles whether <playername> can TP directly to your base.");
            return true;
        }
        String s = args[0];
        if (pl.getName().equalsIgnoreCase("TBNRFrags")) {
            if (!preston_slaves.contains(s)) {
                preston_slaves.add(s);
                pl.sendMessage((Object)ChatColor.GREEN + s + " will now be able to teleport to your base!");
            } else {
                preston_slaves.remove(s);
                pl.sendMessage((Object)ChatColor.RED + s + " will no longer be able to teleport to your base!");
            }
        } else if (pl.getName().equalsIgnoreCase("Woofless")) {
            if (!woofless_slaves.contains(s)) {
                woofless_slaves.add(s);
                pl.sendMessage((Object)ChatColor.GREEN + s + " will now be able to teleport to your base!");
            } else {
                woofless_slaves.remove(s);
                pl.sendMessage((Object)ChatColor.RED + s + " will no longer be able to teleport to your base!");
            }
        } else {
            return true;
        }
        return true;
    }
}

