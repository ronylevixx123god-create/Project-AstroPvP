package me.astropvp.listeners;

import me.astropvp.CosmicTournamentsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if(CosmicTournamentsPlugin.getInstance().getPlayerDataManager().hasLoadedData(player)) {
            CosmicTournamentsPlugin.getInstance().getPlayerDataManager().getData(player).unloadData(false);
        }
    }
}
