package me.astropvp.moneyshop.guis;

import com.cosmicpvp.cosmicutils.utils.*;
import lombok.Getter;
import me.astropvp.moneyshop.MoneyShop;
import me.astropvp.moneyshop.struct.MoneyShopItems;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MoneyShopGUI {
    @Getter
    private final PaginatedGui paginatedGui;
    public MoneyShopGUI(Player player) {
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 10);
        this.paginatedGui = new PaginatedGui(4, 27, "Money Shop");
        paginatedGui.setDefaultClickAction(event -> event.setCancelled(true));
        for (MoneyShopItems shopItem : MoneyShopItems.values()) {
            double price = shopItem.getPrice();
            ItemStack item = shopItem.getItemStack();

            ItemStack guiitem = new ItemBuilder(item).addLoreLine("").addLoreLine(CC.translate("&e&l* PRICE: &f$" + NumberUtils.formatMoney(price))).addLoreLine("").build();
            paginatedGui.addItem(new GuiItem(guiitem, event -> {
                if (EconomyUtils.hasBalance(player, price)) {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage(CC.Red + "Clear some inventory space!");
                        return;
                    }
                    EconomyUtils.withdrawBalance(player, price);
                    player.getInventory().addItem(item);
                    player.sendMessage(CC.translate("&a&l(!)&a You have purchased " + item.getItemMeta().getDisplayName() + " &afor &f$" + NumberUtils.formatMoney(price)));
                } else {
                    player.sendMessage(CC.translate("&c&l(!)&c You do not have enough money to purchase this item."));
                }
            }));
        }
        this.paginatedGui.getFiller().fillBorder(new GuiItem(createFillerItem()));
    }

    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }
}
