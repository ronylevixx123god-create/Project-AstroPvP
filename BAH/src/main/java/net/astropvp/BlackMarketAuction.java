// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp;

import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import net.astropvp.auction.Auction;
import net.astropvp.auction.AuctionManager;
import net.astropvp.command.CommandBAH;
import net.astropvp.menu.AuctionMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;

public class BlackMarketAuction extends JavaPlugin
{
    private static BlackMarketAuction instance;
    private AuctionManager auctionManager;
    private AuctionMenu auctionMenu;
    private ItemStack nextAuctionItem;
    
    public void onEnable() {
        File file = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            saveDefaultConfig();
        }
        this.auctionManager = new AuctionManager();
        this.auctionMenu = new AuctionMenu();
        this.getCommand("bah").setExecutor((CommandExecutor)new CommandBAH());
    }
    
    public void onDisable() {
        if (AuctionManager.get().getCurrentAuction() != null) {
            final Auction currentAuction = AuctionManager.get().getCurrentAuction();
            if (currentAuction.currentTopBidder != null) {
                EconomyUtils.depositBalance(Bukkit.getPlayer(currentAuction.currentTopBidder), currentAuction.currentBid);
            }
        }
    }
    

    public AuctionManager getAuctionManager() {
        return this.auctionManager;
    }
    
    public AuctionMenu getAuctionMenu() {
        return this.auctionMenu;
    }
    
    public ItemStack getNextAuctionItem() {
        return this.nextAuctionItem;
    }
    
    public void setNextAuctionItem(final ItemStack nextAuctionItem) {
        this.nextAuctionItem = nextAuctionItem;
    }
    
    public static BlackMarketAuction get() {
        return instance;
    }
}
