package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Insanity extends CustomEnchantment
{
    public Insanity() {
        super("Insanity", GeneralUtil.axe, 7);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        if (!enemyHasSword) {
            return;
        }
        final double mod = 1.0 + 0.02 * level;
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
    }
}
