/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.gmail.nossr50.mcMMO
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.mcMMO;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

final class NoMobMcmmoListener
        implements Listener {
    NoMobMcmmoListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        e.getEntity().setMetadata("mcMMO: Spawned Entity", mcMMO.metadataValue);
    }
}

