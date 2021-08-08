package me.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.enums.Language;
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
            return true;
        }


        switch (args[0]) {
            case "join":
                if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
                    player.sendMessage(Language.NO_ACTIVE_TOURNAMENT.getMessage());
                    return false;
                }

                if (CosmicTournamentsPlugin.getInstance().getActiveTournament().getPlayerList().size() == 256) {
                    player.sendMessage(Language.FULL_TOURNAMENT.getMessage());
                    return false;
                }

                CosmicTournamentsPlugin.getInstance().getActiveTournament().registerPlayer(player);
                player.sendMessage(CC.translate("&a&l(!)&a You have joined the tournament &7[" + CosmicTournamentsPlugin.getInstance().getActiveTournament().getPlayerList().size() + " / 256]"));

                break;

            case "leave":
                if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
                    player.sendMessage(Language.NO_ACTIVE_TOURNAMENT.getMessage());
                    return false;
                }

                if (CosmicTournamentsPlugin.getInstance().getActiveTournament().getTournementPlayer(player) != null); {
                    CosmicTournamentsPlugin.getInstance().getActiveTournament().unregisterPlayer(player);
                    player.sendMessage(Language.LEAVE_TOURNAMENT.getMessage());
                    return false;
                }


        }
        return false;
    }
}
