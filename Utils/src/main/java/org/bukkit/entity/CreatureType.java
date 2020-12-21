/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

@Deprecated
public enum CreatureType {
    CREEPER("Creeper", Creeper.class, 50),
    SKELETON("Skeleton", Skeleton.class, 51),
    SPIDER("Spider", Spider.class, 52),
    GIANT("Giant", Giant.class, 53),
    ZOMBIE("Zombie", Zombie.class, 54),
    SLIME("Slime", Slime.class, 55),
    GHAST("Ghast", Ghast.class, 56),
    PIG_ZOMBIE("PigZombie", PigZombie.class, 57),
    ENDERMAN("Enderman", Enderman.class, 58),
    CAVE_SPIDER("CaveSpider", CaveSpider.class, 59),
    SILVERFISH("Silverfish", Silverfish.class, 60),
    BLAZE("Blaze", Blaze.class, 61),
    MAGMA_CUBE("LavaSlime", MagmaCube.class, 62),
    ENDER_DRAGON("EnderDragon", EnderDragon.class, 63),
    PIG("Pig", Pig.class, 90),
    SHEEP("Sheep", Sheep.class, 91),
    COW("Cow", Cow.class, 92),
    CHICKEN("Chicken", Chicken.class, 93),
    SQUID("Squid", Squid.class, 94),
    WOLF("Wolf", Wolf.class, 95),
    MUSHROOM_COW("MushroomCow", MushroomCow.class, 96),
    SNOWMAN("SnowMan", Snowman.class, 97),
    VILLAGER("Villager", Villager.class, 120);

    private String name;
    private Class<? extends Entity> clazz;
    private short typeId;
    private static final Map<String, CreatureType> NAME_MAP;
    private static final Map<Short, CreatureType> ID_MAP;

    private CreatureType(String name, Class<? extends Entity> clazz, int typeId) {
        this.name = name;
        this.clazz = clazz;
        this.typeId = (short)typeId;
    }

    public String getName() {
        return this.name;
    }

    public Class<? extends Entity> getEntityClass() {
        return this.clazz;
    }

    @Deprecated
    public short getTypeId() {
        return this.typeId;
    }

    public static CreatureType fromName(String name) {
        return NAME_MAP.get(name);
    }

    @Deprecated
    public static CreatureType fromId(int id) {
        if (id > 32767) {
            return null;
        }
        return ID_MAP.get((short)id);
    }

    @Deprecated
    public EntityType toEntityType() {
        return EntityType.fromName(this.getName());
    }

    public static CreatureType fromEntityType(EntityType creatureType) {
        return CreatureType.fromName(creatureType.getName());
    }

    static {
        NAME_MAP = new HashMap<String, CreatureType>();
        ID_MAP = new HashMap<Short, CreatureType>();
        for (CreatureType type : EnumSet.allOf(CreatureType.class)) {
            NAME_MAP.put(type.name, type);
            if (type.typeId == 0) continue;
            ID_MAP.put(type.typeId, type);
        }
    }
}

