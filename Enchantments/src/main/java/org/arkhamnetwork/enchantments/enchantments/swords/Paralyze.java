package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Paralyze extends CustomEnchantment {
    public Paralyze() {
        super("Paralyze", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 25.0;
        this.interval = 0.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = (level == 3) ? 0.05 : (0.0175 * level);
        if (random < chance) {
            playerHit.getLocation().getWorld().strikeLightningEffect(player.getLocation());
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, (int) ((level > 2) ? 1 : 0)));
            if (level == 4) {
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, (int) ((level > 2) ? 1 : 0)));
            }
            playerHit.damage((double) (1 + level));
        }
    }
}
