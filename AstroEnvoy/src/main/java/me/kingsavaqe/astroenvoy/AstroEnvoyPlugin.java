package me.kingsavaqe.astroenvoy;


import me.kingsavaqe.astroenvoy.commands.EnvoyCommand;
import me.kingsavaqe.astroenvoy.envoy.EnvoyManager;
import me.kingsavaqe.astroenvoy.envoy.EnvoyTask;
import me.kingsavaqe.astroenvoy.file.FileManager;
import me.kingsavaqe.astroenvoy.listeners.Interact;
import me.kingsavaqe.astroenvoy.listeners.Join;
import me.kingsavaqe.astroenvoy.listeners.Quit;
import me.kingsavaqe.astroenvoy.location.LocationManager;
import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import me.kingsavaqe.astroenvoy.stats.StatsManager;
import me.kingsavaqe.astroenvoy.tasks.ParticleTask;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroEnvoyPlugin extends JavaPlugin {
    private static AstroEnvoyPlugin instance;

    private FileManager fileManager;

    private LocationManager locationManager;

    private EnvoyManager envoyManager;

    private PlayerDataManager playerDataManager;

    private StatsManager statsManager;

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        this.fileManager = new FileManager(this);
        this.locationManager = new LocationManager();
        this.envoyManager = new EnvoyManager(this);
        this.playerDataManager = new PlayerDataManager(this);
        this.statsManager = new StatsManager(this);
        (new ParticleTask(this)).runTaskTimer(this, 0L, 20L);
        (new EnvoyTask(this)).runTaskTimerAsynchronously(this, 0L, 20L);
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new Join(this.playerDataManager), this);
        pluginManager.registerEvents(new Quit(this.playerDataManager), this);
        pluginManager.registerEvents(new Interact(this), this);
        PluginCommand EnvoyCommand = getCommand("Envoy");
        EnvoyCommand.setExecutor(new EnvoyCommand(this));
        EnvoyCommand.setTabCompleter(new EnvoyCommand(this));
    }

    public void onDisable() {
        if (this.envoyManager != null)
            this.envoyManager.onDisable();
        if (this.playerDataManager != null)
            this.playerDataManager.onDisable();
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public LocationManager getLocationManager() {
        return this.locationManager;
    }

    public EnvoyManager getEnvoyManager() {
        return this.envoyManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return this.playerDataManager;
    }

    public StatsManager getStatsManager() {
        return this.statsManager;
    }

    public static AstroEnvoyPlugin getInstance() {
        return instance;
    }
}
