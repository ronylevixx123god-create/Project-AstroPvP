package me.ifamasssxd.cosmicmysterychest.struct;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerFilter {

    private Player player;

    private List<Filter> filters;

    public PlayerFilter(Player player, List<Filter> filters) {
        this.player = player;
        if(filters.isEmpty()) {
            this.filters = new ArrayList<>();
        } else {
            this.filters = filters;
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public Filter getFilter(List<ItemStack> items) {
        for(Filter filter : this.filters) {
            if(filter.isFilter(items))
                return filter;
        }
        Filter newFilter = new Filter(this.player, items);
        filters.add(newFilter);
        return newFilter;
    }
}
