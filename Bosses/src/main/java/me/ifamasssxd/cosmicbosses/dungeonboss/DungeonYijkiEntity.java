package me.ifamasssxd.cosmicbosses.dungeonboss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import me.ifamasssxd.cosmicbosses.dungeonboss.abilities.UnstoppableForce;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_8_R3.*;
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

import java.util.List;

public class DungeonYijkiEntity extends EntityMonster implements IRangedEntity {
    private DungeonYijki boss;
    private float[] a = new float[2];
    private float[] b = new float[2];
    private float[] c = new float[2];
    private float[] bm = new float[2];
    private int[] nextShot;
    private int[] shootTimer;
    private int bv;
    int failedTimes;
    private long lastTargetCheck;
    private String lastTargetName;

    public static final Predicate<Entity> bq = new Predicate() {
        public boolean a(Entity entity) {
            return (entity instanceof EntityLiving && ((EntityLiving) entity).getMonsterType() != EnumMonsterType.UNDEAD);
        }

        public boolean apply(Object object) {
            return a((Entity) object);
        }
    };

    protected void h() {
        super.h();
        this.datawatcher.a(17, (Object) new Integer(0));
        this.datawatcher.a(18, (Object) new Integer(0));
        this.datawatcher.a(19, (Object) new Integer(0));
        this.datawatcher.a(20, (Object) new Integer(0));
    }

    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.cl());
    }

    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.getInt("Invul"));
    }

    protected String z() {
        return "mob.wither.idle";
    }

    protected String bo() {
        return "mob.wither.hurt";
    }

    protected String bp() {
        return "mob.wither.death";
    }

    public DungeonYijkiEntity(final World world) {
        super(world);

        DungeonYijkiEntity instance = this;

        Predicate<Entity> entSelectorPredicate = new Predicate() {
            public boolean a(Entity entity) {
                return (entity instanceof EntityHuman && instance instanceof MotherOfYijkiEntity) || WorldGuardUtils.isInRegionsFast(new Location((org.bukkit.World) entity.world.getWorld(), entity.locX, entity.locY, entity.locZ), "hanger", "bridge");
            }

            public boolean apply(Object object) {
                return a((Entity) object);
            }
        };

        this.nextShot = new int[2];
        this.shootTimer = new int[2];
        this.failedTimes = 100;
        this.lastTargetCheck = System.currentTimeMillis();
        this.lastTargetName = "";
        this.setHealth(this.getMaxHealth());
        this.a(0.9f, 4.0f);
        this.fireProof = true;
        ((Navigation) getNavigation()).d(true);
        this.goalSelector.a(0, (PathfinderGoal) new PathfinderGoalFloat((EntityInsentient) this));
        this.goalSelector.a(6, (PathfinderGoal) new PathfinderGoalLookAtPlayer((EntityInsentient) this, (Class) EntityHuman.class, 8.0f));
        this.goalSelector.a(7, (PathfinderGoal) new PathfinderGoalRandomLookaround((EntityInsentient) this));
        this.targetSelector.a(1, (PathfinderGoal) new PathfinderGoalHurtByTarget((EntityCreature) this, false));
        this.targetSelector.a(2, (PathfinderGoal) new PathfinderGoalNearestAttackableTarget((EntityCreature) this, (Class) EntityHuman.class, 0, false, false, entSelectorPredicate));
        this.b_ = 50;
    }

    public void setBoss(final DungeonYijki boss) {
        if (boss != null) {
            this.boss = boss;
        } else {
            --this.failedTimes;
        }
        if (boss != null) {
            final boolean isMOY = boss instanceof MotherOfYijki;
            this.goalSelector.a(2, (PathfinderGoal) new PathfinderGoalDungeonWitherBossAttack(boss, (IRangedEntity) this, 1.0, isMOY ? 25 : 40, isMOY ? 50 : 70, isMOY ? 30.0f : 20.0f));
        }
    }

    public void collide(final Entity entity) {
        if (this.boss != null && this.boss.getActiveAbility() instanceof UnstoppableForce && entity instanceof EntityHuman && ((UnstoppableForce) this.boss.getActiveAbility()).shouldCharge() && entity.isAlive() && !BossUtils.isInGodMode((Player) entity.getBukkitEntity())) {
            if (Math.abs(this.boss.getLocation().getBlockY() - entity.getBukkitEntity().getLocation().getBlockY()) < 4) {
                if (WorldGuardUtils.getRegionName(entity.getBukkitEntity().getLocation()).equals("start_pearlok") || WorldGuardUtils.getRegionName(entity.getBukkitEntity().getLocation()).equals("bridge_start"))
                    return; // in safe room
                ((EntityHuman) entity).lastDamager = (EntityLiving) this;
                ((EntityHuman) entity).setHealth(0.0f);
                Bukkit.getLogger().info("Killing " + entity.getName() + " with unstoppable force!");
            }
            return;
        }
        super.collide(entity);
    }

    public void die() {
        super.die();
    }

    public void setGoalTarget(final EntityLiving entityliving) {
        if (this.getGoalTarget() != null && entityliving == null) {
            this.boss.location = this.boss.getRandomSpawnLocation();
            Bukkit.getLogger().info("Setting new Yijki spawn to " + this.boss.location.getX() + "x, " + this.boss.location.getY() + "y, " + this.boss.location.getZ() + "z");
        }
        super.setGoalTarget(entityliving);
    }

    @SneakyThrows
    public void m() {
        if (this.boss != null) {
            if (this.boss.getActiveAbility() != null) {
                if (this.boss.getActiveAbility().shouldStopTicking()) {
                    this.boss.clearAbilities();
                } else {
                    this.boss.getActiveAbility().onTick();
                }
            } else {
                final long lastAbilityTime = this.boss.getAbilityActivateTime();
                if (this.boss.getActiveAbility() == null && lastAbilityTime >= 8000L) {
                    this.boss.findNewAbility();
                    Bukkit.getLogger().info("Finding new ability for YIjki: " + this.boss.getActiveAbility());
                }
            }
            if (this.boss.getEndOfWorldAbility() != null && !this.boss.getEndOfWorldAbility().shouldStopTicking()) {
                this.boss.getEndOfWorldAbility().onTick();
            }
        }
        final double motionMultiplier = 0.6000000238418579;
        if (this.boss != null && this.boss.getActiveAbility() instanceof UnstoppableForce) {
            final UnstoppableForce force = (UnstoppableForce) this.boss.getActiveAbility();
            final Vector dir = force.getDirection();
            if (force.shouldCharge()) {
                List<EntityLiving> ents = this.world.a(EntityLiving.class, getBoundingBox().grow(1.0D, 1.0D, 1.0D));
                for (final Entity inBox : ents) {
                    if (inBox instanceof EntityHuman) {
                        this.collide(inBox);
                        Bukkit.getLogger().info("Colliding with " + inBox.getName() + " manually due to collide() being missed.");
                    }
                }
                this.motX += dir.getX();
                this.motZ += dir.getZ();
                if (this.ticksLived % 2 == 0) {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), this.getBukkitEntity().getLocation(), 0f, 0f, 0f, 0.09f, 25);
                }
            }
            if (this.getGoalTarget() != null) {
                final double targetY = this.getGoalTarget().locY;
                if (this.locY < targetY && this.motY < 0.0) {
                    this.motY = 0.0;
                }
            }
        } else {
            this.motY *= motionMultiplier;
            if (this.s(0) > 0) {
                final Entity entity = this.world.a(this.s(0));
                if (entity != null) {
                    final Location entLoc = entity.getBukkitEntity().getLocation();
                    if (this.ticksLived % 10 == 0 && entLoc.getWorld().getName().endsWith("yijki_a") && !WorldGuardUtils.isInRegionsFast(entLoc, "bridge", "hanger")) {
                        Bukkit.getLogger().info("Untargetting " + entity.getName() + " due to leaving hanger or bridge region!");
                        this.getBukkitEntity().getLocation().getWorld().playSound(this.getBukkitEntity().getLocation(), Sound.ITEM_BREAK, 5.0f, 2.5f);
                        this.setGoalTarget(null);
                        this.b(0, 0);
                        return;
                    }
                    this.moveTowardsLocation(entity.locX, entity.locY, entity.locZ, motionMultiplier);
                }
            } else if (this.boss != null) {
                final Location spawn = this.boss.getLocation();
                if (this.f(spawn.getX(), spawn.getY(), spawn.getZ()) > 10.0) {
                    this.moveTowardsLocation(spawn.getX(), spawn.getY(), spawn.getZ(), motionMultiplier);
                }
            }
        }
        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806) {
            this.yaw = (float) Math.atan2(this.motZ, this.motX) * 57.295776f - 90.0f;
        }
        super.m();
        for (int i = 0; i < 2; ++i) {
            this.bm[i] = this.b[i];
            this.c[i] = this.a[i];
        }
        for (int i = 0; i < 2; ++i) {
            final int j = this.s(i + 1);
            Entity entity2 = null;
            if (j > 0) {
                entity2 = this.world.a(j);
            }
            if (entity2 != null && entity2 instanceof EntityHuman) {
                double d0 = t(i + 1);
                double d1 = u(i + 1);
                double d2 = v(i + 1);
                double d4 = entity2.locX - d0;
                double d5 = entity2.locY + entity2.getHeadHeight() - d1;
                double d6 = entity2.locZ - d2;
                double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
                float f = (float) (MathHelper.b(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f2 = (float) -(MathHelper.b(d5, d7) * 180.0D / 3.1415927410125732D);
                this.a[i] = this.b(this.a[i], f2, 40.0f);
                this.b[i] = this.b(this.b[i], f, 10.0f);
            } else {
                this.b[i] = this.b(this.b[i], this.aM, 10.0f);
            }
        }
        final boolean flag = this.cm();
        for (int j = 0; j < 3; j++) {
            double d8 = t(j);
            double d9 = u(j);
            double d10 = v(j);
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
            if (flag && this.world.random.nextInt(4) == 0)
                this.world.addParticle(EnumParticle.SPELL_MOB, d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
        }
        if (cl() > 0)
            for (j = 0; j < 3; j++)
                this.world.addParticle(EnumParticle.SPELL_MOB, this.locX + this.random.nextGaussian() * 1.0D, this.locY + (this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
    }

    private void moveTowardsLocation(final double x, final double y, final double z, final double motionMultiplier) {
        double height = 7.0;
        if (this.boss != null) {
            if (this.boss.getActiveAbility() instanceof UnstoppableForce) {
                height = 0.0;
            } else {
                height = ((this.boss.getPhase() == Phase.TWO) ? 7.0 : 3.0);
            }
        }
        if (this.locY < y || this.locY < y + height) {
            if (this.motY < 0.0) {
                this.motY = 0.0;
            }
            this.motY += (0.5 - this.motY) * (motionMultiplier / 2.0);
        }
        final double distanceX = x - this.locX;
        final double distanceZ = z - this.locZ;
        final double distanceSqr = distanceX * distanceX + distanceZ * distanceZ;
        double distanceFromPlayer = 9.0;
        double speedMultiplier = 1.0;
        if (this.boss != null) {
            distanceFromPlayer = ((this.boss.getPhase() == Phase.TWO) ? 12.0 : 4.0);
            speedMultiplier = ((this.boss.getPhase() == Phase.TWO) ? 1.2 : 1.0);
        }
        if (distanceSqr > distanceFromPlayer) {
            final double distanceSqrRoot = MathHelper.sqrt(distanceSqr);
            this.motX += (distanceX / distanceSqrRoot * 0.7 - this.motX) * (motionMultiplier * speedMultiplier);
            this.motZ += (distanceZ / distanceSqrRoot * 0.7 - this.motZ) * (motionMultiplier * speedMultiplier);
        }
    }

    protected void E() {
        if (this.cl() > 0) {
            final int witherTargetId = this.cl() - 1;
            if (witherTargetId <= 0) {
                final ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Entity) this.getBukkitEntity(), 7.0f, false);
                this.world.getServer().getPluginManager().callEvent((Event) event);
                if (!event.isCancelled()) {
                    this.world.createExplosion((Entity) this, this.locX, this.locY + this.getHeadHeight(), this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
                }
                this.world.createExplosion((Entity) this, this.locX, this.locY + this.getHeadHeight(), this.locZ, 7.0f, false, this.world.getGameRules().getBoolean("mobGriefing"));
                final int viewDistance = ((WorldServer) this.world).getServer().getViewDistance() * 16;
                for (Object playerp : this.world.players) {
                    EntityPlayer player = (EntityPlayer) playerp;
                    final double deltaX = this.locX - player.locX;
                    final double deltaZ = this.locZ - player.locZ;
                    final double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                    if (this.world.spigotConfig.witherSpawnSoundRadius > 0 && distanceSquared > this.world.spigotConfig.witherSpawnSoundRadius * this.world.spigotConfig.witherSpawnSoundRadius) {
                        continue;
                    }
                    if (distanceSquared > viewDistance * viewDistance) {
                        final double deltaLength = Math.sqrt(distanceSquared);
                        final double relativeX = player.locX + deltaX / deltaLength * viewDistance;
                        final double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int) relativeX, (int) this.locY, (int) relativeZ), 0, true));
                    } else {
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int) this.locX, (int) this.locY, (int) this.locZ), 0, true));
                    }
                }
            }
            this.r(witherTargetId);
            if (this.ticksLived % 10 == 0) {
                this.heal(10.0f, EntityRegainHealthEvent.RegainReason.WITHER_SPAWN);
            }
        } else {
            super.E();
            for (int witherTargetId = 1; witherTargetId < 3; ++witherTargetId) {
                if (this.ticksLived >= this.nextShot[witherTargetId - 1]) {
                    this.nextShot[witherTargetId - 1] = this.ticksLived + 10 + this.random.nextInt(10);
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
                        final int targetId = witherTargetId - 1;
                        final int value = this.shootTimer[targetId];
                        this.shootTimer[targetId] = value + 1;
                        if (value > 15) {
                            final float width = 10.0f;
                            final float height = 5.0f;
                            final double x = MathHelper.a(this.random, this.locX - width, this.locX + width);
                            final double y = MathHelper.a(this.random, this.locY - height, this.locY + height);
                            final double z = MathHelper.a(this.random, this.locZ - width, this.locZ + width);
                            this.a(witherTargetId + 1, x, y, z, true);
                            this.shootTimer[witherTargetId - 1] = 0;
                        }
                    }
                    final int target = this.s(witherTargetId);
                    if (target > 0) {
                        final Entity entity = this.world.a(target);
                        if (entity instanceof EntityHuman && entity.isAlive() && this.h(entity) <= 900.0) {
                            this.a(witherTargetId + 1, (EntityLiving) entity);
                            this.nextShot[witherTargetId - 1] = this.ticksLived + 5 + this.random.nextInt(20);
                            this.shootTimer[witherTargetId - 1] = 0;
                        } else {
                            this.b(witherTargetId, 0);
                        }
                    } else {
                        List<EntityLiving> list = this.world.a(EntityLiving.class, getBoundingBox().grow(20.0D, 8.0D, 20.0D), Predicates.and(bq, IEntitySelector.d));
                        for (int i1 = 0; i1 < 10 && !list.isEmpty(); i1++) {
                            EntityLiving entityliving = list.get(this.random.nextInt(list.size()));
                            if (entityliving != this && entityliving.isAlive() && hasLineOfSight(entityliving)) {
                                if (entityliving instanceof EntityHuman) {
                                    if (!((EntityHuman) entityliving).abilities.isInvulnerable)
                                        b(witherTargetId, entityliving.getId());
                                    break;
                                }
                                b(witherTargetId, entityliving.getId());
                                break;
                            }
                            list.remove(entityliving);
                        }
                    }
                }
            }
            if (this.getGoalTarget() != null && this.getGoalTarget() instanceof EntityHuman && this.getGoalTarget().isAlive()) {
                this.b(0, this.getGoalTarget().getId());
            } else {
                this.b(0, 0);
            }
            if (this.bv > 0) {
                --this.bv;
                if (this.bv == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    final int witherTargetId = MathHelper.floor(this.locY);
                    final int target = MathHelper.floor(this.locX);
                    final int j1 = MathHelper.floor(this.locZ);
                    boolean flag = false;
                    for (int k1 = -1; k1 <= 1; ++k1) {
                        for (int l1 = -1; l1 <= 1; ++l1) {
                            for (int i2 = 0; i2 <= 3; ++i2) {
                                final int j2 = target + k1;
                                final int k2 = witherTargetId + i2;
                                final int l2 = j1 + l1;
                                final Block block = this.world.getType(new BlockPosition(j2, k2, l2)).getBlock();
                                if (block.getMaterial() != Material.AIR && block != Blocks.BEDROCK && block != Blocks.END_PORTAL && block != Blocks.END_PORTAL_FRAME && block != Blocks.COMMAND_BLOCK) {
                                    if (!CraftEventFactory.callEntityChangeBlockEvent((Entity) this, j2, k2, l2, Blocks.AIR, 0).isCancelled()) {
                                        flag = (this.world.setAir(new BlockPosition(j2, k2, l2), true) || flag);
                                    }
                                }
                            }
                        }
                    }
                    if (flag) {
                        this.world.a((EntityHuman) null, 1012, new BlockPosition(this), 0);
                    }
                }
            }
            if (this.ticksLived % 120 == 0) {
                this.heal(1.0f, EntityRegainHealthEvent.RegainReason.REGEN);
            }
        }
    }

    public void bZ() {
        this.r(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }

    public void as() {
    }

    public int aV() {
        return 4;
    }

    private double t(int i) {
        if (i <= 0)
            return this.locX;
        float f = (this.aI + (180 * (i - 1))) / 180.0F * 3.1415927F;
        float f1 = MathHelper.cos(f);
        return this.locX + f1 * 1.3D;
    }

    private double u(int i) {
        return (i <= 0) ? (this.locY + 3.0D) : (this.locY + 2.2D);
    }

    private double v(int i) {
        if (i <= 0)
            return this.locZ;
        float f = (this.aI + (180 * (i - 1))) / 180.0F * 3.1415927F;
        float f1 = MathHelper.sin(f);
        return this.locZ + f1 * 1.3D;
    }

    private float b(final float f, final float f1, final float f2) {
        float f3 = MathHelper.g(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    private void a(final int i, final EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + entityliving.getHeadHeight() * 0.5, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001f);
    }

    private void a(int entityID, double spawnX, double spawnY, double spawnZ, boolean flag) {
        this.world.a((EntityHuman) null, 1014, new BlockPosition(this), 0);
        double x = t(entityID);
        double y = u(entityID);
        double z = v(entityID);
        double d6 = spawnX - x;
        double d7 = spawnY - y;
        double d8 = spawnZ - z;
        final boolean fromMOY = this instanceof me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijkiEntity;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, (EntityLiving) this, d6 * 1.2D, d7, d8 * 1.2D) {
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
        Bukkit.getPluginManager().callEvent((Event) event);
        this.world.addEntity((Entity) entitywitherskull);
    }

    public void a(final EntityLiving entityliving, final float f) {
        this.a(0, entityliving);
    }

    public boolean damageEntity(final DamageSource damagesource, final float f) {
        if (this.isInvulnerable(damagesource)) {
            return false;
        }
        if (damagesource == DamageSource.DROWN || damagesource == DamageSource.WITHER) {
            return false;
        }
        if (this.cl() > 0) {
            return false;
        }
        final Entity entity = damagesource.getEntity();
        if ((entity != null && !(entity instanceof EntityHuman) && entity instanceof EntityLiving && ((EntityLiving) entity).getMonsterType() == this.getMonsterType()) || entity instanceof EntityWitherSkull) {
            return false;
        }
        if (this.bv <= 0) {
            this.bv = 20;
        }
        for (int i = 0; i < this.shootTimer.length; ++i) {
            final int[] shootTimer = this.shootTimer;
            final int n = i;
            shootTimer[n] += 3;
        }
        return super.damageEntity(damagesource, f);
    }

    protected void dropDeathLoot(final boolean flag, final int i) {
    }

    protected void D() {
        this.ticksFarFromPlayer = 0;
    }

    protected void b(final float f) {
    }

    public void addEffect(final MobEffect mobeffect) {
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6000000238418579);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0);
    }

    public int cl() {
        return this.datawatcher.getInt(20);
    }

    public void r(int i) {
        this.datawatcher.watch(20, Integer.valueOf(i));
    }

    public int s(int i) {
        return this.datawatcher.getInt(17 + i);
    }

    public void b(int i, int j) {
        this.datawatcher.watch(17 + i, Integer.valueOf(j));
    }

    public boolean cm() {
        return (getHealth() <= getMaxHealth() / 2.0F);
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    public void mount(Entity entity) {
        this.vehicle = null;
    }
}
