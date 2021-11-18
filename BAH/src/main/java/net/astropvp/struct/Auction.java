package net.astropvp.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import lombok.Getter;
import net.astropvp.BlackAuctionHouse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Auction {
    @Getter
    private ItemStack item;

    @Getter
    private UUID topBidder;

    @Getter
    private long endTime;

    @Getter
    private double bid;

    @Getter
    private double increment;

    @Getter
    private boolean open;

    public Auction(ItemStack item) {
        this.item = item;
        this.increment = BlackAuctionHouse.getInstance().getConfig().getDouble("increment");
        this.topBidder = null;
        this.bid = 0;
        this.endTime = System.currentTimeMillis() + 300000L;
        this.open = true;
    }

    public void close() {
        this.open = false;
        if(this.topBidder != null) {
            Player winner = Bukkit.getPlayer(this.topBidder);
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(CC.translate("&a&l[/bah] &f&l" + winner.getName() + " &ahas won the bid on " + this.item.getItemMeta().getDisplayName() + "&a for &a&l$&a" + NumberUtils.formatMoney(this.bid) + "!"));
            Bukkit.broadcastMessage("");
        }
    }

    public void bid(Player player) {
        if(!this.open)
            return;
        if(!EconomyUtils.hasBalance(player, this.bid + this.increment)) {
            player.sendMessage(CC.translate("&c&l[/bah] &cYou do not have enough money to bid on this item."));
            return;
        }
        if(this.endTime - System.currentTimeMillis() <= 10000L) {
            Bukkit.broadcastMessage(CC.translate("&c&l[/bah] &cA bid of &c&l$&c" + NumberUtils.formatMoney(this.bid) + " has been made at the last moment!"));            Bukkit.broadcastMessage(CC.translate("&7The auction has been extended by 10 seconds."));
            this.endTime += 10000L;
        }
        if(this.topBidder != null) {
            Player outbid = Bukkit.getPlayer(this.topBidder);
            outbid.sendMessage(CC.translate("&c&l[/bah] &cYou have been outbid by " + player.getName() + " for " + this.item.getItemMeta().getDisplayName()));
            EconomyUtils.depositBalance(outbid, this.bid);
        }
        this.bid += increment;
        EconomyUtils.withdrawBalance(player, this.bid);
        this.topBidder = player.getUniqueId();
        this.increment *= 1.5;
        Bukkit.broadcastMessage(CC.translate("&b&l[/bah] &b" + player.getName() + " has bid &b&l$&b" + NumberUtils.formatMoney(this.bid) + " on " + this.item.getItemMeta().getDisplayName() + "&b, the bid increment is now &b&l$&b" + NumberUtils.formatMoney(this.increment) + "&b!"));
        player.sendMessage(CC.translate("&b&l[/bah] &bYou have bid &b&l$&b" + NumberUtils.formatMoney(this.bid) + "&b on " + this.item.getItemMeta().getDisplayName()));
    }


}
