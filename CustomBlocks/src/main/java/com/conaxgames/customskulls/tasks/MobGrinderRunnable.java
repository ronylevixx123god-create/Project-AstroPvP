package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class MobGrinderRunnable extends BukkitRunnable {
    int amountOfExpPerBlaze = 50;

    @Override
    public void run() {
        if (CoreSkulls.isSavingGrinderBlocks) return;

        for (MobGrinderCache mobGrinderCache : CoreSkulls.instance.getGrinderBlocks().values()) {
            if (mobGrinderCache == null) continue;
            if (!mobGrinderCache.getAbilityData().isActive()) continue;



            if (mobGrinderCache.getAbilityData().getLocation().getChunk().getEntities().length == 0) continue;

            //KILL NEARBY STACKED MOB
            boolean stackedBlazesFound = false;
            for (Entity entity : mobGrinderCache.getAbilityData().getLocation().getChunk().getEntities()) {
                if (entity.getType() == EntityType.BLAZE) {
                    if (CoreSkulls.mobStackerUtils.isStackedMob(entity)) {
                        stackedBlazesFound = true;
                        CoreSkulls.mobStackerUtils.setStackSize(entity, CoreSkulls.mobStackerUtils.getStackSize(entity) - 1);
                        break;
                    }
                }
            }

            if (!stackedBlazesFound) continue;


            //ADD LOOT TO THE CHEST
            mobGrinderCache.setXp(mobGrinderCache.getXp()+amountOfExpPerBlaze);
            double chance = Math.random();
            if (chance < 0.00025) {
                int godlyKeys = mobGrinderCache.getCrateKeys().get(CrateType.GODLY);
                mobGrinderCache.getCrateKeys().put(CrateType.GODLY,  godlyKeys+ 1);
            } else if (chance < 0.01) {
                int legKeys = mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY);
                mobGrinderCache.getCrateKeys().put(CrateType.LEGENDARY, legKeys+1);
            } else if (chance < 0.1) {
                int astroKeys = mobGrinderCache.getCrateKeys().get(CrateType.ASTRO);
                mobGrinderCache.getCrateKeys().put(CrateType.ASTRO, astroKeys+1);
            }
        }

    }
}