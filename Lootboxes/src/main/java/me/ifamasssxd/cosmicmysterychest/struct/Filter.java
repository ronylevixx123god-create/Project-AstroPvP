package me.ifamasssxd.cosmicmysterychest.struct;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import me.ifamasssxd.cosmicmysterychest.guis.FilterGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class Filter {
    private HashMap<ItemStack, Boolean> filters;

    private Player player;

    public Filter(Player player, List<ItemStack> items) {
        this.player = player;
        filters = new HashMap<>();
        for(ItemStack item : items)
            filters.put(item, true);
    }

    public void toggle(ItemStack item) {
        if(filters.get(item)) {
            filters.replace(item, false);
        } else {
            filters.replace(item, true);
        }
    }

    public boolean isEnabled(ItemStack item) {
        return filters.get(item);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isFilter(List<ItemStack> items) {
        for (ItemStack item : items) {
            if(!this.filters.containsKey(item)) {
                return false;
            }
        }
        return true;
    }

    public CosmicGUI getInventory() {
        return new FilterGUI(this.player, this);
    }

    public HashMap<ItemStack, Boolean> getFilters() {
        return this.filters;
    }
}
