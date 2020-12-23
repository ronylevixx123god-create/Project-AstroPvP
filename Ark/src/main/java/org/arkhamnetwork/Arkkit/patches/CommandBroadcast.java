/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandBroadcast
        extends AbstractCommand {
    public CommandBroadcast(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (sender instanceof Player && !sender.isOp()) {
                return true;
            }
            String msg = "";
            for (String s : args) {
                msg = msg + s + " ";
            }
            if (msg.endsWith(" ")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            Bukkit.broadcastMessage(CustomBroadcast.broadcast_prefix + " " + ChatColor.translateAlternateColorCodes('&', msg));
        }
        return true;
    }
}

