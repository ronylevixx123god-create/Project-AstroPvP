package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import me.astropvp.astrospawners.utils.MobCoinUtils;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Map;

public class MobGrinderRunnable extends BukkitRunnable {
    int amountOfExpPerBlaze = 200;

    @Override
    public void run() {
        for (String key : AbilityData.getAbilityDataMap().keySet()) {
            if (key == null) continue;
            if (AbilityData.getByUUID(key) == null) continue;
            AbilityData data = AbilityData.getByUUID(key);
            if (data == null) continue;
            if (data.getAbilityType() == null) continue;
            if (CoreSkulls.customBlocks.get(data.getAbilityType().getName()) == null) continue;
            if (data.getAbilityType() != AbilityType.TRIALS_FAME_AND_FORTUNE) continue;

            if (CustomBlockUtil.getClosestChestLocation(data.getLocation()) != null) {

                Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                boolean playerFound = false;
                assert chestLocation != null;
                if (chestLocation.getChunk().getEntities().length == 0) continue;
                for (Entity entity : chestLocation.getWorld().getNearbyEntities(chestLocation, 16, 16, 16)) {
                    if (entity instanceof Player) {
                        playerFound = true;
                        break;
                    }
                }
                if (!playerFound) continue;
                Chest chest = (Chest) chestLocation.getBlock().getState();

                //KILL NEARBY STACKED MOB
                boolean stackedBlazesFound = false;
                for (Entity entity : chestLocation.getChunk().getEntities()) {
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
                double chance = Math.random();
                if (chance < 0.01) {
                    chest.getInventory().addItem(CoreSkulls.getStellarKey());
                } else if (chance < 0.04) {
                    chest.getInventory().addItem(CoreSkulls.getNovaKey());

                }
                if (!chest.getInventory().contains(Material.EXP_BOTTLE)) {
                    chest.getInventory().addItem(ExpBottles.getXpBottle(amountOfExpPerBlaze, "Mob Grinder"));
                    continue;
                }
                for (int i = 0; i < chest.getInventory().getSize(); i++) {
                    if (chest.getInventory().getItem(i) == null) {
                        continue;
                    } else if (ExpBottles.isXpBottle(chest.getInventory().getItem(i))) {
                        chest.getInventory().setItem(i, ExpBottles.getXpBottle(ExpBottles.getXpAmount(chest.getInventory().getItem(i)) + amountOfExpPerBlaze, "Mob Grinder"));
                        break;
                    } else if (MobCoinUtils.isCoinsNote(chest.getInventory().getItem(i))){
                        chest.getInventory().setItem(i, MobCoinUtils.createCoinsNote("MobGrinder", MobCoinUtils.getCoinsAmountFromNote(chest.getInventory().getItem(i)) + 1));
                        break;

                    }
                }
            }
        }
    }
}
