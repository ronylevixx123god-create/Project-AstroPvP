/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.EntityType
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

final class CommandSpawnerFee
        extends AbstractCommand {
    public CommandSpawnerFee(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 2) {
            for (EntityType et : EntityType.values()) {
                if (SpawnerFeeListener.getSpawnerFeeBase(et) == 0) continue;
                sender.sendMessage(et.toString() + " => " + SpawnerFeeListener.getSpawnerFeeBase(et));
            }
            sender.sendMessage("/spawnerfee <entityType> <spawnerFee>");
            return true;
        }
        if (args[0].equals("disable") && args[1].equals("fees")) {
            SpawnerFee.spawnerFee = false;
            Arkkit.get().getConfig().set("settings.spawner_fee.spawnerFee", false);
            Arkkit.get().saveConfig();
            sender.sendMessage("Spawner Fees DISABLED");
            return true;
        }
        if (args[0].equals("enable") && args[1].equals("fees")) {
            SpawnerFee.spawnerFee = true;
            Arkkit.get().getConfig().set("settings.spawner_fee.spawnerFee", true);
            Arkkit.get().saveConfig();
            sender.sendMessage("Spawner Fees ENABLED");
            return true;
        }
        String entityType = args[0];
        int price = Integer.parseInt(args[1]);
        if (EntityType.valueOf(entityType.toUpperCase()) == null) {
            sender.sendMessage("Invalid entityType.");
            return true;
        }
        Arkkit.get().getConfig().set("settings.spawner_fee." + entityType.toLowerCase(), price);
        Arkkit.get().saveConfig();
        sender.sendMessage(ChatColor.YELLOW + "Set spawner fee for " + entityType + " to $" + price);
        return true;
    }
}

