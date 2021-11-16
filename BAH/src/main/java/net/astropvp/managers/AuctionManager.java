package net.astropvp.managers;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import lombok.Getter;
import net.astropvp.BlackAuctionHouse;
import net.astropvp.struct.Auction;
import net.astropvp.struct.PastAuction;
import net.astropvp.utils.ConfigUtils;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class AuctionManager {

    private List<ItemStack> possibleItems;

    private List<PastAuction> pastAuctions;

    @Getter
    private Auction currentAuction;

    public AuctionManager() {
        this.currentAuction = null;
        List<String> pastAuctions = BlackAuctionHouse.getInstance().getConfig().getStringList("past-auctions");
        for(String s : pastAuctions) {
            this.pastAuctions.add(PastAuction.fromString(s));
        }
        List<String> possibleItems = BlackAuctionHouse.getInstance().getConfig().getStringList("possible-items");
        for(String s : possibleItems) {
            this.possibleItems.add(ConfigUtils.getItem(s));
        }
    }

    public void startAuction() {
        if(this.currentAuction != null) {
            Bukkit.getLogger().info("Failed to start new auction due to an auction already taking place.");
            return;
        }
        ItemStack item = this.possibleItems.get(new Random().nextInt(this.possibleItems.size()));
        currentAuction = new Auction(item);
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(""));
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&f&l* &b&lBlack Market Auction &f&l*")));
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&b&lItem: &f&n" + item.getItemMeta().getDisplayName())));
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&b&lTime: &74m 59s")));
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&7Use &b/bah &7to view and place bids.")));
        Bukkit.broadcastMessage(StringUtils.getCenteredMessage(""));
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
