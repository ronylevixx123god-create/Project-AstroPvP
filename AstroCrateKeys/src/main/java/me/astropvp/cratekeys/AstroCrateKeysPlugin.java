package me.astropvp.cratekeys;

import me.astropvp.cratekeys.commands.*;
import me.astropvp.cratekeys.listeners.CrateKeyInventoryListener;
import me.astropvp.cratekeys.listeners.CrateLogicListener;
import me.astropvp.cratekeys.listeners.ItemRedeemCrateListener;
import me.astropvp.cratekeys.managers.CrateDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroCrateKeysPlugin extends JavaPlugin {
    private static AstroCrateKeysPlugin AstroCrateKeysPlugin;

    public void onEnable() {
        AstroCrateKeysPlugin = this;
        registerListeners();
        registerCommands();
        CrateDataManager.init();
    }

    public void onDisable() {
        CrateDataManager.onDisable();
        AstroCrateKeysPlugin = null;
    }

    private void registerListeners() {
        registerListener((Listener)new ItemRedeemCrateListener());
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
