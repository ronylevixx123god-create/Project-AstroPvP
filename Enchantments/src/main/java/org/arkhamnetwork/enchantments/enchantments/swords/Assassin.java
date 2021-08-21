package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class Assassin extends CustomEnchantment {
    public Assassin() {
        super("Assassin", GeneralUtil.swords, 5);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) Enchantments.getInstance(), () -> this.heroicVersion = EnchantmentAPI.getEnchantment("Shadow Assassin"), 2L);
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!user.getWorld().equals(target.getWorld())) {
            return;
        }
        final double distance = user.getLocation().distance(target.getLocation());
        double dmgMod = 1.0;
        if (distance < 0.25) {
            dmgMod += 0.01 * enchantLevel;
        }
        if (distance < 0.5) {
            dmgMod += 0.01 * enchantLevel;
        }
        if (distance < 0.75) {
            dmgMod += 0.01 * enchantLevel;
        }
        if (distance < 1.0) {
            dmgMod += 0.01 * enchantLevel;
        }
        if (distance < 1.5) {
            dmgMod += 0.01 * enchantLevel;
        }
        if (distance > 2.0) {
            dmgMod -= 0.01 * enchantLevel;
        }
        if (distance > 2.5) {
            dmgMod -= 0.0125 * enchantLevel;
        }
        if (distance > 3.0) {
            dmgMod -= 0.025 * enchantLevel;
        }
    }
}
