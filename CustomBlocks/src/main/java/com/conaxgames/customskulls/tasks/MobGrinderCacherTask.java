package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.ChunkUtil;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Blaze;
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
            if (!entry.getValue().getBlaze().isValid()) {
                CoreSkulls.instance.getGrinderBlocks().remove(entry.getKey());
                System.out.println("[!] Removed a grinder block from cache due to missing blaze");
            }
        }

        final Map<String, AbilityData> abilityDataMap = AbilityData.getAbilityDataMap();
        for (Map.Entry<String, AbilityData> entry : abilityDataMap.entrySet()) {
            String key = entry.getKey();
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            if (CoreSkulls.instance.getGrinderBlocks().containsKey(key)) {
                System.out.println("[Debug] Contiue because it already is in the active grinder blocks");
                continue;
            }
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.BLAZE_STONE_TOTEM) continue;
            CraftEntity blaze = null;

            Chunk chunk = ChunkUtil.getIfLoaded(data.getLocation().getChunk().getX(), data.getLocation().getChunk().getZ(), data.getLocation().getWorld());
            if (chunk == null) continue;
            for (CraftEntity entity : ChunkUtil.getEntities(chunk)) {
                if (entity instanceof Blaze) {
                    blaze = entity;
                    System.out.println("[Debug] Found a blaze for grinder block");
                }
                if (blaze != null) break;
            }

            if (blaze == null) continue;

            if (!CoreSkulls.instance.getGrinderBlocks().containsKey(key)) {
                MobGrinderCache mobGrinderCache = new MobGrinderCache(data, blaze);
                CoreSkulls.instance.getGrinderBlocks().put(key, mobGrinderCache);
            }


        }
    }
}
