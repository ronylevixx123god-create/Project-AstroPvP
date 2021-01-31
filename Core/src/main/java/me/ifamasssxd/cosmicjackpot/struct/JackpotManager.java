/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.google.common.collect.Lists
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicjackpot.struct;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import me.ifamasssxd.cosmicjackpot.utils.ItemUtils;
import me.ifamasssxd.cosmicjackpot.utils.TimeUtils;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class JackpotManager {
    private File topFile;
    private File currentJackpotFile;
    private File notificationToggleFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().create();
    private final Type jackpotHistoryToken = new TypeToken<Map<UUID, JackpotHistory>>(){}.getType();
    private final Type jackpotToken = new TypeToken<Jackpot>(){}.getType();
    private final Type togglesToken = new TypeToken<HashSet<UUID>>(){}.getType();
    private Set<UUID> toggledNofications = new HashSet<UUID>();
    private Map<UUID, JackpotHistory> jackpotHistory;
    private final DecimalFormat moneyFormat = new DecimalFormat("#,###.##");
    private Jackpot currentJackpot;
    private long taxesCollected;
    private int defaultDrawTime = (int)TimeUnit.HOURS.toSeconds(2L);
    private LinkedList<JackpotHistory> sortedHistory = new LinkedList();
    private long lastListUpdate;

    public void onJackpotTick() {
        int secondsLeft = this.currentJackpot.getSecondsLeft();
        if (secondsLeft > 0) {
            if (secondsLeft == 300 || secondsLeft == 60 || secondsLeft == 30 || secondsLeft == 10 || secondsLeft <= 5 && secondsLeft > 0) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (this.toggledNofications.contains(pl.getUniqueId())) continue;
                    if (secondsLeft >= 60) {
                        pl.sendMessage("");
                    }
                    pl.sendMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + ChatColor.AQUA + "Astro Jackpot (" + ChatColor.AQUA + ChatColor.BOLD + "$" + ChatColor.AQUA + this.getMoneyFormat().format(this.currentJackpot.getPlayerWinnings()) + ") drawing in " + ChatColor.AQUA + ChatColor.BOLD.toString() + (secondsLeft == 60 ? "60s" : TimeUtils.formatDifference(secondsLeft).trim()) + ChatColor.AQUA + "!");
                    if (secondsLeft > 5) {
                        pl.sendMessage(ChatColor.GRAY + "Use " + ChatColor.LIGHT_PURPLE + "/jackpot buy" + ChatColor.GRAY + " to purchase a ticket!");
                    }
                    if (secondsLeft < 60) continue;
                }
            }
            this.currentJackpot.setSecondsLeft(secondsLeft - 1);
        } else {
            this.drawWinner();
        }
    }

    public void drawWinner() {
        PlayerInfo winner = this.currentJackpot.getRandomWinner();
        if (winner == null) {
            Bukkit.broadcastMessage(ChatColor.RED.toString() + ChatColor.BOLD + "(!) " + ChatColor.RED + "No Jackpot winner available due to no ticket sales!");
            this.startNewJackpot();
            return;
        }
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + winner.getUsername() + ChatColor.GREEN + " has won the " + ChatColor.GREEN + ChatColor.UNDERLINE + "/jackpot" + ChatColor.GREEN + " and received ");
        Bukkit.broadcastMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "$" + ChatColor.DARK_GREEN + this.moneyFormat.format(this.currentJackpot.getPlayerWinnings()) + ChatColor.GREEN + "! They purchased " + ChatColor.GREEN + this.moneyFormat.format(this.currentJackpot.getTicketsPurchased(winner.getUuid())) + ChatColor.GREEN + " (" + this.moneyFormat.format(this.currentJackpot.getPercentHolder(winner.getUuid())) + "%) ticket(s) out of the ");
        Bukkit.broadcastMessage(ChatColor.GREEN.toString() + this.moneyFormat.format(this.currentJackpot.getTicketsSold()) + ChatColor.GREEN + " ticket(s) sold!");
        Bukkit.broadcastMessage("");
        if (this.currentJackpot != null) {
            this.currentJackpot.getEntries().forEach((info, num) -> {
                if (winner.getUuid().equals(info.getUuid())) {
                    return;
                }
                double percent = this.currentJackpot.getPercentHolder(info.getUuid());
                Bukkit.getPluginManager().callEvent(new CustomCosmicPlayerEvent(info.getUuid(), "JACKPOT_LOSE", new Object[]{num, percent}));
            });
        }
        double winnings = this.currentJackpot.getPlayerWinnings();
        double taxesCollected = this.currentJackpot.getTotalWinnings() - this.currentJackpot.getPlayerWinnings();
        this.startNewJackpot();
        JackpotHistory history = this.jackpotHistory.computeIfAbsent(winner.getUuid(), e -> new JackpotHistory(winner.getUsername()));
        history.setJackpotWinnings(history.getJackpotWinnings() + (long)winnings);
        history.setJackpotWins(history.getJackpotWins() + 1.0);
        Player pl = Bukkit.getPlayer(winner.getUuid());
        if (pl == null) {
            pl = (Player) Bukkit.getOfflinePlayer(winner.getUuid());
        } else {
            pl.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "+ $" + ChatColor.GREEN + this.moneyFormat.format(winnings));
        }
        AstroCore.economy.depositPlayer(pl, winnings);
        Bukkit.getLogger().info("Depositing $" + winnings + " into " + pl.getName() + "'s player (" + "Online" + ").");
        this.taxesCollected = (long)((double)this.taxesCollected + taxesCollected);
        this.saveWinnings();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "contest add " + winner.getUuid() + " lotteryMoneyWin " + winnings);
    }

    public Inventory getJackpotConfirmMenu(int ticketsToBuy, double totalCost) {
        Inventory inv = Bukkit.createInventory(null, 9, "Confirm Ticket Purchase");
        for (int i = 5; i < 9; ++i) {
            inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, DyeColor.RED.getWoolData(), ChatColor.RED + ChatColor.BOLD.toString() + "Cancel Purchase", Lists.newArrayList(ChatColor.GRAY + "Click to " + ChatColor.RED + "cancel" + ChatColor.GRAY + " ticket purchase.")));
        }
        ArrayList<String> lore = Lists.newArrayList(ChatColor.GRAY + "Click to " + ChatColor.GREEN + "confirm" + ChatColor.GRAY + " purchase of", ChatColor.GREEN + this.moneyFormat.format(ticketsToBuy) + ChatColor.GRAY + " ticket(s) for " + ChatColor.GREEN + ChatColor.BOLD + "$" + ChatColor.GREEN + this.moneyFormat.format(totalCost));
        if (ticketsToBuy > 1) {
            lore.add(ChatColor.GRAY + "at " + ChatColor.GREEN + ChatColor.BOLD.toString() + "$" + ChatColor.GREEN + this.moneyFormat.format(this.currentJackpot.getTicketPrice()) + "/ea");
        }
        for (int i = 0; i < 4; ++i) {
            inv.setItem(i, ItemUtils.createItem(Material.STAINED_GLASS_PANE, DyeColor.LIME.getWoolData(), ChatColor.GREEN + ChatColor.BOLD.toString() + "Confirm Purchase", lore));
        }
        return inv;
    }

    public void startNewJackpot() {
        Bukkit.getLogger().info("(CosmicJackpot) Starting new JackPot" + (this.currentJackpot == null ? "!" : ", previous Jackpot: $" + this.currentJackpot.getTotalWinnings()));
        this.currentJackpot = new Jackpot(this.defaultDrawTime);
    }

    public void loadFileData(JavaPlugin plugin) throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        plugin.saveDefaultConfig();
        Jackpot.TAX = plugin.getConfig().getDouble("tax", 0.1);
        this.taxesCollected = plugin.getConfig().getLong("taxesCollected");
        this.defaultDrawTime = plugin.getConfig().getInt("drawTime", 14400);
        this.topFile = this.getOrCreate("jackpotHistory.json");
        this.jackpotHistory = this.gson.fromJson(new FileReader(this.topFile), this.jackpotHistoryToken);
        if (this.jackpotHistory == null) {
            this.jackpotHistory = new HashMap<UUID, JackpotHistory>();
        }
        this.currentJackpotFile = this.getOrCreate("currentJackpot.json");
        this.currentJackpot = this.gson.fromJson(new FileReader(this.currentJackpotFile), this.jackpotToken);
        if (this.currentJackpot == null) {
            this.startNewJackpot();
        } else {
            Bukkit.getLogger().info("Loaded previously running Jackpot drawing: " + this.currentJackpot);
        }
        this.notificationToggleFile = JSONUtils.getOrCreateFile(plugin.getDataFolder(), "notificationToggles.json");
        this.toggledNofications = (Set)JSONUtils.fromJson(this.notificationToggleFile, this.togglesToken);
        if (this.toggledNofications == null) {
            this.toggledNofications = new HashSet<UUID>();
        }
        Bukkit.getLogger().info("Loaded " + this.jackpotHistory.size() + " entries into the Jackpot History list.");
    }

    private File getOrCreate(String name) throws IOException {
        File file = new File(AstroCore.getInstance().getDataFolder(), name);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void saveFileData() throws IOException {
        String string = this.gson.toJson(this.jackpotHistory);
        FileWriter writer = new FileWriter(this.topFile);
        writer.write(string);
        writer.flush();
        writer.close();
        writer = new FileWriter(this.currentJackpotFile);
        writer.write(this.gson.toJson(this.currentJackpot));
        writer.flush();
        writer.close();
        JSONUtils.saveJSONToFile(this.notificationToggleFile, this.toggledNofications, this.togglesToken);
    }

    public void saveWinnings() {
        AstroCore.getInstance().getConfig().set("taxesCollected", (Object)this.taxesCollected);
        AstroCore.getInstance().saveConfig();
    }

    public LinkedList<JackpotHistory> getSortedJackpotHistory() {
        if (this.lastListUpdate == 0L || System.currentTimeMillis() - this.lastListUpdate >= 10000L) {
            this.lastListUpdate = System.currentTimeMillis();
            this.sortedHistory = new LinkedList();
            for (JackpotHistory hist : this.jackpotHistory.values()) {
                if (hist.getJackpotWins() <= 0.0 || hist.getJackpotWinnings() <= 0L) continue;
                this.sortedHistory.add(hist);
            }
            Collections.sort(this.sortedHistory);
        }
        return this.sortedHistory;
    }

    public Set<UUID> getToggledNofications() {
        return this.toggledNofications;
    }

    public Map<UUID, JackpotHistory> getJackpotHistory() {
        return this.jackpotHistory;
    }

    public DecimalFormat getMoneyFormat() {
        return this.moneyFormat;
    }

    public Jackpot getCurrentJackpot() {
        return this.currentJackpot;
    }

    public void setDefaultDrawTime(int defaultDrawTime) {
        this.defaultDrawTime = defaultDrawTime;
    }
}

