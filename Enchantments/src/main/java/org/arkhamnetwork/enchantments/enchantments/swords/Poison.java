package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends CustomEnchantment {
    public Poison() {
        super("Poison", GeneralUtil.swords, 5);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final int time = (int) (Math.random() * enchantLevel * 20.0);
        final double random = Math.random();
        final double chance = 0.1 * enchantLevel;
        if (random < chance) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, enchantLevel - 1));
        }
    }
}
