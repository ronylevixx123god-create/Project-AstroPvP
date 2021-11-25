package net.astropvp.nebulashop.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import me.matthawkk.chat.PrefixedMessage;
import me.matthawkk.gui.CommonGUI;
import me.matthawkk.gui.GUIItem;
import me.matthawkk.item.ItemBuilder;
import net.astropvp.nebulashop.NebulaShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NebulaShopInventory extends CommonGUI {
    public NebulaShopInventory() {
        super("Nebula Shop", 9);
        setItem(3, getCurrentItem());
        setItem(5, getInformationBook());
    }


    private GUIItem getCurrentItem() {
        ItemStack item = new ItemBuilder(NebulaShop.getInstance().getNebulaShopManager().getCurrentItem().clone()).addLore("").addLore(CC.LightPurpleB + " PRICE: " + CC.White + "$" + NebulaShop.getInstance().getNebulaShopManager().getCurrentPrice()).build();
        return new GUIItem(item) {
            public void onClick(Player player1, ClickType clickType, InventoryClickEvent inventoryClickEvent) {
                if(NebulaShop.getInstance().getNebulaShopManager().hasAlreadyBought(player1)) {
                    player1.sendMessage(CC.RedB + "(!) " + CC.Red + "You may only buy this item once per restock.");
                    return;
                }

                double price = NebulaShop.getInstance().getNebulaShopManager().getCurrentPrice();
                if(!EconomyUtils.hasBalance(player1, price)) {
                    player1.sendMessage(CC.RedB + "(!) " + CC.Red + "You require $" + price + " to purchase this item.");
                    return;
                }
                if(player1.getInventory().firstEmpty() < 0) {
                    player1.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have an empty slot to buy this item.");
                    return;
                }
                EconomyUtils.withdrawBalance(player1, price);
                NebulaShop.getInstance().getNebulaShopManager().setAlreadyBought(player1);
                player1.getInventory().addItem(NebulaShop.getInstance().getNebulaShopManager().getCurrentItem());
                Bukkit.broadcastMessage(new PrefixedMessage(NebulaShop.getInstance(), CC.DarkPurple + player1.getName() + CC.LightPurple + " has purchased " + NebulaShop.getInstance().getNebulaShopManager().getCurrentItem().getItemMeta().getDisplayName() + CC.LightPurple + " from the " + CC.LightPurpleU + "Nebula Shop!").get());
            }
        };
    }

    private GUIItem getInformationBook() {
        ItemStack item = new ItemBuilder(Material.BOOK).name(CC.LightPurpleB + "Nebula Shop").lore("The Nebula shop is a secret",
                CC.Gray + "market, only accessible by the",
                CC.translate( "&d&l<&5&l&o&nN&d&l&o&ne&5&l&n&ob&d&l&n&ou&5&l&n&ol&d&l&n&oa&5&l> ") + CC.Gray + "rank holders",
                "",
                CC.Gray + "The items in the Nebula shop are",
                CC.Gray + "only available for " + CC.GrayU + "limited time",
                CC.Gray + "before being replaced with new items.",
                "",
                CC.RedB + "NOTE: " + CC.Red + "You can only purchase items",
                CC.Red + "on from the Nebula Shop 1 time per restock"
                ).build();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.LightPurpleB + "Nebula Shop");
        List<String> lore = new ArrayList<>();
        lore.add(CC.Gray + "The Nebula shop is a secret");
        lore.add(CC.Gray + "market, only accessible by the");
        lore.add(CC.translate( "&d&l<&5&l&o&nN&d&l&o&ne&5&l&n&ob&d&l&n&ou&5&l&n&ol&d&l&n&oa&5&l> ") + CC.Gray + "rank holders");
        lore.add("");
        lore.add(CC.Gray + "The items in the Nebula shop are");
        lore.add(CC.Gray + "only available for " + CC.GrayU + "limited time");
        lore.add(CC.Gray + "before being replaced with new items.");
        lore.add("");
        lore.add(CC.RedB + "NOTE: " + CC.Red + "You can only purchase items");
        lore.add(CC.Red + "on from the Nebula Shop 1 time per restock");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return new GUIItem(item) {};

    }
}
