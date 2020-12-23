/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.utils.BossUtils
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
 *  org.bukkit.entity.Chicken
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 */
package org.arkhamnetwork.Arkkit.patches;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

final class ThanksgivingTask
        extends BukkitRunnable {
    ThanksgivingTask() {
    }

    public static void spawnTurkey(Location l) {
        Chicken c = (Chicken) l.getWorld().spawnCreature(l, EntityType.CHICKEN);
        if (c == null || !c.isValid() || c.isDead()) {
            return;
        }
        c.setAdult();
        c.setCustomNameVisible(true);
        c.setMaxHealth(100.0);
        c.setHealth(100.0);
        c.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        c.setCustomName(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Thanksgiving Turkey");
        EntityInsentient insentient = (EntityInsentient) ((CraftLivingEntity) c).getHandle();
        EntityCreature creature = (EntityCreature) insentient;
        // TODO: FIX WHEN BOSSES IS DONE
     /*   BossUtils.clearGoalSelectors((EntityInsentient)insentient);
        BossUtils.addSelector((EntityInsentient)insentient, (int)0, (PathfinderGoal)new PathfinderGoalFloat(insentient));
        BossUtils.addSelector((EntityInsentient)insentient, (int)1, (PathfinderGoal)new PathfinderGoalPanic(creature, 1.4));
        BossUtils.addSelector((EntityInsentient)insentient, (int)5, (PathfinderGoal)new PathfinderGoalLookAtPlayer(insentient, EntityHuman.class, 6.0f));
        BossUtils.addSelector((EntityInsentient)insentient, (int)6, (PathfinderGoal)new PathfinderGoalRandomStroll(creature, 1.0)); */
        l.getWorld().playSound(l, Sound.CHICKEN_IDLE, 2.0f, 0.25f);
    }

    public void run() {
        if (Bukkit.getOnlinePlayers().size() <= 0) {
            return;
        }
        Player rand_p = (Player) Bukkit.getOnlinePlayers().toArray()[new Random().nextInt(Bukkit.getOnlinePlayers().size())];
        ThanksgivingTask.spawnTurkey(rand_p.getLocation());
    }
}

