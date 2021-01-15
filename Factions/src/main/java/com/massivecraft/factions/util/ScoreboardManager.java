package com.massivecraft.factions.util;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.board.BoardAdapter;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.util.NumberUtils;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreboardManager implements BoardAdapter {

    public static String getDateSuffix() {
        return format.format(new Date());
    }

    private static final DateFormat format = new SimpleDateFormat("MM/dd");

    @Override
    public List<String> getScoreboard(Player player, com.massivecraft.factions.board.Board board) {
        return this.mainBoard(player);
    }

    @Override
    public void preLoop() {
    }

    @Override
    public String getTitle(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        if (fplayer != null) {
            if (fplayer.hasFaction()) {
                return CC.GreenB + fplayer.getFaction().getTag() + CC.Gray + " [" + getDateSuffix() + "]";
            } else {
                return CC.GreenB + "AstroPvP " + CC.Gray + "[" + getDateSuffix() + "]";
            }
        }
        return CC.GreenB + "AstroPvP " + CC.Gray + "[" + getDateSuffix() + "]";
    }

    public List<String> mainBoard(Player player) {
        List<String> board = new LinkedList<>();

        board.add(" ");
        board.add(ChatColor.AQUA + ChatColor.BOLD.toString() + "Account");
        board.add(ChatColor.WHITE + player.getName());
        board.add(" ");
        board.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance");
        board.add(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long) Econ.econ.getBalance(player)));
        board.add(" ");
        board.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "XP");
        board.add(ChatColor.WHITE.toString() + NumberUtils.formatMoney(getTotalExperience(player)));
        board.add(" ");
        board.add(ChatColor.RED + ChatColor.BOLD.toString() + "Ping");
        board.add(ChatColor.WHITE.toString() + ((CraftPlayer) player).getHandle().ping + "ms");
        board.add(" ");
        board.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Server");
        board.add(ChatColor.WHITE.toString() + "astropvp.net");

        return board;
    }

    @Override
    public long getInterval() {
        return 20L;
    }

    @Override
    public void onScoreboardCreate(Player player, org.bukkit.scoreboard.Scoreboard scoreboard) {

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static HashSet<UUID> playerObjectives = new HashSet<>();

    public static boolean nearby = true;



    private static boolean newScoreboardManager = false;

    private static boolean checked = false;

    /*public static void setPlayerFactionScoreboardAsync(Player player) {

        scores.add(getScore(ChatColor.BLACK + " ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.AQUA + ChatColor.BOLD.toString() + "Account", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE + player.getName(), scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.YELLOW + " ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long)Econ.econ.getBalance(player)), scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.LIGHT_PURPLE + "  ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "XP", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE.toString() + NumberUtils.formatMoney(getTotalExperience(player)), scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.GREEN + " ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.RED + ChatColor.BOLD.toString() + "Ping", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE.toString() + ((CraftPlayer) player).getHandle().ping, scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.DARK_BLUE + "  ", scoreboardComp, scoreboardObjective, index--));
            scores.add(getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Server", scoreboardComp, scoreboardObjective, index--));
            scores.add(getScore(ChatColor.WHITE.toString() + "astropvp.net", scoreboardComp, scoreboardObjective, index--));
        if (!playerObjectives.contains(player.getUniqueId())) {
            PacketPlayOutScoreboardObjective scoreboardP = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
            PacketPlayOutScoreboardDisplayObjective displayP = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);
            (craftPlayer.getHandle()).playerConnection.sendPacket(scoreboardP);
            (craftPlayer.getHandle()).playerConnection.sendPacket(displayP);
            playerObjectives.add(player.getUniqueId());
        } else {
            PacketPlayOutScoreboardObjective scoreboardP = new PacketPlayOutScoreboardObjective(scoreboardObjective, 1);
            PacketPlayOutScoreboardDisplayObjective displayP = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);
            PacketPlayOutScoreboardObjective scoreboardP2 = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
            PacketPlayOutScoreboardDisplayObjective displayP2 = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);
            (craftPlayer.getHandle()).playerConnection.sendPacket(scoreboardP);
            (craftPlayer.getHandle()).playerConnection.sendPacket(displayP);
            (craftPlayer.getHandle()).playerConnection.sendPacket(scoreboardP2);
            (craftPlayer.getHandle()).playerConnection.sendPacket(displayP2);
        }
        for (PacketPlayOutScoreboardScore score : scores)
            playerConnection.sendPacket(score);
    } */

    private static final DecimalFormat newFormat = new DecimalFormat("#.##");

    private static String getFactionString(Faction faction) {
        int online = 0;
        Set<FPlayer> lists = faction.getFPlayers();
        for (FPlayer pl : lists) {
            if (pl != null && pl.isCachedAsOnline())
                online++;
        }
        return ChatColor.GREEN.toString() + online + ChatColor.GRAY + " / " + lists.size();
    }

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    private static String getNearestPlayer(Faction current, Player player) {
        int pvp = 0, nonPvp = 0;
        for (Entity ent : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
            if (ent instanceof Player && (!P.p.citizensEnabled || !ent.hasMetadata("NPC"))) {
                Player pl = (Player)ent;
                if (!player.canSee(pl))
                    continue;
                FPlayer fp = FPlayers.i.get(pl);
                if (fp == null)
                    continue;
                Relation relation = current.getRelationTo(fp);
                if (pl.hasMetadata("ghostEnchantment")) {
                    int ghostLevel = pl.getMetadata("ghostEnchantment").get(0).asInt();
                    if (relation.isAtMost(Relation.NEUTRAL)) {
                        Relation r = fp.getRelationToLocation();
                        if ((ghostLevel >= 1 && r.isMember()) || (ghostLevel >= 2 && r.isAlly()) || (ghostLevel >= 3 && r.isTruce()))
                            continue;
                    }
                }
                if (relation != null) {
                    if (relation == Relation.TRUCE || relation == Relation.ALLY || relation == Relation.MEMBER) {
                        nonPvp++;
                        continue;
                    }
                    if (relation == Relation.ENEMY || relation == Relation.NEUTRAL)
                        pvp++;
                }
            }
        }
        if (pvp > 0 || nonPvp > 0)
            return ChatColor.GREEN.toString() + nonPvp + ChatColor.GRAY + " vs. " + ChatColor.RED.toString() + pvp;
        return "None";
    }

    public static PacketPlayOutScoreboardScore getScore(String name, Scoreboard scoreboardComp, ScoreboardObjective objective, int score) {
        if (name.length() >= 16)
            name = name.substring(0, 16);
        ScoreboardScore playerName = scoreboardComp.getPlayerScoreForObjective(name, objective);
        playerName.setScore(score);
        // POSSIBLY FUCKED
        return new PacketPlayOutScoreboardScore(playerName.getPlayerName(), objective);
    }

    private static int getExpAtLevel(Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(int level) {
        if (level > 29)
            return 62 + (level - 30) * 7;
        if (level > 15)
            return 17 + (level - 15) * 3;
        return 17;
    }

    public static int getExpToLevel(int level) {
        int currentLevel = 0;
        int exp = 0;
        while (currentLevel < level) {
            exp += getExpAtLevel(currentLevel);
            currentLevel++;
        }
        if (exp < 0)
            exp = Integer.MAX_VALUE;
        return exp;
    }

    public static int getTotalExperience(Player player) {
        int exp = Math.round(getExpAtLevel(player) * player.getExp());
        int currentLevel = player.getLevel();
        while (currentLevel > 0) {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }
        if (exp < 0)
            exp = Integer.MAX_VALUE;
        return exp;
    }

    public static void removeScoreboardSync(Player player) {
        if (playerObjectives.contains(player.getUniqueId())) {
            CraftPlayer craftPlayer = (CraftPlayer)player;
            Scoreboard scoreboardComp = new Scoreboard();
            ScoreboardObjective scoreboardObjective = scoreboardComp.registerObjective("Factions", new ScoreboardBaseCriteria("dummy"));
            (craftPlayer.getHandle()).playerConnection.sendPacket(new PacketPlayOutScoreboardObjective(scoreboardObjective, 1));
            (craftPlayer.getHandle()).playerConnection.sendPacket(new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective));
            playerObjectives.remove(player.getUniqueId());
        }
    }
}
