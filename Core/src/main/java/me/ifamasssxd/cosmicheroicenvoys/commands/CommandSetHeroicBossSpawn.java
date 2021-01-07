/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicheroicenvoys.commands;

import me.ifamasssxd.cosmicheroicenvoys.utils.LocationUtils;
import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHeroicBossSpawn
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location newLoc;
        String loc = null;
        if (!sender.isOp()) {
            return true;
        }
        Player pl = null;
        if (sender instanceof Player) {
            pl = (Player)sender;
        }
        String string = args.length == 1 && args[0].contains(":") ? args[0] : (loc = pl != null ? LocationUtils.getString(pl.getLocation()) : null);
        if (args.length == 1 && (newLoc = LocationUtils.getLocation(loc)) == null) {
            sender.sendMessage((Object)ChatColor.RED + "Invalid location given!");
            return true;
        }
        AstroCore.getInstance().getConfig().set("bossSpawn", (Object)loc);
        AstroCore.getInstance().saveConfig();
        AstroCore.getInstance().getEnvoyManager().setBossLocation(LocationUtils.getLocation(loc));
        sender.sendMessage((Object)ChatColor.RED + "Set boss spawn location at " + loc + ".");
        return false;
    }
}

