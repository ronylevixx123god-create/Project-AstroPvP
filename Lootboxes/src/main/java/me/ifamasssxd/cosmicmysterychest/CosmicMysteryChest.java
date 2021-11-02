/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicmysterychest;

import me.ifamasssxd.cosmicmysterychest.commands.CommandLootboxFilter;
import me.ifamasssxd.cosmicmysterychest.commands.CommandLootboxToggle;
import me.ifamasssxd.cosmicmysterychest.commands.CommandMysteryChest;
import me.ifamasssxd.cosmicmysterychest.data.Filter;
import me.ifamasssxd.cosmicmysterychest.halflootbox.CommandHalfLootbox;
import me.ifamasssxd.cosmicmysterychest.halflootbox.HalfLootboxApplyListener;
import me.ifamasssxd.cosmicmysterychest.items.MysteryLootboxItem;
import me.ifamasssxd.cosmicmysterychest.listeners.MysteryChestListener;
import me.ifamasssxd.cosmicmysterychest.managers.FilterManager;
import me.ifamasssxd.cosmicmysterychest.managers.MysteryChestManager;
import me.ifamasssxd.cosmicmysterychest.sql.Database;
import me.ifamasssxd.cosmicmysterychest.sql.SQLQueryCustomThread;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class CosmicMysteryChest
        extends JavaPlugin {
    private static CosmicMysteryChest instance;
    private ShardAnimations toggle;
    private MysteryChestManager chestManager;
    private MysteryLootboxItem mysteryLootboxItem;
    private FilterManager filterManager;
    private HashMap<UUID, HashMap<MysteryChest, Filter>> filters;
    private Database sqlDatabase;

    public ShardAnimations getShardAnimations() {
        return this.toggle;
    }

    public void onEnable() {
        instance = this;
        instance.saveDefaultConfig();
        this.saveDefaultConfig();
        this.toggle = new ShardAnimations("toggles.yml");
        this.chestManager = new MysteryChestManager();
        this.chestManager.onEnable(this);
        this.getCommand("mysterychest").setExecutor(new CommandMysteryChest(this));
        this.getCommand("lbtoggle").setExecutor(new CommandLootboxToggle());
        this.getCommand("givehalflootbox").setExecutor(new CommandHalfLootbox());
        this.getCommand("lbfilter").setExecutor(new CommandLootboxFilter());
        this.getServer().getPluginManager().registerEvents(new MysteryChestListener(), this);
        this.getServer().getPluginManager().registerEvents(new HalfLootboxApplyListener(), this);
        this.filterManager = new FilterManager();
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            this.mysteryLootboxItem = new MysteryLootboxItem();
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem(this.mysteryLootboxItem);
        }

        this.filters = new HashMap<>();
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Collect Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS lootboxfilter_data(uuid varchar(64), mysteryChest varchar(64), blockedLoot longblob);", false);
            SQLQueryCustomThread sqlQueryCustomThread = new SQLQueryCustomThread();
            sqlQueryCustomThread.start();
        }
        System.out.println("Started Loading Lootboxes Database");

        filterManager.load();

    }

    public HashMap<UUID, HashMap<MysteryChest, Filter>> getFilters() {
        return filters;
    }

    public void onDisable() {
        this.toggle.saveToggles();
        System.out.println("Started Saving Lootboxes Database");

        filterManager.save();
        if (this.chestManager != null) {
            this.chestManager.onDisable(this);
        }

    }

    public static CosmicMysteryChest get() {
        return instance;
    }

    public MysteryChestManager getChestManager() {
        return this.chestManager;
    }

    public MysteryLootboxItem getMysteryLootboxItem() {
        return this.mysteryLootboxItem;
    }

    public FilterManager getFilterManager() { return this.filterManager; }

    public static CosmicMysteryChest getInstance() {
        return instance;
    }

    public Database getSqlDatabase() {
        return sqlDatabase;
    }
}

