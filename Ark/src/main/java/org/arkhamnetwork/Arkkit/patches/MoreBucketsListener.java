/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

final class MoreBucketsListener
        implements Listener {
    MoreBucketsListener() {
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerEmptyBucket(PlayerBucketEmptyEvent e) {
        Bukkit.getLogger().info("bucket => " + e.getItemStack());
        ItemStack bucket = e.getPlayer().getItemInHand();
        Bukkit.getLogger().info("ibucket => " + bucket);
        if (MoreBuckets.isMultiBucket(bucket)) {
            int uses = MoreBuckets.getBucketUses(bucket);
            if (uses > 1) {
                Bukkit.getLogger().info("bucket uses => " + uses);
                ItemStack i = e.getItemStack().clone();
                MoreBuckets.setBucketUses(i, uses - 1);
                i.setAmount(2);
                e.getPlayer().setItemInHand(i);
            } else {
                return;
            }
        }
    }
}

