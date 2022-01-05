/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Curse
extends CustomEnchantment {
    public Curse() {
        super("Curse", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double chance;
        double random = Math.random();
        if (random < (chance = 0.001 * (double)level) && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * level, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * level, 1));
//            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * level, 1));
        }
    }
}

