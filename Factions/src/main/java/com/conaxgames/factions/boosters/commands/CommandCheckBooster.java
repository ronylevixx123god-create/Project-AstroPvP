/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package com.conaxgames.factions.boosters.commands;

import com.conaxgames.factions.boosters.struct.FactionBoosters;
import com.conaxgames.factions.boosters.utils.FactionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckBooster implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        Faction faction = FactionUtils.getFaction(player);
        if (faction == null || !faction.isNormal()) {
            player.sendMessage(ChatColor.RED + "You must be in a faction to use that command!");
            return true;
        }
        FactionBoosters boosters = P.getP().getBoosterManager().getFactionBooster(faction);
        if (boosters == null || boosters.isEmpty()) {
            player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your faction does not have any active boosters!");
            return true;
        }
        P.getP().getBoosterManager().showActiveBoosters(player, boosters);
        return false;
    }
}
