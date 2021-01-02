package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DurabilityUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class HeroicBlacksmith extends CustomEnchantment {
    public HeroicBlacksmith() {
        super("Master Blacksmith", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 17.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                HeroicBlacksmith.this.heroicEnchantment = true;
                HeroicBlacksmith.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Blacksmith");
            }
        }, 2L);
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = 0.125 * level;
        if (random < chance) {
            DurabilityUtil.healMostDamagedArmorPeice((Player) player, (short) ((level == 5) ? 3 : 2));
            if (level >= 3) {
                DurabilityUtil.healMostDamagedArmorPeice((Player) player, (short) 2);
            }
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (0.25 - level * 0.05));
        }
    }
}
