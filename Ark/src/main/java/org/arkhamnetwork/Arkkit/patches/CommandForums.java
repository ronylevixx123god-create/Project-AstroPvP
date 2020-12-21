/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class CommandForums
extends AbstractCommand {
    public CommandForums(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "OFFICIAL FORUMS");
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.UNDERLINE.toString() + "http://forum.treasurewars.net/");
        sender.sendMessage("");
        return true;
    }
}

