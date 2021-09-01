package com.conaxgames.customskulls.listeners;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.util.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MobGrinderGUIListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock.getType() != Material.NOTE_BLOCK) return;


        if (AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation())) != null) {
            e.setCancelled(true);
            AbilityData data = AbilityData.getByUUID(AbilityData.getUUID(clickedBlock.getLocation()));
            if (data.getAbilityType() == AbilityType.TRIALS_FAME_AND_FORTUNE) {
                Inventory inventory = Bukkit.createInventory(null, 9, "Mob Grinder Collect");

                ItemStack grindingLootboxItem = new ItemStack(Material.CHEST);
                ItemMeta metaLootbox = grindingLootboxItem.getItemMeta();
                List<String> lootboxLore = new ArrayList<>();
                lootboxLore.add("");
                lootboxLore.add(CC.translate("&a&l* &fAmount&7: &a" + data.collectedLoot.getLootboxes()));
                lootboxLore.add("");
                lootboxLore.add(CC.translate("&7&nLeft-Click&7 to Claim."));
                metaLootbox.setDisplayName(CC.translate("&a&lGrinding Lootboxes Farmed"));
                metaLootbox.setLore(lootboxLore);

                ItemStack expBottleItem = new ItemStack(Material.EXP_BOTTLE);
                ItemMeta metaExp = expBottleItem.getItemMeta();
                metaExp.setDisplayName(CC.translate("&a&lExp Farmed"));
                List<String> expLore = new ArrayList<>();
                expLore.add("");
                expLore.add(CC.translate("&a&l* &fAmount&7: &a" + data.collectedLoot.getExp()));
                expLore.add("");
                expLore.add(CC.translate("&7&nLeft-Click&7 to redeem."));
                metaExp.setDisplayName(CC.translate("&a&lExp Farmed"));
                metaExp.setLore(expLore);

                expBottleItem.setItemMeta(metaExp);
                grindingLootboxItem.setItemMeta(metaLootbox);


                NBTWrapper expWrapper = new NBTWrapper(expBottleItem);
                expWrapper.setString("abilityUUID", data.getUuid());
                expBottleItem = expWrapper.build();

                NBTWrapper lootboxWrapper = new NBTWrapper(grindingLootboxItem);
                lootboxWrapper.setString("abilityUUID", data.getUuid());
                grindingLootboxItem = lootboxWrapper.build();


                inventory.setItem(3, grindingLootboxItem);
                inventory.setItem(5, expBottleItem);
                ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                ItemMeta meta = filler.getItemMeta();
                meta.setDisplayName("");
                filler.setItemMeta(meta);
                inventory.setItem(0, filler);
                inventory.setItem(1, filler);
                inventory.setItem(1, filler);
                inventory.setItem(2, filler);
                inventory.setItem(4, filler);
                inventory.setItem(6, filler);
                inventory.setItem(7, filler);
                inventory.setItem(8, filler);


                p.openInventory(inventory);
                p.sendMessage(CC.translate("&a&l(!)&a Opening the Automatic Mob Grinder Custom Block Collection Chest!"));
                p.playSound(p.getLocation(), Sound.CHEST_OPEN, 5, 5);


            }
        }
    }

    public int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont)
            if (item != null && item.getType() != Material.AIR) {
                i++;
            }
        return 36 - i;
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if (e.getClickedInventory().getTitle().equals("Mob Grinder Collect")) {
            Player p = (Player)e.getWhoClicked();
            if (e.getCursor().getType() == Material.CHEST) {
                NBTWrapper lootboxWrapper = new NBTWrapper(e.getCursor());
                AbilityData data = AbilityData.getByUUID(lootboxWrapper.getString("abilityUUID"));
                int lootboxAmount = data.collectedLoot.getLootboxes();
                int amountLootboxesToRemove = getEmptySlots(p) * 64;
                lootboxAmount -= amountLootboxesToRemove;
                data.collectedLoot.setLootboxes(lootboxAmount);
                //MysteryChest grindingLootbox = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("GrindingLootbox");
                p.getInventory().addItem(new ItemStack(Material.DIAMOND, getEmptySlots(p) * 64));
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 10);
                //TODO Change diamond by grinding Lootbox


            } else if (e.getCursor().getType() == Material.EXP_BOTTLE) {
                NBTWrapper lootboxWrapper = new NBTWrapper(e.getCursor());
                AbilityData data = AbilityData.getByUUID(lootboxWrapper.getString("abilityUUID"));
                double expAmount = data.collectedLoot.getExp();
                p.setExp((float) (p.getExp()+expAmount));
                data.collectedLoot.setExp(data.collectedLoot.getExp()-expAmount);
                p.closeInventory();
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 10);


            }
        }
    }


    @EventHandler
    public void cancelInvClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getName().equals("Mob Grinder Collect")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelView(InventoryOpenEvent e) {
        if (e.getInventory().getTitle().equals("Mob Grinder Collect") && (e.getInventory().getViewers().size() >1)) {
            e.getPlayer().sendMessage(CC.translate("&c&l(!)&c You cannot open the Collector Gui when someone else has it opened."));
            e.setCancelled(true);
        }
    }
}
