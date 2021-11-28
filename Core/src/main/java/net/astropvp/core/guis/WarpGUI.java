package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarpGUI {
    private PaginatedGui expShopUI;



    public PaginatedGui getLootGUI() {
        return this.expShopUI;
    }





    public WarpGUI(Player player) {


        this.expShopUI = new PaginatedGui(1, 9, "Warp GUI");
        expShopUI.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });




        ItemStack desert = new ItemBuilder(Material.SAND, CC.translate("&6&lDesert Warp"), CC.translate("&7Click to teleport to this warp")).build();
        ItemStack crates = new ItemBuilder(Material.CHEST, CC.translate("&b&lCrates Warp"), CC.translate("&7Click to teleport to this warp")).build();
        ItemStack end = new ItemBuilder(Material.ENDER_STONE, CC.translate("&d&lEnd Warp"), CC.translate("&7Click to teleport to this warp")).build();
        ItemStack flat = new ItemBuilder(Material.OBSIDIAN, CC.translate("&a&lFlat World"), CC.translate("&7Click to teleport to this world")).build();



        expShopUI.setItem(1, 1, new GuiItem((desert), event -> player.performCommand("warp desert")));
        expShopUI.setItem(1, 2, new GuiItem((crates), event -> player.performCommand("warp crates")));
        expShopUI.setItem(1, 3, new GuiItem((end), event -> player.performCommand("warp end")));
        expShopUI.setItem(1, 4, new GuiItem((flat), event -> player.performCommand("warp flat")));





    }


}
