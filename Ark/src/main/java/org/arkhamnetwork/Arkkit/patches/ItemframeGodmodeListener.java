/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.ItemFrame
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.hanging.HangingBreakEvent
 *  org.bukkit.event.hanging.HangingBreakEvent$RemoveCause
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

final class ItemframeGodmodeListener
        implements Listener {
    ItemframeGodmodeListener() {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
                return;
            }
            e.setCancelled(true);
            e.setDamage(0.0);
        }
    }

    @EventHandler
    public void onHangingEntityDestroy(HangingBreakEvent e) {
        if (e.getCause() != HangingBreakEvent.RemoveCause.ENTITY) {
            e.setCancelled(true);
            return;
        }
    }
}

