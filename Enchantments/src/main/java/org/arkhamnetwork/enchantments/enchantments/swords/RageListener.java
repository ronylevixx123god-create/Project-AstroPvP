package org.arkhamnetwork.enchantments.enchantments.swords;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

final class RageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && (e.getEntity().hasMetadata("pvp_rageHitCombo") || e.getEntity().hasMetadata("pve_rageHitCombo"))) {
            e.getEntity().removeMetadata("pvp_rageHitCombo", (Plugin) Enchantments.getInstance());
            e.getEntity().removeMetadata("pve_rageHitCombo", (Plugin) Enchantments.getInstance());
        }
    }
}
