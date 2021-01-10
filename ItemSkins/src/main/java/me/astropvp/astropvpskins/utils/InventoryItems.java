package me.astropvp.astropvpskins.utils;

import com.cosmicpvp.cosmicutils.utils.*;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.utils.Utils;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import java.util.*;

public class InventoryItems {
    public static ItemStack createPane(int id) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)id);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((new StringBuilder(String.valueOf(CC.DarkRedB))).toString());
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItemSkinTemplate(ItemSkinType type) {
        ItemStack item = (type == ItemSkinType.HELMET) ? new ItemStack(Material.DIAMOND_HELMET) : (
                (type == ItemSkinType.CHESTPLATE) ? new ItemStack(Material.DIAMOND_CHESTPLATE) : (
                        (type == ItemSkinType.LEGGINGS) ? new ItemStack(Material.DIAMOND_LEGGINGS) : (
                                (type == ItemSkinType.BOOTS) ? new ItemStack(Material.DIAMOND_BOOTS) : (
                                        (type == ItemSkinType.SWORD) ? new ItemStack(Material.DIAMOND_SWORD) : (
                                                (type == ItemSkinType.AXE) ? new ItemStack(Material.DIAMOND_AXE) : (
                                                        (type == ItemSkinType.BOW) ? new ItemStack(Material.BOW) : (
                                                                (type == ItemSkinType.PICKAXE) ? new ItemStack(Material.DIAMOND_PICKAXE) :
                                                                        null)))))));
        String display = (type == ItemSkinType.HELMET) ? "Helmets" : (
                (type == ItemSkinType.CHESTPLATE) ? "Chestplates" : (
                        (type == ItemSkinType.LEGGINGS) ? "Leggings" : (
                                (type == ItemSkinType.BOOTS) ? "Boots" : (
                                        (type == ItemSkinType.SWORD) ? "Swords" : (
                                                (type == ItemSkinType.AXE) ? "Axes" : (
                                                        (type == ItemSkinType.BOW) ? "Bows" : (
                                                                (type == ItemSkinType.AMULET) ? "Amulets" : (
                                                                        (type == ItemSkinType.BELT) ? "Belts" : (
                                                                                (type == ItemSkinType.PICKAXE) ? "Pickaxes" :
                                                                                        null)))))))));
        if (item == null)
            if (type == ItemSkinType.BELT) {
                item = new ItemStack(Material.IRON_LEGGINGS);
                item = Utils.addSkinNBT(SkinType.CUPID_BELT.name(), item);
            } else if (type == ItemSkinType.AMULET) {
                item = new ItemStack(Material.IRON_CHESTPLATE);
                item = Utils.addSkinNBT(SkinType.CUPID_AMULET.name(), item);
            }
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        byte b;
        int i;
        SkinType[] arrayOfSkinType;
        for (i = (arrayOfSkinType = SkinType.values()).length, b = 0; b < i; ) {
            SkinType x = arrayOfSkinType[b];
            if (ItemSkinType.isSimilarItemSkin(x.getItemSkinType(), type)) {
                String d = ChatColor.stripColor(x.getName());
                d = String.valueOf(CC.GoldB) + "* " + CC.Yellow + d;
                lore.add(d);
            }
            b++;
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
        String display = (type == ItemSkinType.HELMET) ? "Helmets" : (
                (type == ItemSkinType.CHESTPLATE) ? "Chestplates" : (
                        (type == ItemSkinType.LEGGINGS) ? "Leggings" : (
                                (type == ItemSkinType.BOOTS) ? "Boots" : (
                                        (type == ItemSkinType.SWORD) ? "Swords" : (
                                                (type == ItemSkinType.AXE) ? "Axes" : (
                                                        (type == ItemSkinType.BOW) ? "Bows" : (
                                                                (type == ItemSkinType.AMULET) ? "Amulets" : (
                                                                        (type == ItemSkinType.BELT) ? "Belts" : (
                                                                                (type == ItemSkinType.PICKAXE) ? "Pickaxes" :
                                                                                        null)))))))));
        int len = 0;
        byte b1;
        int j;
        SkinType[] arrayOfSkinType1;
        for (j = (arrayOfSkinType1 = SkinType.values()).length, b1 = 0; b1 < j; ) {
            SkinType x = arrayOfSkinType1[b1];
            if (ItemSkinType.isSimilarItemSkin(type, x.getItemSkinType()))
                len++;
            b1++;
        }
        len = (len <= 9) ? 9 : ((len <= 18) ? 18 : ((len <= 27) ? 27 : ((len <= 36) ? 36 : 45)));
        Inventory inventory = Bukkit.createInventory(null, len, "§8Item Skins: " + display);
        int pos = 0;
        byte b2;
        int k;
        SkinType[] arrayOfSkinType2;
        for (k = (arrayOfSkinType2 = SkinType.values()).length, b2 = 0; b2 < k; ) {
            SkinType x = arrayOfSkinType2[b2];
            if (ItemSkinType.isSimilarItemSkin(x.getItemSkinType(), type)) {
                inventory.setItem(pos, x.getItem());
                pos++;
            }
            b2++;
        }
        inventory.setItem(inventory.getSize() - 1, createBackButton());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, createPane(7));
        }
        return inventory;
    }

    public static ItemStack createBackButton() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(String.valueOf(CC.RedB) + "Back");
        meta.setLore(Arrays.asList(new String[] { String.valueOf(CC.Gray) + "Click to return to", String.valueOf(CC.Gray) + "the main menu." }));
        item.setItemMeta(meta);
        return item;
    }
}
