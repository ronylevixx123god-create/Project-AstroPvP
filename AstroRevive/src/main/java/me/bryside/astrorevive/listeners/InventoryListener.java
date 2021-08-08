package me.bryside.astrorevive.listeners;

import me.bryside.astrorevive.inventories.ScrollerInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player p = (Player) event.getWhoClicked();


        if (!ScrollerInventory.users.containsKey(p.getUniqueId()))
            return;
        ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getItemMeta() == null)
            return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)) {
            event.setCancelled(true);

            if (inv.currpage >= inv.pages.size() - 1) {
                return;
            }

            inv.currpage++;
            p.openInventory(inv.pages.get(inv.currpage));

        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.previousPageName)) {
            event.setCancelled(true);

            if (inv.currpage > 0) {

                inv.currpage--;
                p.openInventory(inv.pages.get(inv.currpage));
            }
        }
    }



}
