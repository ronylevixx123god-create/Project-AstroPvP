package me.astropvp.commands;

import com.sun.scenario.effect.impl.prism.PrReflectionPeer;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.enums.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoinTournament implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player p = ((Player) commandSender).getPlayer();

        if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
            p.sendMessage(Language.NO_ACTIVE_TOURNAMENT.getMessage());
            return false;
        }

        return true;
    }
}
