/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.RegionQueryUtil;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FlagStateManager
implements Runnable {
    public static final int RUN_DELAY = 20;
    private WorldGuardPlugin plugin;
    private Map<String, PlayerFlagState> states;

    public FlagStateManager(WorldGuardPlugin plugin) {
        this.plugin = plugin;
        this.states = new HashMap<String, PlayerFlagState>();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Player[] players = this.plugin.getServer().getOnlinePlayers();
        ConfigurationManager config = this.plugin.getGlobalStateManager();
        for (Player player : players) {
            PlayerFlagState state;
            WorldConfiguration worldConfig = config.get(player.getWorld());
            if (!worldConfig.useRegions) continue;
            FlagStateManager flagStateManager = this;
            synchronized (flagStateManager) {
                state = this.states.get(player.getName());
                if (state == null) {
                    state = new PlayerFlagState();
                    this.states.put(player.getName(), state);
                }
            }
            Vector playerLocation = BukkitUtil.toVector(player.getLocation());
            RegionManager regionManager = this.plugin.getGlobalRegionManager().get(player.getWorld());
            ApplicableRegionSet applicable = regionManager.getApplicableRegions(playerLocation);
            if (RegionQueryUtil.isInvincible(this.plugin, player, applicable) || this.plugin.getGlobalStateManager().hasGodMode(player) || player.getGameMode() == GameMode.CREATIVE) continue;
            this.processHeal(applicable, player, state);
            this.processFeed(applicable, player, state);
        }
    }

    private void processHeal(ApplicableRegionSet applicable, Player player, PlayerFlagState state) {
        if (player.getHealth() <= 0.0) {
            return;
        }
        long now = System.currentTimeMillis();
        Integer healAmount = (Integer)applicable.getFlag(DefaultFlag.HEAL_AMOUNT);
        Integer healDelay = (Integer)applicable.getFlag(DefaultFlag.HEAL_DELAY);
        Double minHealth = (Double)applicable.getFlag(DefaultFlag.MIN_HEAL);
        Double maxHealth = (Double)applicable.getFlag(DefaultFlag.MAX_HEAL);
        if (healAmount == null || healDelay == null || healAmount == 0 || healDelay < 0) {
            return;
        }
        if (minHealth == null) {
            minHealth = 0.0;
        }
        if (maxHealth == null) {
            maxHealth = player.getMaxHealth();
        }
        minHealth = Math.min(player.getMaxHealth(), minHealth);
        maxHealth = Math.min(player.getMaxHealth(), maxHealth);
        if (player.getHealth() >= maxHealth && healAmount > 0) {
            return;
        }
        if (healDelay <= 0) {
            player.setHealth(healAmount > 0 ? maxHealth : minHealth);
            state.lastHeal = now;
        } else if (now - state.lastHeal > (long)(healDelay * 1000)) {
            player.setHealth(Math.min(maxHealth, Math.max(minHealth, player.getHealth() + (double)healAmount.intValue())));
            state.lastHeal = now;
        }
    }

    private void processFeed(ApplicableRegionSet applicable, Player player, PlayerFlagState state) {
        long now = System.currentTimeMillis();
        Integer feedAmount = (Integer)applicable.getFlag(DefaultFlag.FEED_AMOUNT);
        Integer feedDelay = (Integer)applicable.getFlag(DefaultFlag.FEED_DELAY);
        Integer minHunger = (Integer)applicable.getFlag(DefaultFlag.MIN_FOOD);
        Integer maxHunger = (Integer)applicable.getFlag(DefaultFlag.MAX_FOOD);
        if (feedAmount == null || feedDelay == null || feedAmount == 0 || feedDelay < 0) {
            return;
        }
        if (minHunger == null) {
            minHunger = 0;
        }
        if (maxHunger == null) {
            maxHunger = 20;
        }
        minHunger = Math.min(20, minHunger);
        maxHunger = Math.min(20, maxHunger);
        if (player.getFoodLevel() >= maxHunger && feedAmount > 0) {
            return;
        }
        if (feedDelay <= 0) {
            player.setFoodLevel(feedAmount > 0 ? maxHunger : minHunger);
            state.lastFeed = now;
        } else if (now - state.lastFeed > (long)(feedDelay * 1000)) {
            player.setFoodLevel(Math.min(maxHunger, Math.max(minHunger, player.getFoodLevel() + feedAmount)));
            state.lastFeed = now;
        }
    }

    public synchronized void forget(Player player) {
        this.states.remove(player.getName());
    }

    public synchronized void forgetAll() {
        this.states.clear();
    }

    public synchronized PlayerFlagState getState(Player player) {
        PlayerFlagState state = this.states.get(player.getName());
        if (state == null) {
            state = new PlayerFlagState();
            this.states.put(player.getName(), state);
        }
        return state;
    }

    public static class PlayerFlagState {
        public long lastHeal;
        public long lastFeed;
        public String lastGreeting;
        public String lastFarewell;
        public Boolean lastExitAllowed = null;
        public Boolean notifiedForLeave = false;
        public Boolean notifiedForEnter = false;
        public GameMode lastGameMode;
        public World lastWorld;
        public int lastBlockX;
        public int lastBlockY;
        public int lastBlockZ;
        public World lastInvincibleWorld;
        public int lastInvincibleX;
        public int lastInvincibleY;
        public int lastInvincibleZ;
        public boolean wasInvincible;
    }
}

