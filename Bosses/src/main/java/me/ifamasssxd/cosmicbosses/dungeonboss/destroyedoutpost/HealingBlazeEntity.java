/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityBlaze
 *  net.minecraft.server.v1_7_R4.EntityFireball
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntitySmallFireball
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.Item
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.MovingObjectPosition
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityCombustByEntityEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityBlaze;
import net.minecraft.server.v1_7_R4.EntityFireball;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntitySmallFireball;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.World;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class HealingBlazeEntity
extends EntityBlaze {
    private MotherOfYijki parent;
    private int br;
    long timeSinceShot;

    public HealingBlazeEntity(World world) {
        super(world);
    }

    public HealingBlazeEntity(MotherOfYijki parent, World world) {
        super(world);
        this.parent = parent;
        this.getAttributeInstance(GenericAttributes.c).setValue(1.0);
    }

    public void e() {
        if (this.parent == null || !this.parent.isBossSpawned()) {
            if (this.parent != null) {
                this.parent.removeMinion((org.bukkit.entity.Entity)this.bukkitEntity);
            }
            this.die();
            return;
        }
        super.e();
        if (this.ticksLived < 140) {
            boolean beenSecond;
            boolean bl = beenSecond = this.ticksLived % 20 == 0;
            if (beenSecond || this.ticksLived % 3 == 0) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.CLOUD, (Location)this.getBukkitEntity().getLocation(), (float)0.24f, (float)0.24f, (float)0.24f, (float)0.1f, (int)(beenSecond ? 20 : 3));
            }
            return;
        }
        if (!(this.timeSinceShot != 0L && System.currentTimeMillis() - this.timeSinceShot < 5000L || this.parent.isInDifferentDimension())) {
            this.timeSinceShot = System.currentTimeMillis();
            this.shootHealingFireball();
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                if (this.parent != null && this.parent.isBossSpawned()) {
                    this.parent.onHeal(7);
                }
            }, 20L);
        }
    }

    private void shootHealingFireball() {
        Vector spawnVec = this.parent.getEntity().getLocation().add(0.0, 1.0, 0.0).toVector().subtract(this.getBukkitEntity().getLocation().add(0.0, 1.0, 0.0).toVector());
        if (spawnVec.length() != 0.0) {
            spawnVec.normalize();
        }
        spawnVec.multiply(2.5);
        this.world.addEntity((Entity)this.createAccurateFireball(this.world, spawnVec.getX(), spawnVec.getY(), spawnVec.getZ(), false, true));
    }

    protected void a(Entity var1, float var2) {
        if (this.attackTicks <= 0 && var2 < 2.0f && var1.boundingBox.e > this.boundingBox.b && var1.boundingBox.b < this.boundingBox.e) {
            this.attackTicks = 20;
            this.n(var1);
        } else if (var2 < 30.0f) {
            double var3 = var1.locX - this.locX;
            double var5 = var1.boundingBox.b + (double)(var1.length / 2.0f) - (this.locY + (double)(this.length / 2.0f));
            double var7 = var1.locZ - this.locZ;
            if (this.attackTicks == 0) {
                ++this.br;
                if (this.br == 1) {
                    this.attackTicks = 40;
                    this.a(true);
                } else if (this.br <= 4) {
                    this.attackTicks = 6;
                } else {
                    this.attackTicks = 60;
                    this.br = 0;
                    this.a(false);
                }
                if (this.br > 1) {
                    this.world.a((EntityHuman)null, 1009, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    for (int var10 = 0; var10 < 1; ++var10) {
                        EntitySmallFireball fireball = this.createAccurateFireball(this.world, var3, var5, var7, true, false);
                        fireball.locY = this.locY + (double)(this.length / 2.0f) + 0.5;
                        this.world.addEntity((Entity)fireball);
                    }
                }
            }
            this.yaw = (float)(Math.atan2(var7, var3) * 180.0 / 3.1415927410125732) - 90.0f;
            this.bn = true;
        }
    }

    private EntitySmallFireball createAccurateFireball(World world, double dirX, double dirY, double dirZ, final boolean collides, final boolean heal) {
        return new EntitySmallFireball(world, (EntityLiving)this, dirX, dirY, dirZ){

            public void setDirection(double d0, double d1, double d2) {
                double d3 = MathHelper.sqrt((double)(d0 * d0 + d1 * d1 + d2 * d2));
                this.dirX = d0 / d3 * 0.1;
                this.dirY = d1 / d3 * 0.1;
                this.dirZ = d2 / d3 * 0.1;
            }

            public void h() {
                super.h();
                if (this.isAlive() && !collides) {
                    ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.HAPPY_VILLAGER, (Location)this.getBukkitEntity().getLocation(), (float)0.0f, (float)0.0f, (float)0.0f, (float)0.01f, (int)1);
                }
            }

            protected void a(MovingObjectPosition movingobjectposition) {
                if (!this.world.isStatic) {
                    if (movingobjectposition.entity != null) {
                        if (heal && movingobjectposition.entity instanceof MotherOfYijkiEntity && !collides) {
                            HealingBlazeEntity.this.parent.onHeal(20);
                            this.die();
                            return;
                        }
                        if (!(movingobjectposition.entity instanceof EntityHuman)) {
                            return;
                        }
                        if (!movingobjectposition.entity.isFireproof() && movingobjectposition.entity.damageEntity(DamageSource.fireball((EntityFireball)this, (Entity)this.shooter), 5.0f)) {
                            EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((org.bukkit.entity.Entity)((Projectile)this.getBukkitEntity()), (org.bukkit.entity.Entity)movingobjectposition.entity.getBukkitEntity(), 5);
                            movingobjectposition.entity.world.getServer().getPluginManager().callEvent((Event)event);
                            if (!event.isCancelled()) {
                                movingobjectposition.entity.setOnFire(event.getDuration());
                            }
                        }
                    } else {
                        if (!collides && this.ticksLived < 200) {
                            return;
                        }
                        int i = movingobjectposition.b;
                        int j = movingobjectposition.c;
                        int k = movingobjectposition.d;
                        switch (movingobjectposition.face) {
                            case 0: {
                                --j;
                                break;
                            }
                            case 1: {
                                ++j;
                                break;
                            }
                            case 2: {
                                --k;
                                break;
                            }
                            case 3: {
                                ++k;
                                break;
                            }
                            case 4: {
                                --i;
                                break;
                            }
                            case 5: {
                                ++i;
                            }
                        }
                        if (this.world.isEmpty(i, j, k) && !CraftEventFactory.callBlockIgniteEvent((World)this.world, (int)i, (int)j, (int)k, (Entity)this).isCancelled()) {
                            this.world.setTypeUpdate(i, j, k, (Block)Blocks.FIRE);
                        }
                    }
                    this.die();
                }
            }
        };
    }

    protected Item getLoot() {
        return null;
    }
}

