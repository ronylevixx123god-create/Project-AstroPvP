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
        if (hpPercent <= 0.65D) {
            multi += 0.07D;
        }

        if (hpPercent <= 0.4D) {
            multi += 0.07D;
        }

        if (hpPercent <= 0.15D) {
            multi += 0.07D;
        }

        event.setDamage(event.getDamage() * multi);
    }
}


