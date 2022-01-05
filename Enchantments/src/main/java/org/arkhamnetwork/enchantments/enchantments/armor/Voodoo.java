/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Voodoo
extends CustomEnchantment {
    public Voodoo() {
        super("Voodoo", GeneralUtil.armor, 2);
        this.max = 6;
        this.base = 17.0;
        this.interval = 6.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double chance;
        int time = (int)(1.5 * (double)level);
        double random = Math.random();
        if (random < (chance = 0.04 * (double)level) && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time * 20 + 25, level < 4 ? 0 : 1));
            try {
                ParticleEffect.MOB_SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), attacker.getEyeLocation().add(0.0, 0.5, 0.0), 0.04f, 0.04f, 0.04f, 0.1f, 15);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

