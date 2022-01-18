package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.impl.MobGrinder;
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

import java.util.HashMap;

public class MobGrinderCacherTask extends BukkitRunnable {
    private HashMap<AbilityData, Location> currentChests;

    public MobGrinderCacherTask() {
        this.currentChests = new HashMap<>();
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
            if (data.getAbilityType() != AbilityType.BLAZE_STONE_TOTEM) continue;

            boolean playerFound = false;

            for (Entity entity : data.getLocation().getWorld().getNearbyEntities(data.getLocation(), 16, 6, 16)) {
                if (entity instanceof Player) {
                    playerFound = true;
                    break;
                }
            }

            if(!this.currentChests.containsKey(data) || this.currentChests.get(data).getBlock().getType() != Material.CHEST) {
                Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                if (chestLocation != null) {
                    this.currentChests.put(data, chestLocation);
                    MobGrinderCache mobGrinderCache = CoreSkulls.instance.getGrinderBlocks().containsKey(data.getLocation().getChunk()) ? CoreSkulls.instance.getGrinderBlocks().get(data.getLocation().getChunk()) : new MobGrinderCache(data, chestLocation, playerFound);
                    mobGrinderCache.setChestLocation(chestLocation);
                    mobGrinderCache.setPlayerNearby(playerFound);
                    CoreSkulls.instance.getGrinderBlocks().put(data.getLocation().getChunk(), mobGrinderCache);
                }
            }
        }
    }
}
