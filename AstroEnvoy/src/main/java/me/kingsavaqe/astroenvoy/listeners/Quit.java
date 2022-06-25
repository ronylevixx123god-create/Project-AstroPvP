package me.kingsavaqe.astroenvoy.listeners;


import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    private PlayerDataManager playerDataManager;

    public Quit(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (this.playerDataManager.hasPlayerData(player)) {
            this.playerDataManager.savePlayerDataAsync(this.playerDataManager.getPlayerData(player));
            this.playerDataManager.removePlayerData(player);
        }
    }
}
