/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.commands;

import java.util.ArrayList;
import java.util.Collections;
import org.arkhamnetwork.Arkkit.libs.JSONMessage;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandArkList
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        int running = 0;
        int total = 0;
        String s = "";
        JSONMessage json = new JSONMessage("");
        if (sender instanceof Player) {
            json.addText(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "[" + ChatColor.AQUA.toString() + "Compilex" + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "] >> " + s);
            json.sendToPlayer((Player)sender);
        }
        ArrayList<String> patch_names = new ArrayList<String>();
        for (ArkPlugins aps : ArkPlugins.values()) {
            patch_names.add(aps.name());
        }
        Collections.sort(patch_names);
        json = new JSONMessage("");
        sender.sendMessage("");
        StringBuilder message = new StringBuilder();
        for (String patch : patch_names) {
            ArkPlugins aps;
            aps = ArkPlugins.valueOf(patch);
            ++total;
            String msg = "";
            if (aps.getArkPlugin().isRunning()) {
                ++running;
                msg = ChatColor.GREEN.toString() + aps.getConfigName() + (Object)ChatColor.RESET + ", ";
                if (message.length() + msg.length() >= 3200 && sender instanceof Player) {
                    json.sendToPlayer((Player)sender);
                    json = new JSONMessage("");
                    message = new StringBuilder();
                }
                message.append(msg);
                json.addHoverData(msg, ChatColor.GREEN.toString() + ChatColor.UNDERLINE.toString() + aps.getConfigName() + "\n" + this.formatLore(aps.getDescription(), ChatColor.GREEN));
                s = s + ChatColor.GREEN.toString() + aps.getConfigName() + ", " + (Object)ChatColor.RESET;
                continue;
            }
            msg = ChatColor.WHITE.toString() + aps.getConfigName() + (Object)ChatColor.RESET + ", ";
            if (message.length() + msg.length() >= 3200 && sender instanceof Player) {
                json.sendToPlayer((Player)sender);
                json = new JSONMessage("");
                message = new StringBuilder();
            }
            message.append(msg);
            json.addHoverData(msg, ChatColor.RED.toString() + ChatColor.UNDERLINE.toString() + aps.getConfigName() + "\n" + this.formatLore(aps.getDescription(), ChatColor.RED));
            s = s + ChatColor.WHITE.toString() + aps.getConfigName() + ", " + (Object)ChatColor.RESET;
        }
        if (sender instanceof Player) {
            json.sendToPlayer((Player)sender);
        } else {
            sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "[" + ChatColor.AQUA.toString() + "Compilex" + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "] >> " + s);
        }
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "[" + ChatColor.AQUA.toString() + "Compilex" + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "] >> " + (Object)ChatColor.GREEN + (Object)ChatColor.UNDERLINE + running + " / " + total + (Object)ChatColor.GREEN + " patches running!");
        return true;
    }

    private String formatLore(String s, ChatColor cc) {
        String lore_s = "";
        if (s == null) {
            return lore_s;
        }
        if (s.length() <= 38) {
            return s;
        }
        if (s.contains(" ")) {
            String line = cc.toString();
            for (String word : s.split(" ")) {
                if (line.length() - 1 + word.length() <= 38) {
                    line = line + word + " ";
                    continue;
                }
                if (line.endsWith(" ")) {
                    line = line.substring(0, line.length() - 1);
                }
                lore_s = lore_s + line + (Object)ChatColor.RESET + "\n";
                line = (Object)cc + word + " ";
            }
            if (ChatColor.stripColor((String)line).length() > 0) {
                lore_s = lore_s + line + (Object)ChatColor.RESET + "\n";
            }
        } else {
            lore_s = lore_s + s + (Object)ChatColor.RESET + "\n";
            return lore_s;
        }
        return lore_s;
    }
}

