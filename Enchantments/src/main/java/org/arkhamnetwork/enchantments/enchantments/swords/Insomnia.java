package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;

public class Insomnia extends CustomEnchantment
{
    public Insomnia() {
        super("Insomnia", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 15.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = 0.025 * level;
        final int time = (level > 1) ? 6 : 4;
        if (random < chance) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(time * 10, 40), Math.min(level - 1, 2)));
            if (level >= 6) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Math.max(time * 10, 40), 1));
            }
            final double multi = 1.0 + level * 0.05;
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * multi);
        }
    }
}
