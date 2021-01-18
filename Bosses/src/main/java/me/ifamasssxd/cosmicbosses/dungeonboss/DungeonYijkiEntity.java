/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.citizensnpcs.npc.entity.EntityHumanNPC
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
 *  net.minecraft.server.v1_8_R3.MovingObjectPosition
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
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss;

import java.util.List;
import java.util.Random;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.PathfinderGoalDungeonWitherBossAttack;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.UnstoppableForce;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.citizensnpcs.nms.v1_8_R3.entity.EntityHumanNPC;
import net.minecraft.server.v1_8_R3.*;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;
public class DungeonYijkiEntity extends EntityMonster implements IRangedEntity {
    private DungeonYijki boss;

    private float[] bp = new float[2];

    private float[] bq = new float[2];

    private float[] br = new float[2];

    private float[] bs = new float[2];

    private int[] nextShot = new int[2];

    private int[] shootTimer = new int[2];

    private int bv;

    private IEntitySelector entitySelector;

    int failedTimes;

    private long lastTargetCheck;

    private String lastTargetName;

    protected void c() {
        super.h();
        this.datawatcher.a(17, 0);
        this.datawatcher.a(18, 0);
        this.datawatcher.a(19, 0);
        this.datawatcher.a(20, 0);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", ca());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        s(nbttagcompound.getInt("Invul"));
    }

    protected String t() {
        return "mob.wither.idle";
    }

    protected String a() {
        return "mob.wither.hurt";
    }

    protected String b() {
        return "mob.wither.death";
    }

    public DungeonYijkiEntity(World world) {
        super(world);
        this.entitySelector = (entity -> WorldGuardUtils.isInRegionsFast(new Location((World)entity.world.getWorld(), entity.locX, entity.locY, entity.locZ), new String[] { "hanger", "bridge" }));
        this.failedTimes = 100;
        this.lastTargetCheck = System.currentTimeMillis();
        this.lastTargetName = "";
        setHealth(getMaxHealth());
        a(0.9F, 4.0F);
        this.fireProof = true;
        getNavigation().e(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget((EntityCreature)this, EntityHuman.class, 0, false, false, this.entitySelector));
        this.b_ = 50;
        this.as = EnumEntitySize.SIZE_6;
    }

    public void setBoss(DungeonYijki boss) {
        if (boss != null) {
            this.boss = boss;
        } else {
            this.failedTimes--;
        }
        if (boss != null) {
            boolean isMOY = boss instanceof me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
            this.goalSelector.a(2, (PathfinderGoal) new PathfinderGoalDungeonWitherBossAttack(boss, this, 1.0D, isMOY ? 25 : 40, isMOY ? 50 : 70, isMOY ? 30.0F : 20.0F));
        }
        System.out.println("Given: " + boss);
    }

    public void collide(Entity entity) {
        if (this.boss != null && this.boss.getActiveAbility() instanceof UnstoppableForce) {
            if (entity instanceof EntityHuman && !(entity instanceof EntityHumanNPC) && ((UnstoppableForce) this.boss.getActiveAbility()).shouldCharge() && entity.isAlive()) {
                BossUtils.isInGodMode((Player) entity.getBukkitEntity());
            }
        }
        super.collide(entity);
    }

    public void die() {
        super.die();
    }

    private String getTargetList() {
        StringBuilder builder = new StringBuilder();
        for (int witherTargetId = 1; witherTargetId < 3; witherTargetId++) {
            int targetID = t(witherTargetId);
            if (targetID > 0) {
                Entity ent = this.world.getEntity(targetID);
                if (ent != null)
                    builder.append(ent.getName()).append(",");
            }
        }
        return builder.toString();
    }

    public void setGoalTarget(EntityLiving entityliving) {
        if (getGoalTarget() != null && entityliving == null) {
            this.boss.location = this.boss.getRandomSpawnLocation();
            Bukkit.getLogger().info("Setting new Yijki spawn to " + this.boss.location.getX() + "x, " + this.boss.location.getY() + "y, " + this.boss.location.getZ() + "z");
        }
        super.setGoalTarget(entityliving);
    }

