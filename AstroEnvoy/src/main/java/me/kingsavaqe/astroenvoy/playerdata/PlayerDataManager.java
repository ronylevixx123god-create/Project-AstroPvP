package me.kingsavaqe.astroenvoy.playerdata;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerDataManager {
    private AstroEnvoyPlugin instance;

    private Map<UUID, PlayerData> playerDataStorage = new HashMap<>();

    public PlayerDataManager(AstroEnvoyPlugin instance) {
        this.instance = instance;
        for (Player all : Bukkit.getOnlinePlayers()) {
            createPlayerData(all);
            preparePlayerData(all);
        }
        (new PlayerDataTask(this)).runTaskTimerAsynchronously((Plugin)instance, 0L, instance
                .getFileManager().getConfig(new File(instance.getDataFolder(), "config.yml"))
                .getFileConfiguration().getInt("PlayerData.Save.Time") * 20L);
    }

    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!this.playerDataStorage.containsKey(all.getUniqueId()))
                continue;
            savePlayerData(this.playerDataStorage.get(all.getUniqueId()));
        }
    }

    public void createPlayerData(Player player) {
        this.playerDataStorage.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
    }

    public void savePlayerData(PlayerData playerData) {
        File configFile = new File(String.valueOf(this.instance.getDataFolder().toString()) + "/player-data", String.valueOf(playerData.getUUID().toString()) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        yamlConfiguration.set("Notifications", Boolean.valueOf(playerData.hasNotifications()));
        yamlConfiguration.set("Opened", Integer.valueOf(playerData.getOpened()));
        try {
            yamlConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerDataAsync(final PlayerData playerData) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously((Plugin)this.instance, new Runnable() {
            public void run() {
                PlayerDataManager.this.savePlayerData(playerData);
            }
        });
    }

    public void preparePlayerData(Player player) {
        File configFile = new File(String.valueOf(this.instance.getDataFolder().toString()) + "/player-data", String.valueOf(player.getUniqueId().toString()) + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        PlayerData playerData = this.playerDataStorage.get(player.getUniqueId());
        yamlConfiguration.set("Name", player.getName());
        if (yamlConfiguration.getString("Notifications") != null)
            playerData.setNotifications(yamlConfiguration.getBoolean("Notifications"));
        playerData.setOpened(yamlConfiguration.getInt("Opened"));
        try {
            yamlConfiguration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preparePlayerDataAsync(final Player player) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously((Plugin)this.instance, new Runnable() {
            public void run() {
                PlayerDataManager.this.preparePlayerData(player);
            }
        });
    }

    public PlayerData getPlayerData(UUID uuid) {
        return this.playerDataStorage.get(uuid);
    }

    public PlayerData getPlayerData(Player player) {
        return this.playerDataStorage.get(player.getUniqueId());
    }

    public void removePlayerData(UUID uuid) {
        this.playerDataStorage.remove(uuid);
    }

    public void removePlayerData(Player player) {
        this.playerDataStorage.remove(player.getUniqueId());
    }

    public boolean hasPlayerData(UUID uuid) {
        return this.playerDataStorage.containsKey(uuid);
    }

    public boolean hasPlayerData(Player player) {
        return this.playerDataStorage.containsKey(player.getUniqueId());
    }
}
