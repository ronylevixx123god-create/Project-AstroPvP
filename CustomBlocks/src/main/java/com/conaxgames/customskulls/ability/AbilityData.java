package com.conaxgames.customskulls.ability;

import com.conaxgames.customskulls.enums.AbilityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AbilityData {

    @Getter
    private static Map<String, AbilityData> abilityDataMap = new HashMap<>();

    private String uuid;
    private Location location;
    private AbilityType abilityType;

    public AbilityData(String uuid) {
        this.uuid = uuid;
        abilityDataMap.put(uuid, this);
    }

    public static AbilityData getByUUID(String uuid) {
        return abilityDataMap.get(uuid) == null ? new AbilityData(uuid) : abilityDataMap.get(uuid);
    }

    public static String getUUID(Location location) {
        return location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
    }
}
