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

public class CollectInventory {
    private final PaginatedGui collectUI;

    public PaginatedGui getCollectUI() {
        return this.collectUI;
    }

    public CollectInventory(Player player) {
        this.collectUI = new PaginatedGui(6, 45, "Collect Menu");
        collectUI.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        collectUI.getFiller().fillBottom(new GuiItem(createFillerItem()));
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

        collectUI.setItem(6, 1, new GuiItem(prevPage, event -> {
            collectUI.previous();
            event.setCancelled(true);
        }));

        collectUI.setItem(6, 9, new GuiItem(nextPage, event -> {
            collectUI.next();
            event.setCancelled(true);
        }));

        PlayerCollect collect = CollectPlugin.getInstance().getCollectManager().getPlayerCollect(player);
        List<CollectCategory> categories = collect.getCategories();
        for (CollectCategory cat : categories) {
            collectUI.addItem(new GuiItem(getCategoryItem(player, cat), event -> {
                new CategoryInventory(player, cat).getCollectUI().open(player);
            }));
        }
    }

    private ItemStack getCategoryItem(Player player, CollectCategory cat) {
        ItemStack item = new ItemStack(cat.getType().getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(cat.getName());
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(CC.WhiteB + "Contains (" + CC.Gray + cat.getItems().length + " items" + CC.WhiteB + ")");
        for(ItemStack i : cat.getItems()) {
            for(String string : allowedList) {
                if(i.getType().toString().contains(string)) {
                    lore.add(CC.WhiteB + " * " + i.getItemMeta().getDisplayName());
                }
            }
        }
        lore.add("");
        lore.add(CC.Gray + "Click to view...");
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    List<String> allowedList = Lists.newArrayList("_CHESTPLATE", "_LEGGINGS", "_BOOTS", "_HELMET", "_SWORD", "_AXE", "_PICKAXE", "HEAD", "EMERALD", "_ENDER", "_PEARL", "BOTTLE", "_STAR", "PAPER");


    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }
}
