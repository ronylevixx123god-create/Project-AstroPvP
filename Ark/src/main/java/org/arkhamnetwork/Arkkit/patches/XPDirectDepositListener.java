/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.events.EXPDirectDepositEvent;
import org.arkhamnetwork.Arkkit.libs.ExpUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

final class XPDirectDepositListener
implements Listener {
    XPDirectDepositListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity le;
        if (e.getDroppedExp() > 0 && e.getEntity() instanceof LivingEntity && e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && (le = e.getEntity()).getKiller() != null && le.getKiller().isOnline()) {
            int total = ExpUtils.getTotalExperience(le.getKiller());
            EXPDirectDepositEvent event = new EXPDirectDepositEvent(le.getKiller(), e.getDroppedExp(), total);
            Bukkit.getPluginManager().callEvent((Event)event);
            if (event.isCancelled()) {
                return;
            }
            ExpUtils.setTotalExperience(le.getKiller(), total + event.getAmountToGive());
            e.setDroppedExp(0);
        }
    }
}

