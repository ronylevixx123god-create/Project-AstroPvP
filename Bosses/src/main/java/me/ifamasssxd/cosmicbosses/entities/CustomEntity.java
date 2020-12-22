/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.BiomeBase
 *  net.minecraft.server.v1_8_R3.BiomeMeta
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityBlaze
 *  net.minecraft.server.v1_8_R3.EntityCaveSpider
 *  net.minecraft.server.v1_8_R3.EntityEnderDragon
 *  net.minecraft.server.v1_8_R3.EntityEnderman
 *  net.minecraft.server.v1_8_R3.EntityGhast
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntitySkeleton
 *  net.minecraft.server.v1_8_R3.EntitySnowman
 *  net.minecraft.server.v1_8_R3.EntitySpider
 *  net.minecraft.server.v1_8_R3.EntityTypes
 *  net.minecraft.server.v1_8_R3.EntityWither
 *  net.minecraft.server.v1_8_R3.EntityZombie
 *  org.bukkit.entity.EntityType
 */
package me.ifamasssxd.cosmicbosses.entities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragonEntity;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowmanEntity;
import me.ifamasssxd.cosmicbosses.bosses.yijki.WitherBoss;
import me.ifamasssxd.cosmicbosses.dungeonboss.DeathCurseBlaze;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijkiEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijkiImage;
import me.ifamasssxd.cosmicbosses.dungeonboss.YijkiRunnerEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.HealingBlazeEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.YijkiDiscipleEntity;
import me.ifamasssxd.cosmicbosses.entities.CaveSpiderMinion;
import me.ifamasssxd.cosmicbosses.entities.GhastMinion;
import me.ifamasssxd.cosmicbosses.entities.HolidaySpiritEntity;
import me.ifamasssxd.cosmicbosses.entities.SkeletonBoss;
import me.ifamasssxd.cosmicbosses.entities.SoulReaperEnderman;
import me.ifamasssxd.cosmicbosses.entities.SpiderBoss;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbominationEntity;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeMeta;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityCaveSpider;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityEnderman;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.entity.EntityType;

public enum CustomEntity {
    GHAST("GhastMinion", 56, EntityType.GHAST, EntityGhast.class, GhastMinion.class),
    SPIDER("SpiderBoss", 52, EntityType.SPIDER, EntitySpider.class, SpiderBoss.class),
    CAVE_SPIDER("Cave Spider", 59, EntityType.CAVE_SPIDER, EntityCaveSpider.class, CaveSpiderMinion.class),
    SKELETON_BOSS("SkeletonBoss", 51, EntityType.SKELETON, EntitySkeleton.class, SkeletonBoss.class),
    WITHER("Wither", 64, EntityType.WITHER, EntityWither.class, WitherBoss.class),
    ABOMINATION_ZOMBIE("ZombieAbomination", EntityType.ZOMBIE.getTypeId(), EntityType.ZOMBIE, EntityZombie.class, EvilAbominationEntity.class),
    ENDERMAN("Endermans", EntityType.ENDERMAN.getTypeId(), EntityType.ENDERMAN, EntityEnderman.class, SoulReaperEnderman.class),
    SNOWMAN("HolidaySpirit", EntityType.SNOWMAN.getTypeId(), EntityType.SNOWMAN, EntitySnowman.class, HolidaySpiritEntity.class),
    DUNGEON_YIJKI("DungeonYijki", EntityType.WITHER.getTypeId(), EntityType.WITHER, EntityWither.class, DungeonYijkiEntity.class),
    DUNGEON_MOTHER_OF_YIJKI("MotherOfYijki", EntityType.WITHER.getTypeId(), EntityType.WITHER, EntityWither.class, MotherOfYijkiEntity.class),
    DUNGEON_HEALING_BLAZE("DungeonBlaze", EntityType.BLAZE.getTypeId(), EntityType.BLAZE, EntityBlaze.class, HealingBlazeEntity.class),
    DUNGEON_YIJKI_IMAGE("DungeonImage", EntityType.WITHER.getTypeId(), EntityType.WITHER, EntityWither.class, DungeonYijkiImage.class),
    DUNGEON_DISCIPEL("DungeonDisciple", EntityType.SKELETON.getTypeId(), EntityType.SKELETON, EntitySkeleton.class, YijkiDiscipleEntity.class),
    DUNGEON_BLAZE("DeathCurseBlaze", EntityType.BLAZE.getTypeId(), EntityType.BLAZE, EntityBlaze.class, DeathCurseBlaze.class),
    DUNGEON_YIJKI_RUNNER("DungeonYijkiRunner", EntityType.WITHER.getTypeId(), EntityType.WITHER, EntityWither.class, YijkiRunnerEntity.class),
    END_DRAGON("EndDragon", EntityType.ENDER_DRAGON.getTypeId(), EntityType.ENDER_DRAGON, EntityEnderDragon.class, EndDragonEntity.class),
    BATTLEFIELD_SNOW("ChristmasSpirit", EntityType.SNOWMAN.getTypeId(), EntityType.SNOWMAN, EntitySnowman.class, BattlefieldSnowmanEntity.class);

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends EntityInsentient> customClass;

