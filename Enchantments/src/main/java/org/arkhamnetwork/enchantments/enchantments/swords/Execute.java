package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Execute extends CustomEnchantment
{
    public Execute() {
        super("Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 10.0) {
            return;
        }
        if (GeneralUtil.isEffectedByRage(target)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.1 * level;
        final double mod = 4.2;
        if (random < chance) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * mod);
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
        }
    }
}
