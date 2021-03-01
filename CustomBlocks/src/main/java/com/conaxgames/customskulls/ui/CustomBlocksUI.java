package com.conaxgames.customskulls.ui;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.ItemBuilderUtils;
import com.conaxgames.customskulls.util.inventoryui.InventoryUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class CustomBlocksUI {
    public static void open(Player player) {

        InventoryUI inventoryUI = new InventoryUI("Custom Items", 6);
        for (Map.Entry<String, CustomBlock> customBlock : CoreSkulls.customBlocks.entrySet()) {
            inventoryUI.addItem(new InventoryUI.AbstractClickableItem(
                    new ItemBuilderUtils(customBlock.getValue().getItem()).setLore(customBlock.getValue().addLore())
                            .addLoreLine("")
                            .addLoreLine(CC.AQUA + "Click to add to your inventory.")
                            .toItemStack()) {
                @Override
                public void onClick(InventoryClickEvent event) {
                    player.getInventory().addItem(customBlock.getValue().getItem());
                    player.sendMessage(CC.GREEN + "Added " + customBlock.getValue().getName() + CC.GREEN + " to your inventory.");
                }
            });
        }
        player.openInventory(inventoryUI.getCurrentPage());
    }
}
