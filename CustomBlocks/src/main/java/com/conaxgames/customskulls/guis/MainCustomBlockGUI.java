package com.conaxgames.customskulls.guis;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.SaveCustomBlocks;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.inventoryui.TimeUtil;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class MainCustomBlockGUI {
    private final PaginatedGui customBlockGui;

    public MainCustomBlockGUI(AbilityData data, Block block) {
        this.customBlockGui = new PaginatedGui(3, 27, "Custom Block Menu");
        ItemStack activateButton = new ItemBuilder(Material.STONE_BUTTON, CC.translate("&a&lActivate Button"))
                .addLoreLine(CC.translate("&7Click to activate this custom block"))
                .addLoreLine("")
                .addLoreLine(CC.translate("&6Expires in: &f" + ((data.getLastActivated() + 3600000) - System.currentTimeMillis() <= 0 ? 0 : TimeUtil.millisToRoundedTime( (data.getLastActivated() + 3600000) - System.currentTimeMillis()))))
                .addLoreLine(CC.translate("&6Status: " + (data.isActive() ? "&ayes" : "&cno"))).build();


        this.customBlockGui.setDefaultClickAction(event -> event.setCancelled(true));

        this.customBlockGui.setItem(13, new GuiItem(activateButton, event -> {
            if (!data.isActive()) {
                data.setLastActivated(System.currentTimeMillis());
                SaveCustomBlocks.saveCustomBlock((Player) event.getWhoClicked(), data.getAbilityType(), block);
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c This custom block is already activated!"));
            }
        }));
    }
}
