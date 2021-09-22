package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.CollectPlugin;
import net.astropvp.struct.CollectCategory;
import net.astropvp.struct.PlayerCollect;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CategoryInventory {
    private final PaginatedGui categoryInventory;

    public PaginatedGui getCollectUI() {
        return this.categoryInventory;
    }

    public CategoryInventory(Player player, CollectCategory category) {
        this.categoryInventory = new PaginatedGui(6, 45, category.getName());
        categoryInventory.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        categoryInventory.getFiller().fillBottom(new GuiItem(createFillerItem()));
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
        ItemStack removeCategory = new ItemStack(Material.BARRIER);
        ItemMeta meta = removeCategory.getItemMeta();
        meta.setDisplayName(CC.RedB + "REMOVE CATEGORY");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(CC.Gray + "Use this button to remove this category");
        lore.add(CC.RedB + "WARNING: This Cannot Be Undone");
        meta.setLore(lore);
        removeCategory.setItemMeta(meta);

        categoryInventory.setItem(6, 1, new GuiItem(prevPage, event -> {
            categoryInventory.previous();
            event.setCancelled(true);
        }));

        categoryInventory.setItem(6, 9, new GuiItem(nextPage, event -> {
            categoryInventory.next();
            event.setCancelled(true);
        }));

        categoryInventory.setItem(6, 5, new GuiItem(removeCategory, event -> {
            categoryInventory.close(player);
            CollectPlugin.getInstance().getCollectManager().getPlayerCollect(player).remove(category);
            new CollectInventory(player).getCollectUI().open(player);
            event.setCancelled(true);
        }));

        for(ItemStack item : category.getItems()) {
            categoryInventory.addItem(new GuiItem(item, event -> {
                event.setCancelled(true);
                player.getInventory().addItem(event.getCurrentItem());
                categoryInventory.removeItem(event.getSlot());
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
