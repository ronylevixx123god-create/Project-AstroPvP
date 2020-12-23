/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.UUID;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.arkhamnetwork.Arkkit.patches.TimedInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandViewBrag
extends AbstractCommand {
    public CommandViewBrag(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 1) {
            return true;
        }
        try {
            UUID uuid = UUID.fromString(args[0]);
            TimedInventory inv = ItemLinking.inventoryViews.asMap().get(uuid);
            if (inv == null) {
                sender.sendMessage((Object)ChatColor.RED + "Invalid brag!");
                return true;
            }
            if (inv.getExpiration() < System.currentTimeMillis()) {
                sender.sendMessage((Object)ChatColor.RED + "Brag inventory expired!");
                ItemLinking.inventoryViews.asMap().remove(uuid);
                return true;
            }
            ((Player)sender).openInventory(inv.getInventory());
        }
        catch (Exception e) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid!");
        }
        return false;
    }
}

