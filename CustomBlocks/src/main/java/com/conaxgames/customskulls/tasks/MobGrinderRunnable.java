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
    int amountOfExpPerBlaze = 150;

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
            boolean playerFound = false;

            for (Entity entity : data.getLocation().getWorld().getNearbyEntities(data.getLocation(), 16, 16, 16)) {
                if (entity instanceof Player) {
                    playerFound = true;
                    break;
                }
            }
            if (!playerFound) continue;

            if (CustomBlockUtil.getClosestChestLocation(data.getLocation()) != null) {
                Location chestLocation = CustomBlockUtil.getClosestChestLocation(data.getLocation());
                assert chestLocation != null;
                if (chestLocation.getChunk().getEntities().length == 0) continue;

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
                if (chance < 0.008) {
                    chest.getInventory().addItem(CoreSkulls.getStellarKey());
                } else if (chance < 0.015) {
                    chest.getInventory().addItem(CoreSkulls.getNovaKey());

                }
                if (!chest.getInventory().contains(Material.EXP_BOTTLE)) {
                    chest.getInventory().addItem(ExpBottles.getXpBottle(amountOfExpPerBlaze, "Mob Grinder"));
                }
                if (!chest.getInventory().contains(Material.DOUBLE_PLANT)) {
                    chest.getInventory().addItem(MobCoinUtils.createCoinsNote("Mob Grinder", 1));
                }
                int expBottle = -1;
                int mobcoin = -1;
                boolean mobCoinsFound = false;
                boolean expBottleFound = false;


                for (int i = 0; i < chest.getInventory().getSize(); i++) {
                    if (mobCoinsFound && expBottleFound) break;
                    ItemStack itemStack = chest.getInventory().getItem(i);
                    if (itemStack == null || (!MobCoinUtils.isCoinsNote(itemStack) && !ExpBottles.isXpBottle(itemStack))) {
                        continue;
                    }

                    if (MobCoinUtils.isCoinsNote(itemStack) && !mobCoinsFound) {
                        mobcoin = i;
                        mobCoinsFound = true;
                    }

                    if (ExpBottles.isXpBottle(itemStack) && !expBottleFound) {
                        expBottle = i;
                        expBottleFound = true;
                    }

                    if (expBottle == -1 || mobcoin == -1) continue;

                    if (!mobCoinsFound) {
                        if (Math.random() < 0.2) {
                            chest.getInventory().setItem(mobcoin, MobCoinUtils.createCoinsNote("Mob Grinder", MobCoinUtils.getCoinsAmountFromNote(chest.getInventory().getItem(mobcoin)) + 1));
                        }
                    }

                    if (!expBottleFound) {
                        chest.getInventory().setItem(expBottle, ExpBottles.getXpBottle(ExpBottles.getXpAmount(chest.getInventory().getItem(expBottle)) + amountOfExpPerBlaze, "Mob Grinder"));
                    }

                }

            }
        }
    }
}