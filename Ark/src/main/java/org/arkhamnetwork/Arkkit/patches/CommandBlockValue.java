/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.BlockValue;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandBlockValue
extends AbstractCommand {
    public CommandBlockValue(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 2) {
            for (Material m : Material.values()) {
                if (BlockValue.getBlockValueBase(m) == 0) continue;
                sender.sendMessage(m.name() + " => " + BlockValue.getBlockValueBase(m));
            }
            sender.sendMessage("/blockvalue <material> <value>");
            return true;
        }
        String blockType = args[0];
        int price = Integer.parseInt(args[1]);
        if (Material.valueOf((String)blockType.toUpperCase()) == null) {
            sender.sendMessage("Invalid bukkit material. ref: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            return true;
        }
        Arkkit.get().getConfig().set("settings.blockValue." + blockType.toLowerCase(), (Object)price);
        Arkkit.get().saveConfig();
        sender.sendMessage((Object)ChatColor.YELLOW + "Set blockValue of " + blockType + " to $" + price);
        return true;
    }
}

