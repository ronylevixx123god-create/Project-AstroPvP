package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.bukkit.potion.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Frozen extends CustomEnchantment
{
    public Frozen() {
        super("Frozen", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyDefenseEffect(final Player player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.04 * level;
        final int time = (int)(Math.random() * level) + 1 * level;
        final int amp = (level > 2) ? 1 : 0;

        if (random < chance && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && attacker != null) {
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, amp));
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
        }
    }
}
