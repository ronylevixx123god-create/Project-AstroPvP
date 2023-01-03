package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.guis.SlotBotOpenerGUI;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class SlotOpener extends CustomBlock {


    public SlotOpener() {
        super(CustomBlockType.COSMIC_TOKEN_GENERATOR);
    }

    @Override
    public void onClick(Player clicker) {
        SlotBotOpenerGUI slotBotOpenerGUI = new SlotBotOpenerGUI(this, clicker);
        slotBotOpenerGUI.getCustomBlockGui().open(clicker);
    }
}
