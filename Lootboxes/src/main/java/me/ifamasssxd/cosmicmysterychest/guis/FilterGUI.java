package me.ifamasssxd.cosmicmysterychest.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.data.LootboxFilterData;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicmysterychest.utils.FilterUtils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FilterGUI {

    private Player player;

    private final LootboxFilterData filter;

    private final PaginatedGui lootboxMenu;


    public PaginatedGui getFilterGUI() {
        return this.lootboxMenu;
    }

    public FilterGUI(Player player, MysteryChest chest) {
        this.player = player;
            this.filter = CosmicMysteryChest.get().getFilterManager().getLootFilterData(player.getUniqueId());
            this.lootboxMenu = new PaginatedGui(6, 45, "Loot Filter");

        lootboxMenu.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });

        lootboxMenu.getFiller().fillBottom(new GuiItem(createFillerItem()));

        ItemStack prevPage = new ItemStack(Material.ARROW);
        ItemMeta prevMeta = prevPage.getItemMeta();
        prevMeta.setDisplayName(CC.translate("&c<- Previous Page"));
        List<String> prevLore = new ArrayList<>();
        prevLore.add(CC.translate("&7Right-Click&7 to go to the previous page."));
        prevMeta.setLore(prevLore);
        prevPage.setItemMeta(prevMeta);

        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextPage.getItemMeta();
        nextMeta.setDisplayName(CC.translate("&aNext Page ->"));
        List<String> nextLore = new ArrayList<>();
        nextLore.add(CC.translate("&7Right-Click&7 to go to the next page."));
        nextMeta.setLore(nextLore);
        nextPage.setItemMeta(nextMeta);

        lootboxMenu.setItem(6, 1, new GuiItem(prevPage, event -> {
            lootboxMenu.previous();
            event.setCancelled(true);
        }));

        lootboxMenu.setItem(6, 9, new GuiItem(nextPage, event -> {
            lootboxMenu.next();
            event.setCancelled(true);
        }));

        for (ItemStack item : FilterUtils.getItems(chest)) {
            ItemStack copyItem = item.clone();
            if (filter.isFiltered(chest.getInternalName(), item)) {
                ItemMeta meta = copyItem.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore.contains(CC.translate("&f&l➥ &f&lFILTERED"))) {
                    lore.remove(lore.size() - 1);
                    lore.remove(lore.size() - 1);
                }
                lore.add("");
                lore.add(CC.translate("&f&l➥ &f&lFILTERED: &aTrue"));
                meta.setLore(lore);
                copyItem.setItemMeta(meta);
            } else {
                ItemMeta meta = copyItem.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore.contains(CC.translate("&f&l➥ &f&lFILTERED"))) {
                    lore.remove(lore.size() - 1);
                    lore.remove(lore.size() - 1);
                }
                lore.add("");
                lore.add(CC.translate("&f&l➥ &f&lFILTERED: &cFalse"));
                meta.setLore(lore);
                copyItem.setItemMeta(meta);
            }
            lootboxMenu.addItem(new GuiItem(copyItem, event -> {
                if (event.isRightClick() && event.isShiftClick() && event.getWhoClicked().isOp()) {
                    chest.removeItem(item);
                    FilterGUI filterGUI = new FilterGUI(player, chest);
                    filterGUI.getFilterGUI().open(player);
                    player.sendMessage(CC.translate("&c&l(!)&c Removed " + ItemUtils.getDisplayName(item) + "&c from " + chest.getDisplayName()));
                    return;
                }
                filter.filterItem(chest.getInternalName(),item);
                FilterGUI filterGUI = new FilterGUI(player, chest);
                filterGUI.getFilterGUI().open(player);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
            }));
        }
    }

    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }

}
