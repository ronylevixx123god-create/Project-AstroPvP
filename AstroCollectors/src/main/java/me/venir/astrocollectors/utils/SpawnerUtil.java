package me.venir.astrocollectors.utils;

import org.bukkit.entity.EntityType;

public class SpawnerUtil {
    public static double getMoneyPerMinute(EntityType type) {
        double price =0;
        switch (type) {
            case IRON_GOLEM:
                price = 16000;
                break;
            case VILLAGER:
                price = 50000;
                break;
            case CREEPER:
                price = 85;
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
}
