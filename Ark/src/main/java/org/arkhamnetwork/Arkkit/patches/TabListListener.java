/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.patches.TabList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

final class TabListListener
implements Listener {
    TabListListener() {
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        TabList.sendTabList(e.getPlayer());
    }
}

