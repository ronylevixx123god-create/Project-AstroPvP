package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

final class DetonateListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onTagBlockFace(final PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (event.getBlockFace() == null) {
            return;
        }
        if (event.getItem() == null || event.getMaterial() == Material.AIR) {
            return;
        }
        if (!EnchantmentAPI.itemHasEnchantment(event.getItem(), "Detonate")) {
            return;
        }
        Detonate.updateSavedBlockFace(event.getPlayer(), event.getBlockFace());
    }
}
