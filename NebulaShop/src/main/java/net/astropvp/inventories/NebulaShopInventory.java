package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.NebulaShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NebulaShopInventory {
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9, "Nebula Shop");
        inventory.setItem(3, getCurrentItem());
        inventory.setItem(5, getInformationBook());
        return inventory;
    }

    private ItemStack getCurrentItem() {
        ItemStack item = NebulaShop.getInstance().getNebulaShopManager().getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(CC.LightPurpleB + " PRICE: " + CC.White + "$" + NebulaShop.getInstance().getNebulaShopManager().getCurrentPrice());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack getInformationBook() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.LightPurpleB + "Nebula Shop");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "The Nebula shop is a secret");
        lore.add(CC.Gray + "market, only accessible by the");
        lore.add(CC.translate( "&d&l<&5&l&o&nN&d&l&o&ne&5&l&n&ob&d&l&n&ou&5&l&n&ol&d&l&n&oa&5&l> ") + CC.Gray + "rank holders");
        lore.add("");
        lore.add(CC.Gray + "The items in the Nebula shop are");
        lore.add(CC.Gray + "only available for " + CC.GrayU + "limited time");
        lore.add(CC.Gray + "before being replaced with new items.");
        lore.add("");
        lore.add(CC.RedB + "NOTE: " + CC.Red + "You can only purchase items");
        lore.add(CC.Red + "on from the Nebula Shop 1 time per restock");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;

    }
}
