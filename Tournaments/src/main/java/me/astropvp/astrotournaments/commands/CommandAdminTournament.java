package me.astropvp.astrotournaments.commands;

import me.astropvp.astrotournaments.AstroTournamentsAPI;
import me.astropvp.astrotournaments.data.TournamentGlobalData;
import me.astropvp.astrotournaments.inventories.TLocationsInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.galacticpvp.galacticpvputils.utils.CC;

public class CommandAdminTournament implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!sender.isOp()) {
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("start")) {
			if(AstroTournamentsAPI.isActive()) {
				sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You can not start a tournament while there is an active tournament!");
				return false;
			}
			sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have started a tournament!");
			AstroTournamentsAPI.startTournament();
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("end")) {
			if(!AstroTournamentsAPI.isActive()) {
				sender.sendMessage(CC.RedB + "(!) " + CC.Red + "There is not an active tournament!");
				return false;
			}
			sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have ended the current tournament!");
			AstroTournamentsAPI.endTournament();
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("resetstats")) {
			AstroTournamentsAPI.resetAllStats();
			TournamentGlobalData.clearCachedData();
			sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have reset all tournament stats!");
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("setlobby") && sender instanceof Player) {
			AstroTournamentsAPI.setLobbyLocation(((Player)sender).getLocation());
			sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "Updated the lobby location to your current position");
			return false;
		}
		if(args.length >= 2 && args[0].equalsIgnoreCase("setspawn") && sender instanceof Player) {
			AstroTournamentsAPI.addSpawnLocation(Integer.valueOf(args[1]), ((Player)sender).getLocation());
			sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "Spawn Pos #" + args[1] + " has been set to your current position.");
			return false;
		}
		if(args.length >= 1 && args[0].equalsIgnoreCase("listlocations") && sender instanceof Player) {
			Inventory inventory = TLocationsInventory.getInventory();
			((Player)sender).openInventory(inventory);
			sender.sendMessage(CC.Green + "Opening Tournament Locations Inventory...");
			return false;
		}
		sender.sendMessage("");
		sender.sendMessage(CC.RedB + "Admin Tournament Commands");
		sender.sendMessage(CC.Red + "/atournament start " + CC.Gray + "- Starts the tournament");
		sender.sendMessage(CC.Red + "/atournament end " + CC.Gray + "- Ends the tournament");
		sender.sendMessage(CC.Red + "/atournament resetstats " + CC.Gray + "- Reset the tournament stats");
		sender.sendMessage(CC.Red + "/atournament setlobby " + CC.Gray + "- Sets the lobby");
		sender.sendMessage(CC.Red + "/atournament listlocations " + CC.Gray + "- Opens Location-Position GUI");
		sender.sendMessage(CC.Red + "/atournament setspawn <pos> " + CC.Gray + "- Sets a position");
		sender.sendMessage("");
		return false;
	}

}
