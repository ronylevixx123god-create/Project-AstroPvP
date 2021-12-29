/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class Assassin
extends CustomEnchantment {
    public Assassin() {
        super("Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicVersion = EnchantmentAPI.getEnchantment((String)"Shadow Assassin");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!user.getWorld().equals((Object)target.getWorld())) {
            return;
        }
        double distance = user.getLocation().distance(target.getLocation());
        double dmgMod = 1.0;
        if (distance < 0.25) {
            dmgMod += 0.01 * (double)enchantLevel;
        }
        if (distance < 0.5) {
            dmgMod += 0.01 * (double)enchantLevel;
        }
        if (distance < 0.75) {
            dmgMod += 0.01 * (double)enchantLevel;
        }
        if (distance < 1.0) {
            dmgMod += 0.01 * (double)enchantLevel;
        }
        if (distance < 1.5) {
            dmgMod += 0.01 * (double)enchantLevel;
        }
        if (distance > 2.0) {
            dmgMod -= 0.01 * (double)enchantLevel;
        }
        if (distance > 2.5) {
            dmgMod -= 0.0125 * (double)enchantLevel;
        }
        if (distance > 3.0) {
            dmgMod -= 0.025 * (double)enchantLevel;
        }
        event.setDamage(event.getDamage() * dmgMod);
    }
}