    private CustomEntity(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass, Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Class<? extends Entity> getNMSClass() {
        return this.nmsClass;
    }

    public Class<? extends EntityInsentient> getCustomClass() {
        return this.customClass;
    }

    public static void registerEntities() {
        for (CustomEntity entity : CustomEntity.values()) {
            if (entity == ENDERMAN) continue;
            CustomEntity.a(entity.getCustomClass(), entity.getName(), entity.getID());
        }
    }

    public void registerEntity() {
        CustomEntity.a(this.getCustomClass(), this.getName(), this.getID());
    }

    public void unregisterEntity() {
        try {
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "d")).remove(this.getCustomClass());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "f")).remove(this.getCustomClass());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        CustomEntity.a(this.getNMSClass(), this.getName(), this.getID());
    }

    public static void unregisterEntities() {
        BiomeBase[] biomes;
        for (CustomEntity entity : CustomEntity.values()) {
            try {
                ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (CustomEntity entity : CustomEntity.values()) {
            try {
                CustomEntity.a(entity.getNMSClass(), entity.getName(), entity.getID());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            biomes = (BiomeBase[])CustomEntity.getPrivateStatic(BiomeBase.class, "biomes");
        }
        catch (Exception exc) {
            return;
        }
        for (BiomeBase biomeBase : biomes) {
            if (biomeBase == null) break;
            for (String field : new String[]{"as", "at", "au", "av"}) {
                try {
                    Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    List mobList = (List)list.get((Object)biomeBase);
                    for (BiomeMeta meta : mobList) {
                        for (CustomEntity entity : CustomEntity.values()) {
                            if (!entity.getCustomClass().equals(meta.b)) continue;
                            meta.b = entity.getNMSClass();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Object getPrivateStatic(Class clazz, String f) throws Exception {
        Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }

    @Deprecated
    private static void a(Class clazz, String name, int id) {
        try {
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "c")).put(name, clazz);
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "d")).put(clazz, name);
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "e")).put(id, clazz);
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "f")).put(clazz, id);
            ((Map)CustomEntity.getPrivateStatic(EntityTypes.class, "g")).put(name, id);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void registerEntityNEW(String name, int id, Class<? extends EntityInsentient> customClass) {
        try {
            ArrayList<Map> dataMaps = new ArrayList<Map>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (!f.getType().getSimpleName().equals(Map.class.getSimpleName())) continue;
                f.setAccessible(true);
                dataMaps.add((Map)f.get(null));
            }
            if (((Map)dataMaps.get(2)).containsKey(id)) {
                ((Map)dataMaps.get(0)).remove(name);
                ((Map)dataMaps.get(2)).remove(id);
            }
            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, Integer.TYPE);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

