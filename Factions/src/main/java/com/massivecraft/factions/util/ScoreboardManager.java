package com.massivecraft.factions.util;

import com.compilex.CompilexPermissions.PermissionsAPI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.board.BoardAdapter;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.util.NumberUtils;
import io.netty.util.internal.ThreadLocalRandom;
import net.astropvp.core.AstroCore;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreboardManager implements BoardAdapter {

    public static String getDateSuffix() {
        return format.format(new Date());
    }
    public FPlayer fme;
    private static final DateFormat format = new SimpleDateFormat("MM/dd");

    @Override
    public List<String> getScoreboard(Player player, com.massivecraft.factions.board.Board board) {
        if (AstroCore.getInstance().getToggleManager().getScoreboardToggles().contains(player.getUniqueId())) {
            return null;
        } else {
            return this.mainBoard(player);
        }
    }

    @Override
    public void preLoop() {
    }

    @Override
    public String getTitle(Player player) {
        FPlayer fplayer = FPlayers.i.get(player);
        if (fplayer != null) {
            if (fplayer.hasFaction()) {
                return CC.AquaB + fplayer.getFaction().getTag() + CC.Gray + " " + getDateSuffix();
            } else {
                return CC.AquaB + "AstroPvP " + CC.Gray + getDateSuffix();
            }
        }
        return CC.AquaB + "AstroPvP " + CC.Gray + getDateSuffix();
    }

    public List<String> mainBoard(Player player) {
        List<String> board = new LinkedList<>();
        FPlayer fplayer = FPlayers.i.get(player);

        board.add(" ");
        board.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "Account");
        board.add(ChatColor.WHITE + player.getName());
        board.add(" ");
        board.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance");
        board.add(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long) Econ.econ.getBalance(player)));
        board.add(" ");
        board.add(ChatColor.AQUA + ChatColor.BOLD.toString() + "XP");
        board.add(ChatColor.WHITE.toString() + NumberUtils.formatMoney(player.getTotalExperience()));
        board.add(" ");
        board.add(ChatColor.RED + ChatColor.BOLD.toString() + "Ping");
        board.add(ChatColor.WHITE.toString() + getPing(player) + "ms");
        board.add(" ");
        if (fplayer != null) {
            if (fplayer.hasFaction()) {
                board.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Faction");
                board.add(ChatColor.GREEN.toString() + fplayer.getFaction().getOnlinePlayers().size() + ChatColor.GRAY + " / " + fplayer.getFaction().getFPlayers().size());
            } else {
                board.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Server");
                board.add(ChatColor.WHITE.toString() + "astropvp.net");
            }
        }

        return board;
    }

    @Override
    public long getInterval() {
        return 80L;
    }

    @Override
    public void onScoreboardCreate(Player player, org.bukkit.scoreboard.Scoreboard b) {
        Bukkit.getOnlinePlayers().forEach(o -> {
                    Bukkit.getPlayer(o.getUniqueId());

                    if (Bukkit.getPlayer(o.getUniqueId()) != null) {

                        final Player joinPlayer = o.getPlayer();
                        if (joinPlayer == null || !joinPlayer.isOnline()) {
                            return;
                        }
                        if (b == null) {
                            return;
                        }

                        Object fplayer = null;
                        String prefix = FormatUtils.getPlayerPrefix(o);
                        final boolean factionsEnabled = P.getP().isEnabled();
                        if (factionsEnabled) {
                            final FPlayer fp = FactionUtils.getFPlayer(o);
                            if (fplayer == null) {
                                fplayer = FactionUtils.getFPlayer(joinPlayer);
                            }
                            final Faction fJoinFaction = FactionUtils.getFaction(joinPlayer);
                            if (P.getP().getPrefixType() == PrefixType.FACTION) {
                            if (P.getP().getPrefixType() == PrefixType.RANK || o.hasPermission("arkkit.staff.hidden")) {
                                if (prefix != null) {
                                    prefix = FormatUtils.trimPrefix(prefix);
                                }
                                final String color = FormatUtils.getFactionEnding(fp.getFaction(), fJoinFaction);
                                prefix = prefix + color + " ";
                            }
                            else if (P.getP().getPrefixType() == PrefixType.FACTION) {
                                if (P.getP().isEnabled()) {
                                    prefix = FormatUtils.getFactionPrefixForOtherFaction(fp.getPlayer(), fJoinFaction);
                                    if (prefix != null) {
                                        prefix = FormatUtils.trimPrefix(prefix) + " " + FormatUtils.getRankColorEnding(o);
                                    }
                                    else {
                                        prefix = FormatUtils.getRankColorEnding(o);
                                    }
                                }
                                else {
                                    prefix = FormatUtils.getFactionColorForOtherFaction(fp.getPlayer(), fJoinFaction);
                                    prefix = FormatUtils.trimPrefix(prefix);
                                }
                            }
                        }
                        final String pl_teamName = FormatUtils.getTeamName(o);
                        final Team team = (b.getTeam(pl_teamName) != null) ? b.getTeam(pl_teamName) : b.registerNewTeam(pl_teamName);
                        if (!team.getPrefix().equals(prefix)) {
                            team.setPrefix((prefix != null) ? prefix : "");
                        }
                        if (!team.hasEntry(o.getName())) {
                            team.addEntry(o.getName());
                        }
                        final String teamName = FormatUtils.getTeamDisplayName(o);

                        team.setDisplayName(teamName);


                        if (!team.hasEntry(o.getName())) {
                             team.addEntry(o.getName());
                }
            }
        });
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static HashSet<UUID> playerObjectives = new HashSet<>();

    public static boolean nearby = true;

    public static int getPing(Player player) {
        int ping = (((CraftPlayer)player).getHandle()).ping;
            if (ping == 0) {
                ping = ThreadLocalRandom.current().nextInt(40, 50);
            } else if (ping > 80) {
                ping = ThreadLocalRandom.current().nextInt(30, 50);
            }
        return ping;
    }



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
