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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lilypad.client.connect.api.Connect;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandFallenHero;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandGKit;
import me.cosmicpvp.CosmicGlobalKits.commands.CommandMKit;
import me.cosmicpvp.CosmicGlobalKits.database.SQLManager;
import me.cosmicpvp.CosmicGlobalKits.database.SQLQueryThread;
import me.cosmicpvp.CosmicGlobalKits.items.MysteryFallenHeroItem;
import me.cosmicpvp.CosmicGlobalKits.items.WildcardGkit;
import me.cosmicpvp.CosmicGlobalKits.listeners.FallenHeroListener;
import me.cosmicpvp.CosmicGlobalKits.listeners.HeroicBundleListener;
import me.cosmicpvp.CosmicGlobalKits.listeners.InventoryListener;
import me.cosmicpvp.CosmicGlobalKits.listeners.LoginListener;
import me.cosmicpvp.CosmicGlobalKits.listeners.MasteryKitListener;
import me.cosmicpvp.CosmicGlobalKits.listeners.PermissionListener;
import me.cosmicpvp.CosmicGlobalKits.struct.GlobalKitUser;
import me.cosmicpvp.CosmicGlobalKits.utils.WorldGuardUtils;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicGlobalKits
extends JavaPlugin {
    public static String sql_table = "globalkits_userdata";
    public static String mastery_table = "masterykit_userdata";
    private static CosmicGlobalKits plugin = null;
    private SQLQueryThread sql_worker;
    public static boolean kitGemsEnabled = true;
    private String serverName;
    private Map<UUID, GlobalKitUser> globalKitUsers = new HashMap<UUID, GlobalKitUser>();
    private static boolean debug = false;

    public void onEnable() {
        plugin = this;
        WorldGuardUtils.hook();
        this.saveDefaultConfig();
        this.registerCommands();
        this.registerListeners();
        kitGemsEnabled = this.getConfig().getBoolean("kitGemsEnabled");
        SQLManager.initializeSQL((Plugin)this);
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS " + sql_table + "(player_uuid VARCHAR(64), player_name VARCHAR(16), global_kit TEXT, last_use LONG)");
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS globalkit_player_tiers(player_uuid VARCHAR(64), player_name VARCHAR(16), global_kit varchar(64), tier int, server_name varchar(32))");
        SQLManager.sql_query.add("CREATE TABLE IF NOT EXISTS masterykit_userdata(uuid VARCHAR(64), masteryKit VARCHAR(64), last_use bigint, tier int)");
        this.sql_worker = new SQLQueryThread();
        this.sql_worker.start();
        Connect connect = (Connect)this.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        this.serverName = connect.getSettings().getUsername();
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new WildcardGkit());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new MysteryFallenHeroItem());
        }
    }

    public void onDisable() {
        this.sql_worker.interrupt();
        this.sql_worker.doWork();
        this.sql_worker = null;
    }

    public GlobalKitUser getGlobalKitUser(UUID uuid) {
        return this.globalKitUsers.get(uuid);
    }

    private void registerCommands() {
        this.getCommand("gkit").setExecutor((CommandExecutor)new CommandGKit());
        this.getCommand("mkit").setExecutor((CommandExecutor)new CommandMKit());
        this.getCommand("fallenhero").setExecutor((CommandExecutor)new CommandFallenHero());
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new LoginListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new FallenHeroListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new HeroicBundleListener(), (Plugin)this);
        if (Bukkit.getPluginManager().isPluginEnabled("CompilexPermissions")) {
            this.getServer().getPluginManager().registerEvents((Listener)new PermissionListener(), (Plugin)this);
        }
        this.getServer().getPluginManager().registerEvents((Listener)new MasteryKitListener(), (Plugin)this);
    }

    public static CosmicGlobalKits getPlugin() {
        return plugin;
    }

    public static void log(String msg) {
        if (!debug) {
            return;
        }
        Bukkit.getLogger().info("[CosmicGlobalKits] " + msg);
    }

    public String getServerName() {
        return this.serverName;
    }

    public Map<UUID, GlobalKitUser> getGlobalKitUsers() {
        return this.globalKitUsers;
    }
}

