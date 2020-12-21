/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.arkhamnetwork.Arkkit.patches.ExpOrbs;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class ExpOrbsCommand
extends AbstractCommand {
    public ExpOrbsCommand(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p;
        Player player = p = sender instanceof Player ? (Player)sender : null;
        if (p == null) {
            return false;
        }
        if (args.length != 1) {
            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "/xporb <amount>");
            p.sendMessage((Object)ChatColor.GRAY + "Converts <amount> xp into item form.");
            p.sendMessage((Object)ChatColor.GRAY + "Use " + (Object)ChatColor.GOLD + "/xp" + (Object)ChatColor.GRAY + " to display your exp points.");
            return true;
        }
        int amount = 0;
        try {
            amount = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe) {
            p.sendMessage((Object)ChatColor.RED + "Invalid <amount> specified.");
            return true;
        }
        if (amount < 100) {
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You must withdraw at least 100XP points per orb.");
            return true;
        }
        int currentXp = ExpUtils.getTotalExperience(p);
        if (currentXp < amount) {
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You only have " + currentXp + " xp points to convert.");
            p.sendMessage((Object)ChatColor.GRAY + "View your exp values with /xp");
            return true;
        }
        if (p.getInventory().firstEmpty() == -1) {
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You do not have any inventory space.");
            return true;
        }
        FLocation pLoc = new FLocation(p.getLocation());
        if (!p.isOp() && Board.getFactionAt((FLocation)pLoc).isWarZone() && !WorldGuardUtils.getRegionName(p.getLocation()).startsWith("spawn_")) {
            p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " convert EXP in Warzones.");
            return true;
        }
        ExpUtils.setTotalExperience(p, currentXp - amount);
        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + amount + " xp");
        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.2f);
        p.getInventory().addItem(new ItemStack[]{ExpOrbs.getXpOrb(amount, p.getName())});
        return true;
    }
}

