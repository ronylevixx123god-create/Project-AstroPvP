package me.astropvp.astrospawners.utils;

import java.util.HashMap;
import java.util.UUID;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.astropvp.astrospawners.data.SpawnerManager;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.SpawnerSpawnEvent;


public class MobStackerUtils {

    HashMap<UUID, Integer> mobMap = new HashMap<>();

    public MobStackerUtils() {

    }


    public static void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof Player) && !(entity instanceof ArmorStand)) {
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
        SpawnerData data = AstroSpawners.getInstance().getSpawnerManager().findSpawnerData(location);

        int size = 0;

        if (entity.getType() == EntityType.BLAZE) {
            size = entityStack + (data != null ? (data.getSpawnerAmount()*10)+100 : 100);

            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFactionAt(event.getLocation()), OutpostType.THE_GREAT_HALL)) {
                size += size*1.5;
            }


        } else {
            size = entityStack + (data != null ? data.getSpawnerAmount() : 100);
            if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFactionAt(event.getLocation()), OutpostType.THE_GREAT_HALL)) {
                size += size *1.5;
            }
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
