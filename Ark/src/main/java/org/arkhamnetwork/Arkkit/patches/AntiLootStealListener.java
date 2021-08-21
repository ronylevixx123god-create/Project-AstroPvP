/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.player.PlayerPickupItemEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

final class AntiLootStealListener
        implements Listener {
    private final int pickupDelayInMiliseconds = AntiLootSteal.itemPickUpDelayInSeconds * 1000;

    AntiLootStealListener() {
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage("pickup");
        if (AntiLootSteal.disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            Bukkit.broadcastMessage("returns");
            return;
        }
        Bukkit.broadcastMessage(event.getItem().getMetadata("antiLoot_uuid").get(0).asString());
        if (event.getItem().hasMetadata("antiLoot_uuid") && !(player.isOp())) {
            Bukkit.broadcastMessage("has metadata");
            UUID killerUUID = UUID.fromString(event.getItem().getMetadata("antiLoot_uuid").get(0).asString());
            long timeDifference = System.currentTimeMillis() - event.getItem().getMetadata("antiLoot_time").get(0).asLong();
            Bukkit.broadcastMessage(player.getUniqueId() + " " + killerUUID);
            if (player.getUniqueId().equals(killerUUID)) {
                return;
            }
            if (timeDifference >= (long) this.pickupDelayInMiliseconds) {
                return;
            }
            event.setCancelled(true);
            if (!player.hasMetadata("antiLoot_nextWarning") || player.getMetadata("antiLoot_nextWarning").get(0).asLong() < System.currentTimeMillis()) {
                player.setMetadata("antiLoot_nextWarning", new FixedMetadataValue(Arkkit.get(), System.currentTimeMillis() + 10000L));
                player.sendMessage(CC.RedB + "(LOOT) " + CC.Red + "You cannot loot this item for " + ((long) this.pickupDelayInMiliseconds - timeDifference) / 1000L + "s!");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.broadcastMessage("player death");
        if (event.getEntity().getKiller() instanceof Player) {
            Bukkit.broadcastMessage(event.getEntity().getKiller().getName());
            if (AntiLootSteal.disabledWorlds.contains(event.getEntity().getWorld().getName())) {
                return;
            }
            Player killer = event.getEntity().getPlayer().getKiller();
            Player player = event.getEntity().getPlayer();
            for (ItemStack stack : event.getDrops()) {
                Bukkit.broadcastMessage(stack.toString());
                Item entity = player.getWorld().dropItemNaturally(player.getLocation(), stack);
                long time = System.currentTimeMillis();
                entity.setMetadata("antiLoot_uuid", new FixedMetadataValue(Arkkit.get(), killer.getUniqueId()));
                entity.setMetadata("antiLoot_time", new FixedMetadataValue(Arkkit.get(), time));
                Bukkit.broadcastMessage(entity.getMetadata("antiLoot_uuid").get(0).asString());
                Bukkit.broadcastMessage(entity.getMetadata("antiLoot_time").get(0).asString());

            }
            event.getDrops().clear();
        }
    }
}

