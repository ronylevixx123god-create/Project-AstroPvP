package me.astropvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
    @EventHandler
    public void onTournamentInventoryClick(InventoryClickEvent e) {
        if((!(e.getWhoClicked() instanceof Player) ||(!(e.getInventory().getTitle().equals("Tournaments")))))
            return;
        e.setCancelled(true);
        //if slot is 1, open leaderboards etc. etc.
    }
}
