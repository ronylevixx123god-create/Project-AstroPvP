package me.astropvp.astropvpskins.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.ArrayList;
import java.util.Arrays;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemSkinSubInventory {
    public static void openInventory(Player player, ItemSkinType type) {
        String display = (type == ItemSkinType.HELMET) ? "Helmets" : ((type == ItemSkinType.BACKPACK) ? "Backpacks" : ((type == ItemSkinType.LEGGINGS) ? "Leggings" : ((type == ItemSkinType.BOOTS) ? "Boots" : ((type == ItemSkinType.SWORD) ? "Swords" : ((type == ItemSkinType.AXE) ? "Axes" : ((type == ItemSkinType.BOW) ? "Bows" : ((type == ItemSkinType.AMULET) ? "Amulets" : ((type == ItemSkinType.BELT) ? "Belts" : ((type == ItemSkinType.PICKAXE) ? "Pickaxes" : null)))))))));
        int len = 0;
        byte b1;
        int j;
        SkinType[] arrayOfSkinType1;
        for (j = (arrayOfSkinType1 = SkinType.values()).length, b1 = 0; b1 < j; ) {
            SkinType x = arrayOfSkinType1[b1];
            if (ItemSkinType.isSimilarItemSkin(type, x.getItemSkinType()))
                len++;
            b1 = (byte)(b1 + 1);
        }
        len = (len <= 9) ? 9 : ((len <= 18) ? 18 : ((len <= 27) ? 27 : ((len <= 36) ? 36 : 45)));
        Inventory inventory = Bukkit.createInventory(null, len, CC.translate("&8Item Skins: ") + display);
        int pos = 0;
        byte b2 = 0;
        SkinType[] arrayOfSkinType2;
        for (int k = (arrayOfSkinType2 = SkinType.values()).length; b2 < k; ) {
            SkinType x = arrayOfSkinType2[b2];
            if (ItemSkinType.isSimilarItemSkin(x.getItemSkinType(), type)) {
                inventory.setItem(pos, x.getItem());
                pos++;
            }
            b2 = (byte)(b2 + 1);
        }
        inventory.setItem(inventory.getSize() - 1, createBackButton());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, ItemSkinsInventory.createPane(7));
        }
        player.openInventory(inventory);
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
