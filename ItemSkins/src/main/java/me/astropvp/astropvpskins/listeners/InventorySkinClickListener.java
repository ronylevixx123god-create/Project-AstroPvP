/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.astropvpskins.listeners;

import me.astropvp.astropvpskins.commands.CommandSkins;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.utils.InventoryItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySkinClickListener
implements Listener {
    @EventHandler(ignoreCancelled=true)
    public void click(InventoryClickEvent e) {
        String title = e.getInventory().getTitle();
        ItemStack item = e.getCurrentItem();
        if (item == null) {
            return;
        }
        if (title.equals("\u00a78Astro Item Skins")) {
            ItemSkinType type;
            e.setCancelled(true);
            Material material = item.getType();
            ItemSkinType itemSkinType = material == Material.DIAMOND_HELMET ? ItemSkinType.HELMET : (material == Material.DIAMOND_CHESTPLATE ? ItemSkinType.CHESTPLATE : (material == Material.DIAMOND_LEGGINGS ? ItemSkinType.LEGGINGS : (material == Material.DIAMOND_BOOTS ? ItemSkinType.BOOTS : (material == Material.DIAMOND_AXE ? ItemSkinType.AXE : (material == Material.DIAMOND_SWORD ? ItemSkinType.SWORD : (material == Material.BOW ? ItemSkinType.BOW : (material == Material.IRON_LEGGINGS ? ItemSkinType.BELT : (material == Material.IRON_CHESTPLATE ? ItemSkinType.AMULET : (type = material == Material.DIAMOND_PICKAXE ? ItemSkinType.PICKAXE : null)))))))));
            if (type == null) {
                return;
            }
            Inventory inv = InventoryItems.createSkinInventory(type);
            e.getWhoClicked().openInventory(inv);
        } else if (title.startsWith("\u00a78Item Skins:")) {
            if (item.equals((Object)InventoryItems.createBackButton())) {
                Inventory invv = CommandSkins.getInventory();
                e.getWhoClicked().openInventory(invv);
            }
            e.setCancelled(true);
        }
    }
}

