package com.conaxgames.customskulls.ui;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.ItemBuilderUtils;
import com.conaxgames.customskulls.util.inventoryui.InventoryUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class CustomBlocksAdminUI {
    public static void open(Player player) {

        InventoryUI inventoryUI = new InventoryUI("Custom Items", 6);
        for (Map.Entry<String, CustomBlock> customBlock : CoreSkulls.customBlocks.entrySet()) {
            inventoryUI.addItem(new InventoryUI.AbstractClickableItem(
                    new ItemBuilderUtils(customBlock.getValue().getItem()).setLore(customBlock.getValue().addLore())
                            .addLoreLine("")
                            .addLoreLine(CC.DARK_GRAY + customBlock.getKey())
                            .addLoreLine("")
                            .addLoreLine(CC.AQUA + "Click to add to your inventory.")
                            .toItemStack()) {
                @Override
                public void onClick(InventoryClickEvent event) {
                    giveItem(player, customBlock.getValue(), true);
                }
            });
        }
        player.openInventory(inventoryUI.getCurrentPage());
    }

    public static void giveItem(Player player, CustomBlock customBlock, boolean msg) {
        ItemBuilderUtils item = new ItemBuilderUtils(customBlock.getItem()).setLore(customBlock.addLore());
        player.getInventory().addItem(item.toItemStack());
        if (msg) { player.sendMessage(CC.B_GREEN + "+ " + customBlock.getName()); }
    }
}
