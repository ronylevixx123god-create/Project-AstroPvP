package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class DeathGod extends CustomEnchantment
{
    public DeathGod() {
        super("Death God", GeneralUtil.helmets, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double procChance = 0.015 * level;
        if (random < procChance && player.getHealth() - e.getDamage(EntityDamageEvent.DamageModifier.BASE) <= 4 + level) {
            e.setCancelled(true);
            e.setDamage(0.0);
            GeneralUtil.healPlayer(player, 5 + level);
            ((Player)player).sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "* DEATH GOD [" + ChatColor.GOLD + "+" + (5 + level) + "HP" + ChatColor.BOLD + "] *");
        }
    }
}
