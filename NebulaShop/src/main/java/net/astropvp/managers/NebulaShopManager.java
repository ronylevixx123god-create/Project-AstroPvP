package net.astropvp.managers;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import net.astropvp.NebulaShop;
import net.astropvp.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class NebulaShopManager {

    @Getter
    @Setter
    private ItemStack currentItem;

    @Getter
    @Setter
    private double currentPrice;

    @Getter
    @Setter
    private Location shopLocation;

    private List<String> alreadyBought;

    public NebulaShopManager() {
        this.currentItem = ConfigUtils.getItem(NebulaShop.getInstance().getConfig().getString("current-item"));
        this.currentPrice = NebulaShop.getInstance().getConfig().getInt("current-price");
        this.shopLocation = ConfigUtils.stringToLocation(NebulaShop.getInstance().getConfig().getString("shop-location"));
        this.alreadyBought = NebulaShop.getInstance().getConfig().getStringList("already-bought");
    }

    public void saveShop() {
        NebulaShop.getInstance().getConfig().set("current-item", ConfigUtils.toBase64(new ItemStack[]{this.currentItem}, 9));
        NebulaShop.getInstance().getConfig().set("current-price", this.currentPrice);
        NebulaShop.getInstance().getConfig().set("shop-location", ConfigUtils.locationToString(this.shopLocation));
        NebulaShop.getInstance().getConfig().set("already-bought", this.alreadyBought);
    }

    public boolean hasAlreadyBought(Player player) {
        return this.alreadyBought.contains(player.getUniqueId().toString());
    }

    public void setAlreadyBought(Player player) {
        this.alreadyBought.add(player.getUniqueId().toString());
    }




}
