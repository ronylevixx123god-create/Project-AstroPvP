/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.libs.RankItems
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.CosmicSpaceChests.libs.RankItems;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.AutoEntityClear;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class CommandClearLag
extends AbstractCommand {
    public CommandClearLag(String command, String usage, String description, List<String> aliases) {
        super(command, usage, description, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("cosmicpvp.command.clearlag") && !sender.hasPermission("enchantments.max.9")) {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!)" + (Object)ChatColor.RED + " Only " + (String)RankItems.donorTiers.get(3) + "+ ranked players on this planet may use the /clearlag command.");
            sender.sendMessage((Object)ChatColor.GRAY + "Unlock access today at " + ChatColor.RED.toString() + "buy.cosmicpvp.com");
            return true;
        }
        int msLeft = AutoEntityClear.secondsUntilNextClear * 1000;
        String timeLeft = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(msLeft), TimeUnit.MILLISECONDS.toSeconds(msLeft) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(msLeft)));
        if (msLeft == 0) {
            sender.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) ENTITY CLEAR IS DISABLED ON THIS PLANET.");
        } else {
            sender.sendMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "(!) CosmicPvP has recently updated the way ENTITY CLEAR works.");
            sender.sendMessage((Object)ChatColor.LIGHT_PURPLE + "Instead of all items being cleared every X minutes, each individual item now has its own internal timer. Once an item has existed in the world for 15 minutes (or 60 seconds in KOTH), it will automatically despawn itself.");
        }
        if (sender instanceof Player) {
            Player p = (Player)sender;
            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 0.8f, 1.3f);
        }
        return true;
    }
}

