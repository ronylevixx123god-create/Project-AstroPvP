package me.astropvp.astrotournaments.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrotournaments.AstroTournamentsAPI;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.astropvp.astrotournaments.inventories.ChooseSetInventory;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;

public class CommandTournament implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if(args.length >= 1 && args[0].equalsIgnoreCase("join")) {
			if(!AstroTournamentsAPI.isActive() && ActiveTournamentData.getTeams().size() == 0) {
				player.sendMessage(CC.RedB + "(!) " + CC.Red + "There is not a joinable tournament!");
				return false;
			}
			if(ActiveTournamentData.findUser(player) != null) {
				player.sendMessage(CC.RedB + "(!) " + CC.Red + "You are already in the tournament!");
				return false;
			}

			ActiveTournamentData.addUser(new ActiveTournamentUser(player));
			player.teleport(AstroTournamentsAPI.getLobbyLocation());
			player.openInventory(ChooseSetInventory.getInventory());
			player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have joined the tournament!");
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("leave")) {
			if(!AstroTournamentsAPI.isActive()) {
				player.sendMessage(CC.RedB + "(!) " + CC.Red + "There is not an active tournament!");
				return false;
			}
			ActiveTournamentUser user = ActiveTournamentData.findUser(player);
			if(user == null) {
				player.sendMessage(CC.RedB + "(!) " + CC.Red + "You are not in a tournament!");
				return false;
			}
			user.remove();
			player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have left the tournament!");
			return false;
		}
		player.sendMessage("");
		player.sendMessage(CC.DarkPurpleB + "Galactic Tournament Commands");
		player.sendMessage(CC.DarkPurple + "/tournament join " + CC.White + "- Join the active tournament");
		player.sendMessage(CC.DarkPurple + "/tournament leave " + CC.White + "- Leave the active tournament");
		player.sendMessage("");
		return false;
	}

}
