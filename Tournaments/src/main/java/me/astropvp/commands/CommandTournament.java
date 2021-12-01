package me.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.inventories.TournamentMainInventory;
import me.matthawkk.chat.messages.ErrorMessage;
import me.matthawkk.chat.messages.PrefixedMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTournament implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(args.length == 0) {
            new TournamentMainInventory(player).open(player);
            return true;
        }


        switch (args[0]) {
            case "join":
                if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
                    player.sendMessage(new ErrorMessage("There is no active tournament.").get());
                    return false;
                }

                if (CosmicTournamentsPlugin.getInstance().getActiveTournament().getPlayerList().size() == 256) {
                    player.sendMessage(new ErrorMessage("The tournament is full.").get());
                    return false;
                }

                CosmicTournamentsPlugin.getInstance().getActiveTournament().registerPlayer(player);
                player.sendMessage(new PrefixedMessage( CosmicTournamentsPlugin.getInstance(), "You have joined the tournament " + CC.Gray + "[" + CosmicTournamentsPlugin.getInstance().getActiveTournament().getPlayerList().size() + " / 256]").get());
                break;

            case "leave":
                if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
                    player.sendMessage(new ErrorMessage("There is no active tournament").get());
                    return false;
                }

                if (CosmicTournamentsPlugin.getInstance().getActiveTournament().getTournamentPlayer(player) != null); {
                    CosmicTournamentsPlugin.getInstance().getActiveTournament().unregisterPlayer(player);
                    player.sendMessage(new PrefixedMessage(CosmicTournamentsPlugin.getInstance(), "You have left the tournament").get());
                    return false;
                }


        }
        return false;
    }
}
