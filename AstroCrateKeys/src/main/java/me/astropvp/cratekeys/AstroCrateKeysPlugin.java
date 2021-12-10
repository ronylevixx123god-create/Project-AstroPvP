package me.astropvp.cratekeys;

import lombok.Getter;
import me.astropvp.cratekeys.commands.CommandAdminCrate;
import me.astropvp.cratekeys.commands.CommandGiveCrate;
import me.astropvp.cratekeys.commands.CommandGiveCrateKey;
import me.astropvp.cratekeys.data.Filter;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.listeners.CrateKeyInventoryListener;
import me.astropvp.cratekeys.listeners.CrateLogicListener;
import me.astropvp.cratekeys.managers.CrateDataManager;
import me.astropvp.cratekeys.managers.FilterManager;
import me.astropvp.cratekeys.sql.Database;
import me.astropvp.cratekeys.sql.SQLQueryCustomThread;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AstroCrateKeysPlugin extends JavaPlugin {
    public static AstroCrateKeysPlugin AstroCrateKeysPlugin;
    public static FileConfiguration config;
    public static HashMap<CrateType, Map<ItemStack, Double>> cachedCrateLoot;
    private FilterManager filterManager;
    @Getter
    private static HashMap<UUID, HashMap<CrateType, Filter>> filters;
    @Getter
    private Database sqlDatabase;


    public void onEnable() {
        AstroCrateKeysPlugin = this;
        registerListeners();
        registerCommands();
        CrateDataManager.init();
        config = getConfig();
        cachedCrateLoot = new HashMap<>();
        for (CrateType crateType : CrateType.values()) {
            cachedCrateLoot.put(crateType, AstroCrateKeysAPI.getItems(crateType));
        }
        this.filterManager = new FilterManager();


        filters = new HashMap<>();
        this.sqlDatabase = new Database(this);
        if(this.sqlDatabase.isConnected())
            Bukkit.getConsoleSender().sendMessage("SQL Database for Collect Connected");
        if (!this.sqlDatabase.isConnected()) {
            Bukkit.getLogger().severe("Unable to connect to sql database! Please ensure the sql credentials are correct");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            this.sqlDatabase.executeUpdate("CREATE TABLE IF NOT EXISTS cratekeyfilter_data(uuid varchar(64), crateType varchar(64), blockedLoot longblob);", false);
            SQLQueryCustomThread sqlQueryCustomThread = new SQLQueryCustomThread();
            sqlQueryCustomThread.start();
        }
        System.out.println("Started Loading CrateType Database");

        filterManager.load();
    }

    public void onDisable() {
        CrateDataManager.onDisable();
        this.filterManager.save();
        AstroCrateKeysPlugin = null;
    }

    public static Map<CrateType, Map<ItemStack, Double>> getCachedCrateLoot() {
        return cachedCrateLoot;
    }

    private void registerListeners() {
        registerListener((Listener)new CrateLogicListener());
        registerListener((Listener)new CrateKeyInventoryListener());
    }

    private void registerCommands() {
        getCommand("givecratekey").setExecutor((CommandExecutor)new CommandGiveCrateKey());
        getCommand("givecrate").setExecutor((CommandExecutor)new CommandGiveCrate());
        getCommand("adminchestcrate").setExecutor((CommandExecutor)new CommandAdminCrate());
    }



    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, (Plugin) AstroCrateKeysPlugin);
    }

    public static AstroCrateKeysPlugin getInstance() {
        return AstroCrateKeysPlugin;
    }

}
