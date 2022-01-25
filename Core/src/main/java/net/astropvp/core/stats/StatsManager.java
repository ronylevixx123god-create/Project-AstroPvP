package net.astropvp.core.stats;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.astropvp.core.AstroCore;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class StatsManager {
    private HashMap<Player, StatsPlayer> statsPlayers;

    private AstroCore plugin;

    @Getter
    private static StatsManager instance;

    private Gson gson;

    public StatsManager(AstroCore plugin) {
        this.instance = this;
        this.plugin = plugin;
        this.statsPlayers = new HashMap<>();
        this.gson = (new GsonBuilder()).setPrettyPrinting().enableComplexMapKeySerialization().create();
    }

    public StatsPlayer getStatsPlayer(Player player) {
        if(!this.statsPlayers.containsKey(player))
            this.statsPlayers.put(player, new StatsPlayer(player));
        return this.statsPlayers.get(player);
    }

    public void save(Player player) {
        if(!this.statsPlayers.containsKey(player))
            return;
        StatsPlayer statsPlayer = this.statsPlayers.get(player);
        this.gson.toJson(statsPlayer, TypeToken.of(statsPlayer.getClass()).getType());
    }

    public void load(Player player) {
        try {
            StatsPlayer statsPlayer = this.gson.fromJson(new FileReader(new File(this.plugin.getDataFolder(), "stats/" + player.getUniqueId() + ".json")), TypeToken.of(StatsPlayer.class).getType());
            this.statsPlayers.put(player, statsPlayer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
