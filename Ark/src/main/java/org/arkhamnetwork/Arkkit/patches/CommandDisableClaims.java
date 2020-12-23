/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

final class CommandDisableClaims
        extends AbstractCommand {
    public CommandDisableClaims(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/disableclaims <on/off>");
            sender.sendMessage("ON = DISABLE    OFF = ENABLE");
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            DisableClaimsListener.noClaim = true;
            sender.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "All /f claiming is now disabled. Use /cwarzone <radius> to claim warzone areas.");
        } else if (args[0].equalsIgnoreCase("off")) {
            DisableClaimsListener.noClaim = false;
            sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "All /f claiming is now re-enabled.");
        } else {
            sender.sendMessage("/disableclaims <on/off>");
            sender.sendMessage("ON = DISABLE    OFF = ENABLE");
            return true;
        }
        return true;
    }
}

