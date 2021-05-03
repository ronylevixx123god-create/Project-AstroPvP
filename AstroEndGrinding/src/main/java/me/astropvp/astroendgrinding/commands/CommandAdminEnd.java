package me.astropvp.astroendgrinding.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.enums.MonsterType;

public class CommandAdminEnd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player) || !sender.hasPermission("admin.end")) {
			return false;
		}
		
		Player player = (Player) sender;
		
		if(args.length >= 2 && args[0].equalsIgnoreCase("addlocation")) {
			MonsterType type = MonsterType.valueOf(args[1]);
			Location location = player.getLocation();
			AstroEndGrindingAPI.addLocation(type, location);
			player.sendMessage(CC.GreenB + "(!) " + CC.Green + "Added New End Grinding Location at your location.");
			return false;
		}
		
		if(args.length >= 2 && args[0].equalsIgnoreCase("removelocations")) {
			MonsterType type = MonsterType.valueOf(args[1]);
			AstroEndGrindingAPI.removeLocations(type);
			player.sendMessage(CC.GreenB + "(!) " + CC.Green + "Removed all instances of the End Monster '" + type.getName() + "'!");
			return false;
		}
		
		player.sendMessage("");
		player.sendMessage(CC.RedB + "Admin End Commands");
		player.sendMessage(CC.Red + "/aend addlocation <monstertype>");
		player.sendMessage(CC.Red + "/aend removelocations <monstertype>");
		player.sendMessage("");
		return false;
	}

}
