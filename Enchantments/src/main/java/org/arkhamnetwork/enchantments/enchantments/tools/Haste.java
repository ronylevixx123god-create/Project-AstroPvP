package org.arkhamnetwork.enchantments.enchantments.tools;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.event.block.*;
import org.bukkit.potion.*;

public class Haste extends CustomEnchantment
{
    public Haste() {
        super("Haste", GeneralUtil.tools, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 10.0;
    }
    
    public void applyToolEffect(final Player player, final Block block, final int enchantLevel, final BlockEvent event) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, enchantLevel - 1), true);
    }
}
