package gui;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import me.ifamasssxd.cosmicguishop.utils.NumberUtils;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.inventory.ItemStack;
import struct.Language;
import struct.Reward;

public class GambleGui {

    public GambleGui() {
        PaginatedGui gambleGui = new PaginatedGui(3, 3, Language.MAIN_GUI_TITLE.getMessage());

        for (Reward rewardEnum : Reward.values()) {
            ItemStack reward = new ItemBuilder(rewardEnum.getItemStack().clone()).addLoreLine("").addLoreLine(CC.translate("&6&l* PRICE: &f" + NumberUtils.formatMoney((long) rewardEnum.getPrice()))).addLoreLine(CC.translate("&7Click to start gambling for this reward.")).build();
            gambleGui.addItem(new GuiItem(reward, event -> {
                GambleConfirmGui gambleConfirmGui = new GambleConfirmGui(rewardEnum);
                gambleConfirmGui.getGambleConfirmationGui().open(event.getWhoClicked());
            }));
        }
    }
}
