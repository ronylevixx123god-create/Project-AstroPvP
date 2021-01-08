/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmictoggles.commands;

import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandScoreboard
implements CommandExecutor {
    public static String SCOREBOARD_TOGGLE_DATA = "toggle_scoreboard";

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if (player.hasMetadata("toggle_scoreboard") && player.getMetadata("toggle_scoreboard").size() > 0) {
            player.removeMetadata("toggle_scoreboard", AstroCore.getInstance());
            AstroCore.getInstance().getToggleManager().getScoreboardToggles().remove(player.getUniqueId());
            player.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Faction scoreboard toggled " + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "ON");
        } else {
            if (!AstroCore.getInstance().getToggleManager().getScoreboardToggles().contains(player.getUniqueId())) {
                AstroCore.getInstance().getToggleManager().getScoreboardToggles().add(player.getUniqueId());
            }
            player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Faction scoreboard toggled " + (Object)ChatColor.RED + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "OFF");
            AstroCore.getInstance().getToggleManager().clearScoreboard(player);
        }
        return false;
    }
}

