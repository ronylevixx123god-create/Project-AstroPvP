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
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.List;
import java.util.Set;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.IgnoreUtils;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandFind
extends AbstractCommand {
    public CommandFind(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage((Object)ChatColor.YELLOW + "Usage " + (Object)ChatColor.WHITE + "/" + label + " [player/IP]");
            return true;
        }
        String target = args[0];
        if (IgnoreUtils.isIgnored((Player)sender, target)) {
            sender.sendMessage((Object)ChatColor.RED + target + " is currently on your ignore list.");
            sender.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.RED + "/unblock " + target + (Object)ChatColor.GRAY + " to enable finding them.");
            return true;
        }
        if (Bukkit.getPlayer((String)target) != null && !NetworkEvents.getServerSync().isPlayerHidden(Bukkit.getPlayer((String)target))) {
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + target + (Object)ChatColor.WHITE + " is in " + (Object)ChatColor.UNDERLINE + "YOUR" + (Object)ChatColor.WHITE + " game, " + (Object)ChatColor.AQUA + NetworkEvents.getUsername());
            sender.sendMessage(ChatColor.WHITE.toString() + (Object)ChatColor.ITALIC + "  Use " + (Object)ChatColor.YELLOW + "/msg " + target + ChatColor.WHITE.toString() + ChatColor.ITALIC.toString() + " to chat with them!");
            return true;
        }
        if (target.contains(".") && sender.isOp()) {
            sender.sendMessage((Object)ChatColor.YELLOW + "Searching the network for players matching " + (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + target);
            boolean wildCard = target.contains("*");
            for (Player p : Bukkit.getOnlinePlayers()) {
                String address = p.getAddress().getAddress().getHostAddress();
                if ((!wildCard || !address.startsWith(target.replace("*", ""))) && !address.equals(target)) continue;
                sender.sendMessage((Object)ChatColor.YELLOW + p.getName() + (Object)ChatColor.GRAY + " (local) " + (Object)ChatColor.YELLOW + " has IP " + address + "!");
            }
            Set<NetworkPlayer> foundMatching = NetworkEvents.getServerSync().getMatchingPlayersFromIP(target);
            if (foundMatching.isEmpty()) {
                sender.sendMessage((Object)ChatColor.RED + "No players found on the network with that IP.");
                return true;
            }
            foundMatching.forEach(pl -> sender.sendMessage((Object)ChatColor.YELLOW + " " + pl.getName() + " - " + (Object)ChatColor.GREEN + pl.getServerCurrentlyOn() + (Object)ChatColor.YELLOW + " - " + pl.getIp()));
            return true;
        }
        String target_server = NetworkEvents.getServerSync().lookupPlayer(target);
        if (target_server == null) {
            sender.sendMessage((Object)ChatColor.WHITE + "No online player found for '" + (Object)ChatColor.RED + target + (Object)ChatColor.WHITE + "' search query.");
            return true;
        }
        sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + target + (Object)ChatColor.WHITE + " is on the server, playing on " + (Object)ChatColor.AQUA + target_server + "!");
        sender.sendMessage(ChatColor.WHITE.toString() + (Object)ChatColor.ITALIC + "  Use " + (Object)ChatColor.YELLOW + "/server " + target_server + ChatColor.WHITE.toString() + ChatColor.ITALIC.toString() + " to join them!");
        return true;
    }
}

