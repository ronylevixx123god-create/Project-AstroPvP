package net.astropvp.customblocks.struct.impl;


import lombok.Getter;
import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.entity.Player;

public class HalloweenCandle extends CustomBlock {

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("halloween candle");
    }
}
