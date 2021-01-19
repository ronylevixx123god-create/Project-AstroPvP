package me.ifamasssxd.cosmicbosses.commands;

import java.util.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import me.ifamasssxd.cosmicbosses.struct.enums.*;

public class CommandSpawnBoss implements CommandExecutor
{
    HashSet<Byte> empty;
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid usage: /spawnboss <bossName>");
            return true;
        }
        final Player p = (Player)sender;
        final String bossName = args[0];
        final BossType type = BossType.getBossType(bossName);
        if (type == null) {
            p.sendMessage(ChatColor.RED + "Invalid Boss type given: " + bossName);
            return true;
        }
        p.performCommand("boss spawn " + type.name());
        return false;
    }
}
