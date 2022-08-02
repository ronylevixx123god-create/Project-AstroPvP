package net.astropvp.lootfilter.data;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.astropvp.lootfilter.struct.LootTable;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class LootFilterData implements KeyToSave {

    private UUID uuid;
    private Map<LootTable, List<ItemStack>> filteredItems;

    public void filterItem(LootTable lootTable, ItemStack item) {
        if (!filteredItems.containsKey(lootTable)) {
            List<ItemStack> itemList = new ArrayList<>();
            getPlayer().sendMessage(CC.translate("&a&l(!)&a Added " + item.getItemMeta().getDisplayName() + " &ato " + lootTable.getColorCode() + "&l" + lootTable.getFancyName() + " &aFilter."));
            itemList.add(item);
            filteredItems.put(lootTable, itemList);
        } else if (filteredItems.get(lootTable).contains(item)) {
            getPlayer().sendMessage(CC.translate("&c&l(!)&c Removed " + item.getItemMeta().getDisplayName() + " &cfrom " + lootTable.getColorCode() + "&l" + lootTable.getFancyName() + " &cFilter."));
            filteredItems.get(lootTable).remove(item);
        } else {
            getPlayer().sendMessage(CC.translate("&a&l(!)&a Added " + item.getItemMeta().getDisplayName() + " &ato " + lootTable.getColorCode() + "&l" + lootTable.getFancyName() + " &aFilter."));
            filteredItems.get(lootTable).add(item);
        }
    }

    public boolean isFiltered(LootTable lootTable, ItemStack item) {
        if (!filteredItems.containsKey(lootTable)) {
            return false;
        }
        return filteredItems.get(lootTable).contains(item);
    }

    public int getFilteredItemsAmount(LootTable lootTable) {
        if (!filteredItems.containsKey(lootTable)) {
            return 0;
        }
        return filteredItems.get(lootTable).size();
    }

    public Player getPlayer () {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public String getKey() {
        return uuid.toString();
    }

    @Override
    public void setKey(String s) {
        this.uuid = UUID.fromString(s);
    }
}
