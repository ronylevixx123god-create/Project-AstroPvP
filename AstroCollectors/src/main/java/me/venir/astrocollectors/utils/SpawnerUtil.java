package me.venir.astrocollectors.utils;

import org.bukkit.entity.EntityType;

public class SpawnerUtil {
    public static double getMoneyPerMinute(EntityType type) {
        double price = 0;
        switch (type) {
            case IRON_GOLEM:
                price = 16000;
                break;
            case VILLAGER:
                price = 50000;
                break;
            case PIG_ZOMBIE:
                price = 1500;
                break;
            case ZOMBIE:
            case SKELETON:
                price = 383;
                break;
            case SPIDER:
            case CAVE_SPIDER:
                price = 316;
                break;
        }
        return price;
    }

    public static double getMoneyPerSecond(EntityType type) {
        return getMoneyPerMinute(type) / 60;
    }

    public static double getTntPerMinute() {
        return 85;
    }

    public static double getTntPerSecond() {
        return getTntPerMinute() / 60;
    }
}
