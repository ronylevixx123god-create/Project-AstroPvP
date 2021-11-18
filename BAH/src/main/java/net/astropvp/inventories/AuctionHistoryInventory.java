package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import net.astropvp.BlackAuctionHouse;
import net.astropvp.struct.PastAuction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AuctionHistoryInventory {
    public static Inventory get(int page) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Black Market Auction History - Page " + page);
        boolean first = page == 1;
        boolean last = Math.ceil((double) (BlackAuctionHouse.getInstance().getAuctionManager().getPastAuctions().size() / 52) + 2) == page;
        int taken = 0;
        if(!first) {
            inventory.setItem(45, getBackButton());
            taken++;
        }
        if(!last) {
            inventory.setItem(53, getNextButton());
            taken++;
        }
        int min = (page * 52) - (54 - taken);
        for(int i = 0; i < 53; i++) {
            if(inventory.getItem(i) != null || inventory.getItem(i).getType() == Material.AIR)
                continue;
            if(min + 1 >= BlackAuctionHouse.getInstance().getAuctionManager().getPastAuctions().size())
                continue;
            PastAuction past = BlackAuctionHouse.getInstance().getAuctionManager().getPastAuctions().get(min);
            inventory.setItem(i, getPastItem(past));
            min++;
        }
        return inventory;
    }

    private static ItemStack getBackButton() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.RedB + "Previous Page");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Click to view the last page.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getNextButton() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.GreenB + "Next Page");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Click to view the next page.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean isBackButton(ItemStack item) {
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta())
            return false;
        return item.getItemMeta().getDisplayName().equals(CC.RedB + "Previous Page");
    }

    public static boolean isNextButton(ItemStack item) {
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta())
            return false;
        return item.getItemMeta().getDisplayName().equals(CC.GreenB + "Next Page");
    }

    private static ItemStack getPastItem(PastAuction auction) {
        ItemStack item = auction.getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(CC.Gold + "Winning Bidder: " + CC.Yellow + auction.getPlayerName());
        lore.add(CC.Gold + "Winning Bid: " + CC.Yellow + NumberUtils.formatMoney(auction.getBid()));
        lore.add(CC.Gold + "Item Sold: " + CC.Yellow + "???");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
