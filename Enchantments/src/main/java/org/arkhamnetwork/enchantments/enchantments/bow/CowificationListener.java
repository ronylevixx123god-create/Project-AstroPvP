package org.arkhamnetwork.enchantments.enchantments.bow;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class CowificationListener implements Listener {
    private boolean factions_enabled;

    public CowificationListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("cowification")) {
            e.setCancelled(true);
            e.setDamage(0.0);
            if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
                ((Player) ((EntityDamageByEntityEvent) e).getDamager()).playSound(e.getEntity().getLocation(), Sound.COW_HURT, 1.0f, 0.7f);
                e.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) throws Exception {
        if (e.getEntity().hasMetadata(Cowification.cowificationEncahntmentMetadata)) {
            final Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null) {
                e.getEntity().getPassenger().remove();
            }
            if (WorldGuardUtils.isPvPDisabled(loc)) {
                return;
            }
            loc.getWorld().playSound(loc, Sound.COW_HURT, 1.0f, 0.85f);
            final int radius;
            final int level = radius = e.getEntity().getMetadata(Cowification.cowificationEncahntmentMetadata).get(0).asInt();
            final String enchantment_owner = e.getEntity().hasMetadata(Cowification.cowificationEncahntmentOwnerMetadata) ? e.getEntity().getMetadata(Cowification.cowificationEncahntmentOwnerMetadata).get(0).asString() : null;
            for (final Entity ent : e.getEntity().getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                if (ent instanceof LivingEntity) {
                    if (enchantment_owner != null && ent instanceof Player) {
                        if (((Player) ent).getName().equals(enchantment_owner)) {
                            continue;
                        }
                        if (this.isAlly((Player) ent, Bukkit.getPlayer(enchantment_owner))) {
                            continue;
                        }
                    }
                    if (!(ent instanceof LivingEntity)) {
                        continue;
                    }
                    ((LivingEntity) ent).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25 * level, 1));
                }
            }
            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(), loc, 0.0f, 0.0f, 0.0f, 0.25f, 1);
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
