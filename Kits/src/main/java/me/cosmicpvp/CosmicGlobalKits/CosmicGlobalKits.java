/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.Connect
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.cosmicpvp.CosmicGlobalKits;

import lombok.Getter;
import lombok.Setter;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandGKit;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandMKit;
import me.cosmicpvp.CosmicGlobalKits.database.SQLManager;
import me.cosmicpvp.CosmicGlobalKits.database.SQLQueryThread;
import me.cosmicpvp.CosmicGlobalKits.items.MysteryFallenHeroItem;
import me.cosmicpvp.CosmicGlobalKits.items.WildcardGkit;
import me.cosmicpvp.CosmicGlobalKits.listeners.*;
import me.cosmicpvp.CosmicGlobalKits.struct.GlobalKitUser;
import me.cosmicpvp.CosmicGlobalKits.utils.WorldGuardUtils;
import me.ifamasssxd.cosmicevolutionkits.commands.CommandEvolutionKit;
import me.ifamasssxd.cosmicevolutionkits.items.WildcardVkit;
import me.ifamasssxd.cosmicevolutionkits.listeners.ItemListener;
import me.ifamasssxd.cosmicevolutionkits.sql.Database;
import me.ifamasssxd.cosmicevolutionkits.sql.SQLQueryCustomThread;
import me.ifamasssxd.cosmicevolutionkits.structure.EvolutionKitManager;
import me.ifamasssxd.cosmicevolutionkits.structure.KitPlayer;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CosmicGlobalKits extends JavaPlugin {

    @Getter private static CosmicGlobalKits plugin = null;

    @Getter @Setter public static String sql_table = "globalkits_userdata";
    @Getter @Setter public static String mastery_table = "masterykit_userdata";
    @Getter @Setter private SQLQueryThread sql_worker;
    @Getter @Setter public static boolean kitGemsEnabled = true;
    @Getter @Setter private String serverName;
    @Getter private final Map<UUID, GlobalKitUser> globalKitUsers = new HashMap<UUID, GlobalKitUser>();
    @Getter private static final boolean debug = false;

    @Getter @Setter private Database sqlDatabase;
    @Getter @Setter private EvolutionKitManager kitManager;
    @Getter @Setter private SQLQueryCustomThread sqlQueryThread = new SQLQueryCustomThread();

    public void onEnable() {
        plugin = this;
        WorldGuardUtils.hook();
        this.saveDefaultConfig();
        this.registerCommands();
        this.registerListeners();
        kitGemsEnabled = this.getConfig().getBoolean("kitGemsEnabled");
        SQLManager.initializeSQL(this);
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS " + sql_table + "(player_uuid VARCHAR(64), player_name VARCHAR(16), global_kit TEXT, last_use LONG)");
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS globalkit_player_tiers(player_uuid VARCHAR(64), player_name VARCHAR(16), global_kit varchar(64), tier int, server_name varchar(32))");
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS masterykit_userdata(uuid VARCHAR(64), masteryKit VARCHAR(64), last_use bigint, tier int)");
        this.sql_worker = new SQLQueryThread();
        this.sql_worker.start();
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new WildcardGkit());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new MysteryFallenHeroItem());
        }

        this.kitManager = new EvolutionKitManager();
        this.kitManager.startInventoryUpdater();
        if (this.serverName.equalsIgnoreCase("ancientplanet")) {
            this.serverName = "forgottenplanet";
        }
        this.sqlDatabase = new Database("sqlconfig.yml");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS `" + this.serverName + "_evolutionKitData`(uuid varchar(64), name varchar(16), kitName varchar(64), kitLevel int, lastKitUse long, numberOfUses int, PRIMARY KEY (uuid, kitName));", false);
        this.sqlQueryThread = new SQLQueryCustomThread();
        this.sqlQueryThread.start();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            KitPlayer kit = new KitPlayer(pl.getUniqueId(), pl.getName());
            KitPlayer.kitPlayerMap.put(pl.getUniqueId(), kit);
            kit.loadData(false);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new WildcardVkit());
        }
    }

    public void onDisable() {
        this.sql_worker.interrupt();
        this.sql_worker.doWork();
        this.sql_worker = null;

        if (this.sqlQueryThread != null) {
            this.sqlQueryThread.interrupt();
            this.sqlQueryThread.execute();
            this.sqlQueryThread = null;
        }
    }

    public GlobalKitUser getGlobalKitUser(UUID uuid) {
        return this.globalKitUsers.get(uuid);
    }

    private void registerCommands() {
        this.getCommand("gkit").setExecutor(new CommandGKit());
        this.getCommand("mkit").setExecutor(new CommandMKit());
        this.getCommand("fallenhero").setExecutor(new CommandFallenHero());

        this.getCommand("vkit").setExecutor(new CommandEvolutionKit());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
        this.getServer().getPluginManager().registerEvents(new FallenHeroListener(), this);
        this.getServer().getPluginManager().registerEvents(new HeroicBundleListener(), this);
        if (Bukkit.getPluginManager().isPluginEnabled("CompilexPermissions")) {
            this.getServer().getPluginManager().registerEvents(new PermissionListener(), this);
        }
        this.getServer().getPluginManager().registerEvents(new MasteryKitListener(), this);

        this.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new me.ifamasssxd.cosmicevolutionkits.listeners.LoginListener(), this);
        this.getServer().getPluginManager().registerEvents(new me.ifamasssxd.cosmicevolutionkits.listeners.InventoryListener(), this);
    }

    public static void log(String msg) {
        if (!debug) {
            return;
        }
        Bukkit.getLogger().info("[CosmicGlobalKits] " + msg);
    }

}

