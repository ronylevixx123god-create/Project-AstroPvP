package net.astropvp.core.commands;

import net.astropvp.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Broadcast implements CommandExecutor {
    private Main main;

    private List<String> broadcastCommands;

    public Broadcast(Main main) {
        this.main = main;
        this.broadcastCommands = main.getConfig().getStringList("broadcast.commands");
    }

    public StringBuilder generateMessage(String[] message) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length; i++) {
            builder.append(ChatColor.translateAlternateColorCodes('&', message[i]));
            if (i < message.length - 1)
                builder.append(" ");
        }
        return builder;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        boolean followThrough = true;
        if (!this.main.getConfig().getBoolean("broadcast.enabled"))
            followThrough = false;
        if (!commandSender.isOp()) {
            return false;
        }
        if (args.length == 0)
            followThrough = false;
        if (followThrough)
            for (String broadcast : this.main.getConfig().getStringList("broadcast.message")) {
                String message = generateMessage(args).toString();
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', broadcast.replace("%broadcast%", message)));
                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(player.getLocation(), Sound.HORSE_SADDLE, 1.0F, 1.0F);
            }
        return false;
    }
}