package me.astropvp.cratekeys.filter;

import lombok.Getter;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class FilterManager {
    @Getter
    private static FilterManager instance;

    private Plugin plugin;

    private HashMap<UUID, FilterPlayer> filterPlayers;

    public FilterManager(Plugin plugin) {
        this.plugin = plugin;
        instance = this;
        this.filterPlayers = new HashMap<>();
    }

    public FilterPlayer getFilterPlayer(Player player) {
        if(!this.filterPlayers.containsKey(player.getUniqueId())) {
            this.filterPlayers.put(player.getUniqueId(), new FilterPlayer(this.plugin, player.getUniqueId()));
            this.filterPlayers.get(player.getUniqueId()).loadData();
        }
        return this.filterPlayers.get(player.getUniqueId());
    }

    public void unregisterFilterPlayer(Player player) {
        if(!this.filterPlayers.containsKey(player.getUniqueId()))
            return;
        FilterPlayer filterPlayer = this.filterPlayers.get(player.getUniqueId());
        filterPlayer.saveData();
        this.filterPlayers.remove(player.getUniqueId());
    }
}
