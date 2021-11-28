// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.auction;

import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import net.astropvp.util.Utils;
import org.bukkit.entity.Player;
import org.apache.commons.lang.WordUtils;
import me.recolance.auction.data.DataHolder;
import me.recolance.auction.auction.UnclaimedListing;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import com.cosmicpvp.cosmicutils.utils.CC;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class Auction
{
    public ItemStack item;
    public long endsAt;
    public double bidIncrement;
    public double currentBid;
    public UUID currentTopBidder;
    public UUID auctionUUID;
    public String topBidderName;
    public static HashMap<Double, Double> incrementThreshold;
    
    public Auction(final ItemStack item, final int bidIncrement) {
        this.item = item;
        this.endsAt = System.currentTimeMillis() + 300000L;
        this.bidIncrement = bidIncrement;
        this.currentBid = 0.0;
        this.currentTopBidder = null;
        this.auctionUUID = UUID.randomUUID();
    }
    
    public void onEnd() {
        if (this.currentTopBidder != null) {
            Utils.sendCenteredMessage("");
            Bukkit.broadcastMessage(CC.translate("&a&l[/bah] &f&l" + this.topBidderName + " &ahas won the bid on " + this.item.getItemMeta().getDisplayName() + "&a for &a&l$&a" + Utils.formatMoney(this.currentBid) + "!"));
            Utils.sendCenteredMessage("");
            DataHolder.addUnclaimedListing(this.currentTopBidder, new UnclaimedListing(UUID.randomUUID(), this.currentTopBidder, this.item, System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1L)));
        }
        else {
            String displayName = this.item.getItemMeta().getDisplayName();
            if (displayName == null) {
                displayName = WordUtils.capitalizeFully(this.item.getType().name().replace("_", " "));
            }
            Utils.sendCenteredMessage("");
            Bukkit.broadcastMessage(CC.translate("&b&l[/bah] No players &bhave won the bid on " + displayName + "&b!"));
            Utils.sendCenteredMessage("");
        }
        AuctionManager.get().setCurrentAuction(null);
    }
    
    public void doBid(final Player player, final double bidAmount) {
        if (!EconomyUtils.hasBalance(player, bidAmount)) {
            player.sendMessage(CC.translate("&c&l[/bah] &cYou do not have enough money to bid on this item."));
            return;
        }
        if (bidAmount <= this.currentBid) {
            player.sendMessage(CC.translate("&c&l(!) &cYour bid has already been outbid!"));
            player.sendMessage(CC.translate("&cYour bid: &7$" + Utils.formatMoney(bidAmount)));
            player.sendMessage(CC.translate("&cCurrent bid: &7$" + Utils.formatMoney(AuctionManager.get().getCurrentAuction().currentBid)));
            return;
        }
        if (this.endsAt - System.currentTimeMillis() <= 10000L) {
            Bukkit.broadcastMessage(CC.translate("&c&l[/bah] &cA bid of &c&l$&c" + Utils.formatMoney(bidAmount) + " has been made at the last moment!"));
            Bukkit.broadcastMessage(CC.translate("&7The auction has been extended by 10 seconds."));
            this.endsAt += 10000L;
        }
        if (this.currentTopBidder != null) {
            Bukkit.getPlayer(this.currentTopBidder).sendMessage(CC.translate("&c&l[/bah] &cYou have been outbid by " + player.getName() + " for " + this.item.getItemMeta().getDisplayName()));
            EconomyUtils.withdrawBalance(Bukkit.getPlayer(this.currentTopBidder), this.currentBid);

        }
        this.currentBid = bidAmount;
        this.currentTopBidder = player.getUniqueId();
        this.topBidderName = player.getName();
        if (Auction.incrementThreshold.containsKey(this.currentBid)) {
            this.bidIncrement = Auction.incrementThreshold.get(this.currentBid);
            Bukkit.broadcastMessage(CC.translate("&b&l[/bah] &b" + player.getName() + " has bid &b&l$&b" + Utils.formatMoney(bidAmount) + " on " + this.item.getItemMeta().getDisplayName() + "&b, the bid increment is now &b&l$&b" + Utils.formatMoney(this.bidIncrement) + "&b!"));
        }
        String displayName = this.item.getItemMeta().getDisplayName();
        if (displayName == null) {
            displayName = WordUtils.capitalizeFully(this.item.getType().name().replace("_", " "));
        }
        player.sendMessage(CC.translate("&b&l[/bah] &bYou have bid &b&l$&b" + Utils.formatMoney(bidAmount) + "&b on " + displayName));
        EconomyUtils.withdrawBalance(player, bidAmount);
    }
    
    static {
        Auction.incrementThreshold = new HashMap<Double, Double>() {
            {
                this.put(1000000.0, 500000.0);
                this.put(5000000.0, 1000000.0);
                this.put(1.0E7, 2500000.0);
                this.put(2.5E7, 5000000.0);
                this.put(1.0E8, 1.0E7);
                this.put(5.0E8, 5.0E7);
                this.put(1.0E9, 2.0E8);
                this.put(5.0E9, 1.0E9);
            }
        };
    }
}
