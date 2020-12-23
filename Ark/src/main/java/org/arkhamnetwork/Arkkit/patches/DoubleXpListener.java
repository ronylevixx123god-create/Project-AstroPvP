/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

final class DoubleXpListener
        implements Listener {
    DoubleXpListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        e.setDroppedExp((int) Math.round((double) e.getDroppedExp() * DoubleXp.xpMod));
    }
}

