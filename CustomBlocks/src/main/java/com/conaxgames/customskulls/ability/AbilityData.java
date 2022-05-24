package com.conaxgames.customskulls.ability;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.bukkit.wrapper.AsyncBlock;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.blocks.impl.MobGrinder;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AbilityData {

    @Getter
    private static Map<String, AbilityData> abilityDataMap = new HashMap<>();

    private UUID owner;
    private String uuid;
    private Location location;
    private AbilityType abilityType;
    private long lastActivated = 0;
    private Map<CrateType, Integer> crateKeys = new HashMap<>();
    private double xp = 0;
    private int level = 0;

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

    public static String getUUID(int x, int y, int z) {
        return x + "_" + y + "_" + z;
    }

    public boolean isActive() {
        int time = 3600000;
        if (level == 1) {
            time = 4500000;
        } else if (level == 2) {
            time = 5850000;
        } else if (level == 3) {
            time = 7200000;
        }
        return System.currentTimeMillis() < lastActivated + time;
    }

}
