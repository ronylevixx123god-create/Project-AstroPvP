package me.astropvp.listeners;

import me.astropvp.objects.skins.PortablePicnicSkin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PortablePicnicListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if(PortablePicnicSkin.picnicPlayers.contains(e.getPlayer())) {
            PortablePicnicSkin.picnicPlayers.remove(e.getPlayer());
        }
    }
}
