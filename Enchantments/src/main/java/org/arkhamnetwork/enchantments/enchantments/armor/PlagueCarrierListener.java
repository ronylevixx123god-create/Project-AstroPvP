package org.arkhamnetwork.enchantments.enchantments.armor;

import de.inventivegames.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class PlagueCarrierListener implements Listener {
    @EventHandler
    public void onEntityExplode(final EntityExplodeEvent e) throws Exception {
        if (e.getEntity() != null && e.getEntity().hasMetadata("plagueCarrier")) {
            e.setCancelled(true);
            e.setYield(0.0f);
            final int level = e.getEntity().getMetadata("plagueCarrier").get(0).asInt();
            final Location loc = e.getLocation();
            loc.getWorld().playSound(loc, Sound.EXPLODE, 1.0f, 1.0f);
            ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(),  loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 3);

            for (final Entity ent : e.getEntity().getNearbyEntities(5.0, 4.0, 5.0)) {
                if (ent instanceof LivingEntity) {
                    final LivingEntity le = (LivingEntity) ent;
                    le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    if (level >= 3) {
                        le.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    }
                    if (level >= 6) {
                        le.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                    }
                    if (level != 8) {
                        continue;
                    }
                    le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 2 * 20, (level == 8) ? 2 : ((level >= 4) ? 1 : 0)));
                }
            }
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("plagueCarrier")) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            e.getEntity().remove();
        }
    }
}
