package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class GrinderBlockCheckRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Map.Entry<String, AbilityData> entry : AbilityData.getAbilityDataMap().entrySet()) {
            AbilityData data = entry.getValue();

            /*if (data.getAbilityType() == AbilityType.TRIALS_FAME_AND_FORTUNE) {
                CoreSkulls.mobGrinderCustomBlocks.put(data.getUuid(), data);
            }*/
        }
    }
}
