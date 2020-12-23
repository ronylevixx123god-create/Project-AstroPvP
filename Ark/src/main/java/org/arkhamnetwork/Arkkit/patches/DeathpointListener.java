/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

final class DeathpointListener
        implements Listener {
    DeathpointListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Deathpoint.death_locations.put(e.getEntity().getUniqueId(), e.getEntity().getLocation());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().startsWith("/back") || e.getMessage().toLowerCase().startsWith("/return")) {
            e.setCancelled(true);
            e.getPlayer().performCommand("deathpoint");
        }
    }
}

