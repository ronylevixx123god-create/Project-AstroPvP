/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.cosmicbosses.utils.BossUtils
 *  me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalPanic
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomStroll
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Zombie
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.PathfinderGoal;
import net.minecraft.server.v1_7_R4.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R4.PathfinderGoalPanic;
import net.minecraft.server.v1_7_R4.PathfinderGoalRandomStroll;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.PathfinderTrickOrTreat;
import org.arkhamnetwork.Arkkit.patches.TrickOrTreaterCommand;
import org.arkhamnetwork.Arkkit.patches.TrickOrTreaterListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TrickOrTreaters
extends ArkPlugin {
    public TrickOrTreaters(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new TrickOrTreaterListener(this));
        this.registerCommand(new TrickOrTreaterCommand(this, "trickortreater", "/<command>", "Spawns special Trick or Treater."));
        this.registerTask(new BukkitRunnable(){

            public void run() {
                ArrayList players = Lists.newArrayList((Iterable)Bukkit.getOnlinePlayers());
                Collections.shuffle(players);
                for (Player pl : players) {
                    if (WorldGuardUtils.isInRegionsFast((Location)pl.getLocation(), (String[])new String[]{"spawn_region"})) continue;
                    TrickOrTreaters.this.spawnTrickOrTreater(pl.getLocation());
                    return;
                }
            }
        }.runTaskTimer(this.getBukkitPlugin(), 2400L, 3200L), TrickOrTreaters.class);
    }

    @Override
    public void disable() {
        this.unregisterCommands();
        this.unregisterListeners();
        this.unregisterTasks();
    }

    protected boolean isTrickOrTreater(Entity ent) {
        return ent.hasMetadata("trickOrTreater");
    }

    public void spawnTrickOrTreater(Location l) {
        Zombie z = (Zombie)l.getWorld().spawnCreature(l, EntityType.ZOMBIE);
        if (z == null || !z.isValid() || z.isDead()) {
            return;
        }
        z.setBaby(true);
        z.setCustomNameVisible(true);
        z.setMaxHealth(100.0);
        z.setHealth(100.0);
        z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        z.setCustomName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Trick Or Treater");
        z.setMetadata("trickOrTreater", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)l));
        z.setCanPickupItems(false);
        EntityEquipment equipment = z.getEquipment();
        equipment.setItemInHandDropChance(0.0f);
        equipment.setHelmetDropChance(0.0f);
        equipment.setChestplateDropChance(0.0f);
        equipment.setLeggingsDropChance(0.0f);
        equipment.setBootsDropChance(0.0f);
        EntityInsentient insentient = (EntityInsentient)((CraftLivingEntity)z).getHandle();
        EntityCreature creature = (EntityCreature)insentient;
        BossUtils.clearGoalSelectors((EntityInsentient)insentient);
        BossUtils.addSelector((EntityInsentient)insentient, (int)0, (PathfinderGoal)new PathfinderGoalFloat(insentient));
        BossUtils.addSelector((EntityInsentient)insentient, (int)1, (PathfinderGoal)new PathfinderGoalPanic(creature, 1.4));
        BossUtils.addSelector((EntityInsentient)insentient, (int)3, (PathfinderGoal)new PathfinderTrickOrTreat(creature));
        BossUtils.addSelector((EntityInsentient)insentient, (int)5, (PathfinderGoal)new PathfinderGoalLookAtPlayer(insentient, EntityHuman.class, 6.0f));
        BossUtils.addSelector((EntityInsentient)insentient, (int)6, (PathfinderGoal)new PathfinderGoalRandomStroll(creature, 1.0));
        l.getWorld().playSound(l, Sound.CHICKEN_IDLE, 2.0f, 0.25f);
        l.getWorld().playSound(l, Sound.ZOMBIE_IDLE, 2.0f, 0.25f);
    }
}

