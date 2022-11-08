package net.astropvp.loottable.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;

import net.astropvp.loottable.LootTablePlugin;
import net.astropvp.loottable.data.LootTable;
import net.astropvp.loottable.commands.struct.LootRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class LootTableEditGUI {
    private PaginatedGui lootEditGUI;

    public LootTableEditGUI(LootTable lootTable, LootRarity lootRarity) {
        this.lootEditGUI = new PaginatedGui(6, 54, lootTable.getLootTableType().getFancy() + " - " + lootRarity.getFancy());
        this.lootEditGUI.setItem(6, 1, new GuiItem(createPreviousItem(), event -> {
            LootTableRarityGUI lootTableRarityGUI = new LootTableRarityGUI((Player) event.getWhoClicked(), lootTable);
            lootTableRarityGUI.getRarityGUI().open(((Player) event.getWhoClicked()).getPlayer());
        }));

        lootEditGUI.setDefaultClickAction(event -> {
            if (event.getClickedInventory() == event.getWhoClicked().getOpenInventory().getBottomInventory() && event.isLeftClick()) {
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getType() == Material.AIR) return;
                event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Added " + ItemUtils.getDisplayName(event.getCurrentItem()) + "&c to the " + lootTable.getLootTableType().getFancy() + " - " + lootRarity.getFancy()));
                lootTable.addItem(lootRarity,event.getCurrentItem().clone());
            }
            event.setCancelled(true);
            LootTableEditGUI editGUI = new LootTableEditGUI(lootTable, lootRarity);
            editGUI.getLootEditGUI().open(event.getWhoClicked());
        });
        for (ItemStack item : LootTablePlugin.getInstance().getLootTableManager().getLootTable(lootTable.getLootTableType()).getLootRarityMap().get(lootRarity)) {
            if (item == null) continue;
            if (item.getType() == Material.AIR) continue;
            lootEditGUI.addItem(new GuiItem(new ItemBuilder(item.clone()).addLoreLine("").addLoreLine(CC.translate("&aLeft-Click &7items in your inv to add")).addLoreLine(CC.translate("&cRight-Click &7items in gui to remove")).build(), event -> {
                if (event.isRightClick()) {
                    event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Removed " + ItemUtils.getDisplayName(item) + "&c from the " + lootTable.getLootTableType().getFancy() + " - " + lootRarity.getFancy()));
                    lootEditGUI.removeItem(event.getSlot());
                    lootTable.removeItem(lootRarity, item);
                    LootTableEditGUI editGUI = new LootTableEditGUI(lootTable, lootRarity);
                    editGUI.getLootEditGUI().open(event.getWhoClicked());

                }
            }));
        }
    }

    public ItemStack createPreviousItem() {
        return new ItemBuilder(Material.ARROW, CC.translate("&c<- Go Back"), CC.translate("&7Right-Click&7 to go back.")).build();
    }
}
