package me.astropvp.cratekeys.filter;

import lombok.Getter;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class FilterManager {
    @Getter
    private static FilterManager instance;

    private AstroCrateKeysPlugin plugin;

    private HashMap<UUID, FilterPlayer> filterPlayers;

    public FilterManager(AstroCrateKeysPlugin plugin) {
        this.plugin = plugin;
        instance = this;
        this.filterPlayers = new HashMap<>();
    }

    public FilterPlayer getFilterPlayer(Player player) {
        if(!this.filterPlayers.containsKey(player.getUniqueId()))
            this.filterPlayers.put(player.getUniqueId(), new FilterPlayer(player.getUniqueId()));
        return this.filterPlayers.get(player.getUniqueId());
    }
}
