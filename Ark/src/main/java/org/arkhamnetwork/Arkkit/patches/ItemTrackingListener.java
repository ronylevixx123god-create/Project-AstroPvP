/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerDropItemEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ItemTrackingListener
implements Listener {
    ItemTrackingListener() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (ItemID.hasItemID(e.getItem().getItemStack())) {
            Player p = e.getPlayer();
            String dropper = e.getItem().getDroppedBy() != null ? e.getItem().getDroppedBy() : (e.getItem().hasMetadata("droppedBy") ? ((MetadataValue)e.getItem().getMetadata("droppedBy").get(0)).asString() : "???");
            Bukkit.getLogger().info("[ItemTracking] Player " + p.getName() + " picked up item: " + (Object)e.getItem().getItemStack() + " at " + (Object)e.getItem().getLocation() + " dropped by: " + dropper);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onItemDrop(PlayerDropItemEvent e) {
        if (ItemID.hasItemID(e.getItemDrop().getItemStack())) {
            e.getItemDrop().setMetadata("droppedBy", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)e.getPlayer().getName()));
        }
    }
}

