package me.astropvp.factionlogs;

import lombok.Getter;
import me.astropvp.factionlogs.struct.LogManager;
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

    }

    public void loadCommands() {

    }

    public static FactionLogs getInstance() {
        return instance;
    }
}
