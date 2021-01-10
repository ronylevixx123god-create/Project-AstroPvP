/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.astropvp.astropvpskins.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.ArrayList;
import java.util.Arrays;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryItems {
    public static ItemStack createPane(int id) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.valueOf(CC.DarkRedB));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemSkinTemplate(ItemSkinType type) {
        String display;
        ItemStack item;
        Object object = type == ItemSkinType.HELMET ? new ItemStack(Material.DIAMOND_HELMET) : (type == ItemSkinType.CHESTPLATE ? new ItemStack(Material.DIAMOND_CHESTPLATE) : (type == ItemSkinType.LEGGINGS ? new ItemStack(Material.DIAMOND_LEGGINGS) : (type == ItemSkinType.BOOTS ? new ItemStack(Material.DIAMOND_BOOTS) : (type == ItemSkinType.SWORD ? new ItemStack(Material.DIAMOND_SWORD) : (type == ItemSkinType.AXE ? new ItemStack(Material.DIAMOND_AXE) : (type == ItemSkinType.BOW ? new ItemStack(Material.BOW) : (item = type == ItemSkinType.PICKAXE ? new ItemStack(Material.DIAMOND_PICKAXE) : null)))))));
        String string = type == ItemSkinType.HELMET ? "Helmets" : (type == ItemSkinType.CHESTPLATE ? "Chestplates" : (type == ItemSkinType.LEGGINGS ? "Leggings" : (type == ItemSkinType.BOOTS ? "Boots" : (type == ItemSkinType.SWORD ? "Swords" : (type == ItemSkinType.AXE ? "Axes" : (type == ItemSkinType.BOW ? "Bows" : (type == ItemSkinType.AMULET ? "Amulets" : (type == ItemSkinType.BELT ? "Belts" : (display = type == ItemSkinType.PICKAXE ? "Pickaxes" : null)))))))));
        if (item == null) {
            if (type == ItemSkinType.BELT) {
                item = new ItemStack(Material.IRON_LEGGINGS);
                item = Utils.addSkinNBT(SkinType.CUPID_BELT.name(), item);
            } else if (type == ItemSkinType.AMULET) {
                item = new ItemStack(Material.IRON_CHESTPLATE);
                item = Utils.addSkinNBT(SkinType.CUPID_AMULET.name(), item);
            }
        }
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        SkinType[] arrayOfSkinType = SkinType.values();
        int i = arrayOfSkinType.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            SkinType x = arrayOfSkinType[b];
            if (!ItemSkinType.isSimilarItemSkin(x.getItemSkinType(), type)) continue;
            String d = ChatColor.stripColor((String)x.getName());
            d = String.valueOf(CC.GoldB) + "* " + CC.Yellow + d;
            lore.add(d);
        }
        if (lore.size() != 0) {
            lore.add(0, String.valueOf(CC.GoldB) + "Item Skins:");
            lore.add(0, "");
            lore.add("");
            lore.add(String.valueOf(CC.Gray) + "Click to view category.");
        }
        meta.setDisplayName(String.valueOf(CC.GoldB) + display);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory createSkinInventory(ItemSkinType type) {
        String display = type == ItemSkinType.HELMET ? "Helmets" : (type == ItemSkinType.CHESTPLATE ? "Chestplates" : (type == ItemSkinType.LEGGINGS ? "Leggings" : (type == ItemSkinType.BOOTS ? "Boots" : (type == ItemSkinType.SWORD ? "Swords" : (type == ItemSkinType.AXE ? "Axes" : (type == ItemSkinType.BOW ? "Bows" : (type == ItemSkinType.AMULET ? "Amulets" : (type == ItemSkinType.BELT ? "Belts" : (type == ItemSkinType.PICKAXE ? "Pickaxes" : null)))))))));
        int len = 0;
        SkinType[] arrayOfSkinType1 = SkinType.values();
        int j = arrayOfSkinType1.length;
        for (int b1 = 0; b1 < j; b1 = (int)((byte)(b1 + 1))) {
            SkinType x = arrayOfSkinType1[b1];
            if (!ItemSkinType.isSimilarItemSkin(type, x.getItemSkinType())) continue;
            ++len;
        }
        len = len <= 9 ? 9 : (len <= 18 ? 18 : (len <= 27 ? 27 : (len <= 36 ? 36 : 45)));
        Inventory inventory = Bukkit.createInventory(null, (int)len, (String)("\u00a78Item Skins: " + display));
        int pos = 0;
        SkinType[] arrayOfSkinType2 = SkinType.values();
        int k = arrayOfSkinType2.length;
        for (int b2 = 0; b2 < k; b2 = (int)((byte)(b2 + 1))) {
            SkinType x = arrayOfSkinType2[b2];
            if (!ItemSkinType.isSimilarItemSkin(x.getItemSkinType(), type)) continue;
            inventory.setItem(pos, x.getItem());
            ++pos;
        }
        inventory.setItem(inventory.getSize() - 1, InventoryItems.createBackButton());
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, InventoryItems.createPane(7));
        }
        return inventory;
    }

    public static ItemStack createBackButton() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.valueOf(CC.RedB) + "Back");
        meta.setLore(Arrays.asList(String.valueOf(CC.Gray) + "Click to return to", String.valueOf(CC.Gray) + "the main menu."));
        item.setItemMeta(meta);
        return item;
    }
}

