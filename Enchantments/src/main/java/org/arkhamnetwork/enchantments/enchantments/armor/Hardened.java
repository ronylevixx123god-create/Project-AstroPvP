package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;

public class Hardened extends CustomEnchantment
{
    public Hardened() {
        super("Hardened", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 17.0;
        this.interval = 6.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.2 * level;
        if (random < chance) {
            DurabilityUtil.healMostDamagedArmorPeice((Player)player, (short)1);
            final Location l = player.getLocation();
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 1.0f, 4);
        }
    }
}
