package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

final class PhoenixListener implements Listener
{
    Phoenix enchantment;

    protected PhoenixListener(Phoenix enchantment) {
        this.enchantment = enchantment;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            Player p = (Player)e.getEntity();
            if (Phoenix.hasPhoenixEquipped(p))
                this.enchantment.applyDefenseEffect((LivingEntity)p, null, ((Integer)Phoenix.phoenixEquipped.get(p.getUniqueId())).intValue(), e);
        }
    }
}
