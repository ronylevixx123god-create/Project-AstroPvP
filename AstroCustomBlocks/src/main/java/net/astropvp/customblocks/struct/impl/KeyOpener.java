package net.astropvp.customblocks.struct.impl;

import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import org.bukkit.entity.Player;

public class KeyOpener extends CustomBlock {

    public KeyOpener() {
        super(CustomBlockType.SECURITY_SYSTEM);
    }

    @Override
    public void onClick(Player clicker) {
        clicker.sendMessage("Key opener");
    }
}
