/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class CommandGiveAll
extends AbstractCommand {
    public CommandGiveAll(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player)sender;
        }
        if (p == null) {
            Bukkit.getLogger().info("Thats not a console command!");
            return true;
        }
        if (!p.isOp() && !p.hasPermission("giveall.give")) {
            p.sendMessage((Object)ChatColor.RED + "Permission denied!");
            return true;
        }
        if (label.equalsIgnoreCase("giveall")) {
            if (args.length == 0) {
                p.sendMessage((Object)ChatColor.YELLOW + "Usage: /giveall <itemid|cash|hand|command> <amount>");
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("hand")) {
                    ItemStack handstack = p.getItemInHand();
                    if (handstack == null) {
                        p.sendMessage((Object)ChatColor.RED + "You have nothing in your hand!");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage((Object)ChatColor.GREEN + p.getName() + (Object)ChatColor.WHITE + " has given everyone " + (Object)ChatColor.AQUA + handstack.getAmount() + (Object)ChatColor.WHITE + " x " + (Object)ChatColor.AQUA + handstack.getType().toString() + (Object)ChatColor.WHITE + "!");
                        if (player.equals((Object)p)) continue;
                        player.getInventory().addItem(new ItemStack[]{handstack});
                        player.updateInventory();
                    }
                    return true;
                }
                p.sendMessage((Object)ChatColor.YELLOW + "Usage: /giveall <itemid|cash|hand|command> <amount>");
                return true;
            }
            if (args.length > 1 && (args[0].equalsIgnoreCase("command") || args[0].equalsIgnoreCase("cmd"))) {
                StringBuilder message = new StringBuilder("");
                for (String part : args) {
                    if (part.equals(args[0])) continue;
                    if (!message.toString().equals("")) {
                        message.append(" ");
                    }
                    message.append(part);
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage((Object)ChatColor.GREEN + p.getName() + (Object)ChatColor.WHITE + " has executed a command for all users!");
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)message.toString().replace("[NAME]", player.getName()));
                }
                p.sendMessage((Object)ChatColor.YELLOW + "Command executed for all users.");
                return true;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("money") || args[0].equalsIgnoreCase("cash")) {
                    int cash = 0;
                    try {
                        cash = Integer.valueOf(args[1]);
                    }
                    catch (NumberFormatException e) {
                        p.sendMessage((Object)ChatColor.RED + "Thats not a number!");
                        return true;
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage((Object)ChatColor.GREEN + p.getName() + (Object)ChatColor.WHITE + " has given everyone " + (Object)ChatColor.GREEN + "$" + cash + (Object)ChatColor.WHITE + "!");
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("eco give " + player.getName() + " " + cash));
                    }
                    return true;
                }
                String itemid = String.valueOf(args[0]);
                int amount = 0;
                int data = 0;
                int itemidint = 0;
                if (itemid.contains(":")) {
                    String[] split = itemid.split(":");
                    try {
                        itemidint = Integer.valueOf(split[0]);
                    }
                    catch (NumberFormatException e) {
                        p.sendMessage((Object)ChatColor.RED + "Thats not a number!");
                        return true;
                    }
                    try {
                        data = Integer.valueOf(split[1]);
                    }
                    catch (NumberFormatException e) {
                        p.sendMessage((Object)ChatColor.RED + "Thats not a number!");
                        return true;
                    }
                }
                try {
                    itemidint = Integer.valueOf(args[0]);
                }
                catch (NumberFormatException e) {
                    p.sendMessage((Object)ChatColor.RED + "Thats not a number!");
                    return true;
                }
                try {
                    amount = Integer.valueOf(args[1]);
                }
                catch (NumberFormatException e) {
                    p.sendMessage((Object)ChatColor.RED + "Thats not a number!");
                    return true;
                }
                Material item = Material.getMaterial((int)itemidint);
                if (item == null) {
                    p.sendMessage((Object)ChatColor.GOLD + "Thats not a valid item ID!");
                    return true;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage((Object)ChatColor.GREEN + p.getName() + (Object)ChatColor.WHITE + " has given everyone " + (Object)ChatColor.AQUA + amount + (Object)ChatColor.WHITE + " x " + (Object)ChatColor.AQUA + item.toString() + (Object)ChatColor.WHITE + "!");
                    player.getInventory().addItem(new ItemStack[]{new ItemStack(item, amount, (short)data)});
                }
                return true;
            }
        }
        return false;
    }
}

