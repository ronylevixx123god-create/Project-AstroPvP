package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicInsanity extends HeroicCustomEnchantment{
    public HeroicInsanity() {
        super("Extreme Insanity","Insanity", GeneralUtil.axe, 2);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        if (!enemyHasSword) {
            return;
        }
        double mod = 1.0 + 0.03 * (double)level;
        e.setDamage(e.getDamage() * mod);
    }
}
