/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package org.arkhamnetwork.Arkkit.patches.newtrade;

import com.earth2me.essentials.Essentials;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BetterTrade
        extends ArkPlugin {
    private final Map<UUID, PlayerTrade> playerTradeMap = new MapMaker().concurrencyLevel(32).makeMap();
    private BukkitTask tradeTask;
    private Essentials ess;

    public BetterTrade(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        }
        if (ArkPlugins.TRADING.getArkPlugin().isRunning()) {
            ArkPlugins.TRADING.getArkPlugin().kill();
            Bukkit.getLogger().info("Killing old trade system for new one..");
        }
        this.registerListener(new BetterTradeListener(this));
        this.registerCommand(new BetterTradeCommand(this, "trade", "/<command>", "Trade items with other players!", Lists.newArrayList(new String[]{"trade", "trades", "exchange"})));
        this.tradeTask = new BukkitRunnable() {

            public void run() {
                BetterTrade.this.playerTradeMap.values().forEach(PlayerTrade::onTradeTick);
            }
        }.runTaskTimer(Arkkit.get(), 13L, 13L);
        super.enable();
    }

    @Override
    public void disable() {
        if (this.playerTradeMap.size() > 0) {
            Bukkit.getLogger().info("Cancelling " + this.playerTradeMap.values() + " player trades.");
            this.playerTradeMap.values().forEach(trade -> trade.cancelTrade(null));
        }
        this.unregisterListeners();
        this.unregisterCommands();
        if (this.tradeTask != null) {
            this.tradeTask.cancel();
        }
    }

    public PlayerTrade getCurrentTrade(Player player) {
        return this.playerTradeMap.get(player.getUniqueId());
    }

    public void unregisterTrade(PlayerTrade trade) {
        this.playerTradeMap.remove(trade.getPlayer1().getUniqueId());
        this.playerTradeMap.remove(trade.getPlayer2().getUniqueId());
    }

    public void startPlayerTrade(Player player, Player player2) {
        this.clearTeleportRequests(player);
        this.clearTeleportRequests(player2);
        player.closeInventory();
        player2.closeInventory();
        PlayerTrade trade = new PlayerTrade(player, player2);
        this.playerTradeMap.put(player.getUniqueId(), trade);
        this.playerTradeMap.put(player2.getUniqueId(), trade);
        trade.openTradeMenu();
        trade.playSound(Sound.WOOD_CLICK, 1.0f, 0.8f);
        player.sendMessage(ChatColor.YELLOW + "Trading with " + ChatColor.BOLD + player2.getName() + ChatColor.YELLOW + "...");
        player2.sendMessage(ChatColor.YELLOW + "Trading with " + ChatColor.BOLD + player.getName() + ChatColor.YELLOW + "...");
    }

    private void clearTeleportRequests(Player p) {
    }

    public Map<UUID, PlayerTrade> getPlayerTradeMap() {
        return this.playerTradeMap;
    }
}

