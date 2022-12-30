package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class CosmicTrophy extends CustomBlock {

    public CosmicTrophy() {
        super(CustomBlockType.COSMIC_TROPHY);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Cosmic trophy");
    }
}
