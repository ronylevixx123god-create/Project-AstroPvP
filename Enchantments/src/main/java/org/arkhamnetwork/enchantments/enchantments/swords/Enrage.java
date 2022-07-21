package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class Enrage extends CustomEnchantment
{
    public Enrage() {
        super("Enrage", GeneralUtil.swords, 5);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    @SneakyThrows
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double hpPercent = (double)user.getClass().getMethod("getHealth").invoke(user) / (double)user.getClass().getMethod("getMaxHealth").invoke(user);
        double multi = 1.0D;
        if (hpPercent <= 0.75D) {
            multi += 0.15D;
        }

        if (hpPercent <= 0.5D) {
            multi += 0.15D;
        }

        if (hpPercent <= 0.25D) {
            multi += 0.15D;
        }

        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * multi);
    }
}


