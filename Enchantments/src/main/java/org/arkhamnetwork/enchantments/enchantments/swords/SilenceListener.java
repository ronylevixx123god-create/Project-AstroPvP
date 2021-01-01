package org.arkhamnetwork.enchantments.enchantments.swords;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

final class SilenceListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getFinalDamage() > 0.0 && e.getEntity() instanceof Player && e.getEntity().hasMetadata("noDefenseProcs")) {
            e.setDamage(e.getFinalDamage() * 1.05);
        }
    }
}
