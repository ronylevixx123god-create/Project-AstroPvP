package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicBarbarian extends HeroicCustomEnchantment {
    public HeroicBarbarian() {
        super("Brutal Barbarian", "Barbarian", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        boolean enemyHasAxe = playerHit.getEquipment().getItemInHand().getType().name().contains("_AXE");
        if (!enemyHasAxe) {
            return;
        }
        double mod = 1.0 + 0.1 * (double)level;
        e.setDamage(e.getDamage() * mod);
    }
}
