package me.astropvp.struct;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerDataManager {
    private HashMap<Player, PlayerData> loadedPlayerData = new HashMap<>();

    public void registerData(Player player, PlayerData data) {
        loadedPlayerData.put(player, data);
    }

    public PlayerData getData(Player player) {
        if(!hasLoadedData(player))
            this.loadedPlayerData.put(player, new PlayerData(player));
        return loadedPlayerData.get(player);
    }

    public boolean hasLoadedData(Player player) {
        return loadedPlayerData.containsKey(player);
    }
}
