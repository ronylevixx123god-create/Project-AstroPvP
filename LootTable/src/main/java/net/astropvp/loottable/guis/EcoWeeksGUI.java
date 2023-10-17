package net.astropvp.loottable.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;
import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.struct.ECO_UPDATE;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class EcoWeeksGUI {
    private PaginatedGui ecoWeeksGUI;

    public EcoWeeksGUI(Player player, LootTable tableType) {
        this.ecoWeeksGUI = new PaginatedGui(3, 9, "Select an Eco Update");
        ecoWeeksGUI.setDefaultClickAction(event -> event.setCancelled(true));
        for (ECO_UPDATE ecoUpdate : ECO_UPDATE.values()) {
            ecoWeeksGUI.addItem(new GuiItem(new ItemBuilder(Material.CHEST, CC.translate("&6&l" + ecoUpdate.getFancy() + " &7(Week " + ecoUpdate.getWeek() + ")"), CC.translate("&7Click to open the loot for this eco update.")).addLoreLine("").addLoreLine(CC.translate("&a&nSHIFT-RIGHT&a Click to update eco to this eco update")).addLoreLine("").addLoreLine(CC.translate("&c&l" + ecoUpdate.getWeek())).build(), event -> {
                if (event.isRightClick() && event.isShiftClick()) {
                    LootTablePlugin.getInstance().getLootTableManager().getLootTable(tableType).setCurrentEcoUpdateLoot(ecoUpdate);
                    event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Updated the " + tableType.getFancy() + " Loot to the: &f" + ecoUpdate.getFancy() + " &7Eco update"));
                }
                LootTableMainGUI lootTableMainGUI = new LootTableMainGUI((Player) event.getWhoClicked());
                lootTableMainGUI.getMainGUI().open(event.getWhoClicked());
            }));
        }
    }
}
