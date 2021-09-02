package com.conaxgames.customskulls.ability;

import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCollection;
import com.conaxgames.customskulls.util.CC;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import javafx.util.Pair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AbilityData {

    @Getter
    private static Map<String, AbilityData> abilityDataMap = new HashMap<>();

    public MobGrinderCollection collectedLoot;


    private UUID owner;
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


