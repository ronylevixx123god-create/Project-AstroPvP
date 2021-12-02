package me.venir.astrocollectors.listeners;

import me.venir.astrocollectors.inventories.CollectorInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener  implements Listener {
/*    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals("Chunk Collector") && e.getClickedInventory() == p.getOpenInventory().getTopInventory()) {
            e.setCancelled(true);
            CollectorInventory collectorInventory = (CollectorInventory) e.getClickedInventory().getHolder();
            if (collectorInventory != null) {
                collectorInventory.click(e);
            }
        }
    }*/
}
