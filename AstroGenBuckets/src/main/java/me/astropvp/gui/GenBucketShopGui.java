package me.astropvp.gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.GenBucketPlugin;
import me.astropvp.struct.GenBucket;
import me.astropvp.struct.GenBucketDirection;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GenBucketShopGui {
    private final PaginatedGui expShopUI;

    public PaginatedGui getExpShopUI() {
        return this.expShopUI;
    }


    public GenBucketShopGui(Player player) {

        GenBucket cobblestoneGenBucket = GenBucketPlugin.getInstance().getBucketHandler().getGenBucket("cobble");
        GenBucket sandGenBucket = GenBucketPlugin.getInstance().getBucketHandler().getGenBucket("sand");
        GenBucket obsidianGenBucket = GenBucketPlugin.getInstance().getBucketHandler().getGenBucket("obsidian");
        GenBucket netherrackGenBucket = GenBucketPlugin.getInstance().getBucketHandler().getGenBucket("netherrack");

        this.expShopUI = new PaginatedGui(3, 18, "AstroPvP Genbuckets");
        expShopUI.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });

        expShopUI.getFiller().fillBottom(new GuiItem(createFillerItem()));

        //HORIZONTAL
        expShopUI.setItem(1, 1, new GuiItem(cobblestoneGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100), event -> {
            player.getInventory().addItem(cobblestoneGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(1, 2, new GuiItem(sandGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100), event -> {
            player.getInventory().addItem(sandGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(1, 3, new GuiItem(obsidianGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100), event -> {
            player.getInventory().addItem(obsidianGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(1, 4, new GuiItem(netherrackGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100), event -> {
            player.getInventory().addItem(netherrackGenBucket.getItem(GenBucketDirection.HORIZONTAL, 100.0D));
            event.setCancelled(true);
        }));

        //VERTICAL
        expShopUI.setItem(2, 1, new GuiItem(cobblestoneGenBucket.getItem(GenBucketDirection.VERTICAL, 100), event -> {
            player.getInventory().addItem(cobblestoneGenBucket.getItem(GenBucketDirection.VERTICAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(2, 2, new GuiItem(cobblestoneGenBucket.getItem(GenBucketDirection.VERTICAL, 100), event -> {
            player.getInventory().addItem(sandGenBucket.getItem(GenBucketDirection.VERTICAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(2, 3, new GuiItem(cobblestoneGenBucket.getItem(GenBucketDirection.VERTICAL, 100), event -> {
            player.getInventory().addItem(obsidianGenBucket.getItem(GenBucketDirection.VERTICAL, 100.0D));
            event.setCancelled(true);
        }));
        expShopUI.setItem(2, 4, new GuiItem(cobblestoneGenBucket.getItem(GenBucketDirection.VERTICAL, 100), event -> {
            player.getInventory().addItem(netherrackGenBucket.getItem(GenBucketDirection.VERTICAL, 100.0D));
            event.setCancelled(true);
        }));


    }

    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }

}


