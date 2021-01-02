package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class Molten extends CustomEnchantment {
    public Molten() {
        super("Molten", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random() * 1.0;
        final double chance = 0.1 * level;
        final int time = 20 * level;
        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.setFireTicks(time);
        }
    }
}
