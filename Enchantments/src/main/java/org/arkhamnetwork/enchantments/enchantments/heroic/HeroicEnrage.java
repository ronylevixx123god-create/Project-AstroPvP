package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicEnrage extends HeroicCustomEnchantment {
    public HeroicEnrage() {
        super("Unrestrained Enrage", "Enrage", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double hpPercent = user.getHealth() / user.getMaxHealth();
        double multi = 1.0;
        if (hpPercent <= 0.75) {
            multi += 0.1;
        }
        if (hpPercent <= 0.5) {
            multi += 0.1;
        }
        if (hpPercent <= 0.25) {
            multi += 0.1;
        }
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * multi);
    }
}