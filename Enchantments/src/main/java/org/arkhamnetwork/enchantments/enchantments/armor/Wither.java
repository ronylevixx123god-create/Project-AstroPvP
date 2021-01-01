package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;

public class Wither extends CustomEnchantment
{
    public Wither() {
        super("Wither", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.015 * level;
        if (random < chance && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * level, level - 1));
        }
    }
}
