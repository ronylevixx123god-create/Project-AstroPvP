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

import java.util.List;

class IPUnMuteCommand
        extends AbstractCommand {
    private final ExtraMute instance;

    public IPUnMuteCommand(ExtraMute instance, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.isOp() || sender.hasPermission("ipmute.unmute") || sender.hasPermission("staff.ipmute.unmute"))) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid Usage: /ipunmute <ip/name>");
            return true;
        }
        String ip = args[0];
        if (!ip.contains(".")) {
            Player pl = Bukkit.getPlayer(ip);
            if (pl != null) {
                ip = pl.getAddress().getAddress().getHostAddress();
            } else {
                sender.sendMessage(ChatColor.RED + "No player online with that name");
                return true;
            }
        }
        if (!this.instance.getIpsMuted().containsKey(ip)) {
            sender.sendMessage(ChatColor.RED + "That IP is not currently muted!");
            return true;
        }
        this.instance.getIpsMuted().remove(ip);
        sender.sendMessage(ChatColor.RED + "IP " + ip + " has been unmuted.");
        return false;
    }
}

