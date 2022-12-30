package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class TreasureChest extends CustomBlock {

    public TreasureChest() {
        super(CustomBlockType.TREASURE_CHEST);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Tresure chest");
    }
}
