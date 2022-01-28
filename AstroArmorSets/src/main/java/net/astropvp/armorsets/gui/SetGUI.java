package net.astropvp.armorsets.gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetGUI {

    private final Player player;
    private final String title;

    public SetGUI(Player player, String title) {
        this.player = player;
        this.title = title;
    }

    public void openBaseGUI() {
        player.closeInventory();
        int size = (int) (Math.ceil(ArmorSetType.values().length / 9d) * 9);
        Inventory inventory = Bukkit.createInventory(player, size, title);

        for(ArmorSetType setType : ArmorSetType.values()) {
            ItemStack item = new ItemStack(setType.getDisplayItem());
            item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(setType.getDisplay());
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            inventory.addItem(item);
        }

        inventory.setItem(size-1, getCompass());
        player.openInventory(inventory);
    }

    public void openSubGUI(ArmorSetType setType) {
        player.closeInventory();
        Inventory inventory = Bukkit.createInventory(player, 9, title);

        for(ItemStack item : setType.getItems()) {
            inventory.addItem(item);
        }

        inventory.setItem(8, getFeather());
        player.openInventory(inventory);
    }

    public ItemStack getCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(CC.translate("&7Exit Menu"));
        compass.setItemMeta(meta);
        return compass;
    }

    public ItemStack getFeather() {
        ItemStack feather = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta = feather.getItemMeta();
        meta.setDisplayName(CC.translate("&7Return to Menu"));
        feather.setItemMeta(meta);
        return feather;
    }

}
