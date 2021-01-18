/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityCombustEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTargetLivingEntityEvent
 *  org.bukkit.event.entity.SlimeSplitEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.listeners;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class MinionListener
implements Listener {
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            Boss boss = BossUtils.getBossFromMinion(event.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate(event.getEntity(), event);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            Boss boss = BossUtils.getBossFromMinion(event.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate(event.getEntity(), event);
            if (event.isCancelled()) {
                event.setTarget(null);
            }
        }
    }

    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        Boss boss;
        if (event.getEntity().hasMetadata("minion") && (boss = BossUtils.getBossFromMinion(event.getEntity())) != null) {
            boss.onMinionUpdate(event.getEntity(), event);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onEntityDamageByMinion(EntityDamageByEntityEvent e) {
        Boss boss;
        Entity shooter;
        Projectile proj;
        if (e.getDamager().hasMetadata("minion")) {
            Boss boss2 = BossUtils.getBossFromMinion(e.getDamager());
            if (boss2 == null) {
                return;
            }
            boss2.onMinionUpdate(e.getDamager(), (Event)e);
        } else if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("do_not_clear")) {
            if (CosmicBosses.isMcMMOEnabled()) {
                e.getEntity().setMetadata("mcMMO: Spawned Entity", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                if (e.getDamager() instanceof Player || e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) {
                    Player player = e.getDamager() instanceof Projectile ? (Player)((Projectile)e.getDamager()).getShooter() : (Player)e.getDamager();
                    player.setMetadata("no_exp_gain", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                }
            }
            if (e.getEntity().hasMetadata("minion")) {
                Boss boss3 = BossUtils.getBossFromMinion(e.getEntity());
                if (boss3 == null) {
                    return;
                }
                boss3.onMinionUpdate(e.getEntity(), (Event)e);
            }
        } else if (e.getDamager() instanceof Projectile && (proj = (Projectile)e.getDamager()).getShooter() != null && proj.getShooter() instanceof Entity && (shooter = (Entity)proj.getShooter()).hasMetadata("minion") && (boss = BossUtils.getBossFromMinion(shooter)) != null) {
            boss.onMinionUpdate(shooter, (Event)e);
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        if (e.getEntity().hasMetadata("no_slime_split")) {
            e.setCancelled(true);
            e.setCount(0);
        }
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onMinionDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("no_drops")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            Boss boss = BossUtils.getBossFromMinion((Entity)e.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate((Entity)e.getEntity(), (Event)e);
            boss.removeMinion((Entity)e.getEntity());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onMinionDeathMonitor(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("no_drops")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }
}

