package me.astropvp.cratekeys;

import me.astropvp.cratekeys.commands.CommandAdminCrate;
import me.astropvp.cratekeys.commands.CommandGiveCrate;
import me.astropvp.cratekeys.commands.CommandGiveCrateKey;
import me.astropvp.cratekeys.listeners.CrateKeyInventoryListener;
import me.astropvp.cratekeys.listeners.CrateLogicListener;
import me.astropvp.cratekeys.managers.CrateDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class AstroCrateKeysPlugin extends JavaPlugin {
    public static AstroCrateKeysPlugin AstroCrateKeysPlugin;
    public static FileConfiguration config;

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



    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, (Plugin) AstroCrateKeysPlugin);
    }

    public static AstroCrateKeysPlugin getInstance() {
        return AstroCrateKeysPlugin;
    }

}
