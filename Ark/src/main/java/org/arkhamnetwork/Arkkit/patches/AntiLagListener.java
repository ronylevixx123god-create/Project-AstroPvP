/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.world.ChunkLoadEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Chunk;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

final class AntiLagListener
implements Listener {
    AntiLagListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onChunkLoad(ChunkLoadEvent e) {
        for (Entity entity : (Entity[])e.getChunk().getEntities().clone()) {
            if (!(entity instanceof Creeper)) continue;
            entity.remove();
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onIronGolemSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.CREEPER && this.getEntityTypeCount(e.getLocation().getChunk(), EntityType.CREEPER) > 5) {
            e.setCancelled(true);
        }
    }

    private int getEntityTypeCount(Chunk c, EntityType et) {
        int x = 0;
        for (Entity ent : c.getEntities()) {
            if (ent.getType() != et) continue;
            ++x;
        }
        return x;
    }
}

