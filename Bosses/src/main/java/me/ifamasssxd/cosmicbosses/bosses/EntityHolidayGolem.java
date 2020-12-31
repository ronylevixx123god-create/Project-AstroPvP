/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.MapMaker
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityGolem
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.EntitySnowball
 *  net.minecraft.server.v1_8_R3.EntityWitherSkull
 *  net.minecraft.server.v1_8_R3.MathHelper
 *  net.minecraft.server.v1_8_R3.MovingObjectPosition
 *  net.minecraft.server.v1_8_R3.World
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicbosses.bosses;

import com.google.common.collect.MapMaker;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.bosses.MultiProjectileShooter;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityGolem;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;

public class EntityHolidayGolem
extends EntityGolem
implements MultiProjectileShooter {
    public Map<UUID, Long> recentlySplitPlayer = new MapMaker().expireAfterWrite(3L, TimeUnit.SECONDS).makeMap();
    private float newF = 10.0f;
    private float newJ = this.newF * this.newF;

    public EntityHolidayGolem(World world) {
        super(world);
    }

    public boolean rollShootSkull() {
        return ThreadLocalRandom.current().nextInt(10) <= 1;
    }

    public void shootAtTarget(EntityLiving living, boolean witherSkull) {
        this.shootAtTarget((EntityLiving)this, living.locX, living.locY + (double)living.getHeadHeight(), living.locZ, witherSkull);
    }

    public void shootAtTarget(EntityLiving originateFrom, double targetX, double targetY, double targetZ, boolean witherSkull) {
        Object projectile;
        double d0 = targetX - originateFrom.locX;
        double d2 = targetZ - originateFrom.locZ;
        double d1 = targetY - (double)1.1f - originateFrom.locY;
        if (witherSkull) {
            projectile = new EntityWitherSkull(this.world, (EntityLiving)this, d0, d1, d2){

                protected void a(MovingObjectPosition movingobjectposition) {
                    Entity colliding = movingobjectposition.entity;
                    if (colliding != null && !(colliding instanceof EntityHuman)) {
                        Bukkit.getLogger().info("Ignoring collision for Holiday Spirit wither skull!");
                        return;
                    }
                    super.a(movingobjectposition);
                }
            };
            projectile.locY = originateFrom.locY + 1.5;
            projectile.locX = originateFrom.locX;
            projectile.locZ = originateFrom.locZ;
            projectile.motX *= 1.5;
            projectile.motZ *= 1.5;
        } else {
            projectile = new EntitySnowball(originateFrom.world, (EntityLiving)this){

                public void shoot(double d0, double d1, double d2, float f, float f1) {
                    float f2 = MathHelper.sqrt((double)(d0 * d0 + d1 * d1 + d2 * d2));
                    d0 /= (double)f2;
                    d1 /= (double)f2;
                    d2 /= (double)f2;
                    this.motX = d0 *= (double)f;
                    this.motY = d1 *= (double)f;
                    this.motZ = d2 *= (double)f;
                    float f3 = MathHelper.sqrt((double)(d0 * d0 + d2 * d2));
                    this.lastYaw = this.yaw = (float)(Math.atan2(d0, d2) * 180.0 / 3.1415927410125732);
                    this.lastPitch = this.pitch = (float)(Math.atan2(d1, f3) * 180.0 / 3.1415927410125732);
                }
            };
            d1 = targetY - (double)1.1f - projectile.locY;
            float f1 = MathHelper.sqrt((double)(d0 * d0 + d2 * d2)) * 0.2f;
            ((EntitySnowball)projectile).shoot(d0, d1 + (double)f1, d2, 1.6f, 12.0f);
        }
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aI().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity((Entity)projectile);
    }

    @Override
    public void shootNearby(EntityLiving target) {
        int shots = 0;
        int max = this.random.nextInt(4) + 5;
        for (org.bukkit.entity.Entity entity : this.getBukkitEntity().getNearbyEntities(16.0, 16.0, 16.0)) {
            EntityPlayer player;
            Entity ent;
            if (shots >= max) break;
            if (!(entity instanceof Player) || (ent = ((CraftEntity)entity).getHandle()) == target || (player = (EntityPlayer)ent).getBukkitEntity().getGameMode() != GameMode.SURVIVAL) continue;
            ++shots;
            this.shoot(ent);
        }
    }

    public void a(EntityLiving entityLiving, float v) {
        this.shootAtTarget(entityLiving, this.rollShootSkull());
    }

    public float getF(Entity target) {
        float f;
        double d0 = this.e(target.locX, target.boundingBox.b, target.locZ);
        if (d0 > (double)this.newJ || !this.getEntitySenses().canSee(target)) {
            return -1.0f;
        }
        float f1 = f = MathHelper.sqrt((double)d0) / this.newF;
        if (f < 0.1f) {
            f1 = 0.1f;
        }
        if (f1 > 1.0f) {
            f1 = 1.0f;
        }
        return f1;
    }

    public void shoot(Entity target) {
        float f = this.getF(target);
        if (target instanceof EntityHuman) {
            this.a((EntityLiving)target, f);
        }
    }
}

