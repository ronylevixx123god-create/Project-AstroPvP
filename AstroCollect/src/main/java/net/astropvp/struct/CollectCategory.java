package net.astropvp.struct;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

public class CollectCategory implements Serializable {
    private CollectType type;

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public ItemStack[] getItems() {
        return items;
    }

    private ItemStack[] items;

    private String name;

    private final long timeCollected;



    public CollectCategory(CollectType type, ItemStack[] items, String name, long timeCollected) {
        this.type = type;
        this.items = items;
        this.name = name;
        this.timeCollected = timeCollected;
    }

    public CollectType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public long getTimeCollected() {
        return timeCollected;
    }


    public boolean isExpired() {
        return System.currentTimeMillis() > timeCollected + 86400000;
    }

    public ItemStack[] removeItem(ItemStack item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                items[i]=new ItemStack(Material.AIR);
                return items;
            }
        }
        return items;
    }

    public void sort() {
        Arrays.sort(items, Comparator.comparing(a -> a.getItemMeta().getDisplayName()));

    }


    public boolean onlyContainsAir() {
        for (ItemStack item : items) {
            if (item.getType() != Material.AIR) {
                return false;

            }
        }
        return true;
    }

}
