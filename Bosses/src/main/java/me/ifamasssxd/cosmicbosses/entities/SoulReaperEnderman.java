/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.AttributeInstance
 *  net.minecraft.server.v1_7_R4.AttributeModifier
 *  net.minecraft.server.v1_7_R4.Block
 *  net.minecraft.server.v1_7_R4.Blocks
 *  net.minecraft.server.v1_7_R4.DamageSource
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityDamageSource
 *  net.minecraft.server.v1_7_R4.EntityEnderPearl
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityLiving
 *  net.minecraft.server.v1_7_R4.EntityMonster
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.Item
 *  net.minecraft.server.v1_7_R4.ItemStack
 *  net.minecraft.server.v1_7_R4.Items
 *  net.minecraft.server.v1_7_R4.Material
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.NBTTagCompound
 *  net.minecraft.server.v1_7_R4.Vec3D
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory
 *  org.bukkit.entity.Entity
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityTeleportEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.spigotmc.ProtocolData$ByteShort
 */
package me.ifamasssxd.cosmicbosses.entities;

import java.util.UUID;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_7_R4.AttributeInstance;
import net.minecraft.server.v1_7_R4.AttributeModifier;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.Blocks;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityDamageSource;
import net.minecraft.server.v1_7_R4.EntityEnderPearl;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.Item;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import net.minecraft.server.v1_7_R4.Material;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.Vec3D;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.spigotmc.ProtocolData;

