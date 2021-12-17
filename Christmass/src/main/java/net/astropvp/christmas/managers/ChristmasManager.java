package net.astropvp.christmas.managers;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.christmas.Christmas;
import net.astropvp.christmas.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ChristmasManager {

    @Getter
    @Setter
    private ItemStack currentItem;

    @Getter
    @Setter
    private Location shopLocation;

    private List<String> alreadyBought;

    public ChristmasManager() {
        this.currentItem = ConfigUtils.getItem(Christmas.getInstance().getConfig().getString("current-item"));
        this.shopLocation = ConfigUtils.stringToLocation(Christmas.getInstance().getConfig().getString("shop-location"));
        this.alreadyBought = Christmas.getInstance().getConfig().getStringList("already-bought");
    }

    public void saveShop() {
        Christmas.getInstance().getConfig().set("current-item", ConfigUtils.toBase64(new ItemStack[]{this.currentItem}, 9));
        Christmas.getInstance().getConfig().set("shop-location", ConfigUtils.locationToString(this.shopLocation));
        Christmas.getInstance().getConfig().set("already-bought", this.alreadyBought);
        Christmas.getInstance().saveConfig();
    }

    public boolean hasAlreadyBought(Player player) {
        return this.alreadyBought.contains(player.getUniqueId().toString());
    }

    public void setAlreadyBought(Player player) {
        this.alreadyBought.add(player.getUniqueId().toString());
    }

    public void clearAlreadyBought() {
        this.alreadyBought.clear();
    }




}
