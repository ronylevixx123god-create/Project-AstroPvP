package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class Enlighted extends CustomEnchantment {
    public Enlighted() {
        super("Enlighted", GeneralUtil.armor, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 8.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                Enlighted.this.heroicVersion = EnchantmentAPI.getEnchantment("Divine Enlighted");
            }
        }, 2L);
    }

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        double random = Math.random();
        if (level > 3)
            level = 3;
        double chance = 0.075D * level;
        if (random < chance)
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable() {
                public void run() {
                    GeneralUtil.healPlayer(player, 1.0D);
                }
            }, 1L);
    }
}
