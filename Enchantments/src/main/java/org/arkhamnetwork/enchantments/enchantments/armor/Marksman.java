package org.arkhamnetwork.enchantments.enchantments.armor;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import com.rit.sucy.*;
import org.bukkit.inventory.*;
import java.util.*;

public class Marksman extends CustomEnchantment
{
    public Marksman() {
        super("Marksman", GeneralUtil.armor, 4);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            int marksmanLevel = 0;
            for (final ItemStack i : player.getEquipment().getArmorContents()) {
                if (i != null) {
                    final Map<CustomEnchantment, Integer> enchants = (Map<CustomEnchantment, Integer>)EnchantmentAPI.getEnchantments(i);
                    if (enchants.containsKey(this)) {
                        marksmanLevel += enchants.get(this);
                    }
                }
            }
            if (marksmanLevel > 0) {
                final double mod = 1.0 + marksmanLevel * 0.015625;
                e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            }
        }
    }
}
