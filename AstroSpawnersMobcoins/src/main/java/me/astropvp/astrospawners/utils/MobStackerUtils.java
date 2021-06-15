package me.astropvp.astrospawners.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.Faction;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.data.SpawnerDataHelper;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.*;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;


public class MobStackerUtils {

    HashMap<UUID, Integer> mobMap = new HashMap<>();

    public MobStackerUtils() {

    }


    public static void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player)) {
                    entity.remove();
                }
            }
        }
    }

    public HashMap<UUID, Integer> getMobMap() {
        return mobMap;
    }

    public int getStackSize(Entity entity) {
        return mobMap.getOrDefault(entity.getUniqueId(), 1);
    }

    public void setStackSize(Entity entity, int size) {
        entity.setCustomName(CC.Green + EntityUtils.getEntityName(entity.getType()) + " " + CC.Gray + "[" + CC.White + "x " + CC.Underline + size + CC.Gray + "]");
        mobMap.put(entity.getUniqueId(), size);
    }

    public boolean isStackedMob(Entity entity) {
        return mobMap.containsKey(entity.getUniqueId());
    }

    public void removeStackedMob(Entity entity) {
        if (!entity.isDead()) entity.remove();
        mobMap.remove(entity.getUniqueId());
    }

    public void stackMobs(SpawnerSpawnEvent event) {

        Chunk chunk = event.getLocation().getChunk();
        Entity[] entities = chunk.getEntities();


        int entityStack = 0;
        Entity entity = event.getEntity();
        Location location = event.getSpawner().getLocation();
        SpawnerData data = SpawnerDataHelper.findSpawnerData(location);

        int size = 0;

        if (entity.getType() == EntityType.BLAZE) {
            size = entityStack + (data != null ? data.getSpawnerAmount() * 3 : 100);


        } else {
            size = entityStack + (data != null ? data.getSpawnerAmount() + 50 : 100);

        }


        int stackSize = 0;
        Entity highestStackedEntity = null;


        for (Entity nearbyEntity : entities) {
            if (nearbyEntity.getType() != entity.getType()) continue;
            if (!isStackedMob(nearbyEntity)) continue;
            if (getStackSize(nearbyEntity) <= stackSize) continue;
            highestStackedEntity = nearbyEntity;
            stackSize = getStackSize(nearbyEntity);
        }
        if (highestStackedEntity == null) {
            setStackSize(entity, size);
        } else {
            setStackSize(highestStackedEntity, stackSize + size);
            removeStackedMob(entity);
            event.setCancelled(true);
        }
    }

    /*public static void setEntityAmount(LivingEntity entity, int amount, boolean deathEffect) {
        if (amount <= 0) {
            return;
        }
        if (deathEffect) {
            entity = (LivingEntity) entity.getWorld().spawnEntity(entity.getLocation(), entity.getType());
        }
        entity.setMetadata("stacked_mob", new FixedMetadataValue(AstroSpawners.getInstance(), amount));
        //entity.setMetadata("fromMobSpawner", new FixedMetadataValue(AstroSpawners.getInstance(), null));
        //entity.setMetadata("no_attack", new FixedMetadataValue(AstroSpawners.getInstance(), null));
        entity.setCustomName(CC.Green + EntityUtils.getEntityName(entity.getType()) + " " + CC.Gray + "[" + CC.White + "x " + CC.Underline+ amount+ CC.Gray + "]");
    }

    public static int getEntityAmount(LivingEntity entity) {
        if (!entity.hasMetadata("stacked_mob")) {
            return 1;
        }
        if (entity.getMetadata("stacked_mob").isEmpty()) {
            return 0;
        }
        return entity.getMetadata("stacked_mob").get(0).asInt();
    }*/
}
