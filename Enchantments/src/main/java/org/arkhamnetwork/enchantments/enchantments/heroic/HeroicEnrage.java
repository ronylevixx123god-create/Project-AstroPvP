package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicEnrage extends HeroicCustomEnchantment{
    public HeroicEnrage() {
        super("Unrestrained Enrage","Enrage", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double hpPercent = user.getHealth() / user.getMaxHealth();
        double multi = 1.0;
        if (hpPercent <= 0.75) {
            multi += 0.25;
        }
        if (hpPercent <= 0.5) {
            multi += 0.25;
        }
        if (hpPercent <= 0.25) {
            multi += 0.25;
        }
        event.setDamage(event.getDamage() * multi);
    }
}
