/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

final class CommandMultiBucket
        extends AbstractCommand {
    public CommandMultiBucket(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/multibucket <player> <WATER/LAVA> <uses>");
            return true;
        }
        String pName = args[0];
        BucketType type = BucketType.valueOf(args[1].toUpperCase());
        int uses = Integer.parseInt(args[2]);
        if (Bukkit.getPlayer(pName) != null) {
            Player p = Bukkit.getPlayer(pName);
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(MoreBuckets.getMultiBucket(type, uses));
            } else {
                p.getWorld().dropItem(p.getLocation(), MoreBuckets.getMultiBucket(type, uses));
            }
        } else {
            sender.sendMessage("[Arkkit (MoreBuckets)] Could not find player '" + pName + "' online!");
        }
        return true;
    }
}

