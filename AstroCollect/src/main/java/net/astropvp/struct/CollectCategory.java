package net.astropvp.struct;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CollectCategory {
    private CollectType type;

    private ItemStack[] items;

    private String name;

    private String killedName;

    public CollectCategory(CollectType type, ItemStack[] items, String name, String killedName) {
        this.type = type;
        this.items = items;
        this.name = name;
        this.killedName = killedName;
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

    public String getKilledName() {
        return killedName;
    }
}
