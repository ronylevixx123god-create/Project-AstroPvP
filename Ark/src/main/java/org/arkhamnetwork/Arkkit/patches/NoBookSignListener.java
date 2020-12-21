/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerEditBookEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

final class NoBookSignListener
implements Listener {
    NoBookSignListener() {
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent e) {
        if (e.getPlayer().isOp()) {
            return;
        }
        e.setCancelled(true);
        e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Book signing and editing is disabled.");
    }
}

