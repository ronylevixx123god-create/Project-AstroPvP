package com.massivecraft.factions.shield;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShieldInventory {
    public static Inventory get() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Faction Shield");
        ShieldManager.getTimes().forEach((key, value) -> inventory.addItem(getTimeItem(key, value)));
        return inventory;
    }

    private static ItemStack getTimeItem(LocalTime start, LocalTime end) {
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.Green + start + CC.Gray + " ----> " + CC.Green + end);
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "Click to select.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
