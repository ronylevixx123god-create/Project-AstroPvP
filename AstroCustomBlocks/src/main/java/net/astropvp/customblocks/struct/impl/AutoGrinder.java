package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.entity.Player;

public class AutoGrinder extends CustomBlock {

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Auto Grinder");
    }
}
