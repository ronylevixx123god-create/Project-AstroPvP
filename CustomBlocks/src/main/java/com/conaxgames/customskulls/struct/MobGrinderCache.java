package com.conaxgames.customskulls.struct;

import com.conaxgames.customskulls.ability.AbilityData;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class MobGrinderCache {
    private final AbilityData abilityData;
    private final Location chestLocation;
    private final boolean playerNearby;

    public MobGrinderCache(AbilityData abilityData, Location chestLocation, boolean playerNearby) {
        this.abilityData = abilityData;
        this.chestLocation = chestLocation;
        this.playerNearby = playerNearby;

    }


}
