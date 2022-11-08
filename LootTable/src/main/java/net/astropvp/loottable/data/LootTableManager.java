package net.astropvp.loottable.data;


import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.commands.struct.LootTableType;
import net.astropvp.storage.StoragePlugin;
import net.astropvp.storage.utils.SaveLoadManager;

public class LootTableManager extends SaveLoadManager<LootTable> {
    public LootTableManager(LootTablePlugin plugin) {
        super(plugin, StoragePlugin.getInstance().getGson(), "/data", LootTable.class);
    }

    public LootTable getLootTable(LootTableType lootTableType) {
        if (!contains(lootTableType.name())) {
            LootTable lootTable = new LootTable(lootTableType);
            addToCache(lootTable);
        }
        return getFromCache(lootTableType.name());

    }
}
