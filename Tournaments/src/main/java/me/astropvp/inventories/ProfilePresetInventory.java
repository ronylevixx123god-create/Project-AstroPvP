package me.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.matthawkk.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ProfilePresetInventory {
    public static Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 36, "Profile");
        ItemStack potionItem = new ItemStack(Material.POTION, 8);
        Potion potion = new Potion(1);
        potion.setType(PotionType.INSTANT_HEAL);
        potion.setSplash(true);
        potion.apply(potionItem);
        inventory.addItem(potionItem);
        inventory.addItem(potionItem);
        inventory.addItem(potionItem);
        inventory.addItem(potionItem);
        inventory.setItem(27, new ItemBuilder(Material.DIAMOND_SWORD).name(CC.WhiteB + "Weapon").build());
        inventory.setItem(32, new ItemBuilder(Material.SKULL_ITEM).name(CC.WhiteB + "Pet #1").build());
        inventory.setItem(33, new ItemBuilder(Material.SKULL_ITEM).name(CC.WhiteB + "Pet #2").build());
        inventory.setItem(34, new ItemBuilder(Material.EMERALD).name(CC.WhiteB + "Soul Gem").build());
        inventory.setItem(35, new ItemBuilder(Material.ENDER_PEARL).name(CC.WhiteB + "Soul Pearl").build());
        ItemStack singlePotion = new ItemStack(Material.POTION);
        Potion potion1 = new Potion(1);
        potion1.setType(PotionType.INSTANT_HEAL);
        potion1.setSplash(true);
        potion1.apply(singlePotion);
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
                inventory.setItem(i, singlePotion);
        }
        return inventory;
    }
}
