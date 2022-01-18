package com.conaxgames.customskulls.struct;

import com.conaxgames.customskulls.ability.AbilityData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class MobGrinderCache {
    private AbilityData abilityData;
    private Location chestLocation;
    private boolean playerNearby;

    public MobGrinderCache(AbilityData abilityData, Location chestLocation, boolean playerNearby) {
        this.abilityData = abilityData;
        this.chestLocation = chestLocation;
        this.playerNearby = playerNearby;

    }


}
