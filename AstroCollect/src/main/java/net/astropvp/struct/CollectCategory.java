package net.astropvp.struct;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CollectCategory {
    private CollectType type;

    private List<ItemStack> items;

    private String name;

    public CollectCategory(CollectType type, List<ItemStack> items, String name) {
        this.type = type;
        this.items = items;
        this.name = name;
    }

    public CollectType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, name);
        for(ItemStack item : this.items) {
            inventory.addItem(item);
        }
        return inventory;
    }
}
