/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

final class MaxHeightLogicListener
        implements Listener {
    MaxHeightLogicListener() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.getLocation().getY() >= 255.0 && (e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.CREEPER)) {
            e.setCancelled(true);
            e.setYield(0.0f);
            e.getEntity().remove();
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityBlockChange(EntityChangeBlockEvent e) {
        Material material;
        if (e.getEntity() instanceof FallingBlock && ((material = ((FallingBlock) e.getEntity()).getMaterial()) == Material.SAND || material == Material.GRAVEL || material == Material.ANVIL) && (e.getBlock().getLocation().getY() >= 255.0 || MaxHeightLogic.factions && FactionUtils.isWarZone(e.getBlock().getLocation()))) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }
}

