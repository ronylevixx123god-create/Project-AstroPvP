/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class PhoenixListener
implements Listener {
    Phoenix enchantment;

    protected PhoenixListener(Phoenix enchantment) {
        this.enchantment = enchantment;
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        Player p;
        if (e.getEntity() instanceof Player && e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && Phoenix.hasPhoenixEquipped(p = (Player)e.getEntity())) {
            this.enchantment.applyDefenseEffect((LivingEntity)p, null, Phoenix.phoenixEquipped.get(p.getUniqueId()), e);
        }
    }
}

