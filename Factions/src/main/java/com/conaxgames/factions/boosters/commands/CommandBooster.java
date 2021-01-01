/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.conaxgames.factions.boosters.commands;

import me.ifamasssxd.cosmicfactionboosters.CosmicFactionBoosters;
import me.ifamasssxd.cosmicfactionboosters.enums.BoosterType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandBooster
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 4) {
            double mult;
            String type = args[0];
            String pName = args[1];
            Player pl = Bukkit.getPlayer((String)pName);
            if (pl == null) {
                sender.sendMessage((Object)ChatColor.RED + "Invalid player name given.");
                return true;
            }
            if (!StringUtils.isNumeric((String)args[3])) {
                sender.sendMessage((Object)ChatColor.RED + "Please enter a valid duration.");
                return true;
            }
            BoosterType boosterType = null;
            try {
                boosterType = BoosterType.valueOf(type);
            }
            catch (Exception e) {
                sender.sendMessage((Object)ChatColor.RED + "Please enter a valid booster item amount: EXP, MCMMO");
                return true;
            }
            try {
                mult = Math.max(Double.parseDouble(args[2]), 1.1);
            }
            catch (Exception e) {
                sender.sendMessage((Object)ChatColor.RED + "Please enter a valid multplier.");
                return true;
            }
            int duration = Integer.parseInt(args[3]);
            if (duration < 0) {
                duration = 1;
            }
            ItemStack item = CosmicFactionBoosters.get().getBoosterManager().createBooster(boosterType, duration, mult);
            pl.getInventory().addItem(new ItemStack[]{item});
            pl.updateInventory();
            sender.sendMessage((Object)ChatColor.RED + "Booster given to " + pl.getName());
            return true;
        }
        sender.sendMessage((Object)ChatColor.RED + "Usage: /gfb <EXP/MCMMO> <name> <mult> <duration>");
        return false;
    }
}

