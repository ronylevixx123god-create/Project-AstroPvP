/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.utils.ExpUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

final class XPLossListener
        implements Listener {
    XPLossListener() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().startsWith("dungeon_")) {
            return;
        }
        int expToDrop = (int) Math.round((double) ExpUtils.getTotalExperience(e.getEntity()) * 0.9);
        if (expToDrop >= 1) {
            ItemStack expBottle = ExpBottles.getXpBottle(expToDrop, e.getEntity().getName());
            ExpUtils.setTotalExperience(e.getEntity(), 0);
            e.getDrops().add(expBottle);
        }
        e.setDroppedExp(0);
    }
}

