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

import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.EndGrinding;
import org.arkhamnetwork.Arkkit.patches.EndMonsterType;
import org.arkhamnetwork.Arkkit.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandAdminEnd
extends AbstractCommand {
    public CommandAdminEnd(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/aend <addspawn/portals/air>");
            return true;
        }
        if (args[0].equalsIgnoreCase("portals")) {
            EndGrinding.setEndPortalBlocks(119);
            return true;
        }
        if (args[0].equalsIgnoreCase("air")) {
            EndGrinding.setEndPortalBlocks(0);
            return true;
        }
        if (args[0].equalsIgnoreCase("addspawn")) {
            Player pSender = (Player)sender;
            EndMonsterType type = EndMonsterType.valueOf(args[1].toUpperCase());
            if (type == null) {
                sender.sendMessage("Invalid EndMonsterType provided. (CREEPER or CUSTOM)");
                return true;
            }
            ArrayList<String> spawnPoints = Arkkit.get().getConfig().contains("settings.end_grinding.mobSpawns." + type.toString()) ? Arkkit.get().getConfig().getStringList("settings.end_grinding.mobSpawns." + type.toString()) : new ArrayList<String>();
            spawnPoints.add(LocationUtils.convertLocationToString(pSender.getLocation()));
            Arkkit.get().getConfig().set("settings.end_grinding.mobSpawns." + type.toString(), spawnPoints);
            sender.sendMessage((Object)ChatColor.GREEN + "Registered " + (Object)pSender.getLocation() + " as a " + type.toString() + " spawnpoint!");
            return true;
        }
        return true;
    }
}

