package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class acenstoralListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().hasMetadata("acenstoralSummoner")) {
        }
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("acenstoralSummoner")) {
            Spirits.spirits.remove(e.getEntity());
            Spirits.ticks.remove(e.getEntity().getEntityId());
            e.getDrops().clear();
            e.getEntity().remove();
        }
        if (e.getEntity().hasMetadata("targetTaskID")) {
            Bukkit.getScheduler().cancelTask(e.getEntity().getMetadata("targetTaskID").get(0).asInt());
        }
    }

    @EventHandler
    public void onEntityTarget(final EntityTargetEvent e) {
        if (e.getTarget() instanceof Player && e.getEntity().hasMetadata("acenstoralSummoner")) {
            e.setTarget((Entity) null);
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(final ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Fireball) {
            if (e.getEntity().getShooter() == null) return;
            if (!(e.getEntity().getShooter() instanceof Entity)) return;
            final Entity shooter = (Entity) e.getEntity().getShooter();
            if (shooter != null && shooter.getType() == EntityType.BLAZE && shooter.hasMetadata("acenstoralSummoner")) {
                e.setCancelled(true);
                ((Blaze) shooter).setTarget((LivingEntity) null);
                final Projectile orig_proj = e.getEntity();
                orig_proj.setBounce(false);
                orig_proj.setMetadata("spiritFireball", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                Enchantments.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                    @Override
                    public void run() {
                        if (!orig_proj.hasMetadata("no_spawn")) {
                            final Projectile FB = ((Blaze) shooter).launchProjectile((Class) SmallFireball.class);
                            FB.setMetadata("no_spawn", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                            FB.setVelocity(orig_proj.getVelocity());
                            FB.setBounce(false);
                            FB.setMetadata("spiritFireball", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                        }
                    }
                }, 10L);
            }
        }
    }

    @EventHandler
    public void onExplosionPrime(final ExplosionPrimeEvent e) {
        if (e.getEntity().hasMetadata("spiritFireball") || e.getEntity().getWorld().getName().equals("world_duels") || e.getEntity().getWorld().getName().equals("world_duels2")) {
            e.setRadius(0.0f);
            e.setFire(false);
        }
    }

    @EventHandler
    public void onBlockIgnite(final BlockIgniteEvent e) {
        if ((e.getIgnitingEntity() != null && e.getIgnitingEntity().hasMetadata("spiritFireball")) || e.getBlock().getWorld().getName().equals("world_duels") || e.getBlock().getWorld().getName().equals("world_duels2")) {
            e.setCancelled(true);
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
