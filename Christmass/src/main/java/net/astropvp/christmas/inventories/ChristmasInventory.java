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
        super("Christmas Shop", 9);
        setItem(3, getCurrentItem());
        setItem(5, getInformationBook());
    }


    private GUIItem getCurrentItem() {
        ItemStack shopItem = Christmas.getInstance().getChristmasManager().getCurrentItem().clone();
        ItemStack item = new ItemBuilder(shopItem).addLore("").addLore(CC.DarkGreenB + " * PRICE: " + CC.White + "FREE!").build();
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
        ItemStack item = new ItemBuilder(Material.BOOK).name(CC.DarkGreenB + "Christmas Shop").lore(CC.Gray + "The Christmas shop is a secret",
                CC.Gray + "market, only accessible by the",
                CC.translate( "&d&l<&5&l&o&nN&d&l&o&ne&5&l&n&ob&d&l&n&ou&5&l&n&ol&d&l&n&oa&5&l> ") + CC.Gray + "rank holders",
                "",
                CC.Gray + "The items in the Christmas shop are",
                CC.Gray + "only available for " + CC.GrayU + "limited time",
                CC.Gray + "before being replaced with new items.",
                "",
                CC.RedB + "NOTE: " + CC.Red + "You can only purchase items",
                CC.Red + "on from the Christmas Shop 1 time per restock"
                ).build();
        return new GUIItem(item) {};

    }
}
