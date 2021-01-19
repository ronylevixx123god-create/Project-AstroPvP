package me.ifamasssxd.cosmicbosses.listeners;

import me.ifamasssxd.cosmicbosses.utils.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;

public class MinionListener implements Listener
{
    @EventHandler
    public void onEntityTarget(final EntityTargetEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            final Boss boss = BossUtils.getBossFromMinion(event.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate(event.getEntity(), (Event)event);
        }
    }
    
    @EventHandler
    public void onEntityTarget(final EntityTargetLivingEntityEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            final Boss boss = BossUtils.getBossFromMinion(event.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate(event.getEntity(), (Event)event);
            if (event.isCancelled()) {
                event.setTarget((Entity)null);
            }
        }
    }
    
    @EventHandler
    public void onEntityChangeBlockEvent(final EntityChangeBlockEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            final Boss boss = BossUtils.getBossFromMinion(event.getEntity());
            if (boss != null) {
                boss.onMinionUpdate(event.getEntity(), (Event)event);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByMinion(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata("minion")) {
            final Boss boss = BossUtils.getBossFromMinion(e.getDamager());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate(e.getDamager(), (Event)e);
        }
        else if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("do_not_clear")) {
            if (CosmicBosses.isMcMMOEnabled()) {
                e.getEntity().setMetadata("mcMMO: Spawned Entity", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                if (e.getDamager() instanceof Player || (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player)) {
                    Player player;
                    if (e.getDamager() instanceof Projectile) {
                        player = (Player)((Projectile)e.getDamager()).getShooter();
                    }
                    else {
                        player = (Player)e.getDamager();
                    }
                    player.setMetadata("no_exp_gain", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                }
            }
            if (e.getEntity().hasMetadata("minion")) {
                final Boss boss = BossUtils.getBossFromMinion(e.getEntity());
                if (boss == null) {
                    return;
                }
                boss.onMinionUpdate(e.getEntity(), (Event)e);
            }
        }
        else if (e.getDamager() instanceof Projectile) {
            final Projectile proj = (Projectile)e.getDamager();
            if (proj.getShooter() != null && proj.getShooter() instanceof Entity) {
                final Entity shooter = (Entity)proj.getShooter();
                if (shooter.hasMetadata("minion")) {
                    final Boss boss2 = BossUtils.getBossFromMinion(shooter);
                    if (boss2 != null) {
                        boss2.onMinionUpdate(shooter, (Event)e);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onSlimeSplit(final SlimeSplitEvent e) {
        if (e.getEntity().hasMetadata("no_slime_split")) {
            e.setCancelled(true);
            e.setCount(0);
        }
    }
    
    @EventHandler
    public void onEntityCombust(final EntityCombustEvent event) {
        if (event.getEntity().hasMetadata("minion")) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMinionDeath(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("no_drops")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            final Boss boss = BossUtils.getBossFromMinion((Entity)e.getEntity());
            if (boss == null) {
                return;
            }
            boss.onMinionUpdate((Entity)e.getEntity(), (Event)e);
            boss.removeMinion((Entity)e.getEntity());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMinionDeathMonitor(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("minion") || e.getEntity().hasMetadata("no_drops")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }
}
