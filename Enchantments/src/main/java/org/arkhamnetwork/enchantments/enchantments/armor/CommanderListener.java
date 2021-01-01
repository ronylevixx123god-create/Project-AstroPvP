package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.event.player.*;
import org.bukkit.event.*;

final class CommanderListener implements Listener
{
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent e) {
        Commander.commanders.remove(e.getPlayer().getName());
    }
}
