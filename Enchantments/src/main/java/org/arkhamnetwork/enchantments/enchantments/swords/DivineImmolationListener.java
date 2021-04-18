package org.arkhamnetwork.enchantments.enchantments.swords;

import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class DivineImmolationListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) throws Exception {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getEntity().hasMetadata("divineFire") && e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity() instanceof LivingEntity) {
            final int enchantLevel = e.getEntity().getMetadata("divineFireLevel").get(0).asInt();
            if (System.currentTimeMillis() - e.getEntity().getMetadata("divineFire").get(0).asLong() < (3 + enchantLevel) * 1000) {
                final LivingEntity le = (LivingEntity) e.getEntity();
                if (le instanceof Player) {
                    if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE || ((Player) e.getEntity()).getGameMode() == GameMode.SPECTATOR) {
                        return;
                    }
                }
                le.damage((double)2.0);
                //ParticleEffect.playEffect(ParticleEffect.FLAME, le.getEyeLocation(), 0.15f, 20);
                //ParticleEffect.playEffect(ParticleEffect.LAVA, le.getEyeLocation(), 0.5f, 15);

                if (le instanceof Player) {
                    ((Player) le).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** DIVINE IMMOLATION **");
                    ((Player) le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.6f, 0.8f);
                }


            }
        }
    }
}
