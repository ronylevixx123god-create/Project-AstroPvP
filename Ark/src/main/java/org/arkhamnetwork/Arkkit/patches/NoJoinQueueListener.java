/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package org.arkhamnetwork.Arkkit.patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.HashMap;

final class NoJoinQueueListener
        implements Listener {
    private final HashMap<String, Long> recent_login_attempts = new HashMap();

    NoJoinQueueListener() {
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerPreLogin_LOWEST(AsyncPlayerPreLoginEvent e) {
        if (NoLoginQueue.no_new_connections) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage("The server is currently loading objects into memory to maximize performance. Please reconnect in a few moments.");
            return;
        }
        if (this.recent_login_attempts.containsKey(e.getUniqueId().toString()) && System.currentTimeMillis() - this.recent_login_attempts.get(e.getUniqueId().toString()) <= 5000L) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage("You are connecting too quickly! Wait a minute, then connect again.");
            return;
        }
        this.recent_login_attempts.put(e.getUniqueId().toString(), System.currentTimeMillis());
    }
}

