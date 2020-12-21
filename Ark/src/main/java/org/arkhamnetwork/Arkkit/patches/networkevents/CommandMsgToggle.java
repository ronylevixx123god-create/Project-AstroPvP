/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEventsListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandMsgToggle
extends AbstractCommand {
    public CommandMsgToggle(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        if (NetworkEventsListener.msgToggled.contains(player.getUniqueId())) {
            NetworkEventsListener.msgToggled.remove(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GREEN + "Private Messages have been " + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + "enabled" + (Object)ChatColor.GREEN + "!");
        } else {
            NetworkEventsListener.msgToggled.add(player.getUniqueId());
            player.sendMessage(ChatColor.RED.toString() + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Private Messages have been " + (Object)ChatColor.RED + ChatColor.UNDERLINE.toString() + "disabled" + (Object)ChatColor.RED + "!");
            player.sendMessage((Object)ChatColor.GRAY + "You will no longer receive messages from other players.");
        }
        return false;
    }
}

