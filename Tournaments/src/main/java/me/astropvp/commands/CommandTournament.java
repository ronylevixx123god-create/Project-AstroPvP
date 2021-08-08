package me.astropvp.commands;

import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.inventories.TournamentMainInventory;
import me.astropvp.struct.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTournament implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!(CosmicTournamentsPlugin.getInstance().getPlayerDataManager().hasLoadedData(player))) {
            CosmicTournamentsPlugin.getInstance().getPlayerDataManager().registerData(player, new PlayerData(player));
        }
        if(args.length == 0) {
            TournamentMainInventory.openInventory(player);
        }
        return false;
    }
}
