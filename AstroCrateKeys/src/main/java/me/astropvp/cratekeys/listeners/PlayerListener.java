package me.astropvp.cratekeys.listeners;

import me.astropvp.cratekeys.filter.FilterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        FilterManager.getInstance().unregisterFilterPlayer(player);
    }
}
