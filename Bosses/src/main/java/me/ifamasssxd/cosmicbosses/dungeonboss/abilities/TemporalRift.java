/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.sk89q.worldguard.protection.flags.DefaultFlag
 *  net.minecraft.server.v1_8_R3.BlockEnderPortal
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityHuman
 *  net.minecraft.server.v1_8_R3.EntityLiving
 *  net.minecraft.server.v1_8_R3.EntityWitherSkull
 *  net.minecraft.server.v1_8_R3.MovingObjectPosition
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import com.google.common.collect.Lists;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_8_R3.BlockEnderPortal;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class TemporalRift
extends DungeonAbility {
    int shotsFired = 0;
    private long delay = System.currentTimeMillis();
    private int landed = 0;

    public TemporalRift(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
        if (this.delay != 0L && System.currentTimeMillis() - this.delay >= 500L) {
            this.delay = System.currentTimeMillis();
            LivingEntity ent = this.boss.getEntity();
            Location location = ent.getEyeLocation();
            ThreadLocalRandom r = ThreadLocalRandom.current();
            Vector vec = new Vector((double)(((Random)r).nextBoolean() ? -1 : 1) * Math.random(), -1.5, (double)(((Random)r).nextBoolean() ? -1 : 1) * Math.random());
            WorldServer server = ((CraftWorld)location.getWorld()).getHandle();
            EntityWitherSkull skull = new EntityWitherSkull((World)server, (EntityLiving)((DungeonYijki)this.boss).getBossEntity(), vec.getX(), vec.getY(), vec.getZ()){

                protected void a(MovingObjectPosition movingobjectposition) {
                    Entity hit = movingobjectposition.entity;
                    if (hit != null && !(hit instanceof EntityHuman)) {
                        return;
                    }
                    super.a(movingobjectposition);
                }
            };
            location.getWorld().playSound(location, Sound.WITHER_SHOOT, 1.0f, 0.7f);
            skull.setCharged(true);
            if (vec.length() != 0.0) {
                vec = vec.normalize();
            }
            skull.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            skull.getBukkitEntity().setVelocity(vec);
            skull.getBukkitEntity().setMetadata("temporalRift", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.boss.getUuid()));
            server.addEntity((Entity)skull, CreatureSpawnEvent.SpawnReason.CUSTOM);
            ++this.shotsFired;
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(10L);
    }

    @Override
    public boolean shouldStopTicking() {
        return this.shotsFired >= 20 && (this.landed >= 12 || System.currentTimeMillis() - this.initTime >= this.getCooldown());
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();
        this.delay = System.currentTimeMillis();
        this.boss.sendMessage("Fall into my universe mortal!");
    }

    @Override
    public void onAbilityDisable() {
    }

    @Override
    public void onAbilityEvent(Event event) {
        if (event instanceof ProjectileHitEvent) {
            ++this.landed;
            ProjectileHitEvent e = (ProjectileHitEvent)event;
            Block block = e.getEntity().getLocation().getBlock();
            if (block.getType() == Material.AIR) {
                block = block.getRelative(BlockFace.DOWN);
            }
            if (block.getType().isSolid() && block.getRelative(BlockFace.UP).getType() == Material.AIR && !this.boss.getModifier().containsLocation(block.getLocation())) {
                ArrayList changed = Lists.newArrayList((Object[])new Block[]{block});
                int radius = 1;
                for (int x = -radius; x < radius; ++x) {
                    for (int z = -radius; z < radius; ++z) {
                        Block above;
                        Block toExplode = block.getLocation().add((double)x, 0.0, (double)z).getBlock();
                        if (toExplode.equals((Object)block) || ThreadLocalRandom.current().nextBoolean() || WorldGuardUtils.hasFlagDisabled(toExplode.getLocation(), DefaultFlag.OTHER_EXPLOSION) || (above = toExplode.getRelative(BlockFace.UP)).getType() != Material.AIR || !toExplode.getType().isSolid() || toExplode.getType().name().toLowerCase().endsWith("_PLATE")) continue;
                        BlockEnderPortal.a = true;
                        this.boss.getModifier().storeBlockAndSet(this, toExplode.getLocation(), Material.ENDER_PORTAL, (byte)0);
                        BlockEnderPortal.a = false;
                        toExplode.setMetadata("temporalRift", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.boss.getUuid()));
                    }
                }
                if (WorldGuardUtils.hasFlagDisabled(block.getLocation(), DefaultFlag.OTHER_EXPLOSION)) {
                    return;
                }
                BlockModifier mod = this.boss.getModifier();
                BlockEnderPortal.a = true;
                mod.storeBlockAndSet(this, block.getLocation(), Material.ENDER_PORTAL, (byte)0);
                BlockEnderPortal.a = false;
                block.setMetadata("temporalRift", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.boss.getUuid()));
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                    for (Block bl : changed) {
                        mod.restoreBlock(this, bl);
                    }
                }, (long)((ThreadLocalRandom.current().nextInt(5) + 5) * 20));
            }
        }
    }

    @Override
    public String getAbilityName() {
        return "Temporal Rift";
    }
}

