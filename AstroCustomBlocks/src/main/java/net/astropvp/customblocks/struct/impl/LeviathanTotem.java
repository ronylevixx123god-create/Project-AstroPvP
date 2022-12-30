package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class LeviathanTotem extends CustomBlock {

    public LeviathanTotem() {
        super(CustomBlockType.LEVIATHAN_TOTEM);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Levi tothem");
    }
}
