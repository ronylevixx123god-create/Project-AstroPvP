package net.astropvp.listeners;

import net.astropvp.BlackAuctionHouse;
import net.astropvp.inventories.AuctionHistoryInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AuctionInventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getInventory().getTitle().equals("Black Market Auction")) {
            e.setCancelled(true);
            if(e.getSlot() == 4)
                BlackAuctionHouse.getInstance().getAuctionManager().getCurrentAuction().bid((Player) e.getWhoClicked());
            if(e.getSlot() == 8) {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().openInventory(AuctionHistoryInventory.get(1));
            }
        }
        if(e.getInventory().getTitle().contains("Black Market Auction History")) {
            e.setCancelled(true);
            if(e.getSlot() == 45 && AuctionHistoryInventory.isBackButton(e.getCurrentItem())) {
                e.getWhoClicked().closeInventory();
                String[] s = e.getInventory().getTitle().split("Page ");
                e.getWhoClicked().openInventory(AuctionHistoryInventory.get(Integer.parseInt(s[1]) - 1));
            }
            if(e.getSlot() == 53 && AuctionHistoryInventory.isNextButton(e.getCurrentItem())) {
                e.getWhoClicked().closeInventory();
                String[] s = e.getInventory().getTitle().split("Page ");
                e.getWhoClicked().openInventory(AuctionHistoryInventory.get(Integer.parseInt(s[1]) + 1));
            }
        }
    }
}
