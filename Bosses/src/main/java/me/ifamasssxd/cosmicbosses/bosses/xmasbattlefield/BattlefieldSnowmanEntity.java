/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.citizensnpcs.npc.entity.EntityHumanNPC
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.Material
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 */
package me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.EntityHolidayGolem;
import me.ifamasssxd.cosmicbosses.bosses.holidayspirit.PathfinderGoalArrowAttackSnowman;
import me.ifamasssxd.cosmicbosses.bosses.xmasbattlefield.BattlefieldSnowman;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.BlockChangeManager;
import net.citizensnpcs.npc.entity.EntityHumanNPC;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class BattlefieldSnowmanEntity
extends EntityHolidayGolem {
    private BattlefieldSnowman boss;

    public BattlefieldSnowmanEntity(World world) {
        this(world, null);
        this.dead = true;
    }

    public BattlefieldSnowmanEntity(World world, BattlefieldSnowman spiritBoss) {
        super(world);
        this.a(0.4f, 1.8f);
        this.getNavigation().a(true);
        this.boss = spiritBoss;
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalArrowAttackSnowman(this, 1.25, 20, 10.0f));
        this.goalSelector.a(1, (PathfinderGoal)new PathfinderGoalLookAtPlayer((EntityInsentient)this, EntityHuman.class, 6.0f));
        this.goalSelector.a(4, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.targetSelector.a(1, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, EntityHuman.class, 0, true, false, entity -> entity instanceof EntityHuman && !(entity instanceof EntityHumanNPC)));
    }

    public boolean bk() {
        return true;
    }

    public CraftEntity getBukkitEntity() {
        return new CraftCreature(this.world.getServer(), (EntityCreature)this);
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.d).setValue(0.6);
        this.getAttributeInstance(GenericAttributes.b).setValue(32.0);
    }

    public void e() {
        if (this.boss == null) {
            this.die();
            Bukkit.getLogger().info("[BattlefieldSnowman] Unable to find boss.");
            return;
        }
        super.e();
        for (int l = 0; l < 4; ++l) {
            Location location;
            int i = MathHelper.floor((double)(this.locX + (double)((float)(l % 2 * 2 - 1) * 0.25f)));
            int j = MathHelper.floor((double)this.locY);
            int k = MathHelper.floor((double)(this.locZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25f)));
            if (this.random.nextInt(100) == 0 && this.world.getType(i, j, k).getMaterial() == Material.AIR && this.world.getType(i, j - 1, k).getMaterial().isSolid()) {
                BlockChangeManager blockManager = CosmicBosses.getInstance().getBlockChangeManager();
                Location location2 = new Location((org.bukkit.World)this.world.getWorld(), (double)i, (double)j, (double)k);
                blockManager.replaceBlock(location2.getBlock(), this.getId(), -1, org.bukkit.Material.FIRE, (byte)0);
            }
            if (!this.world.getType(i, j - 1, k).getMaterial().isSolid() || (location = new Location((org.bukkit.World)this.world.getWorld(), (double)i, (double)(j - 1), (double)k)).getBlock().getType() == org.bukkit.Material.NETHERRACK) continue;
            BlockChangeManager blockManager = CosmicBosses.getInstance().getBlockChangeManager();
            blockManager.replaceBlock(location.getBlock(), this.getId(), -1, org.bukkit.Material.NETHERRACK, (byte)0);
        }
    }
}

