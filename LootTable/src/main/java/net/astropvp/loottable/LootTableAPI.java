package net.astropvp.loottable;


import net.astropvp.loottable.data.LootTableData;
import net.astropvp.loottable.data.LootTableManager;
import net.astropvp.loottable.struct.LootTable;
import net.astropvp.loottable.utils.RandomUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootTableAPI {
    private static LootTableManager lootTableManager = LootTablePlugin.getInstance().getLootTableManager();

    public static ItemStack getRandomLootItem(LootTable lootTableType) {
        LootTableData lootTable = lootTableManager.getLootTable(lootTableType);
        return RandomUtils.getRandomItem(lootTable.getLootMap());
    }

    public static List<ItemStack> getAllLootFromLootTable(LootTable type) {
        LootTableData lootTable = lootTableManager.getLootTable(type);
        return new ArrayList<>(lootTable.getLootMap().keySet());

    }


}
