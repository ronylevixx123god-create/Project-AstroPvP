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
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class Enlighted
extends CustomEnchantment {
    public Enlighted() {
        super("Enlighted", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

            @Override
            public void run() {
                Enlighted.this.heroicVersion = EnchantmentAPI.getEnchantment((String)"Divine Enlighted");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player.hasMetadata("noDefenseProcs"))
            return;
        double chance;
        double random = Math.random();
        if (level > 3) {
            level = 3;
        }
        if (random < (chance = 0.075 * (double)level)) {
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    GeneralUtil.healPlayer(player, 0.5);
                }
            }, 1L);
        }
    }
}

