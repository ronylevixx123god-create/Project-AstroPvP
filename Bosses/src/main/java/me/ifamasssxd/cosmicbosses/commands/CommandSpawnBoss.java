/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicbosses.commands;

import java.util.HashSet;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawnBoss
implements CommandExecutor {
    HashSet<Byte> empty;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid usage: /spawnboss <bossName>");
            return true;
        }
        Player p = (Player)sender;
        String bossName = args[0];
        BossType type = BossType.getBossType(bossName);
        if (type == null) {
            p.sendMessage((Object)ChatColor.RED + "Invalid Boss type given: " + bossName);
            return true;
        }
        p.performCommand("boss spawn " + type.name());
        return false;
    }
}

