package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import me.astropvp.astrospawners.utils.MobCoinUtils;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
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
    int amountOfExpPerBlaze = 100;

    @Override
    public void run() {
        for (MobGrinderCache mobGrinderCache : CoreSkulls.getGrinderBlocks()) {
            if (mobGrinderCache == null) continue;
            if (!mobGrinderCache.getAbilityData().isActive()) continue;
            boolean playerFound = mobGrinderCache.isPlayerNearby();
            if (!playerFound) continue;



            Location chestLocation = mobGrinderCache.getChestLocation();

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
            if (chance < 0.01) {
                chest.getInventory().addItem(CoreSkulls.getStellarKey());
            } else if (chance < 0.03) {
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
                ItemStack itemStack = chest.getInventory().getItem(i);
                if (itemStack == null || (!MobCoinUtils.isCoinsNote(itemStack) && !ExpBottles.isXpBottle(itemStack))) {
                    continue;
                }

                if (expBottleFound && mobCoinsFound) break;

                if (MobCoinUtils.isCoinsNote(itemStack) && !mobCoinsFound) {
                    mobcoin = i;
                    mobCoinsFound = true;
                }

                if (ExpBottles.isXpBottle(itemStack) && !expBottleFound) {
                    expBottle = i;
                    expBottleFound = true;
                }

                if (expBottle == -1 || mobcoin == -1) continue;

                if (Math.random() < 0.15) {
                    chest.getInventory().setItem(mobcoin, MobCoinUtils.createCoinsNote("Mob Grinder", MobCoinUtils.getCoinsAmountFromNote(chest.getInventory().getItem(mobcoin)) + 1));
                }


                chest.getInventory().setItem(expBottle, ExpBottles.getXpBottle(ExpBottles.getXpAmount(chest.getInventory().getItem(expBottle)) + amountOfExpPerBlaze, "Mob Grinder"));


            }

        }

    }
}
