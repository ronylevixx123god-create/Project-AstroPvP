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
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.ifamasssxd.killtracker.commands;

import me.ifamasssxd.killtracker.KillTrackerAPI;
import me.ifamasssxd.killtracker.listeners.Tracker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveTracker
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 2) {
            String name = args[0];
            String tracker = args[1];
            try {
                Tracker tr = Tracker.valueOf(tracker);
                Player p = Bukkit.getPlayer((String)name);
                if (p == null) {
                    sender.sendMessage((Object)ChatColor.RED + "Invalid player given: " + name);
                    return true;
                }
                ItemStack is = KillTrackerAPI.buildTracker(tr);
                if (p.getInventory().firstEmpty() == -1) {
                    p.sendMessage((Object)ChatColor.RED + "Your inventory was full so your tracker was dropped on the ground.");
                    p.getWorld().dropItem(p.getLocation(), is);
                } else {
                    p.getInventory().addItem(new ItemStack[]{is});
                }
                p.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You have been given a " + tr.getName() + " Soul Tracker!");
                sender.sendMessage((Object)ChatColor.RED + "You have given " + p.getName() + " a Soul Tracker!");
            }
            catch (Exception e) {
                sender.sendMessage((Object)ChatColor.RED + "Invalid Tracker Type.");
                for (Tracker t : Tracker.values()) {
                    sender.sendMessage((Object)ChatColor.GRAY + t.name());
                }
            }
        } else {
            sender.sendMessage((Object)ChatColor.GRAY + "Usage: /givetracker <name> <trackertype>");
        }
        return false;
    }
}

