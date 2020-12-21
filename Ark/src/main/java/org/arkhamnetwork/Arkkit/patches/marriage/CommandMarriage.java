/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.marriage;

import java.util.List;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.marriage.Marriage;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageCouple;
import org.arkhamnetwork.Arkkit.patches.marriage.MarriageDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandMarriage
extends AbstractCommand {
    public CommandMarriage(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Marriage commands are only usable as a player.");
            return true;
        }
        if (args.length > 1 && args[0].equalsIgnoreCase("request")) {
            if (Bukkit.getPlayer((String)args[1]) == null) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " request to marry someone that is offline.");
                return true;
            }
            if (MarriageDataManager.getPartnerUUID(((Player)sender).getUniqueId()) != null) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry someone if you are already married.");
                return true;
            }
            if (MarriageDataManager.getPartnerUUID(Bukkit.getPlayer((String)args[1]).getUniqueId()) != null) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.GRAY + " marry that player. He/she is already married.");
                return true;
            }
            if (!MarriageDataManager.requestsEnabled(Bukkit.getPlayer((String)args[1]).getUniqueId())) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "That player currently does not wish to be married.");
                return true;
            }
            Marriage.pending_requests.put(((Player)sender).getUniqueId(), Bukkit.getPlayer((String)args[1]).getUniqueId());
            sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You have successfully " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + "sent" + (Object)ChatColor.GRAY + " that player a marriage request.");
            Bukkit.getPlayer((String)args[1]).sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + ((Player)sender).getName() + " has requested to marry you! Use       " + (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + "/marry accept " + ((Player)sender).getName() + (Object)ChatColor.GRAY + " to accept, or use                    " + (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + "/marry decline " + ((Player)sender).getName() + (Object)ChatColor.GRAY + " to decline. You may also use     " + (Object)ChatColor.YELLOW + (Object)ChatColor.BOLD + "/marry togglerequests" + (Object)ChatColor.GRAY + " to toggle future requests.");
            return true;
        }
        if (args.length > 1 && args[0].equalsIgnoreCase("accept")) {
            if (!Marriage.pending_requests.containsKey(Bukkit.getPlayer((String)args[1]).getUniqueId()) || Marriage.pending_requests.get(Bukkit.getPlayer((String)args[1]).getUniqueId()) != ((Player)sender).getUniqueId()) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You do " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "not" + (Object)ChatColor.GRAY + " have a pending marriage request from that player at this time.");
                return true;
            }
            new MarriageCouple(Bukkit.getPlayer((String)args[1]).getUniqueId(), ((Player)sender).getUniqueId());
            return true;
        }
        if (args.length > 1 && args[0].equalsIgnoreCase("decline")) {
            if (!Marriage.pending_requests.containsKey(Bukkit.getPlayer((String)args[1]).getUniqueId()) || Marriage.pending_requests.get(Bukkit.getPlayer((String)args[1]).getUniqueId()) != ((Player)sender).getUniqueId()) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You do " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "not" + (Object)ChatColor.GRAY + " have a pending marriage request from that player at this time.");
                return true;
            }
            Marriage.pending_requests.remove(Bukkit.getPlayer((String)args[1]).getUniqueId());
            sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You have successfully " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "declined" + (Object)ChatColor.GRAY + " that player's marriage request.");
            Bukkit.getPlayer((String)args[1]).sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "Your marriage request has been " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "declined" + (Object)ChatColor.GRAY + ".");
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("togglerequests")) {
            boolean toggled = MarriageDataManager.toggleRequests(((Player)sender).getUniqueId());
            String tf = (Object)ChatColor.RED + "" + (Object)ChatColor.UNDERLINE + "disabled" + (Object)ChatColor.GRAY;
            if (toggled) {
                tf = (Object)ChatColor.GREEN + "" + (Object)ChatColor.UNDERLINE + "enabled" + (Object)ChatColor.GRAY;
            }
            sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You have successfully " + tf + " your marriage requests.");
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("divorce")) {
            if (MarriageDataManager.getPartnerUUID(((Player)sender).getUniqueId()) == null) {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "must" + (Object)ChatColor.GRAY + " be married in order to divorce.");
                return true;
            }
            if (Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(((Player)sender).getUniqueId())) != null) {
                Bukkit.getServer().broadcastMessage(Marriage.prefix + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + ((Player)sender).getName() + (Object)ChatColor.GRAY + " and " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + Bukkit.getPlayer((UUID)MarriageDataManager.getPartnerUUID(((Player)sender).getUniqueId())).getName() + (Object)ChatColor.GRAY + " have divorced.");
            } else {
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "You have successfully " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "divorced" + (Object)ChatColor.GRAY + " with your partner.");
            }
            final UUID uuid = ((Player)sender).getUniqueId();
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

                @Override
                public void run() {
                    MarriageDataManager.deleteMarriage(uuid);
                }
            });
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("chat")) {
            if (Marriage.marriage_chatters.contains(((Player)sender).getUniqueId())) {
                Marriage.marriage_chatters.remove(((Player)sender).getUniqueId());
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "Your marriage chat is now " + (Object)ChatColor.RED + (Object)ChatColor.UNDERLINE + "disabled" + (Object)ChatColor.GRAY + ".");
            } else {
                Marriage.marriage_chatters.add(((Player)sender).getUniqueId());
                sender.sendMessage(Marriage.prefix + (Object)ChatColor.GRAY + "Your marriage chat is now " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + "enabled" + (Object)ChatColor.GRAY + ".");
            }
            return true;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)("                    &b-&e=&b-&e=&b-&e=&b- " + Marriage.prefix + " &b-&e=&b-&e=&b-&e=&b-")));
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry request <player>" + (Object)ChatColor.GRAY + " - Request to marry a player.");
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry accept <player>" + (Object)ChatColor.GRAY + " - Accept a marriage request.");
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry decline <player>" + (Object)ChatColor.GRAY + " - Decline a marriage request.");
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry togglerequests" + (Object)ChatColor.GRAY + " - Toggle marriage requests.");
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry divorce" + (Object)ChatColor.GRAY + " - Divorce with your partner.");
        sender.sendMessage((Object)ChatColor.YELLOW + "/marry chat" + (Object)ChatColor.GRAY + " - Toggle marriage chat mode.");
        return true;
    }
}

