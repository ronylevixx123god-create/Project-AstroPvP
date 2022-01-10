/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathGod
extends CustomEnchantment {
    public DeathGod() {
        super("Death God", GeneralUtil.helmets, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if(player.hasMetadata("neutralized"))
            return;
        double procChance;
        double random = Math.random();
        if (random < (procChance = 0.015 * (double)level) && player.getHealth() - e.getFinalDamage() <= (double)(4 + level)) {
            e.setDamage(0.0);
            GeneralUtil.healPlayer(player, 5 + level);
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), ChatColor.GOLD + "" + ChatColor.BOLD + "* DEATH GOD [" + ChatColor.GOLD + "+" + (5 + level) + "HP" + ChatColor.BOLD + "] *", "DEATH_GOD");

            ((Player)player).sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* DEATH GOD [" + (Object)ChatColor.GOLD + "+" + (5 + level) + "HP" + (Object)ChatColor.BOLD + "] *");
        }
    }
}

