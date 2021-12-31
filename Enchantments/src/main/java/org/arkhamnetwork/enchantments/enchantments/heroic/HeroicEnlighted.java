/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class HeroicEnlighted
extends CustomEnchantment {
    public HeroicEnlighted() {
        super("Divine Enlighted", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment((String)"Enlighted");
        }, 2L);
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player.hasMetadata("noDefenseProcs"))
            return;
        double chance;
        double random = Math.random();
        if (random < (chance = 0.0375 * (double)level)) {
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> GeneralUtil.setPlayerHealth(player, player.getHealth() + 1.0), 1L);
        }
    }
}

