package net.astropvp.loottable.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import lombok.Getter;

import net.astropvp.loottable.data.LootTable;
import net.astropvp.loottable.struct.LootRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class LootTableRarityGUI {
    private PaginatedGui rarityGUI;
    public LootTableRarityGUI(Player player, LootTable lootTable) {
        this.rarityGUI = new PaginatedGui(3, 18, lootTable.getLootTableType().getFancy() + " Rarities");
        rarityGUI.setDefaultClickAction(event -> event.setCancelled(true));
        this.rarityGUI.setItem(3, 1, new GuiItem(createPreviousItem(), event -> {
            LootTableMainGUI mainGUI = new LootTableMainGUI((Player) event.getWhoClicked());
            mainGUI.getMainGUI().open(((Player) event.getWhoClicked()).getPlayer());
        }));

        for (LootRarity lootRarity : LootRarity.values()) {
            rarityGUI.addItem(new GuiItem(new ItemBuilder(Material.STAINED_GLASS_PANE).data(lootRarity.getDyeColor().getData()).name(CC.translate(lootRarity.getColor() + "&l" + lootRarity.getFancy())).addLoreLine(CC.translate("&7Click to add loot to this rarity.")).build(), event -> {
                LootTableEditGUI lootTableEditGUI = new LootTableEditGUI(lootTable, lootRarity);
                lootTableEditGUI.getLootEditGUI().open(player);
            }));
        }
    }

    public ItemStack createPreviousItem() {
        return new ItemBuilder(Material.ARROW, CC.translate("&c<- Go Back"), CC.translate("&7Right-Click&7 to go back.")).build();
    }
}
