package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Solitude extends CustomEnchantment
{
    public Solitude() {
        super("Solitude", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 15.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
    }
}
