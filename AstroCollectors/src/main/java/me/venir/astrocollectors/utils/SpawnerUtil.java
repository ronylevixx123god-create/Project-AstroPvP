package me.venir.astrocollectors.utils;

import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.utils.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.util.EnumMap;

public class SpawnerUtil {

    private final ConfigManager configManager;
    private final EnumMap<EntityType, Double> mobIncomeMap;
    private double tntPerMinute;

    public SpawnerUtil() {
        this.configManager = AstroCollectors.INSTANCE.getConfigManager();
        this.mobIncomeMap = new EnumMap<>(EntityType.class);
        this.reloadMobIncomeMap();
        this.reloadTntIncomeValue();
    }

    public void reloadMobIncomeMap() {
        this.mobIncomeMap.clear();
        org.bukkit.configuration.file.FileConfiguration config = this.configManager.getConfig(ConfigManager.ConfigFile.SETTINGS);
        boolean perMinute = config.isConfigurationSection("spawner-income-per-minute");
        ConfigurationSection configurationSection = config.getConfigurationSection(perMinute ? "spawner-income-per-minute" : "spawner-income-per-hour");
        if (configurationSection == null) throw new IllegalArgumentException("settings.yml requires spawner-income-per-hour or spawner-income-per-minute");
        for (String key : configurationSection.getKeys(false)) {
            double value = configurationSection.getDouble(key) / (perMinute ? 1.0 : 60.0);
            EntityType entityType = EntityType.valueOf(key);
            mobIncomeMap.put(entityType, value);
        }
    }

    public void reloadTntIncomeValue() {
        org.bukkit.configuration.file.FileConfiguration config = configManager.getConfig(ConfigManager.ConfigFile.SETTINGS);
        this.tntPerMinute = config.contains("tnt-per-minute") ? config.getDouble("tnt-per-minute") : config.getDouble("tnt-per-hour") / 60.0;
    }

    public double getMoneyPerMinute(EntityType type) {
        try {

            return this.mobIncomeMap.get(type);
        } catch (Exception e) {
            return 0;
        }
    }

    public double getTnTPerHour() {
        try {
            return this.tntPerMinute * 60.0;
        } catch (Exception e) {
            return 0;
        }
    }

    public double getMoneyPerSecond(EntityType type) {
        return getMoneyPerMinute(type) / 60;
    }

    public double getTntPerMinute() {
        return this.tntPerMinute;
    }

    public double getTntPerSecond() {
        return getTntPerMinute() / 60;
    }
}
