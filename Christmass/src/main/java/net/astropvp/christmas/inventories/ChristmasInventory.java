package net.astropvp.christmas.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.matthawkk.chat.messages.PrefixedMessage;
import me.matthawkk.gui.BaseGUI;
import me.matthawkk.gui.GUIItem;
import me.matthawkk.item.ItemBuilder;
import net.astropvp.christmas.Christmas;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChristmasInventory extends BaseGUI {
    public ChristmasInventory() {
        super("Christmas Daily Present", 9);
        setItem(3, getCurrentItem());
        setItem(5, getInformationBook());
    }


    private GUIItem getCurrentItem() {
        ItemStack shopItem = Christmas.getInstance().getChristmasManager().getCurrentItem().clone();
        ItemStack item = new ItemBuilder(shopItem).build();
        return new GUIItem(item.clone()) {
            public void onClick(Player player1, ClickType clickType, InventoryClickEvent inventoryClickEvent) {
                if(Christmas.getInstance().getChristmasManager().hasAlreadyBought(player1)) {
                    player1.sendMessage(CC.RedB + "(!) " + CC.Red + "You may only buy this item once per restock.");
                    return;
                }

                if(player1.getInventory().firstEmpty() < 0) {
                    player1.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have an empty slot to buy this item.");
                    return;
                }
                Christmas.getInstance().getChristmasManager().setAlreadyBought(player1);
                player1.getInventory().addItem(Christmas.getInstance().getChristmasManager().getCurrentItem());
            }
        };
    }

    private GUIItem getInformationBook() {
        ItemStack item = new ItemBuilder(Material.COOKIE).name(CC.DarkGreenB + "Christmas Daily Present").lore(
                CC.translate("&7This is a place where you you can"),
                CC.translate("&7claim your daily reward from"),
                CC.translate("&7Santa Claus. He will give you each item"),
                CC.translate("&7only once and if you miss it, that"),
                CC.translate("&7is your own fault, Santa Claus"),
                CC.translate("&7has got a busy schedule...")
                ).build();
        return new GUIItem(item) {};

    }
}
