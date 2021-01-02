package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Greatsword extends CustomEnchantment {
    public Greatsword() {
        super("Greatsword", GeneralUtil.swords, 7);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasBow = playerHit.getEquipment().getItemInHand().getType().name().equals("BOW");
        if (!enemyHasBow) {
            return;
        }
        final double mod = 1.0 + 0.05 * level;
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
    }
}
