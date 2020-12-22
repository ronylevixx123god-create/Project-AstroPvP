/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.DamageSource
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityMonster
 *  net.minecraft.server.v1_8_R3.EnumEntitySize
 *  net.minecraft.server.v1_8_R3.IRangedEntity
 *  net.minecraft.server.v1_8_R3.World
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EnumEntitySize;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class YijkiRunnerEntity
extends EntityMonster
implements IRangedEntity {
    private Location targetLocation;
    private int aliveTime;
    private Vector direction;

    public YijkiRunnerEntity(World world, Location spawn, Location target, int aliveTime) {
        super(world);
        this.targetLocation = target;
        this.aliveTime = aliveTime * 20;
        this.setHealth(this.getMaxHealth());
        this.a(0.9f, 4.0f);
        this.fireProof = true;
        this.getNavigation().e(true);
        this.as = EnumEntitySize.SIZE_6;
        this.direction = target.toVector().subtract(spawn.toVector());
        if (this.direction.length() != 0.0) {
            this.direction.normalize();
        }
        Bukkit.getLogger().info("Yijki Runner direction set to " + (Object)this.direction + " to " + (Object)target);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    public void e() {
        if (this.getHealth() != this.getMaxHealth()) {
            this.setHealth(this.getMaxHealth());
        }
        double motionMultiplier = 0.1;
        double gravityMultiplier = 0.6f;
        if (this.aliveTime != 0 && this.ticksLived >= this.aliveTime) {
            this.die();
            return;
        }
        this.motX += this.direction.getX() * motionMultiplier;
        this.motZ += this.direction.getZ() * motionMultiplier;
        if (this.direction.getY() > 0.0) {
            this.motY += this.direction.getY() * (gravityMultiplier / 2.0);
        }
        this.motY *= gravityMultiplier;
        super.e();
        if (this.motX * this.motX + this.motZ * this.motZ > (double)0.05f) {
            this.yaw = (float)Math.atan2(this.motZ, this.motX) * 57.295776f - 90.0f;
        }
    }

    public void a(EntityLiving entityLiving, float v) {
    }
}

