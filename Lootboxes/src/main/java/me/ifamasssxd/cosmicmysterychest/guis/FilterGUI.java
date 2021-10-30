package me.ifamasssxd.cosmicmysterychest.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.ifamasssxd.cosmiccoinflip.utils.SlotUtils;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.data.Filter;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.cosmicmysterychest.utils.FilterUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterGUI {

    private Player player;

    private final Filter filter;

    private final PaginatedGui lootboxMenu;



    public PaginatedGui getFilterGUI() {
        return this.lootboxMenu;
    }

    public FilterGUI(Player player, MysteryChest chest) {
        this.player = player;
        if (CosmicMysteryChest.get().getFilters().containsKey(player.getUniqueId()) && CosmicMysteryChest.get().getFilters().get(player.getUniqueId()).containsKey(chest)) {
            this.filter = CosmicMysteryChest.get().getFilters().get(player.getUniqueId()).get(chest);
            this.lootboxMenu = new PaginatedGui(6, 45, "Loot Filter");

        } else {
            player.sendMessage(CC.translate("&cAn error appeared, please report this to an administrator!"));
            this.lootboxMenu = new PaginatedGui(6, 45, "Loot Filter");
            this.filter = null;
            return;
        }

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

        for(ItemStack item : FilterUtils.getItems(filter.getMysteryChest())) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            if (lore.get(lore.size()-1).startsWith(CC.translate("&f&l➥ &fFiltered")) && lore.get(lore.size()-2).equals("")) {
                lore.remove(lore.get(lore.size()-1));
                lore.remove(lore.get(lore.size()-2));
            }

                if (filter.isBlockedItem(item)) {
                    lore.add("");
                    lore.add(CC.translate("&f&l➥ &fFiltered: &atrue"));
                } else {
                    lore.add("");
                    lore.add(CC.translate("&f&l➥ &fFiltered: &cfalse"));
                }
            meta.setLore(lore);
            item.setItemMeta(meta);
            lootboxMenu.addItem(new GuiItem(item, event -> {
                if (filter.isBlockedItem(item)) {
                    filter.removeBlockedLootItem(item);
                } else {
                    filter.addBlockedLootItem(item);
                }
                new FilterGUI(player, chest).getFilterGUI().open(player);
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1f, 1f);
            }));
        }
    }

    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }

}
