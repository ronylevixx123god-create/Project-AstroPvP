package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class HeroicAssassin extends HeroicCustomEnchantment {
    public HeroicAssassin() {
        super("Shadow Assassin", "Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0D;
        this.interval = 6.0D;
        Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Assassin");
        }, 2L);
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (user.getWorld().equals(target.getWorld())) {
            double distance = user.getLocation().distance(target.getLocation());
            double dmgMod = 1.0D;
            double heroicMult = 2.5D;
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

            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * dmgMod);
        }
    }
}
