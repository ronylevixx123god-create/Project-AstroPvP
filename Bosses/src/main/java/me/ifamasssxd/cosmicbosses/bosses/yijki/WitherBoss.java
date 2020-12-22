/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.Block
 *  net.minecraft.server.v1_8_R3.Blocks
 *  net.minecraft.server.v1_8_R3.DamageSource
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityCreature
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityMonster
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.EntityWither
 *  net.minecraft.server.v1_8_R3.EntityWitherSkull
 *  net.minecraft.server.v1_8_R3.EnumDifficulty
 *  net.minecraft.server.v1_8_R3.EnumEntitySize
 *  net.minecraft.server.v1_8_R3.EnumMonsterType
 *  net.minecraft.server.v1_8_R3.GenericAttributes
 *  net.minecraft.server.v1_8_R3.IEntitySelector
 *  net.minecraft.server.v1_8_R3.IRangedEntity
 *  net.minecraft.server.v1_8_R3.Material
 *  net.minecraft.server.v1_8_R3.MathHelper
 *  net.minecraft.server.v1_8_R3.MobEffect
 *  net.minecraft.server.v1_8_R3.NBTTagCompound
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent
 *  net.minecraft.server.v1_8_R3.PathfinderGoal
 *  net.minecraft.server.v1_8_R3.PathfinderGoalFloat
 *  net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 */
package me.ifamasssxd.cosmicbosses.bosses.yijki;

