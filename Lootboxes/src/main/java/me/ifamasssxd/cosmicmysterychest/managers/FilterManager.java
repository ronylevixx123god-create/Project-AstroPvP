package me.ifamasssxd.cosmicmysterychest.managers;

import me.ifamasssxd.cosmicmysterychest.struct.PlayerFilter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterManager {
    HashMap<Player, PlayerFilter> filterMap;

    public FilterManager() {
        filterMap = new HashMap<>();
    }

    public PlayerFilter getPlayerFilter(Player player) {
        if(!filterMap.containsKey(player)) {
            PlayerFilter playerFilter = new PlayerFilter(player, new ArrayList<>());
            this.filterMap.put(player, playerFilter);
            return playerFilter;
        } else {
            return filterMap.get(player);
        }
    }

}
