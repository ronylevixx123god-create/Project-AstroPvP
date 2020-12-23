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
import org.arkhamnetwork.Arkkit.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

class IPMuteCommand
        extends AbstractCommand {
    private final ExtraMute instance;

    public IPMuteCommand(ExtraMute instance, String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        long time;
        if (!(sender.isOp() || sender.hasPermission("ipmute.mute") || sender.hasPermission("staff.ipmute.mute"))) {
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Invalid Usage: /ipmute <ip/name> <length> <reason>");
            return true;
        }
        String ip = args[0];
        String playerName = null;
        if (!ip.contains(".")) {
            Player pl = Bukkit.getPlayer(ip);
            if (pl != null) {
                ip = pl.getAddress().getAddress().getHostAddress();
                playerName = pl.getName();
            } else {
                sender.sendMessage(ChatColor.RED + "No player online with that name");
                return true;
            }
        }
        String length = args[1];
        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; ++i) {
            reason.append(args[i]).append(" ");
        }
        try {
            time = length.equals("-1") ? -1L : TimeUtils.parseDateDiff(length, true);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Invalid time given!");
            return true;
        }
        this.instance.getIpsMuted().put(ip, new MuteReason(time != -1L ? time * 1000L : time, reason.toString().trim()));
        if (sender.hasPermission("ipmute.showip")) {
            sender.sendMessage(ChatColor.RED + "IP " + ip + " has been muted.");
        } else {
            sender.sendMessage(ChatColor.RED + playerName + "'s IP has been muted.");
        }
        return false;
    }
}

