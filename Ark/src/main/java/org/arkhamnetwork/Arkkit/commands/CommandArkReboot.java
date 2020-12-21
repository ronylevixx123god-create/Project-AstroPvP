/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.commands;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.commands.ArkRebootListener;
import org.arkhamnetwork.Arkkit.commands.ServerRebootTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CommandArkReboot
implements CommandExecutor {
    public static boolean restart_task_exists = false;

    public CommandArkReboot() {
        Bukkit.getPluginManager().registerEvents((Listener)new ArkRebootListener(), (Plugin)Arkkit.get());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        if (restart_task_exists) {
            sender.sendMessage((Object)ChatColor.YELLOW + "There is already a pending restart task running.");
            return true;
        }
        restart_task_exists = true;
        new ServerRebootTask(false).runTaskTimer((Plugin)Arkkit.get(), 20L, 20L);
        return true;
    }
}

