package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;

public class HeroicAssassin extends CustomEnchantment
{
    public HeroicAssassin() {
        super("Shadow Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Assassin");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (user.getWorld().equals(target.getWorld())) {
            double distance = user.getLocation().distance(target.getLocation());
            double dmgMod = 1.0D;
            if (distance < 0.625D) {
                dmgMod += 0.015D * (double)enchantLevel;
            }

            if (distance < 1.25D) {
                dmgMod += 0.015D * (double)enchantLevel;
            }

            if (distance < 1.875D) {
                dmgMod += 0.015D * (double)enchantLevel;
            }

            if (distance < 2.5D) {
                dmgMod += 0.015D * (double)enchantLevel;
            }

            if (distance < 3.75D) {
                dmgMod += 0.015D * (double)enchantLevel;
            }

            event.setDamage(event.getDamage() * dmgMod);
        }

    }
}
