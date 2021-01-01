package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

public class Confusion extends CustomEnchantment
{
    public Confusion() {
        super("Confusion", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.075 * level;
        final int time = 2 * level + 4;
        final int amp = level;
        if (random < chance && playerHit instanceof Player) {
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time * 20, amp));
        }
    }
}
