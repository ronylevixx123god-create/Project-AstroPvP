package me.astropvp.factionlogs;

import lombok.Getter;
import me.astropvp.factionlogs.commands.FactionLogsCommand;
import me.astropvp.factionlogs.listeners.FactionListeners;
import me.astropvp.factionlogs.struct.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class FactionLogs extends JavaPlugin {
    public LogManager logManager;
    private static FactionLogs instance;

    public void onEnable() {
        instance = this;
        loadListeners();
        loadCommands();
        this.logManager = new LogManager();
        this.logManager.load();
    }

    public void onDisable() {
        instance = null;
    }


    public void loadListeners() {
        Bukkit.getPluginManager().registerEvents(new FactionListeners(), this);
    }

    public void loadCommands() {
        Bukkit.getPluginManager().registerEvents(new FactionLogsCommand(), this);

    }

    public static FactionLogs getInstance() {
        return instance;
    }
}
