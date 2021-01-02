package org.arkhamnetwork.enchantments.enchantments.axes;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

final class BleedListener implements Listener {
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        e.getEntity().removeMetadata("ce_bleeding", (Plugin) Enchantments.getInstance());
    }
}
