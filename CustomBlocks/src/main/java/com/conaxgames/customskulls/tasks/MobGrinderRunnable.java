package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class MobGrinderRunnable extends BukkitRunnable {
    int amountOfExpPerBlaze = 35;

    @Override
    public void run() {
        if (CoreSkulls.isSavingGrinderBlocks) return;

        for (MobGrinderCache mobGrinderCache : CoreSkulls.instance.getGrinderBlocks().values()) {
            if (mobGrinderCache == null) continue;
            if (!mobGrinderCache.getAbilityData().isActive()) continue;
            AbilityData data = mobGrinderCache.getAbilityData();
            if (data.getLocation().getChunk().getEntities().length == 0) continue;

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
            if (mobGrinderCache.getXp() + amountOfExpPerBlaze < Integer.MAX_VALUE) {
                mobGrinderCache.setXp(mobGrinderCache.getXp() + amountOfExpPerBlaze);
            }

            double chanceGodlyKey = 0.0015;
            double chanceLegKey = 0.01;
            double chanceAstroKey = 0.085;

            int level = data.getLevel();
            if (level == 1) {
                chanceGodlyKey = 0.0075;
                chanceLegKey = 0.02;
                chanceAstroKey = 0.045;
            } else if (level == 2) {
                chanceGodlyKey = 0.0175;
                chanceLegKey = 0.0375;
                chanceAstroKey = 0.0575;
            } else if (level == 3) {
                chanceGodlyKey = 0.02;
                chanceLegKey = 0.04;
                chanceAstroKey = 0.06;
            }


            double chance = Math.random();
            if (chance < chanceGodlyKey) {
                int godlyKeys = mobGrinderCache.getCrateKeys().get(CrateType.GODLY);
                mobGrinderCache.getCrateKeys().put(CrateType.GODLY,  godlyKeys+ 1);
            } else if (chance < chanceLegKey) {
                int legKeys = mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY);
                mobGrinderCache.getCrateKeys().put(CrateType.LEGENDARY, legKeys+1);
            } else if (chance < chanceAstroKey) {
                int astroKeys = mobGrinderCache.getCrateKeys().get(CrateType.ASTRO);
                mobGrinderCache.getCrateKeys().put(CrateType.ASTRO, astroKeys+1);
            }
        }

    }
}
