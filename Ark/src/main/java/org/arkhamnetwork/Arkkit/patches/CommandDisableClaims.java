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
import org.arkhamnetwork.Arkkit.patches.DisableClaimsListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
            sender.sendMessage((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + "All /f claiming is now disabled. Use /cwarzone <radius> to claim warzone areas.");
        } else if (args[0].equalsIgnoreCase("off")) {
            DisableClaimsListener.noClaim = false;
            sender.sendMessage((Object)ChatColor.DARK_GREEN + "" + (Object)ChatColor.BOLD + "All /f claiming is now re-enabled.");
        } else {
            sender.sendMessage("/disableclaims <on/off>");
            sender.sendMessage("ON = DISABLE    OFF = ENABLE");
            return true;
        }
        return true;
    }
}

