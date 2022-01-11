package me.astropvp.cratekeys.inventory;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.astropvp.cratekeys.enums.CrateType;

import me.astropvp.cratekeys.filter.FilterManager;
import me.astropvp.cratekeys.filter.FilterPlayer;
import me.astropvp.cratekeys.filter.impl.CrateKeyFilter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LootPreviewGUI {
    private PaginatedGui expShopUI;
    private CrateType type;



    public PaginatedGui getLootGUI() {
        return this.expShopUI;

    }


    public LootPreviewGUI(Player player, CrateType type) {
        this.type = type;

        this.expShopUI = new PaginatedGui(6, 45, type.getName() + " Crate Loot Preview");




        Map<ItemStack, Double> itemList = AstroCrateKeysPlugin.getCachedCrateLoot().get(type);


        expShopUI.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });

        expShopUI.getFiller().fillBottom(new GuiItem(createFillerItem()));

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

        expShopUI.setItem(6, 1, new GuiItem(prevPage, event -> {
            expShopUI.previous();
            event.setCancelled(true);
        }));

        expShopUI.setItem(6, 9, new GuiItem(nextPage, event -> {
            expShopUI.next();
            event.setCancelled(true);
        }));

        expShopUI.setItem(6, 5, new GuiItem(createGUIItem(), event -> expShopUI.close(player)));

        FilterPlayer filterPlayer = FilterManager.getInstance().getFilterPlayer(player);
        CrateKeyFilter filter = filterPlayer.hasFilter("crate_key_" + type.name().toLowerCase()) ? (CrateKeyFilter) filterPlayer.getFilter("crate_key_" + type.name().toLowerCase()) : (CrateKeyFilter) filterPlayer.registerFilter(new CrateKeyFilter(type));
        for (Map.Entry<ItemStack, Double> entry : itemList.entrySet()) {
            ItemStack item = entry.getKey().clone();
            double chance = entry.getValue();

            ItemBuilder itemBuilder = new ItemBuilder(item);
            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine(CC.translate("&6&lCHANCE: &f") + chance + "%");
            itemBuilder.addLoreLine("");
            itemBuilder.addLoreLine("&6&lFILTERED: " + (filter.isFiltered(item) ? "&a&lTrue" : "&c&lFalse"));
            this.expShopUI.addItem(new GuiItem(itemBuilder.build(), event -> {
                filter.setFiltered(item, !filter.isFiltered(item));
                List<String> lore = itemBuilder.build().getItemMeta().getLore();
                lore = lore.stream().filter(s -> s.startsWith(CC.strip("FILTERED: "))).collect(Collectors.toList());
                this.expShopUI.setItem(event.getSlot(), new GuiItem(itemBuilder.lore(lore).build()));
                this.expShopUI.update();
            }));
        }


    }


    public ItemStack createGUIItem() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.RedB + "Exit Preview");
        List<String> lore = Lists.newArrayList();
        lore.add(CC.Gray + "Click this to exit the loot preview");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }

}


