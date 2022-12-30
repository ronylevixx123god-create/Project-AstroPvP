package net.astropvp.customblocks.struct.impl;

import lombok.Getter;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class ThanksgivingPie extends CustomBlock {

    public ThanksgivingPie() {
        super(CustomBlockType.THANKSGIVING_PIE);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Thanksgiving piiiiii candle");
    }
}
