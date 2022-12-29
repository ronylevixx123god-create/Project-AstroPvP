package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.entity.Player;

public class LeviathanTotem extends CustomBlock {

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Levi tothem");
    }
}
