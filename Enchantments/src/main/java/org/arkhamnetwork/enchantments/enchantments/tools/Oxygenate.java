package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;

public class Oxygenate extends CustomEnchantment
{
    public Oxygenate() {
        super("Oxygenate", GeneralUtil.tools, 6);
        this.max = 1;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        if (event instanceof BlockBreakEvent && player.getRemainingAir() + 20 <= player.getMaximumAir()) {
            player.setRemainingAir(player.getRemainingAir() + 20);
        }
    }
}
