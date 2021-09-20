package net.astropvp.struct;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;

public class CollectManager {
    private HashMap<Player, PlayerCollect> collectList;

    public CollectManager() {
        collectList = new HashMap<>();
    }

    public PlayerCollect getPlayerCollect(Player player) {
        if(collectList.containsKey(player)) {
            Bukkit.broadcastMessage("found player");
            return collectList.get(player);
        }
        Bukkit.broadcastMessage("not found player");
        PlayerCollect collect = new PlayerCollect(player);
        collectList.put(player, collect);
        collect.load(false);
        return collect;
    }

    public void remove(Player player) {
        collectList.remove(player);
    }
}
