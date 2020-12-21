/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Item
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.AutoEntityClearLegacy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

final class AutoEntityClearListenerLegacy
implements Listener {
    AutoEntityClearListenerLegacy() {
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (AutoEntityClearLegacy.forceClearCommands.contains(event.getMessage())) {
            event.setCancelled(true);
            if (!event.getPlayer().hasPermission(AutoEntityClearLegacy.forceClearCommandPermission)) {
                return;
            }
            AutoEntityClearLegacy.clear();
        }
    }

    @EventHandler
    public void onItemDamageEvent(EntityDamageEvent e) {
        if (e.getEntity() instanceof Item) {
            Item item = (Item)e.getEntity();
            Bukkit.getLogger().info("[Arkkit] Item damage event on " + item.getItemStack().getTypeId());
            if (AutoEntityClearLegacy.dontRemoveItems.contains(item.getItemStack().getTypeId()) && (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName() || item.getItemStack().getType() == Material.MOB_SPAWNER)) {
                Bukkit.getLogger().info("[Arkkit] Blocking damage event for " + item.getItemStack().getTypeId());
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
    }
}

