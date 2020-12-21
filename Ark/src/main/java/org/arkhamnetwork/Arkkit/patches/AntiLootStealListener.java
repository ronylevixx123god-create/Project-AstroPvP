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
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiLootSteal;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class AntiLootStealListener
implements Listener {
    private int pickupDelayInMiliseconds = AntiLootSteal.itemPickUpDelayInSeconds * 1000;

    AntiLootStealListener() {
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onItemPickup(PlayerPickupItemEvent event) {
        Player player;
        if (AntiLootSteal.disabledWorlds.contains(event.getPlayer().getWorld().getName())) {
            return;
        }
        if (event.getItem().hasMetadata("antiLoot_uuid") && !(player = event.getPlayer()).hasPermission("anti.loot.bypass")) {
            UUID killerUUID = UUID.fromString(((MetadataValue)event.getItem().getMetadata("antiLoot_uuid").get(0)).asString());
            long timeDifference = System.currentTimeMillis() - ((MetadataValue)event.getItem().getMetadata("antiLoot_time").get(0)).asLong();
            if (player.getUniqueId().equals(killerUUID)) {
                return;
            }
            if (timeDifference >= (long)this.pickupDelayInMiliseconds) {
                return;
            }
            event.setCancelled(true);
            if (!player.hasMetadata("antiLoot_nextWarning") || ((MetadataValue)player.getMetadata("antiLoot_nextWarning").get(0)).asLong() < System.currentTimeMillis()) {
                player.setMetadata("antiLoot_nextWarning", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + 10000L)));
                player.sendMessage(CC.RedB + "(LOOT) " + CC.Red + "You cannot loot this item for " + ((long)this.pickupDelayInMiliseconds - timeDifference) / 1000L + "s!");
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            if (AntiLootSteal.disabledWorlds.contains(event.getEntity().getWorld().getName())) {
                return;
            }
            Player killer = event.getEntity().getPlayer().getKiller();
            Player player = event.getEntity().getPlayer();
            for (ItemStack stack : event.getDrops()) {
                Item entity = player.getWorld().dropItemNaturally(player.getLocation(), stack);
                long time = System.currentTimeMillis();
                entity.setMetadata("antiLoot_uuid", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)killer.getUniqueId()));
                entity.setMetadata("antiLoot_time", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)time));
            }
            event.getDrops().clear();
        }
    }
}

