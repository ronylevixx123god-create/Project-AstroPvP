package org.arkhamnetwork.enchantments.enchantments.swords;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class SilenceListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getFinalDamage() > 0.0 && e.getEntity() instanceof Player && e.getEntity().hasMetadata("noDefenseProcs")) {
            e.setDamage(e.getFinalDamage() * 1.05);
        }
    }
}
