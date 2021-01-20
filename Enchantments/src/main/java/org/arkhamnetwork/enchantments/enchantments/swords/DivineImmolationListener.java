package org.arkhamnetwork.enchantments.enchantments.swords;

import de.inventivegames.particle.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) throws Exception {
        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            final int enchantLevel = e.getEntity().getMetadata("divineFireLevel").get(0).asInt();
            if (System.currentTimeMillis() - e.getEntity().getMetadata("divineFire").get(0).asLong() < (4 + enchantLevel) * 1000) {
                e.setCancelled(true);
                final LivingEntity le = (LivingEntity) e.getEntity();
                le.damage((double) Math.min(7, 4 + enchantLevel));
                //ParticleEffect.playEffect(ParticleEffect.FLAME, le.getEyeLocation(), 0.15f, 20);
                //ParticleEffect.playEffect(ParticleEffect.LAVA, le.getEyeLocation(), 0.5f, 15);

                de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayer((Player) le,le.getEyeLocation(),0,0,0,0.15f, 20);
                ParticleEffect.LAVA.sendToPlayer((Player) le,le.getEyeLocation(),0,0,0,0.5f, 15);
                if (le instanceof Player) {
                    ((Player) le).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** DIVINE IMMOLATION **");
                    ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6f, 0.8f);
                }
            }
        }
    }
}
