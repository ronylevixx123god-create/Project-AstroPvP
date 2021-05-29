package me.astropvp.astrospawners.listeners;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataOnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        //PlayerDataManager manager = AstroSpawners.getPlayerDataManager();
        AstroSpawners.getInstance().getManager().createPlayerDataAsync(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        //PlayerDataManager manager = AstroSpawners.getPlayerDataManager();
        PlayerData data = AstroSpawners.getInstance().getManager().getPlayerData(e.getPlayer());
        AstroSpawners.getInstance().getManager().savePlayerDataAsync(data);

    }
}
