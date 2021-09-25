package net.astropvp.struct;

import net.astropvp.inventories.CategoryInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.persistence.Transient;
import java.io.*;
import java.util.List;

public class CollectCategory implements Serializable {
    private CollectType type;

    private ItemStack[] items;

    private String name;


    public CollectCategory(CollectType type, ItemStack[] items, String name) {
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

    public ItemStack[] getItems() {
        return items;
    }

    public void removeItem(ItemStack item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i] = null;
            }
        }
    }

}
