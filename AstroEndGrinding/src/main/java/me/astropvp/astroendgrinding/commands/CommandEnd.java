package me.astropvp.astroendgrinding.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astroendgrinding.utils.TimeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.data.EndPlayerData;
import me.astropvp.astroendgrinding.enums.MonsterType;

public class CommandEnd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		
		if(args.length >= 1 && args[0].equalsIgnoreCase("stats")) {
			if(!player.getWorld().getName().equals(AstroEndGrindingAPI.getWorldName())) {
				player.sendMessage("");
				player.sendMessage(CC.translate("&c&l(!)&c You must be in the End to view your Stats 7(/warp end)&c."));
				player.sendMessage("");
				return false;
			}
			EndPlayerData data = EndPlayerData.findPlayer(player);
			player.sendMessage("");
			player.sendMessage(CC.translate("&d&lEND GRINDING STATS"));
			player.sendMessage("");
			for(MonsterType type : MonsterType.values()) {
				player.sendMessage(CC.translate("&d&l* &5" + type.getName() + "&7: &f" + data.getKills(type)));
			}
			player.sendMessage("");
			player.sendMessage(CC.LightPurple + "Time Spent: " + CC.White + TimeUtils.format(data.getTimeInEnd()));
			player.sendMessage("");
			return false;
		}
		player.sendMessage("");
		player.sendMessage(CC.translate("&d&lEND GRINDING HELP"));
		player.sendMessage("");
		player.sendMessage(CC.translate("&d&l* &5/Warp End &7- &fTeleport to the end world."));
		player.sendMessage(CC.translate("&d&l* &5/Endgrinding Status &7- &fView your end grinding stats."));
		player.sendMessage("");
		player.sendMessage(CC.Gray + "The End Warp is a place to grind to earn Exp Bottle(s), BankNote(s), "
		+ "Items, and more! There is no way to come back to the overworld, unless you die, or you teleport through the portal " +
		"located in the end.");
		player.sendMessage("");
		return false;
	}

}
