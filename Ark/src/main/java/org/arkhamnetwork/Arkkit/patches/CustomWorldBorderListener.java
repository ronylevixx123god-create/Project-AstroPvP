/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.wimbli.WorldBorder.WorldBorder
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityTNTMoveEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.wimbli.WorldBorder.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTNTMoveEvent;

final class CustomWorldBorderListener
implements Listener {
    CustomWorldBorderListener() {
    }

    @EventHandler
    public void onEntityTNTMove(EntityTNTMoveEvent e) {
        if (WorldBorder.plugin.getWorldBorder(e.getWorld().getName()) != null && !WorldBorder.plugin.getWorldBorder(e.getWorld().getName()).insideBorder(e.getX(), e.getZ())) {
            e.setCancelled(true);
        }
    }
}

