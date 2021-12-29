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
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class HeroicAssassin
extends CustomEnchantment {
    public HeroicAssassin() {
        super("Shadow Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment((String)"Assassin");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!user.getWorld().equals((Object)target.getWorld())) {
            return;
        }
        double distance = user.getLocation().distance(target.getLocation());
        double dmgMod = 1.0;
        double heroicMult = 2.5;
        if (distance < 0.25 * heroicMult) {
            dmgMod += 0.015 * (double)enchantLevel;
        }
        if (distance < 0.5 * heroicMult) {
            dmgMod += 0.015 * (double)enchantLevel;
        }
        if (distance < 0.75 * heroicMult) {
            dmgMod += 0.015 * (double)enchantLevel;
        }
        if (distance < 1.0 * heroicMult) {
            dmgMod += 0.015 * (double)enchantLevel;
        }
        if (distance < 1.5 * heroicMult) {
            dmgMod += 0.015 * (double)enchantLevel;
        }
        event.setDamage(event.getDamage() * dmgMod);
    }
}

