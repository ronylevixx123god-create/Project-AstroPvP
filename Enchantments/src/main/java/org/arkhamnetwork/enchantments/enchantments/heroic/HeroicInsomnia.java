package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicInsomnia extends HeroicCustomEnchantment {
    public HeroicInsomnia() {
        super("Nocturnal Insomnia","Insomnia", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 15.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        int time;
        double random = Math.random();
        double chance = 0.025 * (double)level;
        int n = time = level > 1 ? 6 : 4;
        if (random < chance) {
            double multi = 1.0 + (double)level * 0.05;
            e.setDamage(e.getDamage() * multi);
        }
    }
}
