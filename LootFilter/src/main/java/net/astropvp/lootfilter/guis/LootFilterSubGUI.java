package net.astropvp.lootfilter.guis;

import com.cosmicpvp.cosmicutils.utils.CC;

import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import net.astropvp.lootfilter.AstroLootFilterPlugin;
import net.astropvp.lootfilter.data.LootFilterData;
import net.astropvp.lootfilter.utils.ItemBuilder;
import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

@Getter
public class LootFilterSubGUI {
    private final PaginatedGui lootFilterSubGUI;

    public LootFilterSubGUI(UUID uuid, LootTable lootTable) {
        this.lootFilterSubGUI = new PaginatedGui(6, 54, lootTable.getFancy() + " Filter");
        this.lootFilterSubGUI.setDefaultClickAction(event -> event.setCancelled(true));

        LootFilterData data = AstroLootFilterPlugin.getInstance().getLootFilterManager().getLootFilterData(uuid);
        for (ItemStack item : LootTableAPI.getAllLootFromLootTable(lootTable)) {
            if (data.isFiltered(lootTable, item)) {
                lootFilterSubGUI.addItem(new GuiItem(createFilteredItem(item), event -> {
                    data.filterItem(lootTable, item);
                    LootFilterSubGUI lootFilterSubGUI = new LootFilterSubGUI(uuid, lootTable);
                    lootFilterSubGUI.getLootFilterSubGUI().open(Bukkit.getPlayer(uuid));
                }));
            } else {
                lootFilterSubGUI.addItem(new GuiItem(item, event -> {
                    data.filterItem(lootTable, item);
                    LootFilterSubGUI lootFilterSubGUI = new LootFilterSubGUI(uuid, lootTable);
                    lootFilterSubGUI.getLootFilterSubGUI().open(Bukkit.getPlayer(uuid));
                }));
            }

        }

        this.lootFilterSubGUI.setItem(6, 9, new GuiItem(createBackItem(), event -> {
            new LootFilterMainGUI(uuid).getLootFilterMainGUI().open(Bukkit.getPlayer(uuid));
        }));
    }

    public ItemStack createFilteredItem(ItemStack filterItem) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData());
        ItemMeta meta = filterItem.getItemMeta().clone();
        ItemMeta newMeta = item.getItemMeta();
        newMeta.setDisplayName(meta.getDisplayName());
        newMeta.setLore(meta.getLore());
        item.setItemMeta(newMeta);
        ItemBuilder itemBuilder = new ItemBuilder(item);
        itemBuilder.setGlowing();
        return itemBuilder.build();
    }

    private ItemStack createBackItem() {
        return new ItemBuilder(Material.ARROW, CC.translate("&cGo back"), CC.translate("&7Click to go back to the main menu")).build();
    }
}
