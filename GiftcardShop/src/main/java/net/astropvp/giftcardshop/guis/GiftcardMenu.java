package net.astropvp.giftcardshop.guis;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.giftcardshop.GiftcardShopPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;

@Getter
public class GiftcardMenu {
    private PaginatedGui giftcardsMenu;

    public GiftcardMenu(UUID uuid) {
        this.giftcardsMenu = new PaginatedGui(6,56, "Your Giftcards");

        HashMap<String, Double> codesAndAmount = GiftcardShopPlugin.getInstance().getGiftcardData().getCodesAndAmountMap(uuid);

        for (Map.Entry<String, Double> codeAndAmount : codesAndAmount.entrySet()) {
            String code = codeAndAmount.getKey();
            Double amount = codeAndAmount.getValue();
            ItemStack item = new ItemBuilder(Material.PAPER, CC.translate("&a&lGiftcard"))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&f&l● &aCode: &f") + code)
                    .addLoreLine(CC.translate("&f&l● &aInitial Amount: &f$") + NumberUtils.formatMoney(amount))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7You can use this giftcard on &fbuy.astropvp.net&7."))
                    .build();

            this.giftcardsMenu.addItem(new GuiItem(item));
        }
        this.giftcardsMenu.setDefaultClickAction(event -> event.setCancelled(true));
        this.giftcardsMenu.getFiller().fillBorder(new GuiItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7)).name(CC.translate("&7")).build()));

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

        this.giftcardsMenu.setItem(6, 4, new GuiItem(prevPage, event -> {
            this.giftcardsMenu.previous();
            event.setCancelled(true);
        }));

        this.giftcardsMenu.setItem(6, 6, new GuiItem(nextPage, event -> {
            this.giftcardsMenu.next();
            event.setCancelled(true);
        }));


    }
}
