/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.utils.BossUtils
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityGhast
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftGhast
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import java.io.File;
import java.lang.reflect.Field;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftGhast;
import org.bukkit.entity.Ghast;

public class EntityUtils {
    private static Class<?> endDragonClass;
    private static boolean bossesEnabled;
    private static Field ghastTarget;

    public static void removeAI(org.bukkit.entity.Entity entity) {
        EntityLiving ent = (EntityLiving)((CraftEntity)entity).getHandle();
        ent.fromMobSpawner = true;
    }

    public static boolean isEndDragonSpawned() {
        if (endDragonClass == null && bossesEnabled) {
            try {
                endDragonClass = Class.forName("me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bossesEnabled && !BossUtils.getAllBossesFromType((Class<? extends Boss>) endDragonClass).isEmpty();
    }

    public static Field getGhastTarget() {
        if (ghastTarget == null) {
            ghastTarget = ReflectionUtils.getField(EntityGhast.class, "target");
        }
        return ghastTarget;
    }

    public static void setGhastTarget(org.bukkit.entity.Entity entity, org.bukkit.entity.Entity target) {
        if (entity instanceof Ghast) {
            Ghast ghast = (Ghast)entity;
            EntityGhast entGhast = ((CraftGhast)ghast).getHandle();
            CraftEntity craftEnt = (CraftEntity)target;
            EntityLiving toSet = null;
            if (target != null && craftEnt.getHandle() instanceof EntityLiving) {
                toSet = (EntityLiving)craftEnt.getHandle();
            }
            entGhast.setGoalTarget(toSet);
            ReflectionUtils.setObject((Object)entGhast, EntityUtils.getGhastTarget(), toSet);
        }
    }

    public static org.bukkit.entity.Entity getGhastTarget(org.bukkit.entity.Entity entity) {
        if (entity instanceof Ghast) {
            Ghast ghast = (Ghast)entity;
            EntityGhast entGhast = ((CraftGhast)ghast).getHandle();
            try {
                Entity internal = (Entity)EntityUtils.getGhastTarget().get(entGhast);
                return internal.getBukkitEntity();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static {
        bossesEnabled = new File("plugins/CosmicBosses.jar").exists();
    }
}

