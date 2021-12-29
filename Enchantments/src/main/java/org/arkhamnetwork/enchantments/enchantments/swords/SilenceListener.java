/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

final class SilenceListener
implements Listener {
    SilenceListener() {
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getDamage() > 0.0 && e.getEntity() instanceof Player && e.getEntity().hasMetadata("noDefenseProcs")) {
            e.setDamage(e.getDamage() * 0.75);
        }
    }
}

