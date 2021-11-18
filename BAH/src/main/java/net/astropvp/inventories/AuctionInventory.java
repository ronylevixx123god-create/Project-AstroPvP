package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import net.astropvp.BlackAuctionHouse;
import net.astropvp.struct.Auction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AuctionInventory {
    public static Inventory get() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Black Market Auction");
        inventory.setItem(11, getCurrentItem());
        inventory.setItem(17, getInformationBook());
        for(int i = 0; i < 27; i++) {
            if(i == 11 || i == 17)
                continue;
            inventory.setItem(i, getFillerItem());
        }
        return inventory;
    }

    private static ItemStack getInformationBook() {
        ItemStack item = new ItemStack(Material.BOOK);

        return item;
    }

    private static ItemStack getCurrentItem() {
        Auction auction = BlackAuctionHouse.getInstance().getAuctionManager().getCurrentAuction();
        ItemStack item = BlackAuctionHouse.getInstance().getAuctionManager().getCurrentAuction().getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(CC.translate("&7&m-----------------------"));
        lore.add(CC.translate("&b&lCLICK &7item to bid &a&l$&a" + NumberUtils.formatMoney(auction.getBid() + auction.getIncrement()) + "!"));
        lore.add("");
        lore.add(CC.translate("&bCurrent Bid: &f&l$&f" + NumberUtils.formatMoney(auction.getBid())));
        lore.add(CC.translate("&bHighest Bidder: " + ((auction.getTopBidder() == null) ? "&cNone" : ("&f" + Bukkit.getPlayer(auction.getTopBidder()).getName()))));
        lore.add(CC.translate("&bBid Increment: &f&l$&f" + NumberUtils.formatMoney(auction.getIncrement())));
        lore.add(CC.translate("&bBidding ends in: &f" + TimeUtils.formatFutureTime(auction.getEndTime())));
        lore.add(CC.translate("&7&m-----------------------"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack getFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.Black);
        item.setItemMeta(meta);
        return item;
    }
}
