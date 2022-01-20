package com.conaxgames.customskulls.struct;

import com.conaxgames.customskulls.ability.AbilityData;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.Location;

import java.util.Map;

@Getter
@Setter
public class MobGrinderCache {
    private AbilityData abilityData;
    private Map<CrateType, Integer> crateKeys;
    private double xp;
    public MobGrinderCache(AbilityData abilityData) {
        this.abilityData = abilityData;
        this.crateKeys = abilityData.getCrateKeys();
        this.xp = abilityData.getXp();
    }


}
