/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Enrage
extends CustomEnchantment {
    public Enrage() {
        super("Enrage", GeneralUtil.swords, 5);
        this.max = 3;
        this.base = 15.0;
        this.interval = 6.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double hpPercent = user.getHealth() / user.getMaxHealth();
        double multi = 1.0;
        if (hpPercent <= 0.75) {
            multi += 0.15;
        }
        if (hpPercent <= 0.5) {
            multi += 0.15;
        }
        if (hpPercent <= 0.25) {
            multi += 0.15;
        }
        event.setDamage(event.getDamage() * multi);
    }
}

