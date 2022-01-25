package net.astropvp.core.stats;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = e.getEntity().getKiller();
        StatsManager.getInstance().getStatsPlayer(killer).kill();
        StatsManager.getInstance().getStatsPlayer(killed).death();
    }
}
