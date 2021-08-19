package me.astropvp.astrospawners.utils;

import java.util.List;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;


public class EntityUtils {
    private static List<Object[]> entities;

    public static void onEnable() {
        entities = Lists.newArrayList();
    }

    public static void addHeartsAboveEntity(LivingEntity entity, String display) {
        entity.setCustomName(getHealthString(entity));
        Object[] object = new Object[]{entity, display};
        entities.add(object);
    }

    public static String getHealthString(LivingEntity entity) {
        int intHealth = (int) entity.getHealth();
        int intMaxHealth = (int) entity.getMaxHealth();
        int amountOfTimes = intMaxHealth - (intMaxHealth - intHealth);
        amountOfTimes = (int) Math.ceil((double) amountOfTimes / intMaxHealth * 8);
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            if (amountOfTimes >= i) {
                builder.append(CC.translate(CC.RedB + "❤"));
            } else {
                builder.append(CC.translate(CC.WhiteB + "❤"));
            }
        }
        return builder.toString();
    }

    public static String getEntityName(EntityType type) {
        if (type == EntityType.IRON_GOLEM) {
            return "Iron Golem";
        }
        if (type == EntityType.MUSHROOM_COW) {
            return "Mushroom Cow";
        }
        return type.getName();
    }


    public static void clearAllEntities() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }
}
