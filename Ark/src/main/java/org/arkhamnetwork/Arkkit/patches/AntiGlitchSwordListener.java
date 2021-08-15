/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

final class AntiGlitchSwordListener
        implements Listener {
    AntiGlitchSwordListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            return;
        }
        if (e.getEntity() instanceof Player && e.getDamage() >= 30.0) {
            Player p = (Player) e.getEntity();
            e.setDamage(30);
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Entity eAttacker = ((EntityDamageByEntityEvent) e).getDamager();
                if (eAttacker instanceof Player) {
                    Player pAttacker = (Player) eAttacker;
                    Bukkit.getLogger().info("[CombatMonitor] Player " + pAttacker.getName() + " has dealt " + e.getDamage() + " damage to " + p.getName() + " (silenced: " + p.hasMetadata("noDefenseProcs") + ") with " + pAttacker.getItemInHand() + " at " + p.getLocation());
                }
            } else {
                Bukkit.getLogger().info("[CombatMonitor] Player " + p.getName() + " has taken " + e.getDamage() + " damage from " + e.getCause().name() + " at " + p.getLocation());
            }
        }
    }
}

