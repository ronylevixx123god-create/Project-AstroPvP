package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poisoned extends CustomEnchantment {
    public Poisoned() {
        super("Poisoned", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 14.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (attacker == null) {
            return;
        }
        final int time = (int) (Math.random() * level + 0.5 * level);
        final int amp = (level > 2) ? 1 : 0;
        final double random = Math.random();
        final double chance = (level > 3) ? 0.3 : (0.1 * level);
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time * 20, amp));
        }
    }
}