    public void e() {
        if (this.boss != null) {
            if (this.boss.getActiveAbility() != null) {
                if (this.boss.getActiveAbility().shouldStopTicking()) {
                    this.boss.clearAbilities();
                } else {
                    this.boss.getActiveAbility().onTick();
                }
            } else {
                long lastAbilityTime = this.boss.getAbilityActivateTime();
                if (this.boss.getActiveAbility() == null && lastAbilityTime >= 8000L) {
                    this.boss.findNewAbility();
                    Bukkit.getLogger().info("Finding new ability for YIjki: " + this.boss.getActiveAbility());
                }
            }
            if (this.boss.getEndOfWorldAbility() != null && !this.boss.getEndOfWorldAbility().shouldStopTicking())
                this.boss.getEndOfWorldAbility().onTick();
        }
        double motionMultiplier = 0.6000000238418579D;
        if (this.boss != null && this.boss.getActiveAbility() instanceof UnstoppableForce) {
            UnstoppableForce force = (UnstoppableForce)this.boss.getActiveAbility();
            Vector dir = force.getDirection();
            if (force.shouldCharge()) {
                List<Entity> ents = this.world.getEntities(this, this.getBoundingBox().grow(1.0D, 1.0D, 1.0D));
                for (Entity inBox : ents) {
                    collide(inBox);
                    Bukkit.getLogger().info("Colliding with " + inBox.getName() + " manually due to collide() being missed.");
                }
                this.motX += dir.getX();
                this.motZ += dir.getZ();
                if (this.ticksLived % 2 == 0)
                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, getBukkitEntity().getLocation(), 0.01F, 0.01F, 0.01F, 0.1F, 20);
            }
            if (this.target != null) {
                double targetY = this.target.locY;
                if (this.locY < targetY && this.motY < 0.0D)
                    this.motY = 0.0D;
            }
        } else {
            this.motY *= motionMultiplier;
            if (t(0) > 0) {
                Entity entity = this.world.getEntity(t(0));
                if (entity != null) {
                    Location entLoc = entity.getBukkitEntity().getLocation();
                    if (this.ticksLived % 10 == 0)
                        if (entLoc.getWorld().getName().endsWith("yijki_a") && !WorldGuardUtils.isInRegionsFast(entLoc, "bridge", "hanger")) {
                            Bukkit.getLogger().info("Untargetting " + entity.getName() + " due to leaving hanger or bridge region!");
                            getBukkitEntity().getLocation().getWorld().playSound(getBukkitEntity().getLocation(), Sound.ITEM_BREAK, 5.0F, 2.5F);
                            setGoalTarget((EntityLiving)null);
                            b(0, 0);
                            return;
                        }
                    moveTowardsLocation(entity.locX, entity.locY, entity.locZ, motionMultiplier);
                }
            } else if (this.boss != null) {
                Location spawn = this.boss.getLocation();
                if (f(spawn.getX(), spawn.getY(), spawn.getZ()) > 10.0D)
                    moveTowardsLocation(spawn.getX(), spawn.getY(), spawn.getZ(), motionMultiplier);
            }
        }
        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D)
            this.yaw = (float)Math.atan2(this.motZ, this.motX) * 57.295776F - 90.0F;
        super.e();
        int i;
        for (i = 0; i < 2; i++) {
            this.bs[i] = this.bq[i];
            this.br[i] = this.bp[i];
        }
        for (i = 0; i < 2; i++) {
            int k = t(i + 1);
            Entity entity1 = null;
            if (k > 0)
                entity1 = this.world.getEntity(k);
            if (entity1 != null && entity1 instanceof EntityHuman) {
                double distanceZ = u(i + 1);
                double distanceSqr = v(i + 1);
                double distanceSqrRoot = w(i + 1);
                double d4 = entity1.locX - distanceZ;
                double d5 = entity1.locY + entity1.getHeadHeight() - distanceSqr;
                double d6 = entity1.locZ - distanceSqrRoot;
                double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
                float f = (float)(Math.atan2(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f1 = (float)-(Math.atan2(d5, d7) * 180.0D / 3.1415927410125732D);
                this.bp[i] = b(this.bp[i], f1, 40.0F);
                this.bq[i] = b(this.bq[i], f, 10.0F);
            } else {
                this.bq[i] = b(this.bq[i], this.aM, 10.0F);
            }
        }
        boolean flag = cb();
        int j;
        for (j = 0; j < 3; j++) {
            double d8 = u(j);
            double d9 = v(j);
            double d10 = w(j);
            if (!isInvisible())
                this.world.addParticle(EnumParticle.valueOf("smoke"), d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
            if (flag && this.world.random.nextInt(4) == 0)
                this.world.addParticle(EnumParticle.valueOf("mobSpell"), d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
        }
        if (ca() > 0)
            for (j = 0; j < 3; j++)
                this.world.addParticle(EnumParticle.valueOf("mobSpell"), this.locX + this.random.nextGaussian() * 1.0D, this.locY + (this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
    }

    private void moveTowardsLocation(double x, double y, double z, double motionMultiplier) {
        double height = 7.0D;
        if (this.boss != null)
            if (this.boss.getActiveAbility() instanceof UnstoppableForce) {
                height = 0.0D;
            } else {
                height = (this.boss.getPhase() == Phase.TWO) ? 7.0D : 3.0D;
            }
        if (this.locY < y || this.locY < y + height) {
            if (this.motY < 0.0D)
                this.motY = 0.0D;
            this.motY += (0.5D - this.motY) * motionMultiplier / 2.0D;
        }
        double distanceX = x - this.locX, distanceZ = z - this.locZ;
        double distanceSqr = distanceX * distanceX + distanceZ * distanceZ;
        double distanceFromPlayer = 9.0D;
        double speedMultiplier = 1.0D;
        if (this.boss != null) {
            distanceFromPlayer = (this.boss.getPhase() == Phase.TWO) ? 12.0D : 4.0D;
            speedMultiplier = (this.boss.getPhase() == Phase.TWO) ? 1.2D : 1.0D;
        }
        if (distanceSqr > distanceFromPlayer) {
            double distanceSqrRoot = MathHelper.sqrt(distanceSqr);
            this.motX += (distanceX / distanceSqrRoot * 0.7D - this.motX) * motionMultiplier * speedMultiplier;
            this.motZ += (distanceZ / distanceSqrRoot * 0.7D - this.motZ) * motionMultiplier * speedMultiplier;
        }
    }

    protected void bn() {
        if (this.ca() > 0) {
            int witherTargetId = this.ca() - 1;
            if (witherTargetId <= 0) {
                ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), 7.0f, false);
                this.world.getServer().getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    this.world.createExplosion(this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
                }
                this.world.createExplosion(this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, 7.0f, false, this.world.getGameRules().getBoolean("mobGriefing"));
                int viewDistance = this.world.getServer().getViewDistance() * 16;
                for (EntityHuman player : this.world.players) {
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
            super.doTick();
            int witherTargetId;
            for (witherTargetId = 1; witherTargetId < 3; witherTargetId++) {
                if (this.ticksLived >= this.nextShot[witherTargetId - 1]) {
                    this.nextShot[witherTargetId - 1] = this.ticksLived + 10 + this.random.nextInt(10);
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
                        int targetId = witherTargetId - 1;
                        int value = this.shootTimer[targetId];
                        this.shootTimer[targetId] = value + 1;
                        if (value > 15) {
                            float width = 10.0F;
                            float height = 5.0F;
                            double x = MathHelper.a(this.random, this.locX - width, this.locX + width);
                            double y = MathHelper.a(this.random, this.locY - height, this.locY + height);
                            double z = MathHelper.a(this.random, this.locZ - width, this.locZ + width);
                            a(witherTargetId + 1, x, y, z, true);
                            this.shootTimer[witherTargetId - 1] = 0;
                        }
                    }
                    int target = t(witherTargetId);
                    if (target > 0) {
                        Entity entity = this.world.getEntity(target);
                        if (entity instanceof EntityHuman && entity.isAlive() && f(entity) <= 900.0D) {
                            a(witherTargetId + 1, (EntityLiving)entity);
                            this.nextShot[witherTargetId - 1] = this.ticksLived + 5 + this.random.nextInt(20);
                            this.shootTimer[witherTargetId - 1] = 0;
                        } else {
                            b(witherTargetId, 0);
                        }
                    } else {
                        List<?> list = this.world.a(EntityHuman.class, this.getBoundingBox().grow(50.0D, 16.0D, 50.0D), this.entitySelector);
                        for (int i1 = 0; i1 < 10 && !list.isEmpty(); i1++) {
                            EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));
                            if (entityliving != this && entityliving.isAlive() && hasLineOfSight((Entity)entityliving)) {
                                if (entityliving instanceof EntityHuman) {
                                    EntityHuman human = (EntityHuman)entityliving;
                                    if (!human.abilities.isInvulnerable)
                                        b(witherTargetId, entityliving.getId());
                                }
                                break;
                            }
                            list.remove(entityliving);
                        }
                    }
                }
            }
            if (getGoalTarget() != null && getGoalTarget() instanceof EntityHuman && getGoalTarget().isAlive()) {
                b(0, getGoalTarget().getId());
            } else {
                b(0, 0);
            }
            if (this.bv > 0) {
                this.bv--;
                if (this.bv == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    witherTargetId = MathHelper.floor(this.locY);
                    int target = MathHelper.floor(this.locX);
                    int j1 = MathHelper.floor(this.locZ);
                    boolean flag = false;
                    for (int k1 = -1; k1 <= 1; k1++) {
                        for (int l1 = -1; l1 <= 1; l1++) {
                            for (int i2 = 0; i2 <= 3; i2++) {
                                int j2 = target + k1;
                                int k2 = witherTargetId + i2;
                                int l2 = j1 + l1;
                                Block block = this.world.getType(j2, k2, l2);
                                if (block.getMaterial() != Material.AIR && block != Blocks.BEDROCK && block != Blocks.ENDER_PORTAL && block != Blocks.ENDER_PORTAL_FRAME && block != Blocks.COMMAND)
                                    if (!CraftEventFactory.callEntityChangeBlockEvent((Entity)this, j2, k2, l2, Blocks.AIR, 0).isCancelled())
                                        flag = (this.world.setAir(j2, k2, l2, true) || flag);
                            }
                        }
                    }
                    if (flag)
                        this.world.a((EntityHuman)null, 1012, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
                }
            }
            if (this.ticksLived % 120 == 0)
                heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
        }
    }

    public void bZ() {
        s(220);
        setHealth(getMaxHealth() / 3.0F);
    }

    public void as() {}

    public int aV() {
        return 4;
    }

    private double u(int i) {
        if (i <= 0)
            return this.locX;
        float f = (this.aM + (180 * (i - 1))) / 180.0F * 3.1415927F;
        float f1 = MathHelper.cos(f);
        return this.locX + f1 * 1.3D;
    }

    private double v(int i) {
        return (i <= 0) ? (this.locY + 3.0D) : (this.locY + 2.2D);
    }

    private double w(int i) {
        if (i <= 0)
            return this.locZ;
        float f = (this.aM + (180 * (i - 1))) / 180.0F * 3.1415927F;
        float f1 = MathHelper.sin(f);
        return this.locZ + f1 * 1.3D;
    }

    private float b(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2)
            f3 = f2;
        if (f3 < -f2)
            f3 = -f2;
        return f + f3;
    }

    private void a(int i, EntityLiving entityliving) {
        a(i, entityliving.locX, entityliving.locY + entityliving.getHeadHeight() * 0.5D, entityliving.locZ, (i == 0 && this.random
                .nextFloat() < 0.001F));
    }

    private void a(int entityID, double spawnX, double spawnY, double spawnZ, boolean flag) {
        this.world.a((EntityHuman)null, 1014, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
        double x = u(entityID);
        double y = v(entityID);
        double z = w(entityID);
        double d6 = spawnX - x;
        double d7 = spawnY - y;
        double d8 = spawnZ - z;
        final boolean fromMOY = this instanceof me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, (EntityLiving)this, d6 * 1.2D, d7, d8 * 1.2D) {
            protected void a(MovingObjectPosition movingobjectposition) {
                Entity colliding = movingobjectposition.entity;
                if (colliding != null && !(colliding instanceof EntityHuman))
                    return;
                super.a(movingobjectposition);
            }

            public void setDirection(double d0, double d1, double d2) {
                if (!fromMOY) {
                    d0 += this.random.nextGaussian() * 0.4D;
                    d1 += this.random.nextGaussian() * 0.4D;
                    d2 += this.random.nextGaussian() * 0.4D;
                }
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                this.dirX = d0 / d3 * 0.1D;
                this.dirY = d1 / d3 * 0.1D;
                this.dirZ = d2 / d3 * 0.1D;
            }
        };
        entitywitherskull.locY = y;
        entitywitherskull.locX = x;
        entitywitherskull.locZ = z;
        ProjectileLaunchEvent event = new ProjectileLaunchEvent(entitywitherskull.getBukkitEntity());
        Bukkit.getPluginManager().callEvent(event);
        this.world.addEntity(entitywitherskull);
    }

    public void a(EntityLiving entityliving, float f) {
        a(0, entityliving);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (isInvulnerable())
            return false;
        if (damagesource == DamageSource.DROWN || damagesource == DamageSource.WITHER)
            return false;
        if (ff() > 0)
            return false;
        Entity entity = damagesource.getEntity();
        if ((!(entity instanceof EntityHuman) && entity instanceof EntityLiving && ((EntityLiving) entity)
                .getMonsterType() == getMonsterType()) || entity instanceof EntityWitherSkull)
            return false;
        if (this.bv <= 0)
            this.bv = 20;
        for (int i = 0; i < this.shootTimer.length; i++)
            this.shootTimer[i] = this.shootTimer[i] + 3;
        return super.damageEntity(damagesource, f);
    }

    protected void dropDeathLoot(boolean flag, int i) {}

    protected void l() {
        this.aU = 0;
    }

    protected void b(float f) {}

    public void addEffect(MobEffect mobeffect) {}

    protected boolean bk() {
        return true;
    }

    protected void p() {
        super.aD();
        getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
        getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6000000238418579D);
        getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
    }

    public int ff() {
        return this.datawatcher.getInt(20);
    }

    public void s(int i) {
        this.datawatcher.watch(20, i);
    }

    public int t(int i) {
        return this.datawatcher.getInt(17 + i);
    }

    public void b(int i, int j) {
        this.datawatcher.watch(17 + i, j);
    }

    public boolean cb() {
        return (getHealth() <= getMaxHealth() / 2.0F);
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    public void mount(Entity entity) {
        this.vehicle = null;
    }
}
