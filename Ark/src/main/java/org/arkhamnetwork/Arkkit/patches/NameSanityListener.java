/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerLoginEvent
 *  org.bukkit.event.player.PlayerLoginEvent$Result
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

final class NameSanityListener
        implements Listener {
    String usernameRegex = "^[a-zA-Z0-9_]+$";

    NameSanityListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLogin(PlayerLoginEvent e) {
        String name = e.getPlayer().getName();
        if (!name.matches(this.usernameRegex)) {
            e.setKickMessage("Invalid username.");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}

