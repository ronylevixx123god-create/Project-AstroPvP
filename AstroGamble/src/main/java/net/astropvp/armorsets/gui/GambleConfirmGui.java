package net.astropvp.armorsets.gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import net.astropvp.armorsets.struct.Language;
import net.astropvp.armorsets.struct.Reward;

@Getter
public class GambleConfirmGui {
    private final PaginatedGui gambleConfirmationGui;
    public GambleConfirmGui(Reward rewardEnum) {
        gambleConfirmationGui = new PaginatedGui(3, 3,Language.CONFIRMATION_GUI_TITLE.getMessage());
        ItemStack reward = rewardEnum.getItemStack();
        double price = rewardEnum.getPrice();

        ItemStack confirmationButton = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData())).name(CC.translate("&a&lConfirm"))
                .addLoreLine(CC.translate("&7Click to confirm your gamble."))
                .addLoreLine("")
                .addLoreLine(CC.translate("&aPrice&7: " + reward.getItemMeta().getDisplayName()))
                .addLoreLine(CC.translate("&aPrice: &f$" + NumberUtils.formatMoney(price)))
                .build();

        this.gambleConfirmationGui.setDefaultClickAction(event -> event.setCancelled(true));
        this.gambleConfirmationGui.setItem(1, 4, new GuiItem(reward));
        this.gambleConfirmationGui.setItem(2, 4, new GuiItem(confirmationButton, event -> {

        }));
    }
}
