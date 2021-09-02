package com.conaxgames.customskulls.tasks;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.util.CustomBlockUtil;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class MobGrinderRunnable extends BukkitRunnable {
    int delay = 1; //every x seconds it will kill a blaze and add shit to the chest
    int amountOfExpPerBlaze = 200;
    ItemStack grinderLootbox = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("GrindingLootbox").createClaimableItem(false);

    @Override
    public void run() {
        for (Map.Entry<String, AbilityData> entry : CoreSkulls.mobGrinderCustomBlocks.entrySet()) {
            String uuid = entry.getKey();
            AbilityData data = entry.getValue();


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
                if (Math.random() < 0.1) {
                    chest.getInventory().addItem(grinderLootbox);
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
                    }
                }
            }
        }
    }
}
