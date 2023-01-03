package net.astropvp.loottable.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;

import net.astropvp.loottable.struct.LootTable;
import org.bukkit.entity.Player;

@Getter

public class LootTableMainGUI {
    private PaginatedGui mainGUI;

    public LootTableMainGUI(Player player) {
        this.mainGUI = new PaginatedGui(3, 27, "Loottable Menu");
        mainGUI.setDefaultClickAction(event -> event.setCancelled(true));
        for (LootTable tableType : LootTable.values()) {
            mainGUI.addItem(new GuiItem(new ItemBuilder(tableType.getMaterial(), CC.translate(tableType.getColor() + "&l" + tableType.getFancy()), CC.translate("&7Click to open this loottable.")).build(), event -> {
                LootTableEditGUI lootTableEditGUI = new LootTableEditGUI(tableType);
                lootTableEditGUI.getLootEditGUI().open(event.getWhoClicked());
            }));
        }
    }
}
