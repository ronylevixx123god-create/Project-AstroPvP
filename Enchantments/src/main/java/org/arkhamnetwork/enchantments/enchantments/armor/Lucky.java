package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Lucky extends CustomEnchantment
{
    public Lucky() {
        super("Lucky", GeneralUtil.armor, 3);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
    }
}
