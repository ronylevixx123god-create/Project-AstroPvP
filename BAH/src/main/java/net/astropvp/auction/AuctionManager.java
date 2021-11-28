package net.astropvp.auction;

import net.astropvp.BlackMarketAuction;
import net.astropvp.util.ConfigUtils;
import net.astropvp.util.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.cosmicpvp.cosmicutils.utils.CC;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuctionManager
{
    private static AuctionManager instance;
    private Auction currentAuction;
    private List<PastAuction> pastAuctions;
    List<ItemStack> possibleItems;
    private long lastAuction;

    public AuctionManager() {
        AuctionManager.instance = this;
        this.pastAuctions = new ArrayList<>();
        List<String> pastAuctions = BlackMarketAuction.get().getConfig().contains("past-auctions") ? BlackMarketAuction.get().getConfig().getStringList("past-auctions") : new ArrayList<>();
        pastAuctions.forEach(auction -> this.pastAuctions.add(PastAuction.fromString(auction)));
        List<String> possibleItems = BlackMarketAuction.get().getConfig().getStringList("possible-items");
        possibleItems.forEach(item -> this.possibleItems.add(ConfigUtils.getItem(item)));
        this.lastAuction = BlackMarketAuction.get().getConfig().getLong("last-auction");
    }
    
    public void startRandomAuction() {
        ItemStack rewardItem = BlackMarketAuction.get().getNextAuctionItem();
        if (rewardItem != null) {
            rewardItem = rewardItem.clone();
            BlackMarketAuction.get().setNextAuctionItem(null);
        }
        else {
            rewardItem = this.possibleItems.get(new Random().nextInt(this.possibleItems.size()));
        }
        final Auction auction = new Auction(rewardItem, 100000);
        this.setCurrentAuction(auction);
        final ItemStack item = rewardItem;
        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null) {
            displayName = WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
        }
        Utils.sendCenteredMessage("");
        Utils.sendCenteredMessage(CC.translate("&f&l* &b&lBlack Market Auction &f&l*"));
        Utils.sendCenteredMessage(CC.translate("&b&lItem: &f&n" + displayName));
        Utils.sendCenteredMessage(CC.translate("&b&lTime: &74m 59s"));
        Utils.sendCenteredMessage(CC.translate("&7Use &b/bah &7to view and place bids."));
        Utils.sendCenteredMessage("");
        new BukkitRunnable() {
            public void run() {
                if (auction.endsAt <= System.currentTimeMillis()) {
                    auction.onEnd();
                    this.cancel();
                }
            }
        }.runTaskTimer(BlackMarketAuction.get(), 0L, 20L);
    }
    
    public Auction getCurrentAuction() {
        return this.currentAuction;
    }
    
    public void setCurrentAuction(final Auction currentAuction) {
        this.currentAuction = currentAuction;
    }
    
    public static AuctionManager get() {
        return AuctionManager.instance;
    }

    public boolean addPossibleItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return false;
        this.possibleItems.add(item);
        return true;
    }

    public boolean removePossibleItem(ItemStack item) {
        if(item == null || item.getType() == Material.AIR)
            return false;
        if(!this.possibleItems.contains(item))
            return false;
        this.possibleItems.remove(item);
        return true;
    }

    public void addPastAuction(PastAuction auction) {
        this.pastAuctions.add(auction);
    }
}
