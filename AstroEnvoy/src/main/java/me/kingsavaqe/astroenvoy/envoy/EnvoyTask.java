package me.kingsavaqe.astroenvoy.envoy;


import java.io.File;
import java.util.List;

import me.kingsavaqe.astroenvoy.AstroEnvoyPlugin;
import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import me.kingsavaqe.astroenvoy.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnvoyTask extends BukkitRunnable {
    private final AstroEnvoyPlugin instance;

    public EnvoyTask(AstroEnvoyPlugin instance) {
        this.instance = instance;
    }

    public void run() {
        PlayerDataManager playerDataManager = this.instance.getPlayerDataManager();
        final EnvoyManager envoyManager = this.instance.getEnvoyManager();
        FileConfiguration configLoad = this.instance.getFileManager().getConfig(new File(this.instance.getDataFolder(), "config.yml")).getFileConfiguration();
        Envoy envoy = envoyManager.getEnvoy();
        if (!envoy.hasSetup())
            return;
        if (envoy.hasStarted()) {
            envoy.setCurrentDuration(envoy.getCurrentDuration() + 1);
            if (envoy.getCurrentDuration() >= envoy.getFixedDuration() || !envoy.hasRandomCrateLocation()) {
                envoy.setStarted(false);
                envoy.setCurrentDuration(0);
                envoy.setCurrentStartTime(envoy.getFixedStartTime());
                Bukkit.getServer().getScheduler().runTask(this.instance, envoyManager::removeCrates);
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!playerDataManager.hasPlayerData(all))
                        continue;
                    if (!playerDataManager.getPlayerData(all).hasNotifications())
                        continue;
                    for (String messageLine : configLoad.getStringList("Envoy.Tasks.Envoy.Ended.Lines"))
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', messageLine));
                    if (configLoad.getString("Envoy.Tasks.Envoy.Ended.Sound") != null)
                        all.playSound(all.getLocation(),
                                Sound.valueOf(configLoad.getString("Envoy.Tasks.Envoy.Ended.Sound")), 1.0F, 0.1F);
                }
            }
        } else {
            envoy.setCurrentStartTime(envoy.getCurrentStartTime() - 1);
            if (envoy.getCurrentStartTime() <= 0) {
                envoy.setStarted(true);
                envoy.setCurrentStartTime(envoy.getFixedStartTime());
                final List<Location> crateLocations = envoyManager.getCrateLocations();
                Bukkit.getServer().getScheduler().runTask(this.instance, () -> envoyManager.spawnCrates(crateLocations));
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!playerDataManager.hasPlayerData(all))
                        continue;
                    if (!playerDataManager.getPlayerData(all).hasNotifications())
                        continue;
                    for (String messageLine : configLoad.getStringList("Envoy.Tasks.Envoy.Started.Lines"))
                        all.sendMessage(ChatColor.translateAlternateColorCodes('&', messageLine
                                .replace("%crates", String.valueOf(crateLocations.size()))));
                    if (configLoad.getString("Envoy.Tasks.Envoy.Started.Sound") != null)
                        all.playSound(all.getLocation(),
                                Sound.valueOf(configLoad.getString("Envoy.Tasks.Envoy.Started.Sound")), 1.0F, 0.1F);
                }
            } else {
                for (String broadcast : configLoad.getStringList("Envoy.Starting.Broadcast")) {
                    int broadcastTime = Integer.parseInt(broadcast);
                    if (envoy.getCurrentStartTime() != broadcastTime)
                        continue;
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (!playerDataManager.hasPlayerData(all))
                            continue;
                        if (!playerDataManager.getPlayerData(all).hasNotifications())
                            continue;
                        for (String messageLine : configLoad.getStringList("Envoy.Tasks.Envoy.Starting.Lines"))
                            all.sendMessage(ChatColor.translateAlternateColorCodes('&', messageLine
                                    .replace("%seconds", String.valueOf(envoy.getCurrentStartTime())).replace("%time",
                                            TimeUtil.getDuration(envoy.getCurrentStartTime()))));

                    }
                }
            }
        }
    }
}
