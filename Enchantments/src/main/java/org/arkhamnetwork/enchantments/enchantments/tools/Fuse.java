package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;

public class Fuse extends CustomEnchantment
{
    public Fuse() {
        super("Fuse", GeneralUtil.pickaxe, 6);
        this.max = 1;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
    }
}
