package net.astropvp.lootfilter.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.lootfilter.AstroLootFilterPlugin;
import net.astropvp.lootfilter.struct.LootTable;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter
public class LootFilterMainGUI {
    private final PaginatedGui lootFilterMainGUI;

    public LootFilterMainGUI(UUID uuid) {
        this.lootFilterMainGUI = new PaginatedGui(3, 9, "Loot Filter Menu");
        this.lootFilterMainGUI.setDefaultClickAction(event -> event.setCancelled(true));
        for (LootTable lootTable : LootTable.values()) {
            lootFilterMainGUI.addItem(new GuiItem(new ItemBuilder(lootTable.getGuiItem(), CC.translate(lootTable.getColorCode() + "&l" + lootTable.getFancyName() + " Filter"))
                    .addLoreLine(CC.translate("&7Click to edit this loot filter."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate(lootTable.getColorCode() + "Filtered Items: &f" + AstroLootFilterPlugin.getInstance().getLootFilterManager().getLootFilterData(uuid).getFilteredItemsAmount(lootTable)))
                    .build(), event -> {
            LootFilterSubGUI lootFilterSubGUI = new LootFilterSubGUI(uuid, lootTable);
            lootFilterSubGUI.getLootFilterSubGUI().open(Bukkit.getPlayer(uuid));
            }));
        }
    }
}
