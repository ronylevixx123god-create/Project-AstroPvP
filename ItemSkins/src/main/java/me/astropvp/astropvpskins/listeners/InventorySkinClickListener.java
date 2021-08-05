package me.astropvp.astropvpskins.listeners;

import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.commands.CommandSkins;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.utils.InventoryItems;
import org.bukkit.event.inventory.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

public class InventorySkinClickListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void click(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();
        if (item == null) {
            return;
        }
        if (title.equals(CC.translate("&8Astro Item Skins"))) {
            e.setCancelled(true);
            Material material = item.getType();
            ItemSkinType type = (material == Material.DIAMOND_HELMET) ? ItemSkinType.HELMET : (
                    (material == Material.DIAMOND_CHESTPLATE) ? ItemSkinType.CHESTPLATE : (
                            (material == Material.DIAMOND_LEGGINGS) ? ItemSkinType.LEGGINGS : (
                                    (material == Material.DIAMOND_BOOTS) ? ItemSkinType.BOOTS : (
                                            (material == Material.DIAMOND_AXE) ? ItemSkinType.AXE : (
                                                    (material == Material.DIAMOND_SWORD) ? ItemSkinType.SWORD : (
                                                            (material == Material.BOW) ? ItemSkinType.BOW : (
                                                                    (material == Material.IRON_LEGGINGS) ?
                                                                            ItemSkinType.BELT : (
                                                                            (material == Material.IRON_CHESTPLATE) ?
                                                                                    ItemSkinType.AMULET : (
                                                                                    (material == Material.DIAMOND_PICKAXE) ? ItemSkinType.PICKAXE : null)))))))));
            if (type == null) {
                return;
            }
            Inventory inv = InventoryItems.createSkinInventory(type);

            if (e.getCurrentItem().getType() == Material.DIAMOND_LEGGINGS) {
                e.setCancelled(true);
                return;
            }
            e.getWhoClicked().openInventory(inv);
        } else if (title.startsWith(CC.translate("&8Item Skins:"))) {
            if (item.equals(InventoryItems.createBackButton())) {
                Inventory invv = CommandSkins.getInventory();
                e.getWhoClicked().openInventory(invv);
            }
            e.setCancelled(true);
        }
    }
}