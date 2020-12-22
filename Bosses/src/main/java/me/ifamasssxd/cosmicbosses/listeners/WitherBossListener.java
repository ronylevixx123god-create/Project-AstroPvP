/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.ThrownPotion
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.ExplosionPrimeEvent
 *  org.bukkit.event.entity.PotionSplashEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.listeners;

import java.util.Random;
import java.util.UUID;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.UndeadAssassin;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.MiniBoss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherBossListener
implements Listener {
    Random random = new Random();

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        Entity wither;
        if (e.getEntity() instanceof WitherSkull && e.getEntity().getShooter() != null && (wither = (Entity)e.getEntity().getShooter()).hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(wither.getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onMiscUpdate((Event)e);
        }
    }

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() == null) {
            return;
        }
        if (!(event.getEntity().getShooter() instanceof Entity)) {
            return;
        }
        Entity either = (Entity)event.getEntity().getShooter();
        if (either.hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(either.getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onMiscUpdate((Event)event);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPotionSplash(PotionSplashEvent e) {
        ThrownPotion potion = e.getEntity();
        for (Entity affected : e.getAffectedEntities()) {
            Boss boss;
            if (!affected.hasMetadata("boss") || !((boss = Boss.bosses.get(affected.getUniqueId())) instanceof GuardBoss) && !(boss instanceof MiniBoss)) continue;
            e.setIntensity((LivingEntity)affected, 0.0);
        }
        block1: for (PotionEffect effect : potion.getEffects()) {
            Boss boss;
            if (effect.getType().equals((Object)PotionEffectType.HARM)) {
                for (Entity ent : e.getEntity().getNearbyEntities(5.0, 5.0, 5.0)) {
                    if (!ent.hasMetadata("boss") || (boss = Boss.bosses.get(ent.getUniqueId())) == null || !(boss instanceof Yijki) || boss.getPhase() != Phase.FOUR) continue;
                    boss.getEntity().damage(25.0);
                    if (!(potion.getShooter() instanceof Player)) continue block1;
                    Player thrower = (Player)potion.getShooter();
                    boss.getDamageTracker().trackDamage((Entity)thrower, 25);
                    continue block1;
                }
                continue;
            }
            if (!effect.getType().getName().equals(PotionEffectType.INCREASE_DAMAGE.getName())) continue;
            for (Entity affected : e.getAffectedEntities()) {
                if (e.getIntensity((LivingEntity)affected) == 0.0 || affected == null || !affected.hasMetadata("boss") || (boss = Boss.bosses.get(affected.getUniqueId())) == null || !(boss instanceof UndeadAssassin)) continue;
                e.setIntensity((LivingEntity)affected, 0.0);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity().hasMetadata("boss_block")) {
            event.getEntity().remove();
            event.setCancelled(true);
        } else if (event.getEntity().hasMetadata("chainReactionBlock")) {
            UUID uuid = (UUID)((MetadataValue)event.getEntity().getMetadata("chainReactionBlock").get(0)).value();
            Boss boss = Boss.bosses.get(uuid);
            if (boss == null) {
                return;
            }
            boss.onMiscUpdate((Event)event);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
    public void onWitherSkullLand(ExplosionPrimeEvent event) {
        Entity wither;
        WitherSkull skull;
        if (event.getEntity() instanceof WitherSkull && (skull = (WitherSkull)event.getEntity()).getShooter() instanceof Entity && (wither = (Entity)skull.getShooter()).hasMetadata("boss") && BossUtils.isWarzone(skull.getLocation()) && !WorldGuardUtils.getRegionName(skull.getLocation()).startsWith("spawn_") && this.random.nextInt(10) <= 1) {
            int bossId = -1;
            Boss boss = Boss.bosses.get(wither.getUniqueId());
            if (boss != null) {
                bossId = boss.getEntity().getEntityId();
            }
            event.setCancelled(true);
        }
    }
}

