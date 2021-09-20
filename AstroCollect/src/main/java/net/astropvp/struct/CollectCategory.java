package net.astropvp.struct;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.persistence.Transient;
import java.io.*;
import java.util.List;

public class CollectCategory implements Serializable {
    private CollectType type;

    private transient ItemStack[] items;

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

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, name);
        for(ItemStack item : this.items) {
            inventory.addItem(item);
        }
        return inventory;
    }

    public ItemStack[] getItems() {
        return items;
    }

}
