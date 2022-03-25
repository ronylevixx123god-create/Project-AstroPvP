package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(time * 10, 40), Math.min(level - 1, 2)));

        }
    }
}
