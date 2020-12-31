package net.astropvp.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;
        p.sendMessage("§8");
        p.sendMessage("§a§lAstro§e§lPvP Discord");
        p.sendMessage("§f§nhttps://discord.astropvp.net");
        p.sendMessage("§7Click the link above to join the discord.");
        p.sendMessage("§8");
        return false;
    }

}