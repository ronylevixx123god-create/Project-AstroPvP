package me.astropvp.astroscoreboard;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.struct.factions.CosmicFactionHandler;
import com.cosmicpvp.cosmicutils.struct.factions.FactionHandler;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.*;
import com.massivecraft.factions.cmd.CmdFocus;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


public class AstroScoreboardAPI {

    static Map<String, String> rankOrder = new HashMap<>();


    static {
        rankOrder.put("owner", "1");
        rankOrder.put("manager", "2");
        rankOrder.put("developer", "3");
        rankOrder.put("admin", "4");
        rankOrder.put("banteam", "5");
        rankOrder.put("srmod", "6");
        rankOrder.put("mod", "7");
        rankOrder.put("helper", "8");
        rankOrder.put("media", "9");
        rankOrder.put("interstellar", "a");
        rankOrder.put("apollo", "b");
        rankOrder.put("astro+", "c");
        rankOrder.put("astro", "d");

    }

    public static String getTeamName(Player player) {
        FPlayer fPlayer = FPlayers.i.get(player);
        String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
        String faction;
        Faction factionName = FPlayers.i.get(player).getFaction();
        if (!(FactionUtils.isWilderness(factionName))) {
            faction = CC.strip(fPlayer.getFaction().getTag());
        } else {
            faction = "zzzzzz";
        }
        //return faction;
        return faction + rankOrder.get(rank);
    }


    public static String getDefaultPrefix(Player player, Player other) {
        FPlayer fp = FactionUtils.getFPlayer(player);
        String faction;
        FPlayer fother = FactionUtils.getFPlayer(other);
        Faction factionName = FPlayers.i.get(player).getFaction();
        if (!(FactionUtils.isWilderness(factionName))) {
            faction = CC.strip(fp.getFaction().getTag());
        } else {
            faction = "";
        }

        String color = FactionUtils.isWilderness(fp.getFaction()) ? CC.White : fp.getRelationTo(fother).getColor().toString();
        ChatColor rankColor;
        String rank = PermissionsAPI.getPermissionUser(player.getUniqueId()).getGroup();
        switch (Objects.requireNonNull(rank)) {
            case "owner":
                rankColor = ChatColor.DARK_RED;
                break;
            case "manager":
            case "developer":
            case "media":
                rankColor = ChatColor.LIGHT_PURPLE;
                break;
            case "admin":
                rankColor = ChatColor.RED;
                break;
            case "mod":
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
            case "jrmod":
            case "interstellar":
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

        if (FactionUtils.getFaction(other).focusedPlayer == fp) {
            return CC.translate("&e&k") + faction;
        } else if (factionName.isWilderness()) {
            return color + faction + rankColor;
        } else {
            return color + faction + rankColor + " ";
        }
    }


}
