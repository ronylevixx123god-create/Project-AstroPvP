package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;

public class Experience extends CustomEnchantment {
    public Experience() {
        super("Experience", GeneralUtil.tools, 6);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent) {
            final BlockBreakEvent bbe = (BlockBreakEvent) event;
            final double expMod = 1.0 + 0.25 * enchantLevel;
            bbe.setExpToDrop((int) (bbe.getExpToDrop() * expMod));
        }
    }
}
