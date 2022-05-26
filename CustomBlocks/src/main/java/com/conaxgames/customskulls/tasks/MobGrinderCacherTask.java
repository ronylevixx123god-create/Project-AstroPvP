package com.conaxgames.customskulls.tasks;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.bukkit.wrapper.AsyncBlock;
import com.boydti.fawe.bukkit.wrapper.AsyncChunk;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.earth2me.essentials.craftbukkit.FakeWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MobGrinderCacherTask extends BukkitRunnable {


    @Override
    public void run() {
        CoreSkulls.setCachingGrinderBlocks(true);
        if (CoreSkulls.isSavingGrinderBlocks) return;
        final Set<Map.Entry<String, AbilityData>> datas = AbilityData.getAbilityDataMap().entrySet();
        for (Map.Entry<String, AbilityData> entry : datas) {
            String key = entry.getKey();
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.BLAZE_STONE_TOTEM) continue;
            if (CoreSkulls.instance.getGrinderBlocks().containsKey(key)) {
                continue;
            }
            boolean playerFound = false;
            Blaze blaze = null;
            for (Entity entity : data.getLocation().getWorld().getNearbyEntities(data.getLocation(),10, 4, 10)) {
                if (entity instanceof Player) {
                    playerFound = true;
                } else if (entity instanceof Blaze) {
                    blaze = (Blaze) entity;
                }

                if (playerFound && blaze != null) break;
            }

            if (!playerFound) continue;
            if (blaze == null) continue;

            if (!CoreSkulls.instance.getGrinderBlocks().containsKey(key)) {
                MobGrinderCache mobGrinderCache = new MobGrinderCache(data, blaze);
                CoreSkulls.instance.getGrinderBlocks().put(key, mobGrinderCache);
            }


        }
        CoreSkulls.setCachingGrinderBlocks(false);

    }
}
