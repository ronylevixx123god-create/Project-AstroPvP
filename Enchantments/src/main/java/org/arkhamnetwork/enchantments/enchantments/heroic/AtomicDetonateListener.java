package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.bukkit.event.player.*;
import org.bukkit.*;
import com.rit.sucy.*;
import org.bukkit.event.*;

final class AtomicDetonateListener implements Listener
{
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
        if (!EnchantmentAPI.itemHasEnchantment(event.getItem(), "Atomic Detonate")) {
            return;
        }
        HeroicDetonate.updateSavedBlockFace(event.getPlayer(), event.getBlockFace());
    }
}
