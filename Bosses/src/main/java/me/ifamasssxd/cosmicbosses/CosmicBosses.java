package me.ifamasssxd.cosmicbosses;

import org.bukkit.plugin.java.*;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.*;
import org.bukkit.entity.*;
import me.ifamasssxd.cosmicbosses.entities.*;
import me.ifamasssxd.cosmicbosses.utils.*;
import me.ifamasssxd.factionupgrades.struct.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import me.ifamasssxd.cosmicbosses.listeners.*;
import org.bukkit.command.*;
import me.ifamasssxd.cosmicbosses.commands.*;
import me.ifamasssxd.cosmicbosses.tasks.*;
import com.google.common.collect.*;
import org.bukkit.*;
import me.ifamasssxd.cosmicbosses.bosses.*;
import me.ifamasssxd.cosmicbosses.dungeonboss.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import me.ifamasssxd.cosmicbosses.bosses.dragon.*;
import org.bukkit.event.entity.*;

public class CosmicBosses extends JavaPlugin
{
    private static CosmicBosses instance;
    private static boolean mcMMOEnabled;
    private boolean eggEnabled;
    private boolean naturalSpawnEnabled;
    private boolean scoreboardEnabled;
    private boolean factionUpgradesEnabled;
    private BlockChangeManager blockChangeManager;
    private BossSpawnTask task;
    public static String battlefieldWorld;
    private HashMap<String, List<String[]>> pendingBosses;
    
    public CosmicBosses() {
        this.eggEnabled = false;
        this.naturalSpawnEnabled = false;
        this.scoreboardEnabled = true;
        this.pendingBosses = new HashMap<String, List<String[]>>();
    }
    
    public static CosmicBosses get() {
        return CosmicBosses.instance;
    }
    
    public void onEnable() {
        (CosmicBosses.instance = this).saveDefaultConfig();
        CosmicBosses.mcMMOEnabled = Bukkit.getPluginManager().isPluginEnabled("mcMMO");
        this.registerCommands();
        this.registerListeners();
        this.registerTasks();
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            PlayerListener.aSyncPlayerMap.put(p.getUniqueId(), p);
            ScoreboardUtils.clearBossScoreboard(p);
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
                final FactionUpgrade upgrade = FactionUpgrade.CONQUEST_MASTERY;
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
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            ScoreboardUtils.clearBossScoreboard(p);
        }
        CustomEntity.unregisterEntities();
    }
    
    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new MinionListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BossListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new SkeletonHorseListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new WitherBossListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DungeonBossListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new EggListener(), (Plugin)this);
        if (CosmicBosses.mcMMOEnabled) {
            this.getServer().getPluginManager().registerEvents((Listener)new McmmoListener(), (Plugin)this);
            System.out.println("Registering mcmmo listener.");
        }
    }
    
    private void registerCommands() {
        this.getCommand("boss").setExecutor((CommandExecutor)new CommandBoss());
        this.getCommand("spawnboss").setExecutor((CommandExecutor)new CommandSpawnBoss());
        this.getCommand("bossrewards").setExecutor((CommandExecutor)new CommandBossRewards());
    }
    
    private void registerTasks() {
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BossUpdateTask(), 20L, 5L);
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BossTickTask(), 1L, 1L);
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BleedTask(), 0L, 10L);
        this.task = new BossSpawnTask();
        final int delay = 72000;
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)this.task, (long)delay, (long)delay);
    }
    
    public void loadBosses() {
        for (final String boss : this.getConfig().getStringList("bosses")) {
            final String[] args = boss.split(":");
            final World world = Bukkit.getWorld(args[1]);
            final Location loc = new Location(world, (double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3]), (double)Integer.parseInt(args[4]));
            final String chunkCoords = loc.getChunk().getX() + ":" + loc.getChunk().getZ();
            List<String[]> bosses = Lists.newArrayList();
            if (this.pendingBosses.containsKey(chunkCoords)) {
                bosses = this.pendingBosses.get(chunkCoords);
            }
            bosses.add(args);
            System.out.println("Loading boss at " + chunkCoords + " -> " + args[0]);
            this.pendingBosses.put(chunkCoords, bosses);
        }
        this.getConfig().set("bosses", (Object)null);
        this.saveConfig();
    }
    
    public void saveBosses() {
        final List<String> bossList = Lists.newArrayList();
        for (final Boss boss : Boss.bosses.values()) {
            if (boss.isBossSpawned()) {
                if (boss instanceof GuardBoss || boss instanceof DungeonYijki || boss instanceof MiniBoss || boss instanceof EndDragon) {
                    boss.onDeath(null);
                    boss.getEntity().remove();
                }
                else {
                    final Location loc = boss.getEntity().getLocation();
                    final Location l = boss.getLocation();
                    bossList.add(boss.getBossType().name() + ":" + loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + l.getBlockX() + ":" + l.getBlockY() + ":" + l.getBlockZ() + ":" + boss.getEntity().getHealth());
                    boss.onDeath(null);
                    boss.getEntity().remove();
                }
            }
        }
        this.reloadConfig();
        this.getConfig().set("bosses", (Object)bossList);
        this.saveConfig();
    }
    
    public static CosmicBosses getInstance() {
        return CosmicBosses.instance;
    }
    
    public static boolean isMcMMOEnabled() {
        return CosmicBosses.mcMMOEnabled;
    }
    
    public boolean isEggEnabled() {
        return this.eggEnabled;
    }
    
    public boolean isNaturalSpawnEnabled() {
        return false;//this.naturalSpawnEnabled;
    }
    
    public boolean isScoreboardEnabled() {
        return this.scoreboardEnabled;
    }
    
    public boolean isFactionUpgradesEnabled() {
        return this.factionUpgradesEnabled;
    }
    
    public void setEggEnabled(final boolean eggEnabled) {
        this.eggEnabled = eggEnabled;
    }
    
    public void setNaturalSpawnEnabled(final boolean naturalSpawnEnabled) {
        this.naturalSpawnEnabled = naturalSpawnEnabled;
    }
    
    public void setScoreboardEnabled(final boolean scoreboardEnabled) {
        this.scoreboardEnabled = scoreboardEnabled;
    }
    
    public void setFactionUpgradesEnabled(final boolean factionUpgradesEnabled) {
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
        CosmicBosses.mcMMOEnabled = false;
        CosmicBosses.battlefieldWorld = "world_arena";
    }
}
