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
import org.arkhamnetwork.Arkkit.patches.RedeemMCMMO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandAddCredits
extends AbstractCommand {
    public CommandAddCredits(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("addcredits")) {
            if (!sender.hasPermission("redeemMCMMO.addcredits")) {
                return true;
            }
            if (args.length <= 1) {
                sender.sendMessage((Object)ChatColor.RED + "Too little arguments!");
            } else {
                if (args.length == 2) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage((Object)ChatColor.YELLOW + args[0] + (Object)ChatColor.RED + " is not online or doesn't exist!");
                        return true;
                    }
                    String targetPlayer = target.getUniqueId().toString();
                    String targetId = RedeemMCMMO.getConfig().getString(targetPlayer);
                    try {
                        Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException ex) {
                        sender.sendMessage((Object)ChatColor.RED + "The amount of credits must be a whole number!");
                        return true;
                    }
                    int amount = Integer.parseInt(args[1]);
                    if (amount <= 0) {
                        sender.sendMessage((Object)ChatColor.RED + "The amount must be a positive number!");
                        return true;
                    }
                    if (targetId != null) {
                        int oldAmount = RedeemMCMMO.getConfig().getInt(targetPlayer + ".credits");
                        int newAmount = oldAmount + amount;
                        RedeemMCMMO.getConfig().set(targetPlayer + ".credits", (Object)newAmount);
                        sender.sendMessage((Object)ChatColor.YELLOW + "You have given " + (Object)ChatColor.GOLD + args[0] + ", " + amount + (Object)ChatColor.YELLOW + " MCMMO credits.");
                        target.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "You have just received " + (Object)ChatColor.UNDERLINE + amount + (Object)ChatColor.YELLOW + " MCMMO credits.");
                        target.sendMessage((Object)ChatColor.GRAY + "mcMMO Credit Balance: " + (Object)ChatColor.YELLOW + newAmount);
                        target.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/credits" + (Object)ChatColor.GRAY + " for help with redeeming them!");
                        return true;
                    }
                    RedeemMCMMO.getConfig().set(targetPlayer + ".credits", (Object)amount);
                    sender.sendMessage((Object)ChatColor.GREEN + "You have given " + (Object)ChatColor.GOLD + args[0] + ", " + amount + (Object)ChatColor.GREEN + " MCMMO credits.");
                    sender.sendMessage((Object)ChatColor.YELLOW + "You have given " + (Object)ChatColor.GOLD + args[0] + ", " + amount + (Object)ChatColor.YELLOW + " MCMMO credits.");
                    target.sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "You have just received " + (Object)ChatColor.UNDERLINE + amount + (Object)ChatColor.YELLOW + " MCMMO credits.");
                    target.sendMessage((Object)ChatColor.GRAY + "mcMMO Credit Balance: " + (Object)ChatColor.YELLOW + amount);
                    target.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.YELLOW + "/credits" + (Object)ChatColor.GRAY + " for help with redeeming them!");
                    return true;
                }
                sender.sendMessage((Object)ChatColor.RED + "Too many arguments!");
            }
        }
        return true;
    }
}

