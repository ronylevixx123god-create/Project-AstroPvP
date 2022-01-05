package net.astropvp.struct;

import net.astropvp.CollectPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CollectManager {
    private HashMap<UUID, PlayerCollect> collectList;

    public CollectManager() {
        collectList = new HashMap<>();
    }

    public PlayerCollect getPlayerCollect(UUID uuid) {
        if(collectList.containsKey(uuid))
            return collectList.get(uuid);
        PlayerCollect collect = new PlayerCollect(uuid);
        collectList.put(uuid, collect);
        //Why load when it's new?
        collect.load(false);
        return collect;
    }

    public void remove(UUID uuid) {
        collectList.remove(uuid);
    }
}
