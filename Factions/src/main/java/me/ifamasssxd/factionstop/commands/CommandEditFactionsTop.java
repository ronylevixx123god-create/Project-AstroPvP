/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package me.ifamasssxd.factionstop.commands;

import com.massivecraft.factions.P;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandEditFactionsTop
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("includeBank")) {
                boolean current = P.getP().isIncludeBank();
                boolean bl = current = !current;
                if (args.length == 2) {
                    try {
                        current = Boolean.parseBoolean(args[1]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                P.getP().setIncludeBank(current);
                P.getP().getConfig().set("includeBank", (Object)current);
                P.getP().saveConfig();
                sender.sendMessage((Object)ChatColor.RED + "Include Bank: " + current);
                return true;
            }
            if (args[0].equalsIgnoreCase("gradual") || args[0].equalsIgnoreCase("gradualSpawnerValue")) {
                boolean current = P.getP().isGradualSpawnerValues();
                boolean bl = current = !current;
                if (args.length == 2) {
                    try {
                        current = Boolean.parseBoolean(args[1]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                P.getP().setGradualSpawnerValues(current);
                P.getP().getConfig().set("gradualSpawnerValue", (Object)current);
                P.getP().saveConfig();
                sender.sendMessage((Object)ChatColor.RED + "Gradual Spawner Values: " + current);
                return true;
            }
            if (args[0].equalsIgnoreCase("includeHoppers")) {
                boolean current = P.getP().isIncludeHoppers();
                boolean bl = current = !current;
                if (args.length == 2) {
                    try {
                        current = Boolean.parseBoolean(args[1]);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                P.getP().setIncludeHoppers(current);
                sender.sendMessage((Object)ChatColor.RED + "Hopper Top Calculations: " + current);
                P.getP().getConfig().set("includeHoppers", (Object)current);
                P.getP().saveConfig();
                return true;
            }
        }
        sender.sendMessage((Object)ChatColor.RED + "Invalid! Valid: gradualSpawnerValue, includeBank, includeHoppers");
        return false;
    }
}

