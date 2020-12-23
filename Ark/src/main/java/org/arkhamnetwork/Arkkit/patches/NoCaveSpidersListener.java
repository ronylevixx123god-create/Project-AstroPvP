/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.EntityType
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

final class NoCaveSpidersListener
        implements Listener {
    NoCaveSpidersListener() {
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.CAVE_SPIDER) {
            e.setCancelled(true);
            e.getLocation().getWorld().spawnEntity(e.getLocation(), EntityType.SPIDER);
        }
    }
}

