package net.astropvp.loottable.data;


import lombok.Getter;

import net.astropvp.loottable.struct.LootTable;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LootTableData implements KeyToSave {
    private Map<ItemStack, Double> lootMap;
    private String lootTableType;

    public LootTableData(LootTable lootTableType) {
        this.lootTableType = lootTableType.name();
        this.lootMap = new HashMap<>();
    }

    public void addItem(double chance, ItemStack item) {
        lootMap.put(item, chance);
    }

    public void removeItem(ItemStack item) {
        lootMap.remove(item);
    }


    @Override
    public String getKey() {
        return lootTableType;
    }

    @Override
    public void setKey(String key) {
        this.lootTableType = key;
    }
}
