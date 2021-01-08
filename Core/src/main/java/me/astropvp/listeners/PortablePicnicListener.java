/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package me.astropvp.listeners;

import me.astropvp.objects.skins.PortablePicnicSkin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PortablePicnicListener
implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (PortablePicnicSkin.picnicPlayers.contains((Object)e.getPlayer())) {
            PortablePicnicSkin.picnicPlayers.remove((Object)e.getPlayer());
        }
    }
}

