package me.astropvp.outpost;

import lombok.Getter;
import me.astropvp.outpost.config.ConfigManager;
import me.astropvp.outpost.listener.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Outpost extends JavaPlugin {
    @Getter
    private static Outpost instance;
    @Getter
    private ConfigManager configManager;






    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();

        this.configManager = new ConfigManager(this);
        this.configManager.setupFiles();

        System.out.println("[Outpost] Plugin has been enabled!");
    }


    public void onDisable() {
        System.out.println("[Outpost] Plugin has been disabled!");
        this.configManager = null;
        instance = null;
    }

    public void registerCommands() {



    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new WorldChange(), this);

        getServer().getPluginManager().registerEvents(new MarsListener(), this);
        getServer().getPluginManager().registerEvents(new PlutoListener(), this);
        getServer().getPluginManager().registerEvents(new CommandAliasListener(), this);

    }
}
