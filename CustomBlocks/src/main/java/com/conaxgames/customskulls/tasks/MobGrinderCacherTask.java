package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MobGrinderCacherTask extends BukkitRunnable {

    public final static Map<String, MobGrinderCache> mobGrinderBlocks = new HashMap<>();

    @Override
    public void run() {
        if (!mobGrinderBlocks.isEmpty()) {
            for (String key : mobGrinderBlocks.keySet()) {
                if (!AbilityData.getAbilityDataMap().containsKey(key)) {
                    mobGrinderBlocks.remove(key);
                }
            }
        }

        for (String key : AbilityData.getAbilityDataMap().keySet()) {
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.BLAZE_STONE_TOTEM) continue;
            if (!data.isActive()) continue;
            if (!mobGrinderBlocks.containsKey(key)) {
                Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                if (chestLocation != null) {
                    boolean playerFound = false;

                    for (Entity entity : data.getLocation().getWorld().getNearbyEntities(data.getLocation(), 16, 6, 16)) {
                        if (entity instanceof Player) {
                            playerFound = true;

                            break;
                        }
                    }
                    MobGrinderCache mobGrinderCache = new MobGrinderCache(data, chestLocation, playerFound);

                    mobGrinderBlocks.put(key, mobGrinderCache);
                }
            } else {
                MobGrinderCache mobGrinderCache = mobGrinderBlocks.get(key);
                if (!mobGrinderCache.getAbilityData().isActive()) {
                    mobGrinderBlocks.remove(key);
                }
                Location chestLocation = mobGrinderCache.getChestLocation();
                if (chestLocation.getBlock().getType() != Material.CHEST) {
                    Location newChestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                    mobGrinderCache.setChestLocation(newChestLocation);
                }
            }
        }
    }
}