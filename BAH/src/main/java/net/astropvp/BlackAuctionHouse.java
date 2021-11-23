package net.astropvp;

import lombok.Getter;
import net.astropvp.commands.BlackAuctionHouseCommand;
import net.astropvp.managers.AuctionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlackAuctionHouse extends JavaPlugin {
    @Getter
    private static BlackAuctionHouse instance;

    @Getter
    private AuctionManager auctionManager;

    public void onEnable() {
        instance = this;
        getCommand("blackauctionhouse").setExecutor(new BlackAuctionHouseCommand());
    }

    public void onDisable() {
        instance = null;
    }
}
