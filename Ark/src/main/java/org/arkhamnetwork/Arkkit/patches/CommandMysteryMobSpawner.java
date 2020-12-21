/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class CommandMysteryMobSpawner
extends AbstractCommand {
    public CommandMysteryMobSpawner(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/mms [player]");
            return true;
        }
        String playerName = args[0];
        Player p = Bukkit.getPlayer((String)playerName);
        if (p != null) {
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[]{MysteryMobSpawners.getMysteryMobSpawner()});
            } else {
                p.getWorld().dropItem(p.getLocation(), MysteryMobSpawners.getMysteryMobSpawner());
            }
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + " +1x Mystery Mob Spawner");
            sender.sendMessage((Object)ChatColor.YELLOW + playerName + " has been given 1x Mystery Mob Spawner!");
        } else {
            sender.sendMessage((Object)ChatColor.RED + "Could not locate player " + playerName + "!");
        }
        return true;
    }
}

