/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Insomnia
extends CustomEnchantment {
    public Insomnia() {
        super("Insomnia", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 15.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double random = Math.random();
        double chance = 0.025D * (double)level;
        int time = level > 1 ? 6 : 4;
        if (random < chance) {
            if (level >= 6) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Math.max(time * 10, 40), 1));
            }

            double multi = 1.0D + ((double)level * 0.020D);
            e.setDamage(e.getDamage() * multi);
        }

    }
}
