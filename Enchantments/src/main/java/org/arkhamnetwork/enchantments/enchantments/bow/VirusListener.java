package org.arkhamnetwork.enchantments.enchantments.bow;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

class VirusListener implements Listener {
    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON)) {
            final Player p = (Player) e.getEntity();
            if (p.hasMetadata("virusEnchantmentExpire") && System.currentTimeMillis() < p.getMetadata("virusEnchantmentExpire").get(0).asLong()) {
                final int level = p.getMetadata("virusEnchantmentLevel").get(0).asInt();
                e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (level + 1));
            }
        }
    }
}
