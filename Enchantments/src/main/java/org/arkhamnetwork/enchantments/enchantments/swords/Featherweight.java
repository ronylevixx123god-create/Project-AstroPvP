package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;

public class Featherweight extends CustomEnchantment
{
    public Featherweight() {
        super("Featherweight", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 10.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() <= level * 0.25 && !player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, level * 20, level - 1), true);
        }
    }
}
