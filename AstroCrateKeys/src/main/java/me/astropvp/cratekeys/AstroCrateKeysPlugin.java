package me.astropvp.cratekeys;

import com.google.common.collect.Maps;
import me.astropvp.cratekeys.commands.*;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.listeners.CrateKeyInventoryListener;
import me.astropvp.cratekeys.listeners.CrateLogicListener;
import me.astropvp.cratekeys.managers.CrateDataManager;
import me.astropvp.cratekeys.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class AstroCrateKeysPlugin extends JavaPlugin {
    private static AstroCrateKeysPlugin AstroCrateKeysPlugin;
    private static Map<ItemStack, Double> itemsList;
    private static FileConfiguration config;

    public void onEnable() {
        AstroCrateKeysPlugin = this;
        registerListeners();
        registerCommands();
        CrateDataManager.init();
        config = getConfig();
    }

    public void onDisable() {
        CrateDataManager.onDisable();
        AstroCrateKeysPlugin = null;
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

    public static FileConfiguration getFileConfig() {
        return config;
    }


    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, (Plugin) AstroCrateKeysPlugin);
    }

    public static AstroCrateKeysPlugin getInstance() {
        return AstroCrateKeysPlugin;
    }
}
