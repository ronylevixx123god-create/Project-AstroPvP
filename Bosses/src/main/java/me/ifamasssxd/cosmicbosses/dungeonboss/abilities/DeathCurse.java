/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.GenericAttributes
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.dungeonboss.DeathCurseBlaze;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMonster;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DeathCurse
extends DungeonAbility {
    private Player player;
    private DeathCurseBlaze blaze;
    int ticks = 0;
    private long spawnTime;

    public DeathCurse(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
        ++this.ticks;
        if (this.spawnTime != 0L && System.currentTimeMillis() - this.spawnTime >= 15000L && this.player != null && this.player.getWorld().getName().equals(this.blaze.world.getWorld().getName()) && this.blaze.getBukkitEntity().getLocation().distanceSquared(this.player.getLocation()) <= 10000.0 && !this.player.isDead()) {
            this.player.setHealth(0.0);
            this.boss.sendMessage("Such a waste of life you are [NAME]!");
            if (this.blaze != null) {
                this.boss.removeMinion((org.bukkit.entity.Entity)this.blaze.getBukkitEntity());
                this.blaze.die();
            }
            Bukkit.getLogger().info("Killing " + this.player.getName() + " because Death Curse wasnt killed!");
        }
        if (this.ticks % 5 == 0 && this.blaze != null && this.blaze.isAlive()) {
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LAVA, (Location)this.blaze.getBukkitEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)10);
        }
        if (this.ticks % 20 == 0 && this.blaze != null && this.blaze.isAlive() && this.player != null && this.player.isValid()) {
            this.blaze.getBukkitEntity().getWorld().playSound(this.blaze.getBukkitEntity().getLocation(), Sound.NOTE_PLING, 10.0f, 1.1f);
        }
    }

    @Override
    public long getCooldown() {
        return 0L;
    }

    @Override
    public boolean shouldStopTicking() {
        return this.blaze == null || this.blaze.dead || System.currentTimeMillis() - this.spawnTime >= TimeUnit.SECONDS.toMillis(17L);
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();
        ArrayList nearbyPlayers = Lists.newArrayList();
        for (org.bukkit.entity.Entity ent : this.boss.getEntity().getNearbyEntities(128.0, 128.0, 128.0)) {
            if (!(ent instanceof Player) || ent.hasMetadata("NPC") || EssentialsUtils.isInGodMode((Player)this.player)) continue;
            nearbyPlayers.add(ent);
        }
        if (nearbyPlayers.size() > 0) {
            org.bukkit.entity.Entity ent = (org.bukkit.entity.Entity)nearbyPlayers.get(ThreadLocalRandom.current().nextInt(nearbyPlayers.size()));
            this.player = (Player)ent;
            Bukkit.getLogger().info("Spawning Death Curse on " + this.player.getName());
            ThreadLocalRandom r = ThreadLocalRandom.current();
            Location spawnLoc = this.player.getLocation().clone().add((double)(((Random)r).nextInt(2) * (((Random)r).nextBoolean() ? -1 : 1)), 1.0, (double)(((Random)r).nextInt(2) * (((Random)r).nextBoolean() ? -1 : 1)));
            this.spawnTime = System.currentTimeMillis();
            WorldServer world = ((CraftWorld)this.player.getWorld()).getHandle();
            this.blaze = new DeathCurseBlaze((World)world);
            this.blaze.setPosition(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ());
            world.addEntity((Entity)this.blaze, CreatureSpawnEvent.SpawnReason.CUSTOM);
            this.blaze.setCustomName((Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Death Curse");
            this.blaze.setCustomNameVisible(true);
            this.blaze.getAttributeInstance(GenericAttributes.c).setValue(1.0);
            CraftMonster living = (CraftMonster)this.blaze.getBukkitEntity();
            living.setMaxHealth(10.0);
            living.setHealth(10.0);
            this.boss.sendMessage("I tire of these games. You, [NAME], your life will end in 15 seconds. Goodbye!", Sound.BLAZE_BREATH, 0.9f);
        } else {
            Bukkit.getLogger().info("No target found!");
        }
    }

    @Override
    public void onAbilityDisable() {
        if (this.blaze != null && this.blaze.isAlive()) {
            this.blaze.die();
            this.blaze = null;
        }
    }

    @Override
    public void onAbilityEvent(Event event) {
    }

    @Override
    public String getAbilityName() {
        return "Death Curse";
    }
}

