package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PhoenixCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        long cooldown = Phoenix.getPhoenixCooldown(player);
        if(cooldown == 0) {
            player.sendMessage(CC.DarkRedB + "(" + CC.RedB + "PHOENIX" + CC.DarkRedB + ") " + CC.Red + "You are not on Phoenix cooldown.");
        } else {
            player.sendMessage(CC.DarkRedB + "(" + CC.RedB + "PHOENIX" + CC.DarkRedB + ") " + CC.Red + "You are not on Phoenix cooldown for " + cooldown + "s.");
        }
        return false;
    }
}
