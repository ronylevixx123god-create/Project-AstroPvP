/*
 * Decompiled with CFR 0.145.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ReflectionUtils
 *  com.cosmicpvp.cosmicutils.utils.UserScoreboard
 *  com.cosmicpvp.scoreboard.CosmicScoreboardPlugin
 *  com.cosmicpvp.scoreboard.libs.PlaceholderManager
 *  net.milkbowl.vault.economy.Economy
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.IScoreboardCriteria
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardDisplayObjective
 *  net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardObjective
 *  net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardScore
 *  net.minecraft.server.v1_7_R4.PlayerConnection
 *  net.minecraft.server.v1_7_R4.Scoreboard
 *  net.minecraft.server.v1_7_R4.ScoreboardBaseCriteria
 *  net.minecraft.server.v1_7_R4.ScoreboardObjective
 *  net.minecraft.server.v1_7_R4.ScoreboardScore
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.scoreboard.DisplaySlot
 *  org.bukkit.scoreboard.Objective
 *  org.bukkit.scoreboard.Scoreboard
 */
package com.massivecraft.factions.util;

import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.cosmicpvp.cosmicutils.utils.UserScoreboard;
import com.cosmicpvp.scoreboard.CosmicScoreboardPlugin;
import com.massivecraft.factions.*;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.zcore.util.NumberUtils;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreboardManager {
    public static HashSet<UUID> playerObjectives = new HashSet<>();

    private static DateFormat format = new SimpleDateFormat("MM/dd");

    public static boolean nearby = true;

    public static String getDateSuffix() {
        return format.format(new Date());
    }

    private static boolean newScoreboardManager = false;

    private static boolean checked = false;

    private static boolean usingNewScoreboard() {
        if (checked)
            return newScoreboardManager;
        try {
            newScoreboardManager = (ReflectionUtils.getMethod(CosmicScoreboardPlugin.getPlugin().getClass(), "getPlaceholderManager") != null);
            Bukkit.getLogger().info("New Scoreboard Manager: " + newScoreboardManager);
        } catch (Exception e) {
            Bukkit.getLogger().info("Unable to load new PlaceholderManager data!");
        }
        checked = true;
        return newScoreboardManager;
    }

    public static void setPlayerFactionScoreboardAsync(Player player) {
        if (player == null)
            return;
        if (player.hasMetadata("boss_scoreboard") && player.getMetadata("boss_scoreboard").size() > 0) {
            long expire = ((MetadataValue)player.getMetadata("boss_scoreboard").get(0)).asLong();
            if (expire > System.currentTimeMillis())
                return;
        }
        if (player.hasMetadata("custom_scoreboard") && player.getMetadata("custom_scoreboard").size() > 0) {
            long expire = ((MetadataValue)player.getMetadata("custom_scoreboard").get(0)).asLong();
            if (expire > System.currentTimeMillis())
                return;
        }
        try {
            if (usingNewScoreboard() &&
                    CosmicScoreboardPlugin.getPlugin().getPlaceholderManager().hasPlaceholders(player)) {
                UserScoreboard scoreboard = (UserScoreboard)CosmicScoreboardPlugin.getPlugin().getPlaceholderManager().getStoredScoreboards().get(player.getUniqueId());
                Objective koth;
                if (scoreboard != null && (koth = scoreboard.getScoreboard().getObjective("koth")) != null) {
                    koth.unregister();
                    scoreboard.setSlot(DisplaySlot.SIDEBAR);
                }
                return;
            }
        } catch (Exception exception) {}
        if (player.hasMetadata("toggle_scoreboard") && player.getMetadata("toggle_scoreboard").size() > 0)
            return;
        Scoreboard scoreboardComp = new Scoreboard();
        CraftPlayer craftPlayer = (CraftPlayer)player;
        PlayerConnection playerConnection = (craftPlayer.getHandle()).playerConnection;
        if (playerConnection.isDisconnected())
            return;
        ScoreboardObjective scoreboardObjective = (scoreboardComp.getObjective("Factions") != null) ? scoreboardComp.getObjective("Factions") : scoreboardComp.registerObjective("Factions", (IScoreboardCriteria)new ScoreboardBaseCriteria("dummy"));
        Faction faction = ((FPlayer)FPlayers.i.get((OfflinePlayer)player)).getFaction();
        if (faction == null)
            faction = Factions.i.getNone();
        LinkedList<PacketPlayOutScoreboardScore> scores = new LinkedList<>();
        if (faction != Factions.i.getNone()) {
            scoreboardObjective.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + " " + faction
                    .getTag() + " " + ChatColor.GRAY +
                    getDateSuffix());
        } else {
            scoreboardObjective.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + " Astro" + ChatColor.YELLOW + ChatColor.BOLD + "PvP " + ChatColor.GRAY +

                    getDateSuffix());
        }
        String name = ChatColor.GRAY + player.getName();
        if (name.length() > 16)
            name = name.substring(0, 16);
        ScoreboardScore playerName = scoreboardComp.getPlayerScoreForObjective(name, scoreboardObjective);
        playerName.setScore(15);
        scores.add(new PacketPlayOutScoreboardScore(playerName, 1));
        int index = 15;
        scores.add(getScore(ChatColor.BLACK + " ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.AQUA + ChatColor.BOLD.toString() + "Account", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE + player.getName(), scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.YELLOW + " ", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.GOLD + ChatColor.BOLD.toString() + "Balance", scoreboardComp, scoreboardObjective, index--));
        scores.add(getScore(ChatColor.WHITE.toString() + "$" + NumberUtils.formatMoney((long)Econ.econ.getBalance((OfflinePlayer)player)), scoreboardComp, scoreboardObjective, index--));
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
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)scoreboardP);
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)displayP);
            playerObjectives.add(player.getUniqueId());
        } else {
            PacketPlayOutScoreboardObjective scoreboardP = new PacketPlayOutScoreboardObjective(scoreboardObjective, 1);
            PacketPlayOutScoreboardDisplayObjective displayP = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);
            PacketPlayOutScoreboardObjective scoreboardP2 = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
            PacketPlayOutScoreboardDisplayObjective displayP2 = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)scoreboardP);
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)displayP);
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)scoreboardP2);
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)displayP2);
        }
        for (PacketPlayOutScoreboardScore score : scores)
            playerConnection.sendPacket((Packet)score);
    }

    private static DecimalFormat newFormat = new DecimalFormat("#.##");

    private static String getFactionString(Faction faction) {
        int online = 0;
        Set<FPlayer> lists = faction.getFPlayers();
        for (FPlayer pl : lists) {
            if (pl != null && pl.isCachedAsOnline())
                online++;
        }
        return ChatColor.GREEN.toString() + online + ChatColor.GRAY + " / " + lists.size();
    }

    private static String getKDR(UUID uuid) {
        int kills = WebLinkUtils.getString(uuid, "kills");
        int deaths = WebLinkUtils.getString(uuid, "deaths");
        double kdr = (deaths == 0) ? kills : (kills / deaths);
        return newFormat.format(kdr) + ChatColor.GRAY + " [" + kills + ":" + deaths + "]";
    }

    private static DecimalFormat decimalFormat = new DecimalFormat("#,###");

    private static String getNearestPlayer(Faction current, Player player) {
        int pvp = 0, nonPvp = 0;
        for (Entity ent : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
            if (ent instanceof Player && (!P.p.citizensEnabled || !ent.hasMetadata("NPC"))) {
                Player pl = (Player)ent;
                if (!player.canSee(pl))
                    continue;
                FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                if (fp == null)
                    continue;
                Relation relation = current.getRelationTo((RelationParticipator)fp);
                if (pl.hasMetadata("ghostEnchantment")) {
                    int ghostLevel = ((MetadataValue)pl.getMetadata("ghostEnchantment").get(0)).asInt();
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
        return new PacketPlayOutScoreboardScore(playerName, 0);
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
            ScoreboardObjective scoreboardObjective = scoreboardComp.registerObjective("Factions", (IScoreboardCriteria)new ScoreboardBaseCriteria("dummy"));
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutScoreboardObjective(scoreboardObjective, 1));
            (craftPlayer.getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective));
            playerObjectives.remove(player.getUniqueId());
        }
    }
}
