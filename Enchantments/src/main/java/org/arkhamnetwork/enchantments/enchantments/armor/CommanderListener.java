package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

final class CommanderListener implements Listener {
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        Commander.commanders.remove(e.getPlayer().getName());
    }
}
