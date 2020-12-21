/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import java.util.Collection;
import java.util.Map;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkEvents;
import org.arkhamnetwork.Arkkit.patches.networkevents.NetworkPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandStaff
extends AbstractCommand {
    public CommandStaff(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Searching for online staff members...");
        boolean isUsingUltraBypass = sender.isOp() && args.length == 1 && (args[0].equalsIgnoreCase("-all") || args[0].equalsIgnoreCase("-u"));
        for (String server : NetworkEvents.getServerSync().getServers()) {
            try {
                Map<String, NetworkPlayer> networkPlayers;
                if (NetworkEvents.getServerSync().getStaffMembers(server).size() > 0) {
                    sender.sendMessage(ChatColor.YELLOW.toString() + server + ": " + this.toReadableString(NetworkEvents.getServerSync().getStaffMembers(server), ChatColor.GREEN));
                }
                if (!isUsingUltraBypass || (networkPlayers = NetworkEvents.getServerSync().getUltraHiddenStaff().get(server)) == null || networkPlayers.size() <= 0) continue;
                networkPlayers.keySet().forEach(username -> sender.sendMessage(ChatColor.YELLOW.toString() + server + ": " + this.toReadableString(networkPlayers.values(), ChatColor.RED)));
            }
            catch (NullPointerException npe) {}
        }
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "There are currently " + (Object)ChatColor.BOLD + NetworkEvents.getServerSync().getAllStaffMembers().size() + (Object)ChatColor.YELLOW + " staff members online!");
        sender.sendMessage("");
        return true;
    }

    private String toReadableString(Collection<NetworkPlayer> list, ChatColor color) {
        String s = "";
        for (NetworkPlayer val : list) {
            s = s + (Object)color + val.getName() + (Object)ChatColor.WHITE + ", ";
        }
        return s.substring(0, s.lastIndexOf(","));
    }
}

