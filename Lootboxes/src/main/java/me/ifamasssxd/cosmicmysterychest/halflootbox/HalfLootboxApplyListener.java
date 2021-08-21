package me.ifamasssxd.cosmicmysterychest.halflootbox;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class HalfLootboxApplyListener implements Listener {


    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    @EventHandler
    public void onApply(InventoryClickEvent e) throws PrefixedException {

        ItemStack clicked = e.getCurrentItem();
        ItemStack cursor = e.getCursor();


        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory() != e.getView().getBottomInventory()) {
            return;
        }

        if (clicked == null || clicked.getType() == Material.AIR || cursor == null || cursor.getType() == Material.AIR) {
            return;
        }
        NBTWrapper clickedWrapper = new NBTWrapper(clicked);
        NBTWrapper cursorWrapper = new NBTWrapper(cursor);

        if (cursorWrapper == null || clickedWrapper == null) {

            return;
        }


        if (HalfLootbox.isHalfLootboxItem(cursor) && HalfLootbox.isHalfLootboxItem(clicked)) {
            if (cursor.getAmount() > 1 || clicked.getAmount() > 1) {
                player.sendMessage(CC.translate("&c&l(!)&c You cannot apply stacked half lootboxes!"));
                return;
            }
            HalfLootboxType halfLootboxTypeCursor = HalfLootbox.getHalfLootboxType(cursor);
            HalfLootboxType halfLootboxTypeClicked = HalfLootbox.getHalfLootboxType(clicked);


            if (halfLootboxTypeClicked == HalfLootboxType.LEFT && halfLootboxTypeCursor == HalfLootboxType.RIGHT || halfLootboxTypeClicked == HalfLootboxType.RIGHT && halfLootboxTypeCursor == HalfLootboxType.LEFT) {

                MysteryChest cosmicMysteryChestCursor = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get(new NBTWrapper(cursor).getString("lootbox_name"));
                MysteryChest cosmicMysteryChestClicked = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get(new NBTWrapper(clicked).getString("lootbox_name"));


                if (!cosmicMysteryChestClicked.getInternalName().equals(cosmicMysteryChestCursor.getInternalName())) {
                    return;
                }


                e.setCancelled(true);
                e.getWhoClicked().setItemOnCursor(null);
                e.setCurrentItem(cosmicMysteryChestCursor.createClaimableItem(false));


                player.sendMessage(CC.translate("&a&l(!)&a You have created " + cosmicMysteryChestCursor.getDisplayName()));
                player.playSound(player.getLocation(), Sound.ANVIL_USE, 10, 10);
            }
        }


    }


}

