package net.astropvp.struct;

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

    public List<ItemStack> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }
}
