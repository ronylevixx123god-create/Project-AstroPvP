/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;
import org.arkhamnetwork.Arkkit.patches.HackerHub;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

final class HackerHubListener
implements Listener {
    HackerHubListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("essentials.vanish")) {
            User u = HackerHub.ess.getUser(p);
            u.setVanished(true);
            u.setHidden(true);
            p.setAllowFlight(true);
            p.setFlying(true);
        }
    }
}

