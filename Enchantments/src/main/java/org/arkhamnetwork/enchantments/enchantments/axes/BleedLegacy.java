package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BleedLegacy extends CustomEnchantment {
    public BleedLegacy() {
        super("Bleed", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 3.0;
        Bukkit.getPluginManager().registerEvents((Listener) new BleedListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.075 * level;
        final double chance2 = 0.025 * level;
        final int time = 2 * level;
        double amp = level / 2;
        if (amp < 0.5) {
            amp = 0.5;
        }
        if (random < chance && playerHit instanceof Player) {
            if (random < chance2) {
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time * 20, level - 1));
            }
            if (!playerHit.hasMetadata("ce_bleeding")) {
                new BleedEvent((Player) playerHit, amp, time).start();
            }
        }
    }
}
