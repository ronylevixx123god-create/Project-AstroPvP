package org.arkhamnetwork.enchantments.enchantments.swords;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

final class RageListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && (e.getEntity().hasMetadata("pvp_rageHitCombo") || e.getEntity().hasMetadata("pve_rageHitCombo"))) {
            e.getEntity().removeMetadata("pvp_rageHitCombo", (Plugin)Enchantments.getInstance());
            e.getEntity().removeMetadata("pve_rageHitCombo", (Plugin)Enchantments.getInstance());
        }
    }
}
