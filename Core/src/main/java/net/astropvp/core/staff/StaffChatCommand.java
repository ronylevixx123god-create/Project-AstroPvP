package net.astropvp.core.staff;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffChatCommand extends Command {

    public StaffChatCommand() {
        super("staffchat");
        setAliases(Arrays.asList("sc", "staffc", "chatstaff"));
        setUsage(ChatColor.RED + "/sc");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Player command.");
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("astrocore.staff")) {
            player.sendMessage(CC.Red + "No Permission.");
            return false;
        }
        StringBuilder text = new StringBuilder();
        for (String arg : strings) {
            text.append(arg).append(" ");
        }
        if (text.toString().isEmpty()) {
            player.sendMessage(CC.Red + "Message was empty.");
            return false;
        }
        StaffChat.send(player, text.toString());
        return false;
    }
}