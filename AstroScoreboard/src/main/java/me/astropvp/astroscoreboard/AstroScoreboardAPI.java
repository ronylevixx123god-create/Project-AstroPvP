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

import java.util.Random;


public class AstroScoreboardAPI {

	public static String getTeamName(Player player) {
		String name = player.getName();
		return "s_" + name.substring(0, Math.min(14, name.length()));
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
		if (rank.equals("owner")) {
			return "§4§lOwner§f";
		} else if (rank.equals("manager")) {
			return "§d§lManager§f";
		} else if (rank.equals("admin")) {
			return "§9§lAdmin§f";
		} else if (rank.equals("banteam")) {
			rankColor = ChatColor.GOLD;
		} else if (rank.equals("jradmin")) {
			rankColor = ChatColor.RED;
		} else if (rank.equals("srmod")) {
			rankColor = ChatColor.DARK_AQUA;
		} else if (rank.equals("mod")) {
			rankColor = ChatColor.BLUE;
		} else if (rank.equals("jrmod")) {
			rankColor = ChatColor.DARK_PURPLE;
		} else if (rank.equals("helper")) {
			rankColor = ChatColor.GREEN;
		} else if (rank.equals("apollo")) {
			rankColor = ChatColor.AQUA;
		} else if (rank.equals("media")) {
			rankColor = ChatColor.LIGHT_PURPLE;
		} else {
			rankColor = ChatColor.YELLOW;
		}
		return color + faction + rankColor;
	}


}
