/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicduel.struct.ranked;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.utils.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RankedTopManager {
    private LinkedHashMap<Integer, TopPlayer> topElo = new LinkedHashMap();
    private LinkedHashMap<UUID, TopPlayer> easyAccessMap = new LinkedHashMap();
    private LinkedHashMap<Integer, TopPlayer> top3Duels = new LinkedHashMap();
    public long nextLoadTime = 0L;
    public boolean isLoading = false;
    private DecimalFormat eloFormat = new DecimalFormat("#,###");
    private DecimalFormat format = new DecimalFormat("#.#");

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void checkDepreciation() {
        long start = System.currentTimeMillis();
        Bukkit.getLogger().info("Scanning all duel players that havent participated in ranked every > 72h from now.");
        try {
            long last72h = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3L);
            String tableName = "`" + CosmicDuel.get().getServerName() + "_stats`";
            PreparedStatement statement = CosmicDuel.get().getSqlDatabase().getConnection().prepareStatement("SELECT uuid, elo, lastRankedMatch, name FROM " + tableName + " WHERE lastRankedMatch <= '" + last72h + "' AND elo > 300;");
            try {
                PreparedStatement batch = CosmicDuel.get().getSqlDatabase().getConnection().prepareStatement("");
                try {
                    int deprecated = 0;
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        double elo = rs.getDouble("elo");
                        double newElo = elo - elo * 0.05;
                        long lastRankedMatch = rs.getLong("lastRankedMatch");
                        ++deprecated;
                        Bukkit.getLogger().info("Depreciatating " + rs.getString("name") + " due to last ranked match at " + lastRankedMatch + " Current Elo: " + elo + " to " + newElo);
                        batch.addBatch("UPDATE " + tableName + " SET elo = '" + newElo + "', lastRankedMatch = '" + System.currentTimeMillis() + "' WHERE uuid = '" + uuid + "'");
                    }
                    Bukkit.getLogger().info("Depreciated " + deprecated + " players ELO due to inactivity in " + (System.currentTimeMillis() - start) + "ms.");
                    if (deprecated > 0) {
                        batch.executeBatch();
                    }
                }
                finally {
                    if (Collections.singletonList(batch).get(0) != null) {
                        batch.close();
                    }
                }
            }
            finally {
                if (Collections.singletonList(statement).get(0) != null) {
                    statement.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTopPlayers() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        this.topElo.clear();
        this.easyAccessMap.clear();
        new BukkitRunnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                try {
                    PreparedStatement statement = CosmicDuel.get().getSqlDatabase().getConnection().prepareStatement("SELECT uuid, name, elo, rankedWins, rankedLosses FROM `" + CosmicDuel.get().getServerName() + "_stats` ORDER BY elo DESC LIMIT 100;");
                    try {
                        ResultSet rs = statement.executeQuery();
                        HashMap<UUID, TopPlayer> topPlayers = new HashMap<UUID, TopPlayer>();
                        while (rs.next()) {
                            UUID uuid = UUID.fromString(rs.getString("uuid"));
                            String string = rs.getString("name");
                            DuelPlayer onlinePl = DuelPlayer.getDuelPlayer(uuid);
                            int wins = rs.getInt("rankedWins");
                            int deaths = rs.getInt("rankedLosses");
                            double elo = rs.getDouble("elo");
                            if (onlinePl != null) {
                                elo = onlinePl.getElo();
                                wins = onlinePl.getRankedWins();
                                deaths = onlinePl.getRankedLosses();
                            }
                            TopPlayer player = new TopPlayer(uuid, string, wins, deaths, elo);
                            RankedTopManager.this.easyAccessMap.put(uuid, player);
                            topPlayers.put(uuid, player);
                        }
                        LinkedList<TopPlayer> topPlayerList = Lists.newLinkedList(topPlayers.values());
                        for (Player player2 : Bukkit.getServer().getOnlinePlayers()) {
                            DuelPlayer duelPlayer;
                            if (topPlayers.containsKey(player2.getUniqueId()) || (duelPlayer = DuelPlayer.getDuelPlayer(player2)) == null) continue;
                            topPlayerList.add(new TopPlayer(player2.getUniqueId(), player2.getName(), duelPlayer.getRankedWins(), duelPlayer.getRankedLosses(), duelPlayer.getElo()));
                        }
                        Collections.sort(topPlayerList, (o1, o2) -> {
                            if (o1.getElo() < o2.getElo()) {
                                return 1;
                            }
                            if (o1.getElo() == o2.getElo()) {
                                return 0;
                            }
                            return -1;
                        });
                        topPlayers.clear();
                        HashMap hashMap = RankedTopManager.this.getPreviousPlace();
                        int pos = 1;
                        for (TopPlayer tp : topPlayerList) {
                            if (pos >= 100) break;
                            if (pos <= 3) {
                                if (RankedTopManager.this.top3Duels.containsKey(pos)) {
                                    TopPlayer previousTop = (TopPlayer)RankedTopManager.this.top3Duels.get(pos);
                                    RankedTopManager.this.top3Duels.put(pos, tp);
                                    if (!(previousTop == null || tp.getUuid().equals(previousTop.getUuid()) || hashMap.containsKey(tp.getUuid()) && (Integer)hashMap.get(tp.getUuid()) <= pos)) {
                                        //Bukkit.broadcastMessage((String)((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "** " + (Object)ChatColor.GREEN + tp.getName() + " is now /duel top rank #" + pos + " " + (Object)ChatColor.GRAY + "(" + (int)tp.getElo() + " ELO, " + RankedTopManager.this.format.format(tp.getKDR()) + " KDR)"));
                                    }
                                } else {
                                    RankedTopManager.this.top3Duels.put(pos, tp);
                                }
                            }
                            tp.setPosition(pos);
                            RankedTopManager.this.topElo.put(pos++, tp);
                            if (RankedTopManager.this.easyAccessMap.containsKey(tp.getUuid())) continue;
                            RankedTopManager.this.easyAccessMap.put(tp.getUuid(), tp);
                        }
                        Bukkit.getLogger().info("Loaded " + RankedTopManager.this.topElo.size() + " players into /duel top..");
                        RankedTopManager.this.nextLoadTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5L);
                        RankedTopManager.this.isLoading = false;
                    }
                    finally {
                        if (Collections.singletonList(statement).get(0) != null) {
                            statement.close();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    RankedTopManager.this.isLoading = false;
                }
            }
        }.runTaskAsynchronously((Plugin)CosmicDuel.get());
    }

    private HashMap<UUID, Integer> getPreviousPlace() {
        HashMap<UUID, Integer> map = new HashMap<UUID, Integer>();
        this.top3Duels.forEach((pos, top) -> map.put(top.getUuid(), (Integer)pos));
        return map;
    }

    public void showTop(Player player, int page) {
        int perPage = 10;
        int totalPages = (int)Math.ceil(this.topElo.size() / 10);
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }
        int startIndex = page * 10 - 10;
        player.sendMessage("");
        player.sendMessage((Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Top Ranked Players " + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "(" + (Object)ChatColor.LIGHT_PURPLE + page + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "/" + (Object)ChatColor.LIGHT_PURPLE + Math.max(1, totalPages) + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + ")");
        for (int i = startIndex; i < startIndex + 10 && this.topElo.size() > i; ++i) {
            int position = i + 1;
            TopPlayer pl = this.topElo.get(position);
            JSONMessage message = new JSONMessage("");
            message.addHoverData((Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + position + ". " + (Object)ChatColor.AQUA + pl.getName() + (Object)ChatColor.GRAY + " (" + this.eloFormat.format(pl.getElo()) + " ELO)", (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Wins: " + (Object)ChatColor.LIGHT_PURPLE + pl.getKills() + "\n" + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "Losses: " + (Object)ChatColor.LIGHT_PURPLE + pl.getLosses() + "\n" + (Object)ChatColor.AQUA + ChatColor.BOLD.toString() + "KDR: " + (Object)ChatColor.LIGHT_PURPLE + (pl.getLosses() == 0 ? Integer.valueOf(pl.getKills()) : this.format.format(Double.valueOf(pl.getKills()) / Double.valueOf(pl.getLosses()))));
            message.sendToPlayer(player);
        }
        player.sendMessage("");
        if (this.nextLoadTime < System.currentTimeMillis() && !this.isLoading()) {
            this.loadTopPlayers();
        }
    }

    public LinkedHashMap<UUID, TopPlayer> getEasyAccessMap() {
        return this.easyAccessMap;
    }

    public boolean isLoading() {
        return this.isLoading;
    }
}

