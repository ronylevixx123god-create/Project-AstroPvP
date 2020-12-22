/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.AxisAlignedBB
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityGhast
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityLargeFireball
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EnumDifficulty
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LargeFireball
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTargetEvent$TargetReason
 *  org.bukkit.metadata.MetadataValue
 */
package me.ifamasssxd.cosmicbosses.entities;

import java.util.UUID;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueCarriers;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import net.minecraft.server.v1_7_R4.AxisAlignedBB;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityGhast;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLargeFireball;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.LargeFireball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.metadata.MetadataValue;

public class GhastMinion
extends EntityGhast {
    private Entity target;
    private int br;
    private int explosionPower = 1;
    private PlagueCarriers carrier;

    public GhastMinion(World world) {
        super(world);
    }

    protected void bq() {
        byte b1;
        byte b0;
        if (!this.world.isStatic && this.world.difficulty == EnumDifficulty.PEACEFUL) {
            this.die();
        }
        this.w();
        this.bo = this.bp;
        double d0 = this.i - this.locX;
        double d1 = this.bm - this.locY;
        double d2 = this.bn - this.locZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0 || d3 > 3600.0) {
            this.i = this.locX + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.bm = this.locY + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.bn = this.locZ + (double)((this.random.nextFloat() * 2.0f - 1.0f) * 16.0f);
        }
        if (this.h-- <= 0) {
            this.h += this.random.nextInt(5) + 2;
            if (this.a(this.i, this.bm, this.bn, d3 = (double)MathHelper.sqrt((double)d3))) {
                this.motX += d0 / d3 * 0.1;
                this.motY += d1 / d3 * 0.1;
                this.motZ += d2 / d3 * 0.1;
            } else {
                this.i = this.locX;
                this.bm = this.locY;
                this.bn = this.locZ;
            }
        }
        if (this.target != null && this.target.dead) {
            EntityTargetEvent event = new EntityTargetEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
            this.world.getServer().getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                this.target = event.getTarget() == null ? null : ((CraftEntity)event.getTarget()).getHandle();
            }
        }
        if (this.target == null || this.br-- <= 0) {
            EntityHuman target = this.world.findNearbyVulnerablePlayer((Entity)this, 100.0);
            if (target != null) {
                EntityTargetEvent event = new EntityTargetEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), (org.bukkit.entity.Entity)target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
                this.world.getServer().getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    this.target = event.getTarget() == null ? null : ((CraftEntity)event.getTarget()).getHandle();
                }
            }
            if (this.target != null) {
                this.br = 20;
            }
        }
        double d4 = 64.0;
        if (this.target != null && this.target.f((Entity)this) < d4 * d4) {
            double d5 = this.target.locX - this.locX;
            double d6 = this.target.boundingBox.b + (double)(this.target.length / 2.0f) - (this.locY + (double)(this.length / 2.0f));
            double d7 = this.target.locZ - this.locZ;
            this.aM = this.yaw = -((float)Math.atan2(d5, d7)) * 180.0f / (float)Math.PI;
            if (this.hasLineOfSight(this.target)) {
                if (this.bp == 10) {
                    this.world.a((EntityHuman)null, 1007, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                }
                ++this.bp;
                if (this.bp == 20) {
                    this.world.a((EntityHuman)null, 1008, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.world, (EntityLiving)this, d5, d6, d7);
                    entitylargefireball.yield = this.explosionPower;
                    entitylargefireball.bukkitYield = entitylargefireball.yield;
                    double d8 = 4.0;
                    Vec3D vec3d = this.j(1.0f);
                    entitylargefireball.locX = this.locX + vec3d.a * d8;
                    entitylargefireball.locY = this.locY + (double)(this.length / 2.0f) + 0.5;
                    entitylargefireball.locZ = this.locZ + vec3d.c * d8;
                    this.world.addEntity((Entity)entitylargefireball);
                    this.bp = -100;
                    try {
                        if (this.carrier == null) {
                            this.getCarrier();
                        }
                        if (this.carrier != null) {
                            this.carrier.onCarrierShoot((LargeFireball)entitylargefireball.getBukkitEntity());
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (this.bp > 0) {
                --this.bp;
            }
        } else {
            this.aM = this.yaw = -((float)Math.atan2(this.motX, this.motZ)) * 180.0f / (float)Math.PI;
            if (this.bp > 0) {
                --this.bp;
            }
        }
        if (!this.world.isStatic && (b0 = this.datawatcher.getByte(16)) != (b1 = (byte)(this.bp > 10 ? 1 : 0))) {
            this.datawatcher.watch(16, (Object)b1);
        }
    }

    private boolean a(double d0, double d1, double d2, double d3) {
        double d4 = (this.i - this.locX) / d3;
        double d5 = (this.bm - this.locY) / d3;
        double d6 = (this.bn - this.locZ) / d3;
        AxisAlignedBB axisalignedbb = this.boundingBox.clone();
        int i = 1;
        while ((double)i < d3) {
            axisalignedbb.d(d4, d5, d6);
            if (!this.world.getCubes((Entity)this, axisalignedbb).isEmpty()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public void getCarrier() {
        if (this.getBukkitEntity().hasMetadata("plague_carrier") && this.getBukkitEntity().getMetadata("plague_carrier").size() > 0) {
            UUID uuid = (UUID)((MetadataValue)this.getBukkitEntity().getMetadata("plague_carrier").get(0)).value();
            Boss boss = Boss.bosses.get(uuid);
            if (boss == null) {
                this.die();
                return;
            }
            PlagueBloater bloater = (PlagueBloater)boss;
            this.carrier = bloater.getPlagueCarrier();
        }
    }

    public void setCarrier(PlagueCarriers carrier) {
        this.carrier = carrier;
    }
}

