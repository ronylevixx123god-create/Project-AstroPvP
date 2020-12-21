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

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandHide
extends AbstractCommand {
    public CommandHide(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage((Object)ChatColor.YELLOW + "Usage " + (Object)ChatColor.WHITE + "/" + label);
            return true;
        }
        if (!sender.hasPermission("arkkit.staff.hidden")) {
            return false;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        if (NetworkEvents.getServerSync().toggleHidden(player)) {
            player.sendMessage((Object)ChatColor.YELLOW + "You are now hidden from /find lookups.");
        } else {
            player.sendMessage((Object)ChatColor.YELLOW + "You are no longer hidden from /find lookups.");
        }
        return true;
    }
}