import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicbosses.bosses.yijki.PathfinderGoalWitherBossArrowAttack;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class WitherBoss
extends EntityMonster
implements IRangedEntity {
    private Boss boss;
    private float[] bp = new float[2];
    private float[] bq = new float[2];
    private float[] br = new float[2];
    private float[] bs = new float[2];
    private int[] nextShot = new int[2];
    private int[] shootTimer = new int[2];
    private int bv;
    private IEntitySelector bw;
    int failedTimes = 100;
    private long lastTargetCheck = System.currentTimeMillis();
    private String lastTargetName = "";

    protected void c() {
        super.c();
        this.datawatcher.a(17, (Object)new Integer(0));
        this.datawatcher.a(18, (Object)new Integer(0));
        this.datawatcher.a(19, (Object)new Integer(0));
        this.datawatcher.a(20, (Object)new Integer(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.ca());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.s(nbttagcompound.getInt("Invul"));
    }

    protected String t() {
        return "mob.wither.idle";
    }

    protected String aT() {
        return "mob.wither.hurt";
    }

    protected String aU() {
        return "mob.wither.death";
    }

    public WitherBoss(World world) {
        super(world);
        try {
            this.bw = (IEntitySelector)ReflectionUtils.getObject(EntityWither.class, (Object)this, "bw");
        }
        catch (Exception e) {
            e.printStackTrace();
            this.bw = null;
        }
        this.setHealth(this.getMaxHealth());
        this.a(0.9f, 4.0f);
        this.fireProof = true;
        this.getNavigation().e(true);
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(6, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 8.0f));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.targetSelector.a(1, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, false));
        this.targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, EntityHuman.class, 0, false, false, this.bw));
        this.b = 50;
        this.as = EnumEntitySize.SIZE_6;
    }

    public void setBoss(Boss boss) {
        if (boss != null) {
            this.boss = boss;
        } else {
            --this.failedTimes;
        }
        if (boss != null) {
            this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalWitherBossArrowAttack(boss, this, 1.0, 40, 40, 20.0f));
        }
        System.out.println("Given: " + boss);
    }

    public void e() {
        int j;
        int i;
        double distanceSqrRoot;
        double distanceSqr;
        double distanceZ;
        Entity entity;
        if (this.boss == null && this.failedTimes > 0) {
            if (this.ticksLived > 10) {
                this.boss = Boss.bosses.get(this.getUniqueID());
                this.setBoss(this.boss);
            }
            return;
        }
        if (this.failedTimes == 0) {
            this.die();
            return;
        }
        double motionMultiplier = 0.6f;
        this.motY *= motionMultiplier;
        if (!this.world.isStatic && this.t(0) > 0 && (entity = this.world.getEntity(this.t(0))) != null) {
            double height = 7.0;
            if (this.boss != null) {
                double d = height = this.boss.getPhase() == Phase.TWO ? 7.0 : 3.0;
            }
            if (this.locY < entity.locY || this.locY < entity.locY + height) {
                if (this.motY < 0.0) {
                    this.motY = 0.0;
                }
                this.motY += (0.5 - this.motY) * (motionMultiplier / 2.0);
            }
            double distanceX = entity.locX - this.locX;
            distanceZ = entity.locZ - this.locZ;
            distanceSqr = distanceX * distanceX + distanceZ * distanceZ;
            double distanceFromPlayer = 9.0;
            double speedMultiplier = 1.0;
            if (this.boss != null) {
                distanceFromPlayer = this.boss.getPhase() == Phase.TWO ? 12.0 : 4.0;
                double d = speedMultiplier = this.boss.getPhase() == Phase.TWO ? 1.2 : 1.0;
            }
            if (distanceSqr > distanceFromPlayer) {
                distanceSqrRoot = MathHelper.sqrt((double)distanceSqr);
                this.motX += (distanceX / distanceSqrRoot * 0.7 - this.motX) * (motionMultiplier * speedMultiplier);
                this.motZ += (distanceZ / distanceSqrRoot * 0.7 - this.motZ) * (motionMultiplier * speedMultiplier);
            }
        }
        if (this.motX * this.motX + this.motZ * this.motZ > (double)0.05f) {
            this.yaw = (float)Math.atan2(this.motZ, this.motX) * 57.295776f - 90.0f;
        }
        super.e();
        for (i = 0; i < 2; ++i) {
            this.bs[i] = this.bq[i];
            this.br[i] = this.bp[i];
        }
        for (i = 0; i < 2; ++i) {
            int j2 = this.t(i + 1);
            Entity entity1 = null;
            if (j2 > 0) {
                entity1 = this.world.getEntity(j2);
            }
            if (entity1 != null && entity1 instanceof EntityHuman) {
                distanceZ = this.u(i + 1);
                distanceSqr = this.v(i + 1);
                distanceSqrRoot = this.w(i + 1);
                double d4 = entity1.locX - distanceZ;
                double d5 = entity1.locY + (double)entity1.getHeadHeight() - distanceSqr;
                double d6 = entity1.locZ - distanceSqrRoot;
                double d7 = MathHelper.sqrt((double)(d4 * d4 + d6 * d6));
                float f = (float)(Math.atan2(d6, d4) * 180.0 / 3.1415927410125732) - 90.0f;
                float f1 = (float)(-(Math.atan2(d5, d7) * 180.0 / 3.1415927410125732));
                this.bp[i] = this.b(this.bp[i], f1, 40.0f);
                this.bq[i] = this.b(this.bq[i], f, 10.0f);
                continue;
            }
            this.bq[i] = this.b(this.bq[i], this.aM, 10.0f);
        }
        boolean flag = this.cb();
        for (j = 0; j < 3; ++j) {
            double d8 = this.u(j);
            double d9 = this.v(j);
            double d10 = this.w(j);
            if (!this.isInvisible()) {
                this.world.addParticle("smoke", d8 + this.random.nextGaussian() * (double)0.3f, d9 + this.random.nextGaussian() * (double)0.3f, d10 + this.random.nextGaussian() * (double)0.3f, 0.0, 0.0, 0.0);
            }
            if (!flag || this.world.random.nextInt(4) != 0) continue;
            this.world.addParticle("mobSpell", d8 + this.random.nextGaussian() * (double)0.3f, d9 + this.random.nextGaussian() * (double)0.3f, d10 + this.random.nextGaussian() * (double)0.3f, (double)0.7f, (double)0.7f, 0.5);
        }
        if (this.ca() > 0) {
            for (j = 0; j < 3; ++j) {
                this.world.addParticle("mobSpell", this.locX + this.random.nextGaussian() * 1.0, this.locY + (double)(this.random.nextFloat() * 3.3f), this.locZ + this.random.nextGaussian() * 1.0, (double)0.7f, (double)0.7f, (double)0.9f);
            }
        }
    }

    protected void bn() {
        if (this.ca() > 0) {
            int witherTargetId = this.ca() - 1;
            if (witherTargetId <= 0) {
                ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), 7.0f, false);
                this.world.getServer().getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    this.world.createExplosion((Entity)this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
                }
                this.world.createExplosion((Entity)this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, 7.0f, false, this.world.getGameRules().getBoolean("mobGriefing"));
                int viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
                for (EntityPlayer player : this.world.players) {
                    double deltaX = this.locX - player.locX;
                    double deltaZ = this.locZ - player.locZ;
                    double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                    if (this.world.spigotConfig.witherSpawnSoundRadius > 0 && distanceSquared > (double)(this.world.spigotConfig.witherSpawnSoundRadius * this.world.spigotConfig.witherSpawnSoundRadius)) continue;
                    if (distanceSquared > (double)(viewDistance * viewDistance)) {
                        double deltaLength = Math.sqrt(distanceSquared);
                        double relativeX = player.locX + deltaX / deltaLength * (double)viewDistance;
                        double relativeZ = player.locZ + deltaZ / deltaLength * (double)viewDistance;
                        player.playerConnection.sendPacket((Packet)new PacketPlayOutWorldEvent(1013, (int)relativeX, (int)this.locY, (int)relativeZ, 0, true));
                        continue;
                    }
                    player.playerConnection.sendPacket((Packet)new PacketPlayOutWorldEvent(1013, (int)this.locX, (int)this.locY, (int)this.locZ, 0, true));
                }
            }
            this.s(witherTargetId);
            if (this.ticksLived % 10 == 0) {
                this.heal(10.0f, EntityRegainHealthEvent.RegainReason.WITHER_SPAWN);
            }
        } else {
            int target;
            int witherTargetId;
            super.bn();
            for (witherTargetId = 1; witherTargetId < 3; ++witherTargetId) {
                if (this.ticksLived < this.nextShot[witherTargetId - 1]) continue;
                this.nextShot[witherTargetId - 1] = this.ticksLived + 10 + this.random.nextInt(10);
                if (this.world.difficulty == EnumDifficulty.NORMAL || this.world.difficulty == EnumDifficulty.HARD) {
                    int targetId = witherTargetId - 1;
                    int value = this.shootTimer[targetId];
                    this.shootTimer[targetId] = value + 1;
                    if (value > 15) {
                        float width = 10.0f;
                        float height = 5.0f;
                        double x = MathHelper.a((Random)this.random, (double)(this.locX - (double)width), (double)(this.locX + (double)width));
                        double y = MathHelper.a((Random)this.random, (double)(this.locY - (double)height), (double)(this.locY + (double)height));
                        double z = MathHelper.a((Random)this.random, (double)(this.locZ - (double)width), (double)(this.locZ + (double)width));
                        this.a(witherTargetId + 1, x, y, z, true);
                        this.shootTimer[witherTargetId - 1] = 0;
                    }
                }
                if ((target = this.t(witherTargetId)) > 0 && this.random.nextInt(10) <= 2 && System.currentTimeMillis() - this.lastTargetCheck <= 15000L) {
                    Entity entity = this.world.getEntity(target);
                    if (!(entity instanceof EntityHuman)) continue;
                    if (entity != null && entity.isAlive() && this.f(entity) <= 900.0 && this.hasLineOfSight(entity)) {
                        this.a(witherTargetId + 1, (EntityLiving)entity);
                        this.nextShot[witherTargetId - 1] = this.ticksLived + 5 + this.random.nextInt(20);
                        this.shootTimer[witherTargetId - 1] = 0;
                        continue;
                    }
                    this.b(witherTargetId, 0);
                    continue;
                }
                List list = this.world.a(EntityHuman.class, this.boundingBox.grow(50.0, 8.0, 50.0), (IEntitySelector)ReflectionUtils.getObject(EntityWither.class, (Object)this, "bw"));
                int targetsFound = 0;
                for (int i1 = 0; i1 < 10 && !list.isEmpty(); ++i1) {
                    EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));
                    if (entityliving != this && entityliving.isAlive() && this.hasLineOfSight((Entity)entityliving)) {
                        if (!(entityliving instanceof EntityHuman)) break;
                        EntityHuman human = (EntityHuman)entityliving;
                        if (this.lastTargetName.equals(human.getName())) continue;
                        if (human.abilities.isInvulnerable) break;
                        ++targetsFound;
                        this.b(witherTargetId, entityliving.getId());
                        break;
                    }
                    list.remove((Object)entityliving);
                }
                if (targetsFound <= 0) continue;
                this.lastTargetCheck = System.currentTimeMillis();
            }
            if (this.getGoalTarget() != null && this.getGoalTarget() instanceof EntityHuman) {
                this.b(0, this.getGoalTarget().getId());
            } else {
                this.b(0, 0);
            }
            if (this.bv > 0) {
                --this.bv;
                if (this.bv == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    witherTargetId = MathHelper.floor((double)this.locY);
                    target = MathHelper.floor((double)this.locX);
                    int j1 = MathHelper.floor((double)this.locZ);
                    boolean flag = false;
                    for (int k1 = -1; k1 <= 1; ++k1) {
                        for (int l1 = -1; l1 <= 1; ++l1) {
                            for (int i2 = 0; i2 <= 3; ++i2) {
                                int j2 = target + k1;
                                int k2 = witherTargetId + i2;
                                int l2 = j1 + l1;
                                Block block = this.world.getType(j2, k2, l2);
                                if (block.getMaterial() == Material.AIR || block == Blocks.BEDROCK || block == Blocks.ENDER_PORTAL || block == Blocks.ENDER_PORTAL_FRAME || block == Blocks.COMMAND || CraftEventFactory.callEntityChangeBlockEvent((Entity)this, (int)j2, (int)k2, (int)l2, (Block)Blocks.AIR, (int)0).isCancelled()) continue;
                                flag = this.world.setAir(j2, k2, l2, true) || flag;
                            }
                        }
                    }
                    if (flag) {
                        this.world.a((EntityHuman)null, 1012, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                    }
                }
            }
            if (this.ticksLived % 120 == 0) {
                this.heal(1.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
        }
    }

    public void bZ() {
        this.s(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }

    public void as() {
    }

    public int aV() {
        return 4;
    }

    private double u(int i) {
        if (i <= 0) {
            return this.locX;
        }
        float f = (this.aM + (float)(180 * (i - 1))) / 180.0f * (float)Math.PI;
        float f1 = MathHelper.cos((float)f);
        return this.locX + (double)f1 * 1.3;
    }

    private double v(int i) {
        return i <= 0 ? this.locY + 3.0 : this.locY + 2.2;
    }

    private double w(int i) {
        if (i <= 0) {
            return this.locZ;
        }
        float f = (this.aM + (float)(180 * (i - 1))) / 180.0f * (float)Math.PI;
        float f1 = MathHelper.sin((float)f);
        return this.locZ + (double)f1 * 1.3;
    }

    private float b(float f, float f1, float f2) {
        float f3 = MathHelper.g((float)(f1 - f));
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    private void a(int i, EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + (double)entityliving.getHeadHeight() * 0.5, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001f);
    }

    private void a(int entityID, double spawnX, double spawnY, double spawnZ, boolean flag) {
        this.world.a((EntityHuman)null, 1014, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
        double x = this.u(entityID);
        double y = this.v(entityID);
        double z = this.w(entityID);
        double d6 = spawnX - x;
        double d7 = spawnY - y;
        double d8 = spawnZ - z;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, (EntityLiving)this, d6, d7, d8);
        entitywitherskull.locY = y;
        entitywitherskull.locX = x;
        entitywitherskull.locZ = z;
        this.world.addEntity((Entity)entitywitherskull);
    }

    public void a(EntityLiving entityliving, float f) {
        this.a(0, entityliving);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (this.isInvulnerable()) {
            return false;
        }
        if (damagesource == DamageSource.DROWN || damagesource == DamageSource.WITHER) {
            return false;
        }
        if (this.ca() > 0) {
            return false;
        }
        Entity entity = damagesource.getEntity();
        if (entity != null && !(entity instanceof EntityHuman) && entity instanceof EntityLiving && ((EntityLiving)entity).getMonsterType() == this.getMonsterType() || entity instanceof EntityWitherSkull) {
            return false;
        }
        if (this.bv <= 0) {
            this.bv = 20;
        }
        int i = 0;
        while (i < this.shootTimer.length) {
            int n = i++;
            this.shootTimer[n] = this.shootTimer[n] + 3;
        }
        return super.damageEntity(damagesource, f);
    }

    protected void dropDeathLoot(boolean flag, int i) {
    }

    protected void w() {
        this.aU = 0;
    }

    protected void b(float f) {
    }

    public void addEffect(MobEffect mobeffect) {
    }

    protected boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0);
        this.getAttributeInstance(GenericAttributes.d).setValue((double)0.6f);
        this.getAttributeInstance(GenericAttributes.b).setValue(40.0);
    }

    public int ca() {
        return this.datawatcher.getInt(20);
    }

    public void s(int i) {
        this.datawatcher.watch(20, (Object)i);
    }

    public int t(int i) {
        return this.datawatcher.getInt(17 + i);
    }

    public void b(int i, int j) {
        this.datawatcher.watch(17 + i, (Object)j);
    }

    public boolean cb() {
        return this.getHealth() <= this.getMaxHealth() / 2.0f;
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    public void mount(Entity entity) {
        this.vehicle = null;
    }
}

