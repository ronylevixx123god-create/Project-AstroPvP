package net.astropvp.armorsets.gui;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.ArmorSetWeapon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetGUI {

    private Player player;
    private String title;

    public SetGUI(Player player, String title) {
        this.player = player;
        this.title = title;
    }

    public void open() {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for(ArmorSetType setType : ArmorSetType.values()) {
            ArmorSet set = setType.getArmorSet();
            items.add(set.getHelmet());

            if(set instanceof ArmorSetWeapon) {
                List<ItemStack> weapons = ((ArmorSetWeapon) set).getWeapons();
                items.addAll(weapons);
            }
        }

        int size = (int) (Math.ceil(items.size() / 9d) * 9);
        Inventory inventory = Bukkit.createInventory(this.player, size, this.title);
        for (ItemStack item : items) { inventory.addItem(item); }
        inventory.setItem(size-1, getCompass());
        this.player.openInventory(inventory);
    }

    public ItemStack getCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(CC.translate("&7Exit Menu"));
        compass.setItemMeta(meta);
        return compass;
    }

}
