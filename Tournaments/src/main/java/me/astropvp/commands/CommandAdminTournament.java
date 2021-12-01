package me.astropvp.commands;

import me.astropvp.CosmicTournamentsPlugin;
import me.matthawkk.chat.messages.PrefixedMessage;
import me.matthawkk.config.BaseConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdminTournament implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!player.isOp())
            return true;
        if (args[0].equalsIgnoreCase("forcestart") && CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
            CosmicTournamentsPlugin.getInstance().createTournament();
            Bukkit.broadcastMessage(new PrefixedMessage(CosmicTournamentsPlugin.getInstance(), "You have successfully created a tournament").getMessage());
        }
        if(args[0].equalsIgnoreCase("setlobbyspawn")) {
            CosmicTournamentsPlugin.getInstance().getConfig("spawn-locations").setLocation("lobby", player.getLocation());
        }

        return false;
    }
}
