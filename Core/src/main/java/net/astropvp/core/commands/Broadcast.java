package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(CC.Red + "No Permission.");
            return false;
        }
        if (!AstroCore.getInstance().getConfig().getBoolean("broadcast.enabled")) {
            commandSender.sendMessage(CC.Red + "Broadcast command has been disabled within the configuration.");
            return false;
        }
        StringBuilder text = new StringBuilder("");
        for (String arg : args) {
            text.append(arg).append(" ");
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.HORSE_SADDLE, 1.0F, 1.0F);
        }
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c "));
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a&lAstro&e&lPvP &8| " + text.toString()));
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&c "));
        return false;
    }
}