package com.compilex.CosmicArmorSets.enums;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class GoblinSetManager {
    private HashMap<Player, CachedGoblinSet> cachedGoblinSets;

    private CosmicArmorSetsPlugin plugin;

    public GoblinSetManager(CosmicArmorSetsPlugin plugin) {
        this.cachedGoblinSets = new HashMap<>();
        this.plugin = plugin;
    }

    public CachedGoblinSet getCachedGoblinSet(Player player) {
        if(!this.cachedGoblinSets.containsKey(player))
            this.cachedGoblinSets.put(player, new CachedGoblinSet(player));
        return this.cachedGoblinSets.get(player);
    }


}
