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

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ExtraMute;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class IPUnMuteCommand
extends AbstractCommand {
    private ExtraMute instance;

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
            sender.sendMessage((Object)ChatColor.RED + "Invalid Usage: /ipunmute <ip/name>");
            return true;
        }
        String ip = args[0];
        if (!ip.contains(".")) {
            Player pl = Bukkit.getPlayer((String)ip);
            if (pl != null) {
                ip = pl.getAddress().getAddress().getHostAddress();
            } else {
                sender.sendMessage((Object)ChatColor.RED + "No player online with that name");
                return true;
            }
        }
        if (!this.instance.getIpsMuted().containsKey(ip)) {
            sender.sendMessage((Object)ChatColor.RED + "That IP is not currently muted!");
            return true;
        }
        this.instance.getIpsMuted().remove(ip);
        sender.sendMessage((Object)ChatColor.RED + "IP " + ip + " has been unmuted.");
        return false;
    }
}

