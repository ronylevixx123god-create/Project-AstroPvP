package net.astropvp.customblocks.struct.impl;


import lombok.Getter;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class HalloweenCandle extends CustomBlock {

    public HalloweenCandle() {
        super(CustomBlockType.HALLOWEEN_CANDLES);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("halloween candle");
    }
}
