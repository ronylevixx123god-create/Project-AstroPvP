/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.chat_filter;

import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatFilter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandFilter
extends AbstractCommand {
    public CommandFilter(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player;
        Player player2 = player = sender instanceof Player ? (Player)sender : null;
        if (player != null && !player.isOp()) {
            player.sendMessage((Object)ChatColor.RED + "You do not have permission to do that.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage((Object)ChatColor.RED + "Usage: /filter <add|addp|removep|remove|list>");
            return true;
        }
        boolean addPerm = false;
        if (args[0].equalsIgnoreCase("add") || (addPerm = args[0].equalsIgnoreCase("addp"))) {
            String word = "";
            for (int i = 1; i < args.length; ++i) {
                word = word + args[i].toLowerCase() + " ";
            }
            word.trim();
            if (word.endsWith(" ")) {
                word = word.substring(0, word.length() - 1);
            }
            if (addPerm && ChatFilter.permanent_bad_words.contains(word)) {
                sender.sendMessage((Object)ChatColor.RED + "The word, '" + word + "' is already permantently blocked.");
                return true;
            }
            if (!addPerm && ChatFilter.bad_words.contains(word)) {
                sender.sendMessage((Object)ChatColor.RED + "The word, '" + word + "' is already blocked.");
                return true;
            }
            if (addPerm) {
                ChatFilter.permanent_bad_words.add(word);
                Arkkit.get().getConfig().set("settings.chat_filter.permanent_bad_words", ChatFilter.permanent_bad_words);
                sender.sendMessage((Object)ChatColor.YELLOW + "The word, '" + (Object)ChatColor.WHITE + word + (Object)ChatColor.YELLOW + "' is now blocked PERMANENTLY.");
            } else {
                ChatFilter.bad_words.add(word);
                Arkkit.get().getConfig().set("settings.chat_filter.bad_words", ChatFilter.bad_words);
                sender.sendMessage((Object)ChatColor.YELLOW + "The word, '" + (Object)ChatColor.WHITE + word + (Object)ChatColor.YELLOW + "' is now blocked.");
            }
            return true;
        }
        boolean removePerm = false;
        if (args[0].equalsIgnoreCase("remove") || (removePerm = args[0].equalsIgnoreCase("removep"))) {
            String word = "";
            for (int i = 1; i < args.length; ++i) {
                word = word + args[i].toLowerCase() + " ";
            }
            word.trim();
            if (word.endsWith(" ")) {
                word = word.substring(0, word.length() - 1);
            }
            if (removePerm && !ChatFilter.permanent_bad_words.contains(word)) {
                sender.sendMessage((Object)ChatColor.RED + "The word, '" + word + "' is not blocked permanently!");
                return true;
            }
            if (!removePerm && !ChatFilter.bad_words.contains(word)) {
                sender.sendMessage((Object)ChatColor.RED + "The word, '" + word + "' is not blocked.");
                return true;
            }
            if (removePerm) {
                ChatFilter.permanent_bad_words.remove(word);
                Arkkit.get().getConfig().set("settings.chat_filter.permanent_bad_words", ChatFilter.permanent_bad_words);
                sender.sendMessage((Object)ChatColor.GREEN + "The word, '" + (Object)ChatColor.WHITE + word + (Object)ChatColor.GREEN + "' is no longer permanently blocked!.");
            } else {
                ChatFilter.bad_words.remove(word);
                Arkkit.get().getConfig().set("settings.chat_filter.bad_words", ChatFilter.bad_words);
                sender.sendMessage((Object)ChatColor.GREEN + "The word, '" + (Object)ChatColor.WHITE + word + (Object)ChatColor.GREEN + "' is now unblocked.");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            String s = "";
            String perm = "";
            for (String word : ChatFilter.bad_words) {
                s = s + word + ", ";
            }
            for (String word : ChatFilter.permanent_bad_words) {
                perm = perm + word + ", ";
            }
            sender.sendMessage((Object)ChatColor.RED + "Bad Words: " + (Object)ChatColor.GRAY + s);
            sender.sendMessage((Object)ChatColor.RED + "Permanent Bad Words: " + (Object)ChatColor.GRAY + perm);
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + "Usage: /filter <add|addp|remove|removep|list>");
        return true;
    }
}

