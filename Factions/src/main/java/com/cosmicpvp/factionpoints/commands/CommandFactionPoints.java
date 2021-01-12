/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  com.massivecraft.factions.struct.Role
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.cosmicpvp.factionpoints.commands;

import com.cosmicpvp.cosmicutils.utils.*;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.FactionsPointsAPI;
import com.cosmicpvp.factionpoints.managers.CoreChunkManager;
import com.cosmicpvp.factionpoints.managers.PointManager;
import com.cosmicpvp.factionpoints.menus.PointPurchaseMenu;
import com.cosmicpvp.factionpoints.struct.*;
import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Role;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandFactionPoints
implements CommandExecutor {
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd hh:mm:ss aa");

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player pl;
        if (args.length == 1 && args[0].equalsIgnoreCase("buy")) {
            Player player = (Player)sender;
            if (P.getP().getPointManager().getPointsBuyable()) {
                FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                Faction current = fplayer.getFaction();
                if (current == null || !current.isNormal()) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to purchase Faction Points!");
                    return true;
                }
                if (!fplayer.getRole().isAtLeast(Role.COLEADER)) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be at least a faction Co-Leader to purchase Faction Points!");
                    return true;
                }
                new PointPurchaseMenu((Player)sender, current).openGUI(P.getP());
            } else {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Faction Points are not available for purchase at this time!");
            }
            return true;
        }
        if (args.length >= 1 && sender.isOp()) {
            if (args[0].equalsIgnoreCase("debug")) {
                P.debug = !P.debug;
                sender.sendMessage(CC.Red + "Debug: " + P.debug);
                return true;
            }
            PointManager pointManager = P.getP().getPointManager();
            if (args[0].equalsIgnoreCase("admininfo")) {
                sender.sendMessage(CC.Red + "Current Weekly Percent: " + pointManager.getCurrentWeeklyPercent());
                sender.sendMessage(CC.Red + "Last Reset: " + TimeUtils.formatTimeToDate((long)pointManager.getLastSetExpiration(), (String)"MM dd hh:mm aa", (String)"CST"));
                sender.sendMessage(CC.Red + "Weekly Increment Amount: " + pointManager.getWeeklyIncreasePercent() + "%");
                return true;
            }
            if (args[0].equalsIgnoreCase("setweeklypercent") && args.length > 1) {
                Integer value = NumberUtils.parseIntOrNull((String)args[1]);
                if (value != null) {
                    sender.sendMessage(CC.Red + "Current Weekly Percent: " + value + "%");
                    pointManager.setCurrentWeeklyPercent(value.intValue());
                    pointManager.saveExpirationData(false);
                    return true;
                }
                sender.sendMessage(CC.Red + "Invalid percent entered!");
                return true;
            }
            if (args[0].equalsIgnoreCase("setcorechunk")) {
                if (args.length > 1) {
                    Faction faction = FactionUtils.getFactionByTag((String)args[1]);
                    if (faction == null || !faction.isNormal()) {
                        sender.sendMessage(CC.Red + "Invalid faction given!");
                        return true;
                    }
                    FLocation removed = FactionsCoreChunkAPI.removeCoreChunk(faction);
                    AtomicInteger settoExpire = new AtomicInteger(0);
                    P.getP().getChunkManager().getChunksToExpire().forEach((loc, expir) -> {
                        if (expir.getFId() != null && expir.getFId().equals(faction.getId()) && expir.getExpirationTime() <= 0L) {
                            settoExpire.incrementAndGet();
                            expir.setExpirationTime(expir.getTimeClaimed() + TimeUnit.HOURS.toMillis(24L));
                        }
                    });
                    FactionsCoreChunkAPI.setCoreChunk(faction, new FLocation((Player)sender));
                    sender.sendMessage(CC.Red + "Core Chunk set to your location: " + (removed != null ? "removed core chunk at: " + (Object)removed : "") + " Set to expire " + settoExpire.get() + " chunks that were set to never expire!");
                } else {
                    sender.sendMessage(CC.RedB + "/fp setcorechunk <faction>");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("setlastreset") && args.length > 1) {
                try {
                    Long value = Long.parseLong(args[1]);
                    sender.sendMessage(CC.Red + "Reset time set to: " + value);
                    pointManager.setLastSetExpiration(value);
                    pointManager.saveExpirationData(false);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    sender.sendMessage(CC.Red + "Invalid time entered!");
                    return true;
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("setweeklyincrement") && args.length > 1) {
                Integer value = NumberUtils.parseIntOrNull((String)args[1]);
                if (value != null) {
                    sender.sendMessage(CC.Red + "Current Weekly Increment: " + value + "%");
                    pointManager.setWeeklyIncreasePercent(value.intValue());
                    pointManager.saveExpirationData(true);
                    return true;
                }
                sender.sendMessage(CC.Red + "Invalid percent entered!");
                return true;
            }
            if (args[0].equalsIgnoreCase("audit")) {
                int startIndex;
                Integer potential;
                if (args.length < 2) {
                    sender.sendMessage(CC.Red + "You must provide a faction to lookup!");
                    return true;
                }
                Faction found = Factions.i.getByTag(args[1]);
                if (found == null || !found.isNormal()) {
                    sender.sendMessage(CC.Red + "Invalid faction entered!");
                    return true;
                }
                PointChangeLogs logs = P.getP().getPointManager().getFactionPointLogs().get(found.getId());
                if (logs == null || logs.getLogs() == null) {
                    sender.sendMessage(CC.Red + found.getTag() + " does not have any logged point changes!");
                    return true;
                }
                List<PointChangeLog> logList = logs.getLogs();
                Collections.sort(logList);
                int page = 1;
                double perPage = 30.0;
                int maxPages = (int)Math.ceil((double)logList.size() / perPage);
                if (args.length > 2 && (potential = NumberUtils.parseIntOrNull((String)args[2])) != null) {
                    page = potential;
                }
                page = Math.max(1, Math.min(maxPages, page));
                sender.sendMessage(CC.Red + "Viewing logged faction point changes for " + found.getTag() + " (" + page + "/" + maxPages + ")");
                for (int ind = startIndex = (int)((double)page * perPage - perPage); (double)ind < (double)startIndex + perPage && ind < logList.size(); ++ind) {
                    PointChangeLog log = logList.get(ind);
                    if (log == null) continue;
                    String color = log.getAmount() <= 0 ? CC.Red : CC.Green;
                    sender.sendMessage(" " + color + TimeUtils.formatSeconds((long)((System.currentTimeMillis() - log.getTime()) / 1000L)) + " ago, " + (log.getPl() != null ? "Player (" + log.getPl() + ") " : "") + log.getAmount() + " points - " + log.getReason());
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("getstarttime")) {
                long start = P.getP().getStartOfMap();
                sender.sendMessage(CC.Red + "Start Time Set to: " + (start < System.currentTimeMillis() ? TimeUtils.formatSeconds((long)((System.currentTimeMillis() - start) / 1000L)) + " ago" : TimeUtils.formatFutureTime((long)start) + " in the future from now"));
                return true;
            }
            if (args[0].equalsIgnoreCase("setstarttime")) {
                try {
                    Long time = Long.parseLong(args[1]);
                    P.getP().setStartOfMap(time);
                    P.getP().getConfig().set("startOfMap", (Object)time);
                    P.getP().saveConfig();
                    String timeString = time > System.currentTimeMillis() ? "in " + TimeUtils.formatFutureTime((long)time) : (System.currentTimeMillis() - time) / 1000L + "s ago";
                    sender.sendMessage(CC.Red + "Start of Map: " + time + " (" + timeString + ")");
                }
                catch (Exception e) {
                    sender.sendMessage(CC.Red + "Invalid time entered! Enter time in millis!");
                }
                return true;
            }
            boolean modifyDailyLimit = args[0].equalsIgnoreCase("setdailylimit");
            if (modifyDailyLimit || args[0].equalsIgnoreCase("setpointcost")) {
                Integer val = NumberUtils.parseIntOrNull((String)args[1]);
                if (val != null) {
                    if (modifyDailyLimit) {
                        PointPurchaseMenu.POINTS_PER_DAY = val;
                        P.getP().getConfig().set("points-purchasable-per-day", (Object)PointPurchaseMenu.POINTS_PER_DAY);
                        sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Points Per Day: " + val);
                    } else {
                        PointPurchaseMenu.COST_PER_POINT = val;
                        P.getP().getConfig().set("cost-per-point", (Object)PointPurchaseMenu.COST_PER_POINT);
                        sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Costs Faction Point: $" + val);
                    }
                    P.getP().saveConfig();
                    return true;
                }
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid number entered!");
                return true;
            }
            if (args[0].equalsIgnoreCase("togglebuy")) {
                sender.sendMessage(CC.Red + "Points Purchasable: " + P.getP().getPointManager().togglePointsBuyable());
                return true;
            }
            if (args[0].equalsIgnoreCase("mark")) {
                P.getP().getChunkManager().markExpiringChunks();
                sender.sendMessage(CC.Red + "Marking chunks..");
                return true;
            }
            if (args[0].equalsIgnoreCase("giveitem")) {
                if (args.length >= 3) {
                    Player pl2 = PlayerUtil.getPlayerFromString((String)args[1]);
                    if (pl2 == null) {
                        sender.sendMessage(CC.Red + "Unable to find player!");
                        return true;
                    }
                    Double amount = NumberUtils.parseDoubleOrNull((String)args[2]);
                    if (amount == null) {
                        sender.sendMessage(CC.Red + "Amount entered was not valid!");
                        return true;
                    }
                    ItemStack item = null;
                    if (args.length == 4) {
                        Integer found = NumberUtils.parseIntOrNull((String)args[3]);
                        if (found != null) {
                            item = FactionsPointsAPI.createPointItem(amount, true, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(found.intValue()));
                            sender.sendMessage(CC.RedB + "Creating note that expires in " + found + " seconds from now!");
                        } else {
                            sender.sendMessage(CC.Red + "Invalid number entered!");
                            return true;
                        }
                    }
                    if (item == null) {
                        item = FactionsPointsAPI.createPointItem(amount, true);
                    }
                    PlayerUtil.giveOrDropProtectedItem((Player)pl2, item, (int)-1);
                    return true;
                }
                return true;
            }
            boolean add = args[0].equalsIgnoreCase("addpoints") || args[0].equalsIgnoreCase("givepoints");
            if (add || args[0].equalsIgnoreCase("subtractpoints") || args[0].equalsIgnoreCase("takepoints")) {
                Faction faction = FactionUtils.getFactionByTag((String)args[1]);
                Player pl3 = null;
                if (faction == null && (pl3 = PlayerUtil.getPlayerFromString((String)args[1])) != null && (faction = FactionUtils.getFaction((Player)pl3, (boolean)false)) == null) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "That player is not currently in a faction!");
                    return true;
                }
                if (faction == null || !faction.isNormal()) {
                    sender.sendMessage(CC.Red + "Unable to find player or faction with that name!");
                    return true;
                }
                Double toGive = NumberUtils.parseDoubleOrNull((String)args[2]);
                if (!add && toGive > 0.0) {
                    toGive = -toGive.doubleValue();
                }
                if (add && toGive < 0.0) {
                    toGive = Math.abs(toGive);
                }
                Long newPoints = pointManager.modifyFactionPoints(faction, toGive.longValue(), true);
                P.getP().getPointManager().logPointChange(faction, pl3, "given via command", toGive.intValue());
                sender.sendMessage(CC.GreenB + "(!) " + CC.Green + "New Faction Points: " + newPoints + " for " + faction.getTag());
                return true;
            }
            if (args[0].equalsIgnoreCase("clearcooldown")) {
                Player pl4;
                Faction faction = FactionUtils.getFactionByTag((String)args[1]);
                if (faction == null && (pl4 = PlayerUtil.getPlayerFromString((String)args[1])) != null && (faction = FactionUtils.getFaction((Player)pl4, (boolean)false)) == null) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "That player is not currently in a faction!");
                    return true;
                }
                if (faction == null) {
                    sender.sendMessage(CC.Red + "Unable to find player or faction with that name!");
                    return true;
                }
                CoreChunkData data2 = FactionsCoreChunkAPI.getChunkData(faction);
                if (data2 == null || data2.canBeRaided()) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Faction not on raid cooldown!");
                    return true;
                }
                data2.setLastRaided(0L);
                sender.sendMessage(CC.Green + "Last Raided Set to 0 for " + faction.getTag() + ", lastRaidedBy: " + data2.getLastFactionWhoRaided());
                return true;
            }
            if (args[0].equalsIgnoreCase("stats")) {
                Player pl5 = (Player)sender;
                FLocation current = new FLocation(pl5.getLocation());
                CoreChunkManager manager = P.getP().getChunkManager();
                ExpiringChunkData data3 = manager.getChunksToExpire().get((Object)current);
                if (data3 != null) {
                    sender.sendMessage(CC.Red + "Current Chunk Data: " + current.getX() + "x " + current.getZ() + "z, timeClaimed=" + data3.getTimeClaimed() + " Expiration: " + TimeUtils.formatFutureTime((long)data3.getExpirationTime()));
                    if (args.length == 3 && args[1].equalsIgnoreCase("set")) {
                        Long found = null;
                        try {
                            found = Long.parseLong(args[2]);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (found == null) {
                            sender.sendMessage(CC.Red + "Invalid number given.");
                            return true;
                        }
                        data3.setExpirationTime(found);
                        sender.sendMessage(CC.Red + "Expiration set at " + found);
                        return true;
                    }
                } else {
                    sender.sendMessage(CC.Red + "No data found for your current chunk!");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("logs")) {
                List<RaidLog> raidsLog;
                Player pl6;
                Faction faction = FactionUtils.getFactionByTag((String)args[1]);
                if (faction == null && (pl6 = PlayerUtil.getPlayerFromString((String)args[1])) != null && (faction = FactionUtils.getFaction((Player)pl6, (boolean)false)) == null) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "That player is not currently in a faction!");
                    return true;
                }
                if (faction == null) {
                    sender.sendMessage(CC.Red + "Unable to find player or faction with that name!");
                    return true;
                }
                CoreChunkData data4 = FactionsCoreChunkAPI.getChunkData(faction);
                if (data4 != null && (raidsLog = data4.getRaidLogList(false)) != null) {
                    int index = 1;
                    sender.sendMessage(CC.Red + "Showing " + raidsLog.size() + " logged Raids on " + faction.getTag());
                    for (RaidLog log : raidsLog) {
                        sender.sendMessage(CC.Red + index++ + ". " + this.dateFormat.format(new Date(log.getRaidTime())) + " - " + log.getFWhoRaided() + " sploded at " + log.getBreachedAt() + ", originating from " + log.getOriginated() + ", taking " + log.getPointsLost() + " points");
                    }
                } else {
                    sender.sendMessage(CC.Red + "No Raid Log data found for " + faction.getTag() + "!");
                }
                return true;
            }
            if (sender.isOp() && args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(CC.Red + "Usage: /factionpoints giveitem/givepoints/takepoints/addpoints/clearcooldown/logs/stats/togglebuy/setpointcost/setdailylimit/setlastreset/setweeklypercent/admininfo <player/faction> [amount]");
                return true;
            }
        }
        if (!sender.isOp() && args.length >= 1 && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("");
            sender.sendMessage(CC.YellowB + "Faction Points Help");
            sender.sendMessage(CC.YellowB + "Point Multiplier: " + CC.White + P.getP().getPointManager().getCurrentWeeklyPercent() + "%");
            sender.sendMessage(CC.Gray + "Faction Points are what determines your faction's /f top ranking on this planet. Faction Points can be purchased in exchange for /balance, but they can also be obtained through world events such as: KOTH, Conquests, Outposts, Bosses, Dungeons, and of course by Raiding the Core Chunks of enemy factions!");
            sender.sendMessage("");
            sender.sendMessage(CC.Yellow + " /fp buy");
            sender.sendMessage(CC.White + "  Buy Faction Points with in-game /balance.");
            sender.sendMessage("");
            sender.sendMessage(CC.Yellow + " /fp claims");
            sender.sendMessage(CC.White + "  View all claims of your Faction that are at risk of expiring.");
            sender.sendMessage("");
            sender.sendMessage(CC.Yellow + " /factionpoints");
            sender.sendMessage(CC.White + "  View your faction's current Faction Point total.");
            sender.sendMessage("");
            return true;
        }
        Faction toLookup = null;
        if (args.length >= 1 && (args[0].equalsIgnoreCase("claims") || args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("raidclaims"))) {
            if (sender.isOp() && args.length == 2) {
                toLookup = FactionUtils.getFactionByTag((String)args[1]);
                if (toLookup == null) {
                    sender.sendMessage(CC.Red + "Invalid faction given!");
                    return true;
                }
            } else {
                toLookup = FactionUtils.getFaction((Player)((Player)sender), (boolean)false);
                if (toLookup == null || !toLookup.isNormal()) {
                    sender.sendMessage(CC.Red + "You must be in a faction to view your non-connected expiring claims!");
                    return true;
                }
            }
            P.getP().getChunkManager().sendExpiringChunkInfo(toLookup, sender);
            return true;
        }
        if (args.length > 1 && (toLookup = FactionUtils.getFactionByTag((String)args[0])) == null && (pl = PlayerUtil.getPlayerFromString((String)args[0])) != null && (toLookup = FactionUtils.getFaction((Player)pl, (boolean)false)) == null) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "That player is not currently in a faction!");
            return true;
        }
        if (toLookup == null) {
            toLookup = FactionUtils.getFaction((Player)((Player)sender), (boolean)false);
        }
        if (toLookup == null) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to view your faction points!");
            return true;
        }
        Long points = FactionsPointsAPI.getPoints(toLookup);
        if (points == null) {
            points = 0L;
        }
        sender.sendMessage(CC.GreenB + toLookup.getTag() + "'s Faction Points: " + CC.GreenU + NumberUtils.formatMoney((double)points.longValue()));
        return false;
    }
}

