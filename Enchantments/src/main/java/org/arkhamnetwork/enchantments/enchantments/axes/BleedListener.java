/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

final class BleedListener
implements Listener {
    BleedListener() {
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.getEntity().removeMetadata("ce_bleeding", (Plugin)Enchantments.getInstance());
    }
}

