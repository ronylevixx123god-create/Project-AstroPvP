package net.astropvp.core.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.earth2me.essentials.utils.FormatUtil;
import me.ifamasssxd.cosmiccoinflip.utils.ItemBuilder;
import net.astropvp.core.items.RogueScratchcard;
import net.astropvp.core.utils.GUIBuilder;
import org.arkhamnetwork.Arkkit.patches.Balance;
import org.arkhamnetwork.Arkkit.patches.balancetop.BalanceManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;

public class MerchantGUI extends GUIBuilder {
    public MerchantGUI(Player player) {
        super(player);
    }

    @Override
    protected String getName() {
        return CC.translate("&8Scratch Card Merchant");
    }

    @Override
    protected int getSlots() {
        return 9;
    }

    @Override
    protected void setItems() {
        for (int i = 0; i < 9; i++) {
        }
        this.inventory.setItem(0, getItems(0));
    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == 0) {
            ItemStack rogueSratchCard = RogueScratchcard.getRogueSratchCard();
            ItemMeta meta = rogueSratchCard.getItemMeta();
            List lore = meta.getLore();
            lore.add("");
            lore.add(CC.translate("&c&l* PRICE: &f$1,000,000,000"));
            lore.add("");
            meta.setLore(lore);
            rogueSratchCard.setItemMeta(meta);
            return rogueSratchCard;
        }
        return null;
    }

    @Override
    public void click(InventoryClickEvent event) throws UserDoesNotExistException, NoLoanPermittedException {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        switch (event.getSlot()) {
            case 0:
                buyItem("rogue_scratchcard", 1000000000, player);
                setItems();
                break;
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }


    public void buyItem(String customItem, int price, Player p) throws UserDoesNotExistException, NoLoanPermittedException {
        String name = p.getName();
        double balance = Economy.getMoney(name);

        if (balance < price) {
            p.sendMessage(CC.translate("&c&l(!)&c You don't have enough money to purchase this Scratch Card."));
            p.sendMessage(CC.translate("&c&l* &cRequired: &f" + Economy.format(price-balance)));
            p.closeInventory();
            return;
        } else {
            Economy.subtract(name, price);
            p.sendMessage(CC.translate("&a&l(!)&a You have purchased an item from the Scratch Card Merchant."));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "customitems give " + p.getName() + " " + customItem + " 1");
        }
    }

}
