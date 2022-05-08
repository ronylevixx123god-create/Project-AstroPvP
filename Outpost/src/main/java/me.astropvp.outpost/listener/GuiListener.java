package me.astropvp.outpost.listener;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle() != null && CC.strip(e.getClickedInventory().getTitle()).equals("Astro Outposts")) {
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() == Material.NETHER_STALK) {
                    ((Player) e.getWhoClicked()).performCommand("warp outpost-mars");
                    e.getWhoClicked().closeInventory();
                    e.setCancelled(true);

                } else if (e.getCurrentItem().getType() == Material.QUARTZ) {
                    ((Player) e.getWhoClicked()).performCommand("warp outpost-pluto");
                    e.getWhoClicked().closeInventory();
                    e.setCancelled(true);
                }
            }
        }
    }
}