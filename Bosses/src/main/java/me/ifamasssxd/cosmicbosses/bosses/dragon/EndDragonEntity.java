/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.server.v1_7_R4.AxisAlignedBB
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.BlockEnderPortal
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityComplexPart
 *  net.minecraft.server.v1_7_R4.EntityEnderCrystal
 *  net.minecraft.server.v1_7_R4.EntityEnderDragon
 *  net.minecraft.server.v1_7_R4.EntityExperienceOrb
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityLargeFireball
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.EntityTNTPrimed
 *  net.minecraft.server.v1_7_R4.Explosion
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.Material
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.MovingObjectPosition
 *  net.minecraft.server.v1_7_R4.Packet
 *  net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange
 *  net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent
 *  net.minecraft.server.v1_7_R4.PathPoint
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  net.minecraft.server.v1_7_R4.World
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.PortalType
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory
 *  org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator
 *  org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityCreatePortalEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.bosses.dragon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.dragon.DragonFightManager;
import me.ifamasssxd.cosmicbosses.bosses.dragon.EndDragon;
import me.ifamasssxd.cosmicbosses.bosses.dragon.PhaseManager;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.IPhase;
import me.ifamasssxd.cosmicbosses.bosses.dragon.phases.PhaseList;
import me.ifamasssxd.cosmicbosses.struct.BossPath;
import me.ifamasssxd.cosmicbosses.struct.BossPathHeap;
import me.ifamasssxd.cosmicbosses.struct.BossPathPoint;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockEnderPortal;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityComplexPart;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLargeFireball;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_8_R3.PathPoint;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.PortalType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class EndDragonEntity
extends EntityEnderDragon {
    public EndDragon bossInstance;
    public double[][] ringEffects = new double[64][3];
    public int bo = -1;
    public EntityComplexPart head;
    public EntityComplexPart body;
    public EntityComplexPart tail1;
    public float prevAnimTime;
    public float animTime;
    public boolean firstTargetSearch;
    public boolean bA;
    public Entity target;
    public int bB;
    public EntityEnderCrystal bC;
    private Explosion explosionSource = new Explosion((World)null, (Entity)this, Double.NaN, Double.NaN, Double.NaN, Float.NaN, false, false);
    public IPhase phase;
    public DragonFightManager fightManager;
    public PhaseManager phaseManager;
    public Random random = new Random();
    public Location homeLocation;
    public long timeSinceLastApproach;
    private BossPathPoint[] bossPathPoints = new BossPathPoint[24];
    private final int[] neighbors = new int[24];
    private BossPathHeap pathQueue = new BossPathHeap();
    private int sittingDamageReceived;

    public EndDragonEntity(World world) {
        super(world);
        this.dead = true;
    }

    public EndDragonEntity(World world, EndDragon boss) {
        super(world);
        this.bossInstance = boss;
        this.head = this.children[0];
        this.body = this.children[1];
        this.tail1 = this.children[1];
        this.setHealth(this.getMaxHealth());
        this.head.loadChunks = true;
        this.loadChunks = true;
        this.persistent = true;
        this.a(16.0f, 8.0f);
        this.X = true;
        this.fireProof = true;
        this.ak = true;
        this.fightManager = new DragonFightManager(world);
        this.phaseManager = new PhaseManager(this);
        this.homeLocation = new Location((org.bukkit.World)this.world.getWorld(), 62.0, 39.0, 69.0);
    }

    public PhaseManager getPhaseManager() {
        return this.phaseManager;
    }

    public DragonFightManager getFightManager() {
        return this.fightManager;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0);
    }

    protected void c() {
        super.c();
    }

    public double[] b(int i, float f) {
        if (this.getHealth() <= 0.0f) {
            f = 0.0f;
        }
        f = 1.0f - f;
        int j = this.bo - i * 1 & 0x3F;
        int k = this.bo - i * 1 - 1 & 0x3F;
        double[] adouble = new double[3];
        double d0 = this.ringEffects[j][0];
        double d1 = MathHelper.g((double)(this.ringEffects[k][0] - d0));
        adouble[0] = d0 + d1 * (double)f;
        d0 = this.ringEffects[j][1];
        d1 = this.ringEffects[k][1] - d0;
        adouble[1] = d0 + d1 * (double)f;
        adouble[2] = this.ringEffects[j][2] + (this.ringEffects[k][2] - this.ringEffects[j][2]) * (double)f;
        return adouble;
    }

    public void g(double d0, double d1, double d2) {
        super.g(d0, d1, d2);
    }

    public void shootTNT() {
        Vec3D vec3d = this.getHeadLookVec(1.0f).a();
        vec3d.b(-0.7853982f);
        int toSpawn = (int)Math.min(1.0, Math.ceil(this.getHealth() / this.getMaxHealth() * 3.0f));
        double d0 = this.head.locX;
        double d1 = this.head.locY - (double)(this.head.height / 2.0f) - 2.5;
        double d2 = this.head.locZ;
        for (int i = 0; i < toSpawn; ++i) {
            EntityTNTPrimed primed = new EntityTNTPrimed(this.world, d0 + this.random.nextGaussian() / 3.0 * (double)i, d1 + this.random.nextGaussian() / 2.0, d2 + this.random.nextGaussian() / 3.0 * (double)i, (EntityLiving)this);
            primed.fuseTicks = 20 + this.random.nextInt(6);
            primed.getBukkitEntity().setMetadata("dragon_bomb", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)""));
            primed.getBukkitEntity().setMetadata("mouth_bomb", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)this.getUniqueID()));
            primed.getBukkitEntity().setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.get(), (Object)this.getUniqueID()));
            this.world.addEntity((Entity)primed, CreatureSpawnEvent.SpawnReason.CUSTOM);
            float offX = (float)(-vec3d.a * (double)0.08f * (double)i);
            float offY = (float)(-vec3d.b * (double)0.6f);
            float offZ = (float)(-vec3d.c * (double)0.08f * (double)i);
            primed.getBukkitEntity().setVelocity(new Vector(offX, offY, offZ));
            this.world.a((EntityHuman)null, 1009, (int)this.head.locX, (int)this.head.locY, (int)this.head.locZ, 0);
        }
    }

    public boolean shootFireball() {
        if (this.target != null && this.head.isAlive() && this.target.world == this.world) {
            Vec3D vec3d1 = Vec3D.a((double)(this.target.locX - this.locX), (double)0.0, (double)(this.target.locZ - this.locZ)).a();
            Vec3D vec3d = Vec3D.a((double)Math.sin(this.yaw * ((float)Math.PI / 180)), (double)0.0, (double)(-Math.cos(this.yaw * ((float)Math.PI / 180)))).a();
            float f1 = (float)vec3d.b(vec3d1);
            float f = (float)(Math.acos(f1) * 57.29577951308232);
            f += 0.5f;
            double d14 = 1.0;
            Vec3D vec3d2 = this.j(1.0f);
            double d6 = this.head.locX - vec3d2.a * 1.0;
            double d7 = this.head.locY + (double)(this.head.height / 2.0f) + 0.5;
            double d8 = this.head.locZ - vec3d2.c * 1.0;
            double d9 = this.target.locX - d6;
            double d10 = this.target.locY + (double)(this.target.height / 2.0f) - (d7 + (double)(this.head.height / 2.0f));
            double d11 = this.target.locZ - d8;
            EntityLargeFireball fireball = new EntityLargeFireball(this.world, (EntityLiving)this, d9, d10, d11){

                protected void a(MovingObjectPosition movingobjectposition) {
                    if (movingobjectposition.entity != null && !(movingobjectposition.entity instanceof EntityHuman)) {
                        return;
                    }
                    super.a(movingobjectposition);
                }

                public boolean damageEntity(DamageSource damagesource, float f) {
                    if (damagesource.getEntity() != null) {
                        return false;
                    }
                    return super.damageEntity(damagesource, f);
                }
            };
            fireball.setPositionRotation(d6, d7, d8, 0.0f, 0.0f);
            fireball.yield = 0;
            fireball.isIncendiary = false;
            this.world.addEntity((Entity)fireball, CreatureSpawnEvent.SpawnReason.CUSTOM);
            return true;
        }
        return false;
    }

    public float setRandomYawVelocity(float val) {
        this.bf = val;
        return this.bf;
    }

    public float getRandomYawVelocity() {
        return this.bf;
    }

    private void log(String msg) {
        Bukkit.getLogger().info("[EndDragonEntity] " + msg);
    }

    public void e() {
        if (this.world.players.isEmpty()) {
            return;
        }
        if (this.bossInstance == null) {
            this.dead = true;
            Bukkit.getLogger().info("[EndDragonEntity] Removing found EndDragonEntity mob due to bossInstance == null!");
            return;
        }
        try {
            float f2;
            if (this.world.isStatic) {
                float animTime = MathHelper.cos((float)(this.animTime * (float)Math.PI * 2.0f));
                float prevAnimTime = MathHelper.cos((float)(this.prevAnimTime * (float)Math.PI * 2.0f));
                if (prevAnimTime <= -0.3f && animTime >= -0.3f) {
                    this.world.a(this.locX, this.locY, this.locZ, "mob.enderdragon.wings", 5.0f, 0.8f + this.random.nextFloat() * 0.3f, false);
                }
            }
            this.world.spigotConfig.loadUnloadedChunks((Entity)this);
            this.prevAnimTime = this.animTime;
            if (this.getHealth() <= 0.0f) {
                this.f = (this.random.nextFloat() - 0.5f) * 8.0f;
                float f1 = (this.random.nextFloat() - 0.5f) * 4.0f;
                f2 = (this.random.nextFloat() - 0.5f) * 8.0f;
                this.world.addParticle("largeexplode", this.locX + (double)this.f, this.locY + 2.0 + (double)f1, this.locZ + (double)f2, 0.0, 0.0, 0.0);
            } else {
                float f8;
                this.bP();
                this.f = 0.2f / (MathHelper.sqrt((double)(this.motX * this.motX + this.motZ * this.motZ)) * 10.0f + 1.0f);
                this.f *= (float)Math.pow(2.0, this.motY);
                this.animTime = this.bA ? (this.animTime += this.f * 0.5f) : (this.animTime += this.f);
                this.yaw = MathHelper.g((float)this.yaw);
                if (this.bo < 0) {
                    for (int d05 = 0; d05 < this.ringEffects.length; ++d05) {
                        this.ringEffects[d05][0] = this.yaw;
                        this.ringEffects[d05][1] = this.locY;
                    }
                }
                if (++this.bo == this.ringEffects.length) {
                    this.bo = 0;
                }
                this.ringEffects[this.bo][0] = this.yaw;
                this.ringEffects[this.bo][1] = this.locY;
                IPhase iphase = this.phaseManager.getCurrentPhase();
                if (iphase != null) {
                    Vec3D vec3D;
                    iphase.doLocalUpdate();
                    if (this.phaseManager.getCurrentPhase() != iphase) {
                        iphase = this.phaseManager.getCurrentPhase();
                        iphase.doLocalUpdate();
                    }
                    if ((vec3D = iphase.getTargetLocation()) != null) {
                        double d6 = vec3D.a - this.locX;
                        double d7 = vec3D.b - this.locY;
                        double d8 = vec3D.c - this.locZ;
                        double d3 = d6 * d6 + d7 * d7 + d8 * d8;
                        float f5 = iphase.getMaxRiseOrFall();
                        d7 = MathHelper.a((double)(d7 / (double)MathHelper.sqrt((double)(d6 * d6 + d8 * d8))), (double)(-f5), (double)f5);
                        this.motY += d7 * (double)0.1f;
                        this.yaw = MathHelper.g((float)this.yaw);
                        double d4 = MathHelper.a((double)MathHelper.g((double)(180.0 - Math.atan2(d6, d8) * 57.29577951308232 - (double)this.yaw)), (double)-50.0, (double)50.0);
                        Vec3D vec3d1 = Vec3D.a((double)(vec3D.a - this.locX), (double)(vec3D.b - this.locY), (double)(vec3D.c - this.locZ)).a();
                        Vec3D vec3d2 = Vec3D.a((double)MathHelper.sin((float)(this.yaw * ((float)Math.PI / 180))), (double)this.motY, (double)(-MathHelper.cos((float)(this.yaw * ((float)Math.PI / 180))))).a();
                        float f7 = Math.max(((float)vec3d2.b(vec3d1) + 0.5f) / 1.5f, 0.0f);
                        this.setRandomYawVelocity(this.getRandomYawVelocity() * 0.8f);
                        this.setRandomYawVelocity((float)((double)this.getRandomYawVelocity() + d4 * (double)iphase.getYawFactor()));
                        this.yaw += this.getRandomYawVelocity() * 0.1f;
                        f8 = (float)(2.0 / (d3 + 1.0));
                        float f9 = 0.06f;
                        this.moveRelative(0.0f, 0.0f, -1.0f, 0.06f * (f7 * f8 + (1.0f - f8)));
                        if (this.bA) {
                            this.move(this.motX * (double)0.8f, this.motY * (double)0.8f, this.motZ * (double)0.8f);
                        } else {
                            this.move(this.motX, this.motY, this.motZ);
                        }
                        Vec3D vec3d3 = Vec3D.a((double)this.motX, (double)this.motY, (double)this.motZ).a();
                        float f10 = ((float)vec3d3.b(vec3d2) + 1.0f) / 2.0f;
                        f10 = 0.8f + 0.15f * f10;
                        this.motX *= (double)f10;
                        this.motZ *= (double)f10;
                        this.motY *= (double)0.91f;
                    }
                }
                this.aM = this.yaw;
                this.head.length = 3.0f;
                this.head.width = 3.0f;
                this.tail1.length = 2.0f;
                this.tail1.width = 2.0f;
                this.bt.length = 2.0f;
                this.bt.width = 2.0f;
                this.bu.length = 2.0f;
                this.bu.width = 2.0f;
                this.body.length = 3.0f;
                this.body.width = 5.0f;
                this.bv.length = 2.0f;
                this.bv.width = 4.0f;
                this.bw.length = 3.0f;
                this.bw.width = 4.0f;
                float f1 = (float)(this.b(5, 1.0f)[1] - this.b(10, 1.0f)[1]) * 10.0f / 180.0f * (float)Math.PI;
                f2 = MathHelper.cos((float)f1);
                float f9 = -MathHelper.sin((float)f1);
                float f10 = this.yaw * (float)Math.PI / 180.0f;
                float f11 = MathHelper.sin((float)f10);
                float f12 = MathHelper.cos((float)f10);
                this.body.h();
                this.body.setPositionRotation(this.locX + (double)(f11 * 0.5f), this.locY, this.locZ - (double)(f12 * 0.5f), 0.0f, 0.0f);
                this.bv.h();
                this.bv.setPositionRotation(this.locX + (double)(f12 * 4.5f), this.locY + 2.0, this.locZ + (double)(f11 * 4.5f), 0.0f, 0.0f);
                this.bw.h();
                this.bw.setPositionRotation(this.locX - (double)(f12 * 4.5f), this.locY + 2.0, this.locZ - (double)(f11 * 4.5f), 0.0f, 0.0f);
                if (!this.world.isStatic && this.hurtTicks == 0) {
                    this.a(this.world.getEntities((Entity)this, this.bv.boundingBox.grow(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0)));
                    this.a(this.world.getEntities((Entity)this, this.bw.boundingBox.grow(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0)));
                    this.b(this.world.getEntities((Entity)this, this.head.boundingBox.grow(1.0, 1.0, 1.0)));
                }
                double[] adouble = this.b(5, 1.0f);
                float maxYVel = MathHelper.sin((float)(this.yaw * (float)Math.PI / 180.0f - this.bf * 0.01f));
                float f4 = MathHelper.cos((float)(this.yaw * (float)Math.PI / 180.0f - this.bf * 0.01f));
                this.head.h();
                float f20 = this.getHeadYOffset(1.0f);
                this.head.setPositionRotation(this.locX + (double)(maxYVel * 6.5f * f2), this.locY + (double)f20 + (double)(f2 * 6.5f), this.locZ - (double)(f4 * 6.5f * f2), 0.0f, 0.0f);
                for (int j = 0; j < 3; ++j) {
                    EntityComplexPart entitycomplexpart = null;
                    if (j == 0) {
                        entitycomplexpart = this.tail1;
                    }
                    if (j == 1) {
                        entitycomplexpart = this.bt;
                    }
                    if (j == 2) {
                        entitycomplexpart = this.bu;
                    }
                    double[] adouble2 = this.b(12 + j * 2, 1.0f);
                    float f6 = this.yaw * (float)Math.PI / 180.0f + this.b(adouble2[0] - adouble[0]) * (float)Math.PI / 180.0f * 1.0f;
                    float f7 = MathHelper.sin((float)f6);
                    float f16 = MathHelper.cos((float)f6);
                    f8 = 1.5f;
                    float f18 = (float)(j + 1) * 2.0f;
                    entitycomplexpart.h();
                    entitycomplexpart.setPositionRotation(this.locX - (double)((f11 * f8 + f7 * f18) * f2), this.locY + (adouble2[1] - adouble[1]) * 1.0 - (double)((f18 + f8) * f9) + 1.5, this.locZ + (double)((f12 * f8 + f16 * f18) * f2), 0.0f, 0.0f);
                }
                if (!this.world.isStatic) {
                    this.bA = this.a(this.head.boundingBox) | this.a(this.body.boundingBox);
                    if (this.fightManager != null) {
                        this.fightManager.dragonUpdate(this);
                    }
                }
                if (this.fightManager != null) {
                    this.fightManager.tick();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float getHeadYOffset(float p_184662_1_) {
        double d0;
        if (this.phaseManager.getCurrentPhase().getIsStationary()) {
            d0 = -1.0;
        } else {
            double[] adouble = this.b(5, 1.0f);
            double[] adouble1 = this.b(0, 1.0f);
            d0 = adouble[1] - adouble1[1];
        }
        return (float)d0;
    }

    public Vec3D getHeadLookVec(float p_184665_1_) {
        Vec3D vec3d;
        IPhase iphase = this.phaseManager.getCurrentPhase();
        PhaseList<? extends IPhase> phaselist = iphase.getType();
        if (phaselist != PhaseList.LANDING && phaselist != PhaseList.TAKEOFF) {
            if (iphase.getIsStationary()) {
                float f4 = this.pitch;
                float f5 = 1.5f;
                this.pitch = -45.0f;
                vec3d = this.j(p_184665_1_);
                this.pitch = f4;
            } else {
                vec3d = this.j(p_184665_1_);
            }
        } else {
            Location blockpos = this.homeLocation;
            float f = Math.max(MathHelper.sqrt((double)this.distanceSqToCenter(blockpos.getX(), blockpos.getY(), blockpos.getZ())) / 4.0f, 1.0f);
            float f1 = 6.0f / f;
            float f2 = this.pitch;
            float f3 = 1.5f;
            this.pitch = -f1 * 1.5f * 5.0f;
            vec3d = this.j(p_184665_1_);
            this.pitch = f2;
        }
        return vec3d;
    }

    public boolean j(Entity entity) {
        return super.j(entity);
    }

    private void bP() {
        if (this.bC != null) {
            if (this.bC.dead) {
                if (!this.world.isStatic) {
                    CraftEventFactory.entityDamage = this.bC;
                    this.a(this.head, DamageSource.explosion((Explosion)null), 10.0f);
                    CraftEventFactory.entityDamage = null;
                }
                this.bC = null;
            } else if (this.ticksLived % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                EntityRegainHealthEvent event = new EntityRegainHealthEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), 1.0, EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
                this.world.getServer().getPluginManager().callEvent((Event)event);
                if (!event.isCancelled()) {
                    this.setHealth((float)((double)this.getHealth() + event.getAmount()));
                }
            }
        }
        if (this.random.nextInt(10) == 0) {
            float f = 32.0f;
            List list = this.world.a(EntityEnderCrystal.class, this.boundingBox.grow((double)f, (double)f, (double)f));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = Double.MAX_VALUE;
            for (EntityEnderCrystal entityendercrystal1 : list) {
                double d1 = entityendercrystal1.f((Entity)this);
                if (!(d1 < d0)) continue;
                d0 = d1;
                entityendercrystal = entityendercrystal1;
            }
            this.bC = entityendercrystal;
        }
    }

    private void a(List list) {
        double d0 = (this.body.boundingBox.a + this.body.boundingBox.d) / 2.0;
        double d1 = (this.body.boundingBox.c + this.body.boundingBox.f) / 2.0;
        for (Entity entity : list) {
            if (!(entity instanceof EntityLiving)) continue;
            double d2 = entity.locX - d0;
            double d3 = entity.locZ - d1;
            double d4 = d2 * d2 + d3 * d3;
            Vector vect = new Vector(d2 / d4 * 4.0 * 0.3, (double)0.2f, d3 / d4 * 4.0 * 0.3);
            if (entity instanceof EntityHuman) {
                Player player = (Player)entity.getBukkitEntity();
                vect.multiply(1.6);
                player.setVelocity(vect);
                entity.al = true;
                continue;
            }
            entity.g(vect.getX(), vect.getY(), vect.getZ());
        }
    }

    private void b(List list) {
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);
            if (!(entity instanceof EntityLiving)) continue;
            entity.damageEntity(DamageSource.mobAttack((EntityLiving)this), 30.0f);
        }
    }

    private float b(double d0) {
        return (float)MathHelper.g((double)d0);
    }

    public void moveRelative(float strafe, float up, float forward, float friction) {
        float f = strafe * strafe + up * up + forward * forward;
        if (f >= 1.0E-4f) {
            if ((f = MathHelper.sqrt((double)f)) < 1.0f) {
                f = 1.0f;
            }
            f = friction / f;
            float f1 = MathHelper.sin((float)(this.yaw * ((float)Math.PI / 180)));
            float f2 = MathHelper.cos((float)(this.yaw * ((float)Math.PI / 180)));
            this.motX += (double)((strafe *= f) * f2 - (forward *= f) * f1);
            this.motY += (double)(up *= f);
            this.motZ += (double)(forward * f2 + strafe * f1);
        }
    }

    private boolean a(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor((double)axisalignedbb.a);
        int j = MathHelper.floor((double)axisalignedbb.b);
        int k = MathHelper.floor((double)axisalignedbb.c);
        int l = MathHelper.floor((double)axisalignedbb.d);
        int i1 = MathHelper.floor((double)axisalignedbb.e);
        int j1 = MathHelper.floor((double)axisalignedbb.f);
        boolean flag = false;
        boolean flag1 = false;
        ArrayList<Block> destroyedBlocks = new ArrayList<Block>();
        CraftWorld craftWorld = this.world.getWorld();
        for (int k1 = i; k1 <= l; ++k1) {
            for (int l1 = j; l1 <= i1; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    net.minecraft.server.v1_8_R3.Block block = this.world.getType(k1, l1, i2);
                    if (block.getMaterial() == Material.AIR) continue;
                    if (block != Blocks.OBSIDIAN && block != Blocks.WHITESTONE && block != Blocks.BEDROCK && this.world.getGameRules().getBoolean("mobGriefing")) {
                        flag1 = true;
                        destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
                        continue;
                    }
                    flag = true;
                }
            }
        }
        if (flag1) {
            CraftEntity bukkitEntity = this.getBukkitEntity();
            EntityExplodeEvent event = new EntityExplodeEvent((org.bukkit.entity.Entity)bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0f);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (event.isCancelled()) {
                return flag;
            }
            if (event.getYield() == 0.0f) {
                for (net.minecraft.server.v1_8_R3.Block block : event.blockList()) {
                    this.world.setAir(block.getX(), block.getY(), block.getZ());
                }
            } else {
                for (net.minecraft.server.v1_8_R3.Block block : event.blockList()) {
                    org.bukkit.Material blockId = block.getType();
                    if (blockId == org.bukkit.Material.AIR) continue;
                    int blockX = block.getX();
                    int blockY = block.getY();
                    int blockZ = block.getZ();
                    net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock((org.bukkit.Material)blockId);
                    if (nmsBlock.a(this.explosionSource)) {
                        nmsBlock.dropNaturally(this.world, blockX, blockY, blockZ, (int)block.getData(), event.getYield(), 0);
                    }
                    nmsBlock.wasExploded(this.world, blockX, blockY, blockZ, this.explosionSource);
                    this.world.setAir(blockX, blockY, blockZ);
                }
            }
            double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double)this.random.nextFloat();
            double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double)this.random.nextFloat();
            double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double)this.random.nextFloat();
            this.world.addParticle("largeexplode", d0, d1, d2, 0.0, 0.0, 0.0);
        }
        return flag;
    }

    public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        if (entitycomplexpart != this.head) {
            f /= 3.0f;
        }
        if ((f = this.phaseManager.getCurrentPhase().getAdjustedDamage(entitycomplexpart, damagesource, f)) < 0.01f) {
            return false;
        }
        this.damageEntity(damagesource, f);
        return true;
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        boolean retr;
        if (damagesource == DamageSource.STUCK || damagesource == DamageSource.CACTUS) {
            return false;
        }
        if (damagesource.getEntity() instanceof EndDragonEntity || damagesource.getEntity() instanceof EntityTNTPrimed || damagesource.isExplosion()) {
            return false;
        }
        this.target = null;
        if (damagesource.getEntity() instanceof EntityHuman) {
            float health = this.getHealth();
            retr = super.damageEntity(damagesource, f);
            if (this.phaseManager.getCurrentPhase().getIsStationary()) {
                this.sittingDamageReceived = (int)((float)this.sittingDamageReceived + (health - this.getHealth()));
                if ((float)this.sittingDamageReceived > 0.1f * this.getMaxHealth()) {
                    this.sittingDamageReceived = 0;
                    this.phaseManager.setPhase(PhaseList.TAKEOFF);
                    EntityHuman nearbyPlayer = this.world.findNearbyVulnerablePlayer((Entity)this, 100.0);
                    if (nearbyPlayer != null) {
                        this.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
                        this.getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER).setTarget(Vec3D.a((double)nearbyPlayer.locX, (double)nearbyPlayer.locY, (double)nearbyPlayer.locZ));
                    }
                }
            }
        } else {
            retr = super.damageEntity(damagesource, f);
        }
        return retr;
    }

    public boolean dealDamage(DamageSource damagesource, float f) {
        return this.damageEntity(damagesource, f);
    }

    protected void aF() {
        if (!this.dead) {
            ++this.bB;
            if (this.bB >= 180 && this.bB <= 200) {
                float f = (this.random.nextFloat() - 0.5f) * 8.0f;
                float f1 = (this.random.nextFloat() - 0.5f) * 4.0f;
                float f2 = (this.random.nextFloat() - 0.5f) * 8.0f;
                this.world.addParticle("hugeexplosion", this.locX + (double)f, this.locY + 2.0 + (double)f1, this.locZ + (double)f2, 0.0, 0.0, 0.0);
            }
            if (!this.world.isStatic) {
                if (this.bB > 150 && this.bB % 5 == 0) {
                    int j;
                    for (int i = this.expToDrop / 12; i > 0; i -= j) {
                        j = EntityExperienceOrb.getOrbValue((int)i);
                        this.world.addEntity((Entity)new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
                    }
                }
                if (this.bB == 1) {
                    int viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
                    for (EntityPlayer player : this.world.players) {
                        double deltaX = this.locX - player.locX;
                        double deltaZ = this.locZ - player.locZ;
                        double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                        if (this.world.spigotConfig.dragonDeathSoundRadius > 0 && distanceSquared > (double)(this.world.spigotConfig.dragonDeathSoundRadius * this.world.spigotConfig.dragonDeathSoundRadius)) continue;
                        if (distanceSquared > (double)(viewDistance * viewDistance)) {
                            double deltaLength = Math.sqrt(distanceSquared);
                            double relativeX = player.locX + deltaX / deltaLength * (double)viewDistance;
                            double relativeZ = player.locZ + deltaZ / deltaLength * (double)viewDistance;
                            player.playerConnection.sendPacket((Packet)new PacketPlayOutWorldEvent(1018, (int)relativeX, (int)this.locY, (int)relativeZ, 0, true));
                            continue;
                        }
                        player.playerConnection.sendPacket((Packet)new PacketPlayOutWorldEvent(1018, (int)this.locX, (int)this.locY, (int)this.locZ, 0, true));
                    }
                }
            }
            this.move(0.0, 0.1f, 0.0);
            this.aM = this.yaw += 20.0f;
            if (this.bB == 200 && !this.world.isStatic) {
                int j;
                for (int i = this.expToDrop - 10 * this.expToDrop / 12; i > 0; i -= j) {
                    j = EntityExperienceOrb.getOrbValue((int)i);
                    this.world.addEntity((Entity)new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
                }
                this.die();
            }
        }
    }

    private void b(int i, int j) {
        int b0 = 64;
        BlockEnderPortal.a = true;
        int b1 = 4;
        BlockStateListPopulator world = new BlockStateListPopulator((org.bukkit.World)this.world.getWorld());
        for (int k = b0 - 1; k <= b0 + 32; ++k) {
            for (int l = i - b1; l <= i + b1; ++l) {
                for (int i1 = j - b1; i1 <= j + b1; ++i1) {
                    double d0 = l - i;
                    double d1 = i1 - j;
                    double d2 = d0 * d0 + d1 * d1;
                    if (!(d2 <= ((double)b1 - 0.5) * ((double)b1 - 0.5))) continue;
                    if (k < b0) {
                        if (!(d2 <= ((double)(b1 - 1) - 0.5) * ((double)(b1 - 1) - 0.5))) continue;
                        world.setTypeUpdate(l, k, i1, Blocks.BEDROCK);
                        continue;
                    }
                    if (k > b0) {
                        world.setTypeUpdate(l, k, i1, Blocks.AIR);
                        continue;
                    }
                    if (d2 > ((double)(b1 - 1) - 0.5) * ((double)(b1 - 1) - 0.5)) {
                        world.setTypeUpdate(l, k, i1, Blocks.BEDROCK);
                        continue;
                    }
                    world.setTypeUpdate(l, k, i1, Blocks.ENDER_PORTAL);
                }
            }
        }
        world.setType(i, b0 + 0, j, Blocks.BEDROCK);
        world.setType(i, b0 + 1, j, Blocks.BEDROCK);
        world.setType(i, b0 + 2, j, Blocks.BEDROCK);
        world.setTypeAndData(i - 1, b0 + 2, j, Blocks.TORCH, 2, 0);
        world.setTypeAndData(i + 1, b0 + 2, j, Blocks.TORCH, 1, 0);
        world.setTypeAndData(i, b0 + 2, j - 1, Blocks.TORCH, 4, 0);
        world.setTypeAndData(i, b0 + 2, j + 1, Blocks.TORCH, 3, 0);
        world.setType(i, b0 + 3, j, Blocks.BEDROCK);
        world.setType(i, b0 + 4, j, Blocks.DRAGON_EGG);
        EntityCreatePortalEvent event = new EntityCreatePortalEvent((LivingEntity)this.getBukkitEntity(), Collections.unmodifiableList(world.getList()), PortalType.ENDER);
        this.world.getServer().getPluginManager().callEvent((Event)event);
        if (!event.isCancelled()) {
            for (BlockState state : event.getBlocks()) {
                state.update(true);
            }
        } else {
            for (BlockState state : event.getBlocks()) {
                PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(state.getX(), state.getY(), state.getZ(), this.world);
                for (EntityHuman entity : this.world.players) {
                    if (!(entity instanceof EntityPlayer)) continue;
                    ((EntityPlayer)entity).playerConnection.sendPacket((Packet)packet);
                }
            }
        }
        BlockEnderPortal.a = false;
    }

    protected void w() {
    }

    public Entity[] at() {
        return this.children;
    }

    public boolean R() {
        return false;
    }

    public World a() {
        return this.world;
    }

    protected String t() {
        return "mob.enderdragon.growl";
    }

    protected String aT() {
        return "mob.enderdragon.hit";
    }

    protected float bf() {
        return 5.0f;
    }

    public int getExpReward() {
        return 12000;
    }

    public double distanceSqToCenter(double xIn, double yIn, double zIn) {
        double d0 = this.locX + 0.5 - xIn;
        double d1 = this.locY + 0.5 - yIn;
        double d2 = this.locZ + 0.5 - zIn;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public int initPathPoints() {
        if (this.bossPathPoints[0] == null) {
            int x = this.homeLocation.getBlockX();
            int z = this.homeLocation.getBlockZ();
            for (int i = 0; i < 24; ++i) {
                int circleZ;
                int circleX;
                int j = 5;
                if (i < 12) {
                    circleX = (int)(40.0f * MathHelper.cos((float)(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)i))));
                    circleZ = (int)(40.0f * MathHelper.sin((float)(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)i))));
                } else if (i < 20) {
                    int lvt_3_1_ = i - 12;
                    circleX = (int)(30.0f * MathHelper.cos((float)(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)lvt_3_1_))));
                    circleZ = (int)(30.0f * MathHelper.sin((float)(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)lvt_3_1_))));
                    j += 10;
                } else {
                    int k1 = i - 20;
                    circleX = (int)(20.0f * MathHelper.cos((float)(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)k1))));
                    circleZ = (int)(20.0f * MathHelper.sin((float)(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)k1))));
                }
                circleX = x + circleX;
                circleZ = z + circleZ;
                int j1 = Math.max(40, BossUtils.getHighestBlockYAt((org.bukkit.World)this.world.getWorld(), circleX, circleZ, false) + j);
                this.bossPathPoints[i] = new BossPathPoint(circleX, j1, circleZ);
            }
            this.neighbors[0] = 6146;
            this.neighbors[1] = 8197;
            this.neighbors[2] = 8202;
            this.neighbors[3] = 16404;
            this.neighbors[4] = 32808;
            this.neighbors[5] = 32848;
            this.neighbors[6] = 65696;
            this.neighbors[7] = 131392;
            this.neighbors[8] = 131712;
            this.neighbors[9] = 263424;
            this.neighbors[10] = 526848;
            this.neighbors[11] = 525313;
            this.neighbors[12] = 1581057;
            this.neighbors[13] = 3166214;
            this.neighbors[14] = 2138120;
            this.neighbors[15] = 6373424;
            this.neighbors[16] = 4358208;
            this.neighbors[17] = 12910976;
            this.neighbors[18] = 9044480;
            this.neighbors[19] = 9706496;
            this.neighbors[20] = 15216640;
            this.neighbors[21] = 0xD0E000;
            this.neighbors[22] = 11763712;
            this.neighbors[23] = 0x7E0000;
        }
        return this.getNearestPpIdx(this.locX, this.locY, this.locZ);
    }

    public int getNearestPpIdx(double x, double y, double z) {
        float f = 10000.0f;
        int i = 0;
        PathPoint pathpoint = new PathPoint(MathHelper.floor((double)x), MathHelper.floor((double)y), MathHelper.floor((double)z));
        int j = 0;
        if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0) {
            j = 12;
        }
        for (int k = j; k < 24; ++k) {
            float f1;
            if (this.bossPathPoints[k] == null || !((f1 = this.bossPathPoints[k].distanceToSquared(pathpoint)) < f)) continue;
            f = f1;
            i = k;
        }
        return i;
    }

    public BossPath findPath(int startIdx, int finishIdx, @Nullable BossPathPoint andThen) {
        for (int i = 0; i < 24; ++i) {
            BossPathPoint pathpoint = this.bossPathPoints[i];
            pathpoint.setVisited(false);
            pathpoint.setDistanceToTarget(0.0f);
            pathpoint.setTotalPathDistance(0.0f);
            pathpoint.setDistanceToNext(0.0f);
            pathpoint.setPrevious(null);
            pathpoint.setIndex(-1);
        }
        BossPathPoint pathpoint4 = this.bossPathPoints[startIdx];
        BossPathPoint pathpoint5 = this.bossPathPoints[finishIdx];
        pathpoint4.setTotalPathDistance(0.0f);
        pathpoint4.setDistanceToNext(pathpoint4.distanceTo(pathpoint5));
        pathpoint4.setDistanceToTarget(pathpoint4.getDistanceToNext());
        this.pathQueue.clearPath();
        this.pathQueue.addPoint(pathpoint4);
        BossPathPoint pathpoint1 = pathpoint4;
        int j = 0;
        if (this.fightManager == null || this.fightManager.getNumAliveCrystals() == 0) {
            j = 12;
        }
        while (!this.pathQueue.isPathEmpty()) {
            BossPathPoint pathpoint2 = this.pathQueue.dequeue();
            if (pathpoint2.equals((Object)pathpoint5)) {
                if (andThen != null) {
                    andThen.setPrevious(pathpoint5);
                    pathpoint5 = andThen;
                }
                return this.makePath(pathpoint4, pathpoint5);
            }
            if (pathpoint2.distanceTo(pathpoint5) < pathpoint1.distanceTo(pathpoint5)) {
                pathpoint1 = pathpoint2;
            }
            pathpoint2.setVisited(true);
            int k = 0;
            for (int l = 0; l < 24; ++l) {
                if (this.bossPathPoints[l] != pathpoint2) continue;
                k = l;
                break;
            }
            for (int i1 = j; i1 < 24; ++i1) {
                BossPathPoint pathpoint3;
                if ((this.neighbors[k] & 1 << i1) <= 0 || (pathpoint3 = this.bossPathPoints[i1]).isVisited()) continue;
                float f = pathpoint2.getTotalPathPointDistance() + pathpoint2.distanceTo(pathpoint3);
                if (pathpoint3.isAssigned() && !(f < pathpoint3.getTotalPathPointDistance())) continue;
                pathpoint3.setPrevious(pathpoint2);
                pathpoint3.setTotalPathDistance(f);
                pathpoint3.setDistanceToNext(pathpoint3.distanceTo(pathpoint5));
                if (pathpoint3.isAssigned()) {
                    this.pathQueue.changeDistance(pathpoint3, pathpoint3.getTotalPathPointDistance() + pathpoint3.getDistanceToNext());
                    continue;
                }
                pathpoint3.setDistanceToTarget(pathpoint3.getTotalPathPointDistance() + pathpoint3.getDistanceToNext());
                this.pathQueue.addPoint(pathpoint3);
            }
        }
        if (pathpoint1 == pathpoint4) {
            return null;
        }
        if (andThen != null) {
            andThen.setPrevious(pathpoint1);
            pathpoint1 = andThen;
        }
        return this.makePath(pathpoint4, pathpoint1);
    }

    private BossPath makePath(BossPathPoint start, BossPathPoint finish) {
        int i = 1;
        BossPathPoint pathpoint = finish;
        while (pathpoint.getPrevious() != null) {
            ++i;
            pathpoint = pathpoint.getPrevious();
        }
        BossPathPoint[] apathpoint = new BossPathPoint[i];
        BossPathPoint pathpoint1 = finish;
        apathpoint[--i] = finish;
        while (pathpoint1.getPrevious() != null) {
            pathpoint1 = pathpoint1.getPrevious();
            apathpoint[--i] = pathpoint1;
        }
        return new BossPath(apathpoint);
    }

    public Location getHomeLocation() {
        return this.homeLocation;
    }
}

