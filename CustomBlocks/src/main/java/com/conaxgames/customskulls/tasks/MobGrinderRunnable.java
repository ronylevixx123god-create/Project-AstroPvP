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
            if (mobGrinderCache == null) {
                System.out.println("[Debug] Cache is null");

            }
            if (!mobGrinderCache.getAbilityData().isActive()) continue;
            boolean stackedBlazesFound = false;
            AbilityData data = mobGrinderCache.getAbilityData();
            if (mobGrinderCache.getBlaze() != null) {
                stackedBlazesFound = true;
            }

            if (!stackedBlazesFound) {
                System.out.println("[Debug] No blaze found in task");

            }

            //KILL NEARBY STACKED MOB

            if (CoreSkulls.mobStackerUtils.isStackedMob(mobGrinderCache.getBlaze())) {
                CoreSkulls.mobStackerUtils.setStackSize(mobGrinderCache.getBlaze(), CoreSkulls.mobStackerUtils.getStackSize(mobGrinderCache.getBlaze()) - 1);
            }


            //ADD LOOT TO THE CHEST
            if (mobGrinderCache.getXp() + amountOfExpPerBlaze < Integer.MAX_VALUE) {
                mobGrinderCache.setXp(mobGrinderCache.getXp() + amountOfExpPerBlaze);
            }

            double chanceGodlyKey = 0.0015;
            double chanceLegKey = 0.01;
            double chanceAstroKey = 0.065;

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
                chanceLegKey = 0.045;
                chanceAstroKey = 0.065;
            }


            double chance = Math.random();
            if (chance < chanceGodlyKey) {
                int godlyKeys = mobGrinderCache.getCrateKeys().get(CrateType.GODLY);
                mobGrinderCache.getCrateKeys().put(CrateType.GODLY, godlyKeys + 1);
            } else if (chance < chanceLegKey) {
                int legKeys = mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY);
                mobGrinderCache.getCrateKeys().put(CrateType.LEGENDARY, legKeys + 1);
            } else if (chance < chanceAstroKey) {
                int astroKeys = mobGrinderCache.getCrateKeys().get(CrateType.ASTRO);
                mobGrinderCache.getCrateKeys().put(CrateType.ASTRO, astroKeys + 1);
            }
        }

    }
}
