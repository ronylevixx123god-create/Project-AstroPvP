package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MobGrinderCacherTask extends BukkitRunnable {


    @Override
    public void run() {
        CoreSkulls.getGrinderBlocks().clear();

        for (String key : AbilityData.getAbilityDataMap().keySet()) {
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


            Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
            if (chestLocation != null) {
                MobGrinderCache mobGrinderCache = new MobGrinderCache(data, chestLocation, playerFound);
                CoreSkulls.getGrinderBlocks().add(mobGrinderCache);

            }
        }
    }
}
