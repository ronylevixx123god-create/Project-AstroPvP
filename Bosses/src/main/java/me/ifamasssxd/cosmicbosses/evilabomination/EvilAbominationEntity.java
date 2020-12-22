/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.EntityWitherSkull
 *  net.minecraft.server.v1_7_R4.EntityZombie
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.MovingObjectPosition
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.evilabomination;

import java.util.List;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossMeleeAttack;
import me.ifamasssxd.cosmicbosses.bosses.yijki.PathfinderGoalWitherBossArrowAttack;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbomination;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntityWitherSkull;
import net.minecraft.server.v1_7_R4.EntityZombie;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_7_R4.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EvilAbominationEntity
extends EntityZombie
implements IRangedEntity {
    private EvilAbomination boss;

    public EvilAbominationEntity(EvilAbomination boss, World world) {
        this(world);
        this.boss = boss;
    }

    public EvilAbominationEntity(World world) {
        super(world);
        List list = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, (Object)this.goalSelector, "b");
        list.clear();
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalWitherBossArrowAttack(this, 1.0, 10, 25, 23.0f));
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalBossMeleeAttack((EntityCreature)this, EntityHuman.class, 1.2, false));
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
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

    public void shootNearby(EntityLiving originalTarget) {
        if (this.boss == null || this.boss.getPhase() != Phase.TWO) {
            return;
        }
        int shots = 0;
        int max = this.random.nextInt(5) + 6;
        for (org.bukkit.entity.Entity entity : this.getBukkitEntity().getNearbyEntities(32.0, 32.0, 32.0)) {
            EntityPlayer player;
            Entity ent;
            if (shots >= max) break;
            if (!(entity instanceof Player) || !(Math.random() <= 0.9) || (ent = ((CraftEntity)entity).getHandle()).equals((Object)originalTarget) || (player = (EntityPlayer)ent).getBukkitEntity().getGameMode() != GameMode.SURVIVAL) continue;
            ++shots;
            this.a((EntityLiving)ent, 1.0f);
        }
    }

    public void a(EntityLiving entityliving, float f) {
        Location headLoc = this.getBukkitEntity().getLocation().add(0.0, (double)this.getHeadHeight(), 0.0);
        LivingEntity ent = (LivingEntity)entityliving.getBukkitEntity();
        Vector velocity = headLoc.toVector().subtract(ent.getLocation().add(0.0, 0.2, 0.0).toVector());
        if (velocity.length() != 0.0) {
            velocity.normalize();
        }
        velocity.multiply(-1);
        Vector spawnVel = velocity.clone().multiply(3);
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, (EntityLiving)this, spawnVel.getX(), spawnVel.getY(), spawnVel.getZ()){

            protected void a(MovingObjectPosition movingobjectposition) {
                Entity colliding = movingobjectposition.entity;
                if (colliding != null && !(colliding instanceof EntityHuman)) {
                    return;
                }
                super.a(movingobjectposition);
            }

            protected float e() {
                return 0.95f;
            }

            public void setDirection(double d0, double d1, double d2) {
                double d3 = MathHelper.sqrt((double)(d0 * d0 + d1 * d1 + d2 * d2));
                this.dirX = d0 / d3 * 0.1;
                this.dirY = d1 / d3 * 0.1;
                this.dirZ = d2 / d3 * 0.1;
            }
        };
        double d = this.random.nextDouble();
        double d2 = this.vehicle != null && this.vehicle.isAlive() ? 0.13 : 0.18;
        if (d <= d2 && this.boss != null && this.boss.getMinions().size() < 10) {
            entitywitherskull.setCharged(true);
        }
        Location spawnLoc = headLoc.add(velocity.getX(), 0.0, velocity.getZ());
        entitywitherskull.locY = spawnLoc.getY();
        entitywitherskull.locX = spawnLoc.getX();
        entitywitherskull.locZ = spawnLoc.getZ();
        this.world.addEntity((Entity)entitywitherskull);
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aI().nextFloat() * 0.4f + 0.8f));
    }
}

