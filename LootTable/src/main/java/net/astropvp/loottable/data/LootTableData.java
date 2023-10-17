package net.astropvp.loottable.data;


import lombok.Getter;

import net.astropvp.loottable.struct.ECO_UPDATE;
import net.astropvp.loottable.struct.LootTable;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
public class LootTableData implements KeyToSave {
    private Map<ECO_UPDATE, Map<ItemStack, Double>> ecoUpdateMap;
    private ECO_UPDATE currentEcoUpdateLoot;
    private String lootTableType;

    public LootTableData(LootTable lootTableType) {
        this.lootTableType = lootTableType.name();
        this.ecoUpdateMap = new HashMap<>();
        this.currentEcoUpdateLoot = ECO_UPDATE.SOTW;
    }

    public void addItem(ECO_UPDATE ecoUpdate,double chance, ItemStack item) {
        if (!ecoUpdateMap.containsKey(ecoUpdate)) {
            Map<ItemStack, Double> newLootMap = new HashMap<>();
            ecoUpdateMap.put(ecoUpdate, newLootMap);
        }
        Map<ItemStack, Double> currentLoot = ecoUpdateMap.get(ecoUpdate);
        currentLoot.put(item.clone(), chance);
    }

    public void removeItem(ECO_UPDATE ecoUpdate, ItemStack item) {
        ecoUpdateMap.get(ecoUpdate).remove(item);
    }

    public Map<ItemStack, Double> getLootFromEcoUpdate(ECO_UPDATE update) {
        if (!ecoUpdateMap.containsKey(update)) {
            Map<ItemStack, Double> loot = new HashMap<>();
            ecoUpdateMap.put(update, loot);
        }
        return ecoUpdateMap.get(update);
    }

    public void setCurrentEcoUpdateLoot(ECO_UPDATE ecoUpdate) {
        this.currentEcoUpdateLoot = ecoUpdate;
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