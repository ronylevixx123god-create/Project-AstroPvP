package me.kingsavaqe.astroenvoy.listeners;


import me.kingsavaqe.astroenvoy.playerdata.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    private PlayerDataManager playerDataManager;

    public Join(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.playerDataManager.createPlayerData(player);
        this.playerDataManager.preparePlayerDataAsync(player);
    }
}
