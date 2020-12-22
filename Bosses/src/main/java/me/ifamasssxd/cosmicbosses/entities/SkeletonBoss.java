/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.AttributeInstance
 *  net.minecraft.server.v1_7_R4.Enchantment
 *  net.minecraft.server.v1_7_R4.EnchantmentManager
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityArrow
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.EntitySkeleton
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack
 *  net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.craftbukkit.v1_7_R4.TrigMath
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTargetEvent$TargetReason
 */
package me.ifamasssxd.cosmicbosses.entities;

import java.util.List;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossArrowAttack;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossMeleeAttack;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.UndeadAssassin;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_7_R4.AttributeInstance;
import net.minecraft.server.v1_7_R4.Enchantment;
import net.minecraft.server.v1_7_R4.EnchantmentManager;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityArrow;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntitySkeleton;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class SkeletonBoss
extends EntitySkeleton {
    UndeadAssassin boss;

    public SkeletonBoss(World world) {
        super(world);
        ReflectionUtils.setObject(EntitySkeleton.class, (Object)this, (Object)new PathfinderGoalBossArrowAttack((IRangedEntity)this, 1.0, 20, 60, 15.0f), "bp");
        ReflectionUtils.setObject(EntitySkeleton.class, (Object)this, (Object)new PathfinderGoalBossMeleeAttack((EntityCreature)this, EntityHuman.class, 1.2, false), "bq");
        List list = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, (Object)this.goalSelector, "b");
        list.clear();
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, EntityHuman.class, 1.0, false));
        this.goalSelector.a(3, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 8.0f));
        this.goalSelector.a(4, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, false));
        this.targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, EntityHuman.class, 0, true));
        if (world != null && !world.isStatic) {
            this.bZ();
        }
    }

    protected Entity findTarget() {
        EntityHuman human = this.world.findNearbyVulnerablePlayer((Entity)this, 16.0);
        if (human != null && !human.getBukkitEntity().hasMetadata("NPC")) {
            return human;
        }
        return null;
    }

    protected void bq() {
        this.world.methodProfiler.a("ai");
        if (this.bo > 0 && --this.bo == 0) {
            AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.d);
            attributeinstance.b(i);
        }
        this.bn = this.bP();
        float f11 = 16.0f;
        if (this.target == null) {
            System.out.println("Target null!");
            Entity target = this.findTarget();
            if (target != null) {
                EntityTargetEvent event = new EntityTargetEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), (org.bukkit.entity.Entity)target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
                this.world.getServer().getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    this.target = event.getTarget() == null ? null : ((CraftEntity)event.getTarget()).getHandle();
                }
            }
            if (this.target != null) {
                this.pathEntity = this.world.findPath((Entity)this, this.target, f11, true, false, false, true);
            }
        } else if (this.target.isAlive()) {
            float f1 = this.target.e((Entity)this);
            if (this.hasLineOfSight(this.target)) {
                System.out.println("Targetting?");
                this.a(this.target, f1);
            }
        } else {
            EntityTargetEvent event = new EntityTargetEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
            this.world.getServer().getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                if (event.getTarget() == null) {
                    System.out.println("target dead");
                    this.target = null;
                } else {
                    this.target = ((CraftEntity)event.getTarget()).getHandle();
                }
            }
        }
        if (this.target instanceof EntityPlayer && ((EntityPlayer)this.target).playerInteractManager.isCreative()) {
            this.target = null;
        }
        this.world.methodProfiler.b();
        if (!(this.bn || this.target == null || this.pathEntity != null && this.random.nextInt(20) != 0)) {
            this.pathEntity = this.world.findPath((Entity)this, this.target, f11, true, false, false, true);
        } else if (!this.bn && (this.pathEntity == null && this.random.nextInt(180) == 0 || this.random.nextInt(120) == 0 || this.bo > 0) && this.aU < 100) {
            this.bQ();
        }
        int i = MathHelper.floor((double)(this.boundingBox.b + 0.5));
        boolean flag = this.M();
        boolean flag1 = this.P();
        this.pitch = 0.0f;
        if (this.pathEntity != null && this.random.nextInt(100) != 0) {
            this.world.methodProfiler.a("followpath");
            Vec3D vec3d = this.pathEntity.a((Entity)this);
            double d0 = this.width * 2.0f;
            while (vec3d != null && vec3d.d(this.locX, vec3d.b, this.locZ) < d0 * d0) {
                this.pathEntity.a();
                if (this.pathEntity.b()) {
                    vec3d = null;
                    this.pathEntity = null;
                    continue;
                }
                vec3d = this.pathEntity.a((Entity)this);
            }
            this.bc = false;
            if (vec3d != null) {
                double d1 = vec3d.a - this.locX;
                double d2 = vec3d.c - this.locZ;
                double d3 = vec3d.b - (double)i;
                float f2 = (float)(TrigMath.atan2((double)d2, (double)d1) * 180.0 / 3.1415927410125732) - 90.0f;
                float f3 = MathHelper.g((float)(f2 - this.yaw));
                this.be = (float)this.getAttributeInstance(GenericAttributes.d).getValue();
                if (f3 > 30.0f) {
                    f3 = 30.0f;
                }
                if (f3 < -30.0f) {
                    f3 = -30.0f;
                }
                this.yaw += f3;
                if (this.bn && this.target != null) {
                    double d4 = this.target.locX - this.locX;
                    double d5 = this.target.locZ - this.locZ;
                    float f4 = this.yaw;
                    this.yaw = (float)(Math.atan2(d5, d4) * 180.0 / 3.1415927410125732) - 90.0f;
                    f3 = (f4 - this.yaw + 90.0f) * (float)Math.PI / 180.0f;
                    this.bd = -MathHelper.sin((float)f3) * this.be * 1.0f;
                    this.be = MathHelper.cos((float)f3) * this.be * 1.0f;
                }
                if (d3 > 0.0) {
                    this.bc = true;
                }
            }
            if (this.target != null) {
                this.a(this.target, 30.0f, 30.0f);
            }
            if (this.positionChanged && !this.bS()) {
                this.bc = true;
            }
            if (this.random.nextFloat() < 0.8f && (flag || flag1)) {
                this.bc = true;
            }
            this.world.methodProfiler.b();
        } else {
            super.bq();
            this.pathEntity = null;
        }
    }

    public void e() {
        if (this.boss == null) {
            this.boss = (UndeadAssassin)Boss.bosses.get(this.getUniqueID());
        }
        if (this.boss != null && this.boss.invincible) {
            return;
        }
        super.e();
    }

    public void a(EntityLiving entityliving, float f) {
        EntityCombustEvent event;
        EntityArrow entityarrow = new EntityArrow(this.world, (EntityLiving)this, entityliving, 1.6f, (float)(14 - this.world.difficulty.a() * 4));
        int i = EnchantmentManager.getEnchantmentLevel((int)Enchantment.ARROW_DAMAGE.id, (ItemStack)this.be());
        int j = EnchantmentManager.getEnchantmentLevel((int)Enchantment.ARROW_KNOCKBACK.id, (ItemStack)this.be());
        entityarrow.b(2.0);
        if (i > 0) {
            entityarrow.b(entityarrow.e() + (double)i * 0.5 + 0.5);
        }
        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }
        if (EnchantmentManager.getEnchantmentLevel((int)Enchantment.ARROW_FIRE.id, (ItemStack)this.be()) > 0 || this.getSkeletonType() == 1) {
            event = new EntityCombustEvent((org.bukkit.entity.Entity)entityarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent((Event)event);
            if (!event.isCancelled()) {
                entityarrow.setOnFire(event.getDuration());
            }
        }
        if ((event = CraftEventFactory.callEntityShootBowEvent((EntityLiving)this, (ItemStack)this.be(), (EntityArrow)entityarrow, (float)0.8f)).isCancelled()) {
            event.getProjectile().remove();
            return;
        }
        if (event.getProjectile() == entityarrow.getBukkitEntity()) {
            this.world.addEntity((Entity)entityarrow);
        }
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aI().nextFloat() * 0.4f + 0.8f));
    }
}

