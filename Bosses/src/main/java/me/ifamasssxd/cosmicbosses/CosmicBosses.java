/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicbosses;

import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import lombok.SneakyThrows;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.commands.CommandBoss;
import me.ifamasssxd.cosmicbosses.commands.CommandBossRewards;
import me.ifamasssxd.cosmicbosses.commands.CommandSpawnBoss;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.listeners.*;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.MiniBoss;
import me.ifamasssxd.cosmicbosses.struct.RewardInventory;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.BlockChangeManager;
import me.ifamasssxd.cosmicbosses.tasks.BleedTask;
import me.ifamasssxd.cosmicbosses.tasks.BossSpawnTask;
import me.ifamasssxd.cosmicbosses.tasks.BossUpdateTask;
import me.ifamasssxd.cosmicbosses.utils.MapUtils;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.utils.ScoreboardUtils;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicBosses
extends JavaPlugin {
    private static CosmicBosses instance;
    private static CosmicBosses cbe;
    private static boolean mcMMOEnabled;
    private boolean eggEnabled = false;
    private boolean naturalSpawnEnabled = false;
    private boolean scoreboardEnabled = true;
    private boolean factionUpgradesEnabled;
    private BlockChangeManager blockChangeManager;
    private BossSpawnTask task;
    public static String battlefieldWorld;
    private HashMap<String, List<String[]>> pendingBosses = new HashMap();

    public static CosmicBosses get() {
        return instance;
    }

    @SneakyThrows
    public void onEnable() {
        cbe = this;
        instance = this;

        this.saveDefaultConfig();
        mcMMOEnabled = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        this.registerCommands();
        this.registerListeners();
        this.registerTasks();
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerListener.aSyncPlayerMap.put(p.getUniqueId(), p);
            // SCOREBOARD OOF ScoreboardUtils.clearBossScoreboard(p);
        }
        CustomEntity.registerEntities();
        StringUtils.getCenteredMessage(" Swag");
        MapUtils.sortByComparator(null, true, 0);
        WorldGuardUtils.hook();
        this.eggEnabled = this.getConfig().getBoolean("eggEnabled", false);
        this.naturalSpawnEnabled = this.getConfig().getBoolean("naturalSpawn", false);
        this.scoreboardEnabled = this.getConfig().getBoolean("scoreboardEnabled", true);
        this.factionUpgradesEnabled = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        if (this.factionUpgradesEnabled) {
            try {
                FactionUpgrade upgrade = FactionUpgrade.CONQUEST_MASTERY;
                Bukkit.getLogger().info("Hook loaded for Faction Upgrades.");
            }
            catch (Exception e) {
                Bukkit.getLogger().info("Unable to load new faction ugprade support!");
                this.factionUpgradesEnabled = false;
            }
        }
        this.loadBosses();
        this.blockChangeManager = new BlockChangeManager();
        RewardInventory.loadBossRewards(this);
    }

    public void onDisable() {
        RewardInventory.saveBossRewards();
        this.blockChangeManager.restoreAllBlocks();
        this.saveBosses();
        for (Player p : Bukkit.getOnlinePlayers()) {
            // SCOREBOARD OOF ScoreboardUtils.clearBossScoreboard(p);
        }
        CustomEntity.unregisterEntities();

        for (Entity ent : me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.keySet()) {
            LivingEntity le = (LivingEntity)ent;
            le.damage(2.147483647E9);
            le.remove();
        }
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BossEggListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BossListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DungeonBossListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EggListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new MinionListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new RewardInventoryListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SkeletonHorseListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new WitherBossListener(), (Plugin)this);

        if (mcMMOEnabled) {
            this.getServer().getPluginManager().registerEvents((Listener)new McmmoListener(), (Plugin)this);
            System.out.println("Registering mcmmo listener.");
        }
    }

    private void registerCommands() {
        this.getCommand("boss").setExecutor((CommandExecutor)new CommandBoss());
        this.getCommand("spawnboss").setExecutor((CommandExecutor)new CommandSpawnBoss());
        this.getCommand("bossrewards").setExecutor((CommandExecutor)new CommandBossRewards());
        //this.getCommand("???").setExecutor((CommandExecutor)new CommandBossEgg()); the fuck
    }

    private void registerTasks() {
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BossUpdateTask(), 20L, 5L);
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BleedTask(), 0L, 10L);
        this.task = new BossSpawnTask();
        int delay = 72000;
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)this.task, (long)delay, (long)delay);
    }

    public void loadBosses() {
        for (String boss : this.getConfig().getStringList("bosses")) {
            String[] args = boss.split(":");
            World world = Bukkit.getWorld((String)args[1]);
            Location loc = new Location(world, (double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3]), (double)Integer.parseInt(args[4]));
            String chunkCoords = loc.getChunk().getX() + ":" + loc.getChunk().getZ();
            List<String[]> bosses = Lists.newArrayList();
            if (this.pendingBosses.containsKey(chunkCoords)) {
                bosses = this.pendingBosses.get(chunkCoords);
            }
            bosses.add(args);
            System.out.println("Loading boss at " + chunkCoords + " -> " + args[0]);
            this.pendingBosses.put(chunkCoords, bosses);
        }
        this.getConfig().set("bosses", null);
        this.saveConfig();
    }

    public void saveBosses() {
        ArrayList<String> bossList = Lists.newArrayList();
        for (Boss boss : Boss.bosses.values()) {
            if (!boss.isBossSpawned()) continue;
            if (boss instanceof GuardBoss || boss instanceof DungeonYijki || boss instanceof MiniBoss || boss instanceof EndDragon) {
                boss.onDeath(null);
                boss.getEntity().remove();
                continue;
            }
            Location loc = boss.getEntity().getLocation();
            Location l = boss.getLocation();
            bossList.add(boss.getBossType().name() + ":" + loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ() + ":" + boss.getEntity().getHealth());
            boss.onDeath(null);
            boss.getEntity().remove();
        }
        this.reloadConfig();
        this.getConfig().set("bosses", (Object)bossList);
        this.saveConfig();
    }

    public static CosmicBosses getInstance() {
        return instance;
    }

    public static boolean isMcMMOEnabled() {
        return mcMMOEnabled;
    }

    public boolean isEggEnabled() {
        return this.eggEnabled;
    }

    public boolean isNaturalSpawnEnabled() {
        return this.naturalSpawnEnabled;
    }

    public boolean isScoreboardEnabled() {
        return this.scoreboardEnabled;
    }

    public boolean isFactionUpgradesEnabled() {
        return this.factionUpgradesEnabled;
    }

    public void setEggEnabled(boolean eggEnabled) {
        this.eggEnabled = eggEnabled;
    }

    public void setNaturalSpawnEnabled(boolean naturalSpawnEnabled) {
        this.naturalSpawnEnabled = naturalSpawnEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;
    }

    public void setFactionUpgradesEnabled(boolean factionUpgradesEnabled) {
        this.factionUpgradesEnabled = factionUpgradesEnabled;
    }

    public BlockChangeManager getBlockChangeManager() {
        return this.blockChangeManager;
    }

    public BossSpawnTask getTask() {
        return this.task;
    }

    public HashMap<String, List<String[]>> getPendingBosses() {
        return this.pendingBosses;
    }

    static {
        mcMMOEnabled = false;
        battlefieldWorld = "world_arena";
    }
}