public class SoulReaperEnderman
extends EntityMonster {
    private static final UUID bp = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier bq = new AttributeModifier(bp, "Attacking speed boost", (double)6.2f, 0).a(false);
    private int teleportTick;
    private int bt;
    private Entity bu;
    private boolean angry;
    private boolean teleportBehind = false;
    public boolean shooting = false;
    private double d = 0.0;
    private double flagTimer = 0.0;
    private float newF = 10.0f;
    private float newJ = this.newF * this.newF;

    public SoulReaperEnderman(World world, boolean teleportBehind) {
        super(world);
        this.a(0.6f, 2.9f);
        this.teleportBehind = teleportBehind;
        this.W = 1.0f;
    }

    public SoulReaperEnderman(World world) {
        super(world);
        this.a(0.6f, 2.9f);
        this.W = 1.0f;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(40.0);
        this.getAttributeInstance(GenericAttributes.d).setValue(6.0);
        this.getAttributeInstance(GenericAttributes.e).setValue(21.0);
    }

    protected void c() {
        super.c();
        this.datawatcher.a(16, (Object)new ProtocolData.ByteShort(0));
        this.datawatcher.a(17, (Object)new Byte(0));
        this.datawatcher.a(18, (Object)new Byte(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setShort("carried", (short)Block.getId((Block)this.getCarried()));
        nbttagcompound.setShort("carriedData", (short)this.getCarriedData());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setCarried(Block.getById((int)nbttagcompound.getShort("carried")));
        this.setCarriedData(nbttagcompound.getShort("carriedData"));
    }

    protected Entity findTarget() {
        EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer((Entity)this, 64.0);
        if (entityhuman != null) {
            this.angry = true;
            if (this.bt == 0) {
                this.world.makeSound(entityhuman.locX, entityhuman.locY, entityhuman.locZ, "mob.endermen.stare", 1.0f, 1.0f);
            }
            if (this.bt++ == 5) {
                this.bt = 0;
                this.a(true);
                return entityhuman;
            }
        }
        return null;
    }

    private boolean f(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.armor[3];
        if (itemstack != null && itemstack.getItem() == Item.getItemOf((Block)Blocks.PUMPKIN)) {
            return false;
        }
        Vec3D vec3d = entityhuman.j(1.0f).a();
        Vec3D vec3d1 = Vec3D.a((double)(this.locX - entityhuman.locX), (double)(this.boundingBox.b + (double)(this.length / 2.0f) - (entityhuman.locY + (double)entityhuman.getHeadHeight())), (double)(this.locZ - entityhuman.locZ));
        double d0 = vec3d1.b();
        double d1 = vec3d.b(vec3d1 = vec3d1.a());
        return d1 > 1.0 - 0.025 / d0 && entityhuman.hasLineOfSight((Entity)this);
    }

    public void e() {
        if (!(this.target instanceof EntityHuman)) {
            this.target = null;
        }
        this.getAttributeInstance(GenericAttributes.d).setValue(5.0);
        if (this.bu != this.target) {
            AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.d);
            attributeinstance.b(bq);
            if (this.target != null) {
                attributeinstance.a(bq);
            }
        }
        this.bu = this.target;
        if (!this.world.isStatic && this.world.getGameRules().getBoolean("mobGriefing")) {
            int k;
            int j;
            if (this.getCarried().getMaterial() == Material.AIR) {
                if (this.random.nextInt(20) == 0) {
                    int i = MathHelper.floor((double)(this.locX - 2.0 + this.random.nextDouble() * 4.0));
                    j = MathHelper.floor((double)(this.locY + this.random.nextDouble() * 3.0));
                    k = MathHelper.floor((double)(this.locZ - 2.0 + this.random.nextDouble() * 4.0));
                    Block block = this.world.getType(i, j, k);
                }
            } else if (this.random.nextInt(2000) == 0) {
                int i = MathHelper.floor((double)(this.locX - 1.0 + this.random.nextDouble() * 2.0));
                j = MathHelper.floor((double)(this.locY + this.random.nextDouble() * 2.0));
                k = MathHelper.floor((double)(this.locZ - 1.0 + this.random.nextDouble() * 2.0));
                Block block = this.world.getType(i, j, k);
                Block block1 = this.world.getType(i, j - 1, k);
                if (block.getMaterial() == Material.AIR && block1.getMaterial() != Material.AIR && block1.d() && !CraftEventFactory.callEntityChangeBlockEvent((Entity)this, (int)i, (int)j, (int)k, (Block)this.getCarried(), (int)this.getCarriedData()).isCancelled()) {
                    this.world.setTypeAndData(i, j, k, this.getCarried(), this.getCarriedData(), 3);
                    this.setCarried(Blocks.AIR);
                }
            }
        }
        for (int i = 0; i < 2; ++i) {
            this.world.addParticle("portal", this.locX + (this.random.nextDouble() - 0.5) * (double)this.width, this.locY + this.random.nextDouble() * (double)this.length - 0.25, this.locZ + (this.random.nextDouble() - 0.5) * (double)this.width, (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
        }
        if (this.ticksLived % 20 == 0) {
            // empty if block
        }
        if (!this.world.w() || !this.world.isStatic) {
            // empty if block
        }
        this.bc = false;
        if (this.target != null) {
            this.a(this.target, 100.0f, 100.0f);
        }
        if (!this.world.isStatic && this.isAlive()) {
            if (this.target != null) {
                double distance = this.target.f((Entity)this);
                if (this.target instanceof EntityHuman && this.f((EntityHuman)this.target)) {
                    if (this.target.f((Entity)this) < 16.0 && this.random.nextInt(100) <= 20) {
                        this.c(this.target);
                    }
                    this.teleportTick = 0;
                } else if (distance > 80.0 && distance < 1000.0 && this.teleportTick++ >= 5 && WorldGuardUtils.isPvPEnabled(this.target.getBukkitEntity().getLocation()) && this.c(this.target)) {
                    this.teleportTick = 0;
                }
            } else {
                this.a(false);
                this.teleportTick = 0;
            }
        }
        if (this.target != null) {
            this.shootTick();
        }
        super.e();
    }

    public CraftEntity getBukkitEntity() {
        return super.getBukkitEntity();
    }

    protected boolean bZ() {
        double d0 = this.locX + (this.random.nextDouble() - 0.5) * 3.0;
        double d1 = this.locY + (double)(this.random.nextInt(64) - 1);
        double d2 = this.locZ + (this.random.nextDouble() - 0.5) * 3.0;
        return this.k(d0, d1, d2);
    }

    protected boolean c(Entity entity) {
        CraftWorld wrld = this.world.getWorld();
        if (this.teleportBehind) {
            Location loc = entity.getBukkitEntity().getLocation();
            org.bukkit.block.Block behind = loc.getBlock();
            int direction = (int)loc.getYaw();
            if (direction < 0) {
                direction += 360;
                direction = (direction + 45) / 90;
            } else {
                direction = (direction + 45) / 90;
            }
            switch (direction) {
                case 1: {
                    behind = wrld.getBlockAt((int)loc.getX() + 1, (int)loc.getY(), (int)loc.getZ());
                    break;
                }
                case 2: {
                    behind = wrld.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ() + 1);
                    break;
                }
                case 3: {
                    behind = wrld.getBlockAt((int)loc.getX() - 1, (int)loc.getY(), (int)loc.getZ());
                    break;
                }
                case 4: {
                    behind = wrld.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ() - 1);
                    break;
                }
                case 0: {
                    behind = wrld.getBlockAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ() - 1);
                    break;
                }
            }
            double d1 = (double)behind.getX() + (this.random.nextDouble() - 0.5) * 10.0;
            double d2 = behind.getY() + (this.random.nextInt(15) - 1);
            double d3 = (double)behind.getZ() + (this.random.nextDouble() - 0.5) * 10.0;
            return this.k(d1, d2, d3);
        }
        double d1 = entity.locX + (this.random.nextDouble() - 0.5) * 10.0;
        double d2 = entity.locY + (double)(this.random.nextInt(15) - 1);
        double d3 = entity.locZ + (this.random.nextDouble() - 0.5) * 10.0;
        return this.k(d1, d2, d3);
    }

    protected boolean k(double d0, double d1, double d2) {
        int k;
        int j;
        double d3 = this.locX;
        double d4 = this.locY;
        double d5 = this.locZ;
        double y = d4;
        boolean flag = false;
        int i = MathHelper.floor((double)d0);
        if (this.world.isLoaded(i, j = MathHelper.floor((double)d1), k = MathHelper.floor((double)d2))) {
            boolean flag1 = false;
            while (!flag1 && j > 0) {
                Block block = this.world.getType(i, j - 1, k);
                if (block.getMaterial().isSolid()) {
                    flag1 = true;
                    continue;
                }
                --j;
            }
            if (flag1) {
                EntityTeleportEvent teleport = new EntityTeleportEvent((org.bukkit.entity.Entity)this.getBukkitEntity(), new Location((org.bukkit.World)this.world.getWorld(), d3, (double)j, d5), new Location((org.bukkit.World)this.world.getWorld(), this.locX, this.locY, this.locZ));
                this.world.getServer().getPluginManager().callEvent((Event)teleport);
                if (teleport.isCancelled()) {
                    return false;
                }
                Location to = teleport.getTo();
                this.setPosition(to.getX(), to.getY(), to.getZ());
                if (this.world.getCubes((Entity)this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox)) {
                    flag = true;
                }
            }
        }
        this.locX = d0;
        this.locY = j;
        this.locZ = d2;
        if (!flag) {
            this.setPosition(d3, d4, d5);
            return false;
        }
        int short1 = 128;
        for (int l = 0; l < short1; ++l) {
            double d6 = (double)l / ((double)short1 - 1.0);
            float f = (this.random.nextFloat() - 0.5f) * 0.2f;
            float f1 = (this.random.nextFloat() - 0.5f) * 0.2f;
            float f2 = (this.random.nextFloat() - 0.5f) * 0.2f;
            double d7 = d3 + (this.locX - d3) * d6 + (this.random.nextDouble() - 0.5) * (double)this.width * 2.0;
            double d8 = d4 + (this.locY - d4) * d6 + this.random.nextDouble() * (double)this.length;
            double d9 = d5 + (this.locZ - d5) * d6 + (this.random.nextDouble() - 0.5) * (double)this.width * 2.0;
            this.world.addParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
        }
        this.world.makeSound(d3, d4, d5, "mob.endermen.portal", 1.0f, 1.1f);
        this.makeSound("mob.endermen.portal", 1.0f, 1.1f);
        return true;
    }

    protected String t() {
        return this.cd() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String aT() {
        return "mob.endermen.hit";
    }

    protected String aU() {
        return "mob.endermen.death";
    }

    protected Item getLoot() {
        return Items.ENDER_PEARL;
    }

    protected void dropDeathLoot(boolean flag, int i) {
    }

    public void setCarried(Block block) {
        this.datawatcher.watch(16, (Object)new ProtocolData.ByteShort((short)Block.getId((Block)block)));
    }

    public Block getCarried() {
        return Block.getById((int)this.datawatcher.getShort(16));
    }

    public void setCarriedData(int i) {
        this.datawatcher.watch(17, (Object)((byte)(i & 0xFF)));
    }

    public int getCarriedData() {
        return this.datawatcher.getByte(17);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        boolean happens;
        if (this.isInvulnerable()) {
            return false;
        }
        this.a(true);
        if (damagesource instanceof EntityDamageSource) {
            EntityDamageSource source;
            if (damagesource.getEntity() instanceof EntityHuman) {
                this.angry = true;
            }
            if (!((source = (EntityDamageSource)damagesource).getEntity() instanceof EntityHuman)) {
                return false;
            }
        }
        if ((happens = super.damageEntity(damagesource, f)) && damagesource instanceof EntityDamageSource && damagesource.getEntity() instanceof EntityHuman && damagesource.getEntity() != this.target && this.random.nextInt(30) == 0) {
            this.target = damagesource.getEntity();
            this.setGoalTarget((EntityLiving)((EntityHuman)damagesource.getEntity()));
        }
        return happens;
    }

    public boolean cd() {
        return this.datawatcher.getByte(18) > 0;
    }

    public void a(boolean flag) {
        this.datawatcher.watch(18, (Object)((byte)(flag ? 1 : 0)));
    }

    public void die() {
        super.die();
    }

    public void shootTick() {
        if (!this.shooting) {
            return;
        }
        double d0 = this.e(this.target.locX, this.target.boundingBox.b, this.target.locZ);
        boolean flag = this.getEntitySenses().canSee(this.target);
        this.flagTimer = flag ? (this.flagTimer += 1.0) : 0.0;
        if (d0 <= (double)this.newJ && this.flagTimer >= 20.0) {
            this.getNavigation().h();
        } else {
            this.getNavigation().a(this.target, 1.25);
        }
        this.getControllerLook().a(this.target, 30.0f, 30.0f);
        if ((this.d -= 1.0) == 0.0) {
            float f;
            if (d0 > (double)this.newJ || !flag) {
                return;
            }
            float f1 = f = MathHelper.sqrt((double)d0) / this.newF;
            if (f < 0.1f) {
                f1 = 0.1f;
            }
            if (f1 > 1.0f) {
                f1 = 1.0f;
            }
            if (this.target instanceof EntityHuman) {
                this.shootEnderpearl((EntityLiving)this.target, f1);
            }
            this.d = this.random.nextInt(5) + 5;
        } else if (this.d < 0.0) {
            this.d = this.random.nextInt(5) + 5;
        }
    }

    private void shootEnderpearl(EntityLiving entityLiving, float v) {
        EntityEnderPearl entitysnowball = new EntityEnderPearl(this.world, (EntityLiving)this);
        double d0 = entityLiving.locX - this.locX;
        double d1 = entityLiving.locY + (double)entityLiving.getHeadHeight() - (double)1.6f - entitysnowball.locY;
        double d2 = entityLiving.locZ - this.locZ;
        float f1 = MathHelper.sqrt((double)(d0 * d0 + d2 * d2)) * 0.2f;
        entitysnowball.getBukkitEntity().setMetadata("exploding", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        entitysnowball.shoot(d0, d1 + (double)f1, d2, 1.6f, 12.0f);
        this.makeSound("random.bow", 1.0f, 1.0f / (this.aI().nextFloat() * 0.4f + 0.8f));
        this.world.addEntity((Entity)entitysnowball);
    }
}

