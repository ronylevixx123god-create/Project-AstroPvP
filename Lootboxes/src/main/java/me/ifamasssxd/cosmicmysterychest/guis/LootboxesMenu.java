package me.ifamasssxd.cosmicmysterychest.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LootboxesMenu {
    private final PaginatedGui lootboxMenu;

    public PaginatedGui getExpShopUI() {
        return this.lootboxMenu;
    }


    Map<String, MysteryChest> chests = CosmicMysteryChest.get().getChestManager().getMysteryChestMap();


    public LootboxesMenu(Player player) {

        this.lootboxMenu = new PaginatedGui(6, 45, "Lootbox Menu");
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


        for (MysteryChest chest : chests.values()) {
            ItemStack item = chest.createClaimableItem(false);

            lootboxMenu.addItem(new GuiItem(item, event -> {
                player.getInventory().addItem(item);
                }
            ));
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
