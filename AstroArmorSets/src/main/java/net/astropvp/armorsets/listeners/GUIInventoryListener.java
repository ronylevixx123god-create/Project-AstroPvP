package net.astropvp.armorsets.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if(inventory.getName().equals("Armor Sets")) {
            ItemStack item = inventory.getItem(event.getSlot());
            if(item.getType() != Material.COMPASS) event.setCancelled(true);
            else p.closeInventory();
        }
    }

}
