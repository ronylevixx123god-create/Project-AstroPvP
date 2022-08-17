package net.astropvp.lootfilter;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import net.astropvp.lootfilter.data.LootFilterData;
import net.astropvp.lootfilter.struct.LootTable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LootFilterAPI {
    public static void giveItem(Player player, LootTable lootTable, ItemStack item) {
        LootFilterData lootFilterData = AstroLootFilterPlugin.getInstance().getLootFilterManager().getLootFilterData(player.getUniqueId());
        if (lootFilterData.isFiltered(lootTable, item)) {
            player.sendMessage(CC.translate("&c&l(!)&c " + ItemUtils.getDisplayName(item) + " &chas been filtered out, type &7\"/lootfilter\"&c to edit your &l" + lootTable.getFancyName() + " Filter&c."));
            return;
        }
        player.sendMessage(CC.translate("&a&l(!)&a You have received " + ItemUtils.getDisplayName(item) + " &afrom the &l" + lootTable.getFancyName() + "&a. Type &7\"/lootfilter\"&a to edit your &l" + lootTable.getFancyName() + " Filter&a."));
        PlayerUtil.giveOrDropProtectedItem(player, item, 60);
    }

    public static boolean isFiltered(Player player, LootTable lootTable, ItemStack item) {
        LootFilterData lootFilterData = AstroLootFilterPlugin.getInstance().getLootFilterManager().getLootFilterData(player.getUniqueId());
        return lootFilterData.isFiltered(lootTable, item);
    }

}
