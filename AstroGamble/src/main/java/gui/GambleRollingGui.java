package gui;

import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import struct.Language;
import struct.Reward;

@Getter
public class GambleRollingGui {
    private final PaginatedGui gambleRollingGui;
    public GambleRollingGui(Reward rewardEnum) {
        gambleRollingGui = new PaginatedGui(1, 1, Language.ROLLING_GUI.getMessage());
        //Start rolling animation here
    }


}
