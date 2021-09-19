package net.astropvp.struct;

import org.bukkit.entity.Player;
import java.util.HashMap;

public class CollectManager {
    public static HashMap<Player, PlayerCollect> collectList;

    public CollectManager() {
        collectList = new HashMap<>();
    }

    public PlayerCollect getPlayerCollect(Player player) {
        if(collectList.containsKey(player))
            return collectList.get(player);
        PlayerCollect collect = new PlayerCollect(player);
        collectList.put(player, collect);
        return collect;
    }

    public static HashMap<Player, PlayerCollect> getCollectList() {
        return collectList;
    }
}
