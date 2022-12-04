package net.astropvp.christmas.managers;
import net.astropvp.christmas.ChristmasPlugin;
import net.astropvp.christmas.utils.ConfigUtils;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.storage.utils.SaveLoadManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ChristmasManager extends SaveLoadManager<ChristmasPlayer> {
    public ChristmasManager() {

        super(ChristmasPlugin.getInstance(), StoragePlugin.getInstance().getGson(), "data/", ChristmasPlayer.class);
    }

    public ChristmasPlayer getChristmasPlayer(Player player) {
        String ip = player.getAddress().getAddress().toString();
        if (getFromCache(ip) == null) {
            addToCache(new ChristmasPlayer(player.getUniqueId(), ip));
        }
        return getFromCache(ip);
    }

    private ItemStack currentItem = ConfigUtils.getItem(ChristmasPlugin.getInstance().getConfig().getString("current-item"));
    private Location shopLocation = ConfigUtils.stringToLocation(ChristmasPlugin.getInstance().getConfig().getString("shop-location"));


    public void saveShop() {
        ChristmasPlugin.getInstance().getConfig().set("current-item", (Object)ConfigUtils.toBase64(new ItemStack[]{this.currentItem}, 9));
        ChristmasPlugin.getInstance().getConfig().set("shop-location", (Object)ConfigUtils.locationToString(this.shopLocation));
        ChristmasPlugin.getInstance().saveConfig();
    }


    public boolean hasAlreadyBought(Player player) {
        return getChristmasPlayer(player).isHasPurchased() && getCacheKeys().contains(player.getAddress().getAddress().toString());
    }

    public void setAlreadyBought(Player player) {
        getChristmasPlayer(player).setHasPurchased(true);
    }

    public void clearAlreadyBought() {
        for (ChristmasPlayer christmasPlayer : getCacheValues()) {
            christmasPlayer.setHasPurchased(false);
        }
    }

    public ItemStack getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(ItemStack currentItem) {
        this.currentItem = currentItem;
    }

    public Location getShopLocation() {
        return this.shopLocation;
    }

    public void setShopLocation(Location shopLocation) {
        this.shopLocation = shopLocation;
    }
}
