package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class Cauldron extends CustomBlock {

    public Cauldron() {
        super(CustomBlockType.HALLOWEEN_CAULDRON);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Cauldron");
    }
}
