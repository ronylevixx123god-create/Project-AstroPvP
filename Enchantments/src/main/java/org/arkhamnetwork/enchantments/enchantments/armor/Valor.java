package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Valor extends CustomEnchantment
{
    public Valor() {
        super("Valor", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        final double damage_negation = level * 0.05;
        final double hp_threshold = level * 2 + 8;
        if (player.getHealth() <= hp_threshold && (((Player)player).getItemInHand().getType() == Material.GOLD_SWORD || ((Player)player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage() * (1.0D - damage_negation));            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 41, 2001);
        }
    }
}
