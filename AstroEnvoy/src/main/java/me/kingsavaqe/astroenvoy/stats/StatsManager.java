package me.kingsavaqe.astroenvoy.stats;


import java.io.File;
import java.util.*;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import me.kingsavaqe.astroenvoy.utils.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class StatsManager {
    private final AstroEnvoyPlugin instance;
    private List<Stats> stats = new ArrayList<Stats>();

    public StatsManager(AstroEnvoyPlugin instance) {
        this.instance = instance;
        this.prepareStats();
        new StatsTask(this).runTaskTimerAsynchronously(instance, 0L, (long)instance.getConfig().getInt("Stats.Top.Time") * 20L);
    }

    public void prepareStats() {
        /*PlayerDataManager playerDataManager = this.instance.getPlayerDataManager();
        File playerDataFile = new File(this.instance.getDataFolder() + "/player-data");
        if (!playerDataFile.exists()) {
            return;
        }
        File[] arrayOfFile = playerDataFile.listFiles();
        int i = arrayOfFile.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            File configFile = arrayOfFile[b];
            if (!FileUtil.getFileExtension(configFile).equals(".yml")) {
                return;
            }
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
            if (yamlConfiguration.getString("Name") == null) continue;
            UUID uuid = UUID.fromString(configFile.getName().replace(".yml", ""));
            int opened = 0;
            opened = playerDataManager.hasPlayerData(uuid) ? playerDataManager.getPlayerData(uuid).getOpened() : yamlConfiguration.getInt("Opened");
            this.stats.add(new Stats(uuid, yamlConfiguration.getString("Name"), opened));
        }
        this.stats.sort((stats1, stats2) -> Integer.valueOf(stats2.getOpened()).compareTo(stats1.getOpened()));*/
    }

    public void clearStats() {
        this.stats.clear();
    }

    public Stats getStats(String name) {
        for (Stats stats : this.stats) {
            if (!stats.getName().equalsIgnoreCase(name)) continue;
            return stats;
        }
        return null;
    }

    public List<Stats> getStats() {
        return this.stats;
    }
}
