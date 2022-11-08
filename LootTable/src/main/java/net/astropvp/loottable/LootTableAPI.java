package net.astropvp.loottable;


import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import net.astropvp.loottable.data.LootTable;
import net.astropvp.loottable.data.LootTableManager;
import net.astropvp.loottable.commands.struct.LootRarity;
import net.astropvp.loottable.commands.struct.LootTableType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LootTableAPI {
    private static LootTableManager lootTableManager = LootTablePlugin.getInstance().getLootTableManager();

    public static ItemStack getRandomLootItem(LootTableType lootTableType) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        LootTable lootTable = LootTablePlugin.getInstance().getLootTableManager().getLootTable(lootTableType);
        for(LootRarity lootRarity : LootRarity.getReversed()) {
            if(chance <= lootRarity.getChance()) {
                Random rand = new Random();
                if (lootTable.getLootRarityMap().get(lootRarity).isEmpty()) continue;
                return lootTable.getLootRarityMap().get(lootRarity).get(rand.nextInt(lootTable.getLootRarityMap().get(lootRarity).size()));
            }
        }
        Random rand = new Random();

        return lootTable.getLootRarityMap().get(LootRarity.COMMON).get(rand.nextInt(lootTable.getLootRarityMap().get(LootRarity.COMMON).size()));
    }

    public static List<ItemStack> getAllLootFromLootTable(LootTableType type) {
        List<ItemStack> items = new ArrayList<>();
        for (List<ItemStack> itemStackList : lootTableManager.getLootTable(type).getLootRarityMap().values()) {
            items.addAll(itemStackList);
        }
        items.sort(Comparator.comparing(s -> ItemUtils.getDisplayName(s)));
        return items;

    }



}
