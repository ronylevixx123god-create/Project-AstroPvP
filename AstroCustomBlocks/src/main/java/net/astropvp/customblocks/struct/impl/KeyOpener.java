package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import org.bukkit.entity.Player;

public class KeyOpener extends CustomBlock {

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Key opener");
    }
}
