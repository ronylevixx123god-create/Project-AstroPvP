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
package com.compilex.CosmicCrate.cmds;

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveCC
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("/givecc <version> <player>");
            return true;
        }
        String version = args[0];
        String playerName = args[1];
        ItemStack i = CCAPI.getCosmicCrate(Version.valueOf(version.toUpperCase()), playerName);
        if (i != null && Bukkit.getPlayer((String)playerName) != null) {
            Player p = Bukkit.getPlayer((String)playerName);
            Bukkit.getLogger().info("[CosmicCrate (GiveCC)] Giving " + version.toUpperCase() + " /cc to " + p.getName() + " (" + p.getUniqueId() + ")");
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[]{i});
                return true;
            }
            p.getWorld().dropItem(p.getLocation(), i);
            p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) Your inventory is full, the /cc has been dropped on the floor!");
            return true;
        }
        return true;
    }
}

