/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.teams;

import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.utils.TeamAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeamChat
extends AbstractCommand {
    public CommandTeamChat(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player pl;
        Player player = pl = sender instanceof Player ? (Player)sender : null;
        if (pl == null) {
            sender.sendMessage("This command may only be executed by players.");
            return true;
        }
        if (TeamAPI.getTeam(pl) == null) {
            pl.sendMessage((Object)ChatColor.RED + "You are not currently in a /team!");
            pl.sendMessage((Object)ChatColor.GRAY + "Create one now by using /team invite.");
            return true;
        }
        if (args.length == 0) {
            pl.sendMessage((Object)ChatColor.RED + "/tc <message>");
            pl.sendMessage((Object)ChatColor.GRAY + "Sends a chat message to your team/party.");
            return true;
        }
        String msg = "";
        for (String s : args) {
            msg = msg + s + " ";
        }
        msg.trim();
        String format_msg = ChatColor.translateAlternateColorCodes((char)'&', (String)((Object)TeamAPI.getTeamColor(TeamAPI.getTeam(pl)) + "&l(TEAM) &r" + pl.getName() + ": &7" + msg));
        for (Player ply : TeamAPI.getMembers(TeamAPI.getTeam(pl))) {
            ply.sendMessage(format_msg);
        }
        return true;
    }
}

