/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

final class CactusListener
implements Listener {
    CactusListener() {
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Cactus.cactus.remove(e.getPlayer().getName());
    }
}

