package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class MobGrinderCacherTask extends BukkitRunnable {


    @Override
    public void run() {
        if (CoreSkulls.isSavingGrinderBlocks) return;
        final Map<String, MobGrinderCache> grinderBlocks = CoreSkulls.instance.getGrinderBlocks();
        for (Map.Entry<String, MobGrinderCache> entry : grinderBlocks.entrySet()) {
            if (!AbilityData.getAbilityDataMap().containsKey((entry.getKey()))) {
                CoreSkulls.instance.getGrinderBlocks().remove(entry.getKey());
            }
        }
        final Map<String, AbilityData> abilityDataMap = AbilityData.getAbilityDataMap();
        for (Map.Entry<String, AbilityData> entry : abilityDataMap.entrySet()) {
            String key = entry.getKey();
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.BLAZE_STONE_TOTEM) continue;

            boolean playerFound = false;

            for (Entity entity : data.getLocation().getWorld().getNearbyEntities(data.getLocation(), 16, 6, 16)) {
                if (entity instanceof Player) {
                    playerFound = true;
                    break;
                }
            }

            if (!playerFound) continue;

            if (!CoreSkulls.instance.getGrinderBlocks().containsKey(key)) {
                MobGrinderCache mobGrinderCache = new MobGrinderCache(data);
                CoreSkulls.instance.getGrinderBlocks().put(key, mobGrinderCache);
            }


        }
    }
}