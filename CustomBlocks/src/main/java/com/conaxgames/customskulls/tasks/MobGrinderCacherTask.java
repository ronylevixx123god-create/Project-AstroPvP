package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class MobGrinderCacherTask extends BukkitRunnable {
    private HashMap<String, Location> grinderBlocks;

    public MobGrinderCacherTask(HashMap<String, Location> grinderBlocks) {
        this.grinderBlocks = grinderBlocks;
    }

    @Override
    public void run() {
        for (String key : AbilityData.getAbilityDataMap().keySet()) {
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.TRIALS_FAME_AND_FORTUNE) continue;
            grinderBlocks.put(key, data.getLocation());
        }
    }
}
