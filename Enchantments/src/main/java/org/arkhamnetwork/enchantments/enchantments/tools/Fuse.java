package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;

public class Fuse extends CustomEnchantment {
    public Fuse() {
        super("Fuse", GeneralUtil.pickaxe, 6);
        this.max = 1;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
    }
}
