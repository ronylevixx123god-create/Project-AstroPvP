package net.astropvp.armorsets.gui;

import lombok.Getter;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import net.astropvp.armorsets.struct.Language;
import net.astropvp.armorsets.struct.Reward;

@Getter
public class GambleRollingGui {
    private final PaginatedGui gambleRollingGui;
    public GambleRollingGui(Reward rewardEnum) {
        gambleRollingGui = new PaginatedGui(1, 1, Language.ROLLING_GUI.getMessage());
        //Start rolling animation here
    }


}
