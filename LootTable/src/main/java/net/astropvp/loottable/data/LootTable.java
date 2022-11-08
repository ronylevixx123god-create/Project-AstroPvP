package net.astropvp.loottable.data;


import lombok.Getter;

import net.astropvp.loottable.commands.struct.LootRarity;
import net.astropvp.loottable.commands.struct.LootTableType;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class LootTable implements KeyToSave {
    private final Map<LootRarity, List<ItemStack>> lootRarityMap;
    private LootTableType lootTableType;

    public LootTable(LootTableType lootTableType) {
        this.lootTableType = lootTableType;
        this.lootRarityMap = new HashMap<>();
        for (LootRarity rarity : LootRarity.values()) {
            lootRarityMap.put(rarity, new ArrayList<>());
        }
    }

    public void addItem(LootRarity lootRarity, ItemStack item) {
        lootRarityMap.get(lootRarity).add(item);
    }

    public void removeItem(LootRarity lootRarity, ItemStack item) {
        lootRarityMap.get(lootRarity).remove(item);
    }


    @Override
    public String getKey() {
        return lootTableType.name();
    }

    @Override
    public void setKey(String key) {
        this.lootTableType = LootTableType.valueOf(key);
    }
}
