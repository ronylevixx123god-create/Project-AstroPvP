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
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCustomCreepers.cmds;

import com.compilex.CosmicCustomCreepers.enums.CreeperTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandCCC
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("/ccc <player> <creeperType>");
            sender.sendMessage("creeperType: Huge, Lucky, Tactical, Stun");
            return true;
        }
        String pName = args[0];
        CreeperTypes ct = CreeperTypes.valueOf(args[1]);
        if (Bukkit.getPlayer(pName) != null) {
            ItemStack i = CreeperTypes.getCCItem(ct);
            Player p = Bukkit.getPlayer(pName);
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(i);
            } else {
                p.getWorld().dropItem(p.getLocation(), i);
            }
            sender.sendMessage(ChatColor.YELLOW + "Added 1x " + ct.name() + " Creeper to " + p.getName() + "'s inventory.");
        }
        return true;
    }
}

