package me.astropvp.astroscoreboard;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class AstroScoreboardAPI {

	static Map<String, String> rankOrder = new HashMap<>();


	static  {
		rankOrder.put("owner", "1");
		rankOrder.put("manager", "2");
		rankOrder.put("admin", "3");
		rankOrder.put("banteam", "4");
		rankOrder.put("srmod", "5");
		rankOrder.put("mod", "6");
		rankOrder.put("helper", "7");
		rankOrder.put("media", "8");
		rankOrder.put("apollo", "9");
		rankOrder.put("astro+", "a");
		rankOrder.put("astro", "b");
	}

	public static String getTeamName(Player player) {
		FPlayer fPlayer = FPlayers.i.get(player);
		String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();

		return rankOrder.get(rank) + fPlayer.getFaction().getId();
	}



	public static String getDefaultPrefix(Player player, Player other) throws Exception {
		FPlayer fp = FactionUtils.getFPlayer(player);
		String faction;
		FPlayer fother = FactionUtils.getFPlayer(other);
		Faction factionName = ((FPlayer) FPlayers.i.get((OfflinePlayer)player)).getFaction();
        final Random random = new Random();

        if (!(factionName.isWilderness())) {
			faction = CC.strip(fp.getFaction().getTag());
		} else {
			faction = "-";
		}

        String color = fp.getFaction().isWilderness() ? CC.White : fp.getRelationTo(fother).getColor().toString();
		ChatColor rankColor;
		String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
		switch (rank) {
			case "owner":
				rankColor = ChatColor.DARK_RED;
				break;
			case "manager":
			case "media":
				rankColor = ChatColor.LIGHT_PURPLE;
				break;
			case "admin":
				rankColor = ChatColor.BLUE;
				break;
			case "banteam":
				rankColor = ChatColor.GOLD;
				break;
			case "jradmin":
				rankColor = ChatColor.RED;
				break;
			case "srmod":
				rankColor = ChatColor.DARK_AQUA;
				break;
			case "mod":
				rankColor = ChatColor.BLUE;
				break;
			case "jrmod":
				rankColor = ChatColor.DARK_PURPLE;
				break;
			case "helper":
				rankColor = ChatColor.GREEN;
				break;
			case "apollo":
				rankColor = ChatColor.AQUA;
				break;
			default:
				rankColor = ChatColor.YELLOW;
				break;
		}
		return color + faction + rankColor;
	}


}
