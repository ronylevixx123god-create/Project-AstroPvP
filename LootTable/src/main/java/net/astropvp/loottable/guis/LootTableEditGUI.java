package net.astropvp.loottable.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;

import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.struct.LootTable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
public class LootTableEditGUI {
    private PaginatedGui lootEditGUI;

    public LootTableEditGUI(LootTable lootTableType) {
        this.lootEditGUI = new PaginatedGui(6, 54, lootTableType.getFancy() + " Loot");
        this.lootEditGUI.setItem(6, 1, new GuiItem(createPreviousItem(), event -> {
            LootTableMainGUI lootTableMainGUI = new LootTableMainGUI((Player) event.getWhoClicked());
            lootTableMainGUI.getMainGUI().open(event.getWhoClicked());
        }));

        lootEditGUI.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        for (Map.Entry<ItemStack, Double> entry : LootTablePlugin.getInstance().getLootTableManager().getLootTable(lootTableType).getLootMap().entrySet()) {
            ItemStack item = entry.getKey();
            double chance = entry.getValue();
            if (ItemUtils.isAirOrNull(item)) continue;
            lootEditGUI.addItem(new GuiItem(new ItemBuilder(item.clone()).addLoreLine("").addLoreLine(CC.translate(lootTableType.getColor() + "Chance: &f" + chance + "%")).addLoreLine("").addLoreLine(CC.translate("&cRight-Click &7items in gui to remove")).build(), event -> {
                if (event.isRightClick()) {
                    event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Removed " + ItemUtils.getDisplayName(item) + "&c from the " + lootTableType.getFancy() + " - " + lootTableType.getFancy()));
                    lootEditGUI.removeItem(event.getSlot());
                    LootTablePlugin.getInstance().getLootTableManager().getLootTable(lootTableType).removeItem(item);
                    LootTableEditGUI editGUI = new LootTableEditGUI(lootTableType);
                    editGUI.getLootEditGUI().open(event.getWhoClicked());
                } else {
                    event.getWhoClicked().getInventory().addItem(item);
                }
            }));
        }
    }

    public ItemStack createPreviousItem() {
        return new ItemBuilder(Material.ARROW, CC.translate("&c<- Go Back"), CC.translate("&7Right-Click&7 to go back.")).build();
    }
}
