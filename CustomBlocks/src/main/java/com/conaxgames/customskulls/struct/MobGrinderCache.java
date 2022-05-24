package com.conaxgames.customskulls.struct;

import com.conaxgames.customskulls.ability.AbilityData;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.Map;

@Getter
@Setter
public class MobGrinderCache {
    private AbilityData abilityData;
    private Map<CrateType, Integer> crateKeys;
    private double xp;
    private Entity blaze;
    public MobGrinderCache(AbilityData abilityData, Entity blaze) {
        this.abilityData = abilityData;
        this.crateKeys = abilityData.getCrateKeys();
        this.xp = abilityData.getXp();
        this.blaze = blaze;
    }



}
