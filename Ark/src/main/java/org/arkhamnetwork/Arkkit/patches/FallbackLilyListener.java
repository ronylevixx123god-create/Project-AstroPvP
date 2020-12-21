/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexServerSync.libs.GameServerState
 *  com.compilex.CompilexServerSync.libs.ServerStatusChangeEvent
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.ServerRemoveEvent
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexServerSync.libs.GameServerState;
import com.compilex.CompilexServerSync.libs.ServerStatusChangeEvent;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.ServerRemoveEvent;
import org.arkhamnetwork.Arkkit.patches.FallbackServers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

class FallbackLilyListener
implements Listener {
    FallbackLilyListener() {
    }

    @EventListener
    public void onServerDisconnect(ServerRemoveEvent event) {
        if (FallbackServers.ONLINE_SERVERS.remove(event.getServer())) {
            // empty if block
        }
    }

    @EventHandler
    public void onServerStatusChange(ServerStatusChangeEvent event) {
        if (FallbackServers.SERVERS.contains(event.getServerName())) {
            boolean onlineList = FallbackServers.ONLINE_SERVERS.contains(event.getServerName());
            if (event.getNewState() == GameServerState.ONLINE && !onlineList) {
                FallbackServers.ONLINE_SERVERS.add(event.getServerName());
            } else if (onlineList && event.getNewState() != GameServerState.ONLINE) {
                FallbackServers.ONLINE_SERVERS.remove(event.getServerName());
            }
        }
    }
}

